package graphique;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import joueurPackage.MainJoueur;
import mainPackage.Moteur;
import mainPackage.Plateau;
import tuilePackage.Tuile;
import constantesPackages.Constantes;

public class Panneau extends JPanel{
	String name;
	Color couleur;
	JTextArea message;
	MainJoueur mainJoueur1, mainJoueur2;
	
	//Ajout Mathieu
	int depart = 100;
	int tailleCase = 50;
	int ecart = 220;
	int nbCases = 12;
	BufferedImage fond;
	BufferedImage plateau ;
	BufferedImage tuile003;
	BufferedImage tuile004 ;
	BufferedImage tuile005 ;
	BufferedImage tuile006 ;
	BufferedImage tuile007;
	BufferedImage tuile008 ;
	BufferedImage tuile009 ;
	BufferedImage tuile010 ;
	BufferedImage tuile011 ;
	BufferedImage pioche ;
	public int main = -1;
	public int carte = -1;
	public int caseX = -1;
	public int caseY = -1;
	Moteur mot;
	//Ajout Mathieu
		
	int typeDeZone;
	boolean contoursDessines;
	public boolean piocher = false;
		
	public Panneau (Color newCouleur, String newName){
		super();	
		name = newName;
		couleur = newCouleur;
		contoursDessines = false;
		addMouseListener(new EcouteTerrain(this));
		initImage();
	}
	
	public Panneau (Color newCouleur, String newName, int numeroDeZone){
		super();		
		name = newName;
		couleur = newCouleur;
		typeDeZone = numeroDeZone;
		contoursDessines = false;
		if (numeroDeZone == Constantes.Panneau.notifications){
			message = new JTextArea("Notifications");
			message.setEditable(false);
			add(message);
		}
		//On est sensé créer un mouse listener par joueur, à modifier
		initImage();
	}
	
	public Panneau (Color newCouleur, String newName, int numeroDeZone, Moteur m){
		super();		
		name = newName;
		couleur = newCouleur;
		typeDeZone = numeroDeZone;
		mot = m;
		contoursDessines = false;
		//On est sensé créer un mouse listener par joueur, à modifier
		addMouseListener(new EcouteTerrain(this, m));
		mainJoueur1 = new MainJoueur();
		mainJoueur2 = new MainJoueur();
		initImage();
	}
	
	public String getName (){
		return name;
	}
	
	public int getTypeZone (){
		return typeDeZone;
	}
	
	/*
	 * Methode paintComponent de Panneau.java
	 */
	public void paintComponent (Graphics g){
		Graphics2D crayon = (Graphics2D) g;
		
		int largeur = getSize().width;
		int hauteur = getSize().height;
		
		nettoyage(crayon);
		
		crayon.setColor(couleur);
		crayon.fillRect(0, 0, largeur, hauteur);
		
		if(typeDeZone == Constantes.Panneau.plateau)
		{
			dessinerFond(crayon);
			dessinerPlateau(crayon);
			dessinerPioche(crayon);
			
			dessinerContenuPlateau(crayon, mot.getPlateau());
			
			dessinerMain1(crayon, mainJoueur1);
			dessinerMain2(crayon, mainJoueur2);
			
			
			//Visuel des cases
			//crayon.drawImage(tuile001, depart+5*tailleCase+1, depart+tailleCase*3+1, tailleCase-1, tailleCase-1, this);
			//crayon.drawImage(tuile001, depart+5*tailleCase+1, depart+tailleCase*4+1, tailleCase-1, tailleCase-1, this);
			
			if(main != -1){ colorMain(crayon); }
			if(caseX != -1){ colorCase(crayon); }
			if(piocher) { colorPioche(crayon);}
		}
		
		if (contoursDessines){
			crayon.setColor(Color.black);
			crayon.drawRect(0, 0, largeur-1, hauteur-1);
		}
		
	}
	
	/*
	 * Methodes Public de Panneau.java
	 */
	
	public void updateMessage (String newNotif){
		message.setText(newNotif);
	}
	
	
	/*
	 * Methodes Privates de Panneau.java
	 */
	
	private void colorPioche(Graphics2D drawable) {
		drawable.setColor(Color.white);
		drawable.drawRect(3*depart+2*ecart-1, 820-1, tailleCase+21, tailleCase+21);
		piocher = false;
	}

	private void dessinerPioche(Graphics2D drawable) {	
		drawable.drawImage(pioche, 3*depart+2*ecart, 820, tailleCase+20, tailleCase+20, this);
	}

