package graphique;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
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
	
	/*
	 * AJOUT Kevin
	 */
	JTextArea message;
	int largeur, hauteur;
	Point2D menuBoutonsurligne = null;
	/*
	 * FIN AJOUT Kevin
	 */
	
	/*
	 * AJOUT Mathieu
	 */
	int depart = 100;
	int tailleCase = 50;
	int ecart = 220;
	int nbCases = 12;
	BufferedImage fond;
	BufferedImage plateau ;
	BufferedImage tuile001;
	BufferedImage tuile002;
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
	BufferedImage rotate ;
	BufferedImage histogramme_haut;
	BufferedImage histogramme_centre;
	BufferedImage histogramme_bas;
	BufferedImage bouton_Undo;
	BufferedImage bouton_Conseil;
	BufferedImage bouton_Aide;
	BufferedImage bouton_Menu;
	public int main = -1;
	public int carte = -1;
	public int caseX = -1;
	public int caseY = -1;
	Moteur mot;
	/*
	 * FIN AJOUT Mathieu
	 */
		
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
		EcouteTerrain ecouteur = new EcouteTerrain(this);
		addMouseListener(ecouteur);
		if (numeroDeZone == Constantes.Panneau.notifications){
			message = new JTextArea("Notifications");
			message.setEditable(false);
			add(message);
		}
		else if (numeroDeZone == Constantes.Panneau.menuBoutons){
			addMouseMotionListener(ecouteur);
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
		initImage();
	}
	
	public String getName (){
		return name;
	}
	
	public int getTypeZone (){
		return typeDeZone;
	}
	
	public void setBoutonSurligne (Point2D bouton){
		menuBoutonsurligne = bouton;
	}
	
	/*
	 * Methode paintComponent de Panneau.java
	 */
	public void paintComponent (Graphics g){
		Graphics2D crayon = (Graphics2D) g;
		
		largeur = getSize().width;
		hauteur = getSize().height;
		
		nettoyage(crayon);
		
		crayon.setColor(couleur);
		crayon.fillRect(0, 0, largeur, hauteur);
		
		if(typeDeZone == Constantes.Panneau.plateau)
		{
			dessinerFond(crayon);
			dessinerPlateau(crayon);
			dessinerPioche(crayon);
			
			dessinerContenuPlateau(crayon, mot.getPlateau());
			
			dessinerMain1(crayon, mot.getTabPlayers()[0].getMain());
			dessinerMain2(crayon, mot.getTabPlayers()[1].getMain());
					
			if(main != -1){ colorMain(crayon); }
			if(caseX != -1){ colorCase(crayon); }
			if(piocher) { colorPioche(crayon);}
		}
		else if (typeDeZone == Constantes.Panneau.menuBoutons){

			int rayon = (largeur < hauteur ) ? largeur/8 : hauteur/8;
			dispositionCarre(crayon, rayon);

//			dispositionLosange(crayon, rayon);
			
			if (menuBoutonsurligne != null){
				surlignerBouton(crayon, menuBoutonsurligne, rayon);
			}
		}
		else if (typeDeZone == Constantes.Panneau.histo_central){
			dessinerHistorique(crayon);
		}
		else if (typeDeZone == Constantes.Panneau.histo_bouton_nord){
			dessinerBoutonHaut(crayon);
		}
		else if (typeDeZone == Constantes.Panneau.histo_bouton_sud){
			dessinerBoutonBas(crayon);
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
	
	public void activerContours() {
		contoursDessines = true;
		repaint();
	}
	
	public void desactiverContours() {
		contoursDessines = false;
		repaint();
	}
	
	
	
	//change le bouton undo
	public void changeImageUndo(String nomImage){
		bouton_Undo = Constantes.Images.initBouton(nomImage);
	}
	
	//change le bouton conseil
	public void changeImageConseil(String nomImage){
		bouton_Conseil = Constantes.Images.initBouton(nomImage);
	}
	
	//change le bouton aide
	public void changeImageAide(String nomImage){
		bouton_Aide = Constantes.Images.initBouton(nomImage);
	}
		
	//change le bouton menu
	public void changeImageMenu(String nomImage){
		bouton_Menu = Constantes.Images.initBouton(nomImage);
	}
	
	/*
	 * Methodes Privates de Panneau.java
	 */
	
	private void colorPioche(Graphics2D drawable) {
		if(mot.getPioche().isEmpty())
		{
			drawable.setColor(Color.white);
			drawable.fillRect(3*depart+2*ecart-1, 820-1, tailleCase+21, tailleCase+21);
		}
		else
		{
			drawable.setColor(Color.white);
			drawable.drawRect(3*depart+2*ecart-1, 820-1, tailleCase+21, tailleCase+21);
			piocher = false;
		}
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
		if(main == 2){ 
			drawable.drawRect(carte*depart+ecart, 20, tailleCase+20, tailleCase+20); 
			//drawable.drawImage(rotate, carte*depart+ecart-10, 20-10, 20, 20, this);
		}
		else 
		{
			drawable.drawRect(carte*depart+ecart, 820, tailleCase+20, tailleCase+20);
			//drawable.drawImage(rotate, carte*depart+ecart-10, 820-10, 20, 20, this);
		}
		
	}

	private void dessinerPlateau(Graphics2D drawable) {
		drawable.drawImage(plateau, depart, depart, depart+12*tailleCase,depart+12*tailleCase, this);
		
	}

	private void dessinerFond(Graphics2D drawable) {
		drawable.drawImage(fond, 0, 0, this.getWidth(), this.getHeight(), this);
		
	}

	
	/*
	 * Methodes pour dessiner la main de chaque joueur
	 */
	private void dessinerMain1(Graphics2D drawable, MainJoueur main) {
		for(int i = 0;i<5;i++)
		{
			Tuile t = main.getTuileAt(i);
			
			if (t != null)
			{
				BufferedImage img = t.getImage();
				String orient = t.getOrientation();
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
				rotation(drawable,img,angle,i*depart+ecart, 820, tailleCase+20);
			}		
		}
	}

	private void dessinerMain2(Graphics2D drawable, MainJoueur main) {
		for(int i = 0;i<5;i++)
		{
			Tuile t = main.getTuileAt(i);
			
			if (t != null)
			{
				BufferedImage img = t.getImage();
				String orient = t.getOrientation();
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
				
				rotation(drawable,img,angle,i*depart+ecart, 20, tailleCase+20);
			}
		}
	}
	
	/*
	 * Methodes pour dessiner les 4 menuBoutons : Undo, Conseils, Help, Menu
	 * selon 2 dispositions possibles
	 */
	private void dispositionCarre (Graphics2D crayon, int rayon){
		dessinerUndoCar(crayon, rayon);
		dessinerConseilsCar(crayon, rayon);
		dessinerHelpCar(crayon, rayon);
		dessinerMenuCar(crayon, rayon);
	}
	
	private void dessinerUndoCar (Graphics2D crayon, int rayon){
		crayon.setColor(Color.black);
		crayon.drawImage(bouton_Undo, largeur/4-rayon, hauteur/30, 3*rayon, 3*rayon, this);
	}

	
	private void dessinerConseilsCar (Graphics2D crayon, int rayon){
		crayon.setColor(Color.black);
		crayon.drawImage(bouton_Conseil, (largeur/4) + (2*rayon), hauteur/30, 3*rayon, 3*rayon, this);
	}
	
	private void dessinerHelpCar (Graphics2D crayon, int rayon){
		crayon.setColor(Color.black);
		crayon.drawImage(bouton_Aide, largeur/3-rayon, hauteur/3, 3*rayon, 3*rayon, this);
	}
	
	private void dessinerMenuCar (Graphics2D crayon, int rayon){
		crayon.setColor(Color.black);
		crayon.drawImage(bouton_Menu, largeur/3 + 2*rayon, hauteur/3, 3*rayon, 3*rayon, this);
	}

/*
	private void dispositionLosange (Graphics2D crayon, int rayon){
		dessinerUndoLos(crayon, rayon);
		dessinerConseilsLos(crayon, rayon);
		dessinerHelpLos(crayon, rayon);
		dessinerMenuLos(crayon, rayon);
	}
	private void dessinerUndoLos (Graphics2D crayon, int rayon){
		crayon.setColor(Color.black);
		crayon.drawOval(largeur/2-rayon, hauteur/6, 2*rayon, 2*rayon);
		crayon.drawImage(Constantes.Images.initBouton("bouton_undo.png"), largeur/6, hauteur/2-rayon, 2*rayon, 2*rayon, this);
	}
	private void dessinerConseilsLos (Graphics2D crayon, int rayon){
		crayon.setColor(Color.black);
		crayon.drawOval(largeur/6, hauteur/2-rayon, 2*rayon, 2*rayon);
		crayon.drawImage(Constantes.Images.initBouton("bouton_conseil.png"), largeur/2-rayon, hauteur/6, 2*rayon, 2*rayon, this);
	}
	private void dessinerHelpLos (Graphics2D crayon, int rayon){
		crayon.setColor(Color.black);
		crayon.drawOval((5*largeur/6)-(2*rayon), hauteur/2-rayon, 2*rayon, 2*rayon);
		crayon.drawImage(Constantes.Images.initBouton("bouton_aide.png"), (5*largeur/6)-(2*rayon), hauteur/2-rayon, 2*rayon, 2*rayon, this);
	}
	private void dessinerMenuLos (Graphics2D crayon, int rayon){
		crayon.setColor(Color.black);
		crayon.drawOval(largeur/2-rayon, (5*hauteur/6)-(2*rayon), 2*rayon, 2*rayon);
		crayon.drawImage(Constantes.Images.initBouton("bouton_menu.png"), largeur/2-rayon, (5*hauteur/6)-(2*rayon), 2*rayon, 2*rayon, this);
	}
*/
	
	/*
	 * Methodes pour mettre en surbrillance le bouton sur
	 * lequel le curseur se trouve s'il y en a un.
	 */
	private void surlignerBouton (Graphics2D crayon, Point2D centreBouton, int rayon){
		crayon.setColor(Color.white);
		//crayon.drawOval((int)centreBouton.getX()-rayon, (int)centreBouton.getY()-rayon, 2*rayon, 2*rayon);
	}
	
	/*
	 * Methodes pour dessiner les boutons du haut, du bas et le centre pour l'historique de navigation
	 */
	private void dessinerBoutonHaut (Graphics2D crayon){
		crayon.drawImage(histogramme_haut, 0, 0, largeur, hauteur, this);
	}
	
	private void dessinerBoutonBas (Graphics2D crayon){
		crayon.drawImage(histogramme_bas, 0, 0, largeur, hauteur, this);
	}
	
	private void dessinerHistorique (Graphics2D crayon){
		crayon.drawImage(histogramme_centre, 0, 0, largeur, hauteur, this);
	}
	
	/*
	 * Methodes d'init des Images
	 */
	private void initImage (){
		//Chargement des images
		fond = Constantes.Images.initBackground("tramOui.png");
		plateau = Constantes.Images.initBackground("plateau.png");
		pioche = Constantes.Images.initBackground("pioche.png");
		rotate = Constantes.Images.initBouton("tourner.png");
		tuile001 = Constantes.Images.initTuile("ligneDroite.jpg");
		tuile002 = Constantes.Images.initTuile("virage.jpg");
		tuile003 = Constantes.Images.initTuile("bifurcationDroite.jpg");
		tuile004 = Constantes.Images.initTuile("bifurcationGauche.jpg");
		tuile005 = Constantes.Images.initTuile("separation.jpg");
		tuile006 = Constantes.Images.initTuile("doubleVirage.jpg");
		tuile007 = Constantes.Images.initTuile("doubleBifurcation.jpg");
		tuile008 = Constantes.Images.initTuile("croisement.jpg");
		tuile009 = Constantes.Images.initTuile("bifurcationSepareGauche.jpg");
		tuile010 = Constantes.Images.initTuile("bifurcationSepareDroite.jpg");
		tuile011 = Constantes.Images.initTuile("bifurcationsEmbrassees.jpg");
		histogramme_haut = Constantes.Images.initBackground("histo_haut.png");
		histogramme_centre = Constantes.Images.initBackground("histo_centre.png");
		histogramme_bas = Constantes.Images.initBackground("histo_bas.png");
		bouton_Undo = Constantes.Images.initBouton("bouton_undo.png");
		bouton_Conseil = Constantes.Images.initBouton("bouton_conseil.png");
		bouton_Aide = Constantes.Images.initBouton("bouton_aide.png");
		bouton_Menu = Constantes.Images.initBouton("bouton_menu.png");
		
	}
	
}