	private void dessinerContenuPlateau(Graphics2D crayon, Plateau plateau) {
		for(int i = 0; i<13; i++)
		{
			for(int j = 0; j<13; j++)
			{
				if(plateau.getTuileAt(i, j) != null)
				{
					dessinerTuile(crayon,plateau.getTuileAt(i, j), i, j);
				}
			}
		}
		
	}

	private void dessinerTuile(Graphics2D drawable, Tuile tuileAt, int x, int y) {
		BufferedImage img = tuileAt.getImage();
		String orient = tuileAt.getOrientation();
		int angle;
			
		switch(orient)
		{
			case(Constantes.Orientation.nord) :
				angle = 0;
				break;
			case(Constantes.Orientation.sud) :
				angle = 180;
				break;
			case(Constantes.Orientation.est) :
				angle = 90;
				break;
			case(Constantes.Orientation.ouest) :
				angle = 270;
				break;
			default :
				angle = 0;
				break;
		}
		
		if(img != null)
		{
			rotation(drawable,img,angle,depart+x*tailleCase+1,depart+y*tailleCase+1,tailleCase-1);
		}
		
	}

	private void rotation(Graphics2D drawable, BufferedImage img, int angle, int x, int y, int taille)
	{
		double rotationRequired = Math.toRadians(angle);
		double locationX = img.getWidth() / 2;
		double locationY = img.getHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		
		drawable.drawImage(op.filter(img,null), x, y, taille, taille, this);		
	}

	public void activerContours() {
		contoursDessines = true;
		repaint();
	}
	
	public void desactiverContours() {
		contoursDessines = false;
		repaint();
	}
	
	private void nettoyage(Graphics2D drawable) {
		drawable.setColor(Color.white);
		drawable.fillRect(0, 0, getWidth(), getHeight());
	}

	private void colorCase(Graphics2D drawable) {
		main = -1;
		drawable.setColor(Color.white);
		drawable.drawRect(caseX*50+depart, caseY*50+depart, 50,50);		

	}

	private void colorMain(Graphics2D drawable) {
		caseX = -1;
		drawable.setColor(Color.white);
		if(main == 2){ drawable.drawRect(carte*depart+ecart, 20, tailleCase+20, tailleCase+20); }
		else drawable.drawRect(carte*depart+ecart, 820, tailleCase+20, tailleCase+20);
		
	}

	private void dessinerPlateau(Graphics2D drawable) {
		drawable.drawImage(plateau, depart, depart, depart+12*tailleCase,depart+12*tailleCase, this);
		
	}

	private void dessinerFond(Graphics2D drawable) {
		drawable.drawImage(fond, 0, 0, this.getWidth(), this.getHeight(), this);
		
	}

	private void dessinerMain1(Graphics2D drawable, MainJoueur main) {
		//Attention, ici c'est toujours la main de base, il faut remplacer par la main du joueur 1
		for(int i = 0;i<5;i++)
		{
			drawable.drawImage(main.getTuileAt(i).getImage(), i*depart+ecart, 820, tailleCase+20, tailleCase+20, this);
		}
	}

	private void dessinerMain2(Graphics2D drawable, MainJoueur main) {
		//Attention, ici c'est toujours la main de base, il faut remplacer par la main du joueur 2
		for(int i = 0;i<5;i++)
		{
			drawable.drawImage(main.getTuileAt(i).getImage(), i*depart+ecart, 20, tailleCase+20, tailleCase+20, this);
		}
	}
	
	private void initImage (){
		//Chargement des images
		fond = Constantes.Images.initBackground("tramOui.png");
		plateau = Constantes.Images.initBackground("plateau.png");
		pioche = Constantes.Images.initBackground("pioche.png");
		tuile003 = Constantes.Images.initTuile("bifurcationDroite.jpg");
		tuile004 = Constantes.Images.initTuile("bifurcationGauche.jpg");
		tuile005 = Constantes.Images.initTuile("separation.jpg");
		tuile006 = Constantes.Images.initTuile("doubleVirage.jpg");
		tuile007 = Constantes.Images.initTuile("doubleBifurcation.jpg");
		tuile008 = Constantes.Images.initTuile("croisement.jpg");
		tuile009 = Constantes.Images.initTuile("bifurcationSeparesGauche.jpg");
		tuile010 = Constantes.Images.initTuile("bifurcationsSeparesDroite.jpg");
		tuile011 = Constantes.Images.initTuile("bifurcationEmbrassees.jpg");
	}
	
}
