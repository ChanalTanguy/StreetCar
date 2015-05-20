package graphique;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Panneau extends JPanel{
	String name;
	Color couleur;
	
	//Ajout Mathieu
	int depart = 100;
	int tailleCase = 50;
	int ecart = 220;
	int nbCases = 12;
	String message = "X = 0, Y = 0";
	BufferedImage fond;
	BufferedImage plateau ;
	BufferedImage tuile001 ;
	BufferedImage tuile002 ;
	BufferedImage tuile003;
	BufferedImage tuile004 ;
	BufferedImage tuile005 ;
	BufferedImage tuile006 ;
	BufferedImage tuile007;
	BufferedImage tuile008 ;
	BufferedImage tuile009 ;
	BufferedImage tuile010 ;
	BufferedImage tuile011 ;
	public int main = -1;
	public int carte = -1;
	public int caseX = -1;
	public int caseY = -1;
	//Ajout Mathieu
		
	boolean zoneDeJeu;
	boolean contoursDessines;
		
	public Panneau (Color newCouleur, String newName){
		super();	
		name = newName;
		couleur = newCouleur;
		zoneDeJeu = false;
		contoursDessines = false;
		addMouseListener(new GestionSouris(this));
		initImage();
	}
	
	public Panneau (Color newCouleur, String newName, boolean panneauDeJeu){
		super();		
		name = newName;
		couleur = newCouleur;
		zoneDeJeu = panneauDeJeu;
		contoursDessines = false;
		addMouseListener(new GestionSouris(this));
		initImage();
	}
	
	public String getName (){
		return name;
	}
	
	public void paintComponent (Graphics g){
		Graphics2D crayon = (Graphics2D) g;
		
		int largeur = getSize().width;
		int hauteur = getSize().height;
		
		nettoyage(crayon);
		
		crayon.setColor(couleur);
		crayon.fillRect(0, 0, largeur, hauteur);
		
		if(zoneDeJeu)
		{
			dessinerFond(crayon);
			dessinerPlateau(crayon);
			dessinerMain1(crayon);
			dessinerMain2(crayon);
			
			if(main != -1){ colorMain(crayon); }
			if(caseX != -1){ colorCase(crayon); }
		}
		
		if (contoursDessines){
			crayon.setColor(Color.black);
			crayon.drawRect(0, 0, largeur-1, hauteur-1);
		}
		
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
		drawable.setColor(Color.white);
		drawable.drawRect(caseX*50+depart, caseY*50+depart, 50,50);		
		caseX = -1;
		drawable.setColor(Color.gray);	
	}

	private void colorMain(Graphics2D drawable) {
		drawable.setColor(Color.white);
		if(main == 2){ drawable.drawRect(carte*depart+ecart, 20, tailleCase+20, tailleCase+20); }
		else drawable.drawRect(carte*depart+ecart, 820, tailleCase+20, tailleCase+20);
		main = -1;
		drawable.setColor(Color.gray);		
	}

	private void dessinerPlateau(Graphics2D drawable) {
		drawable.drawImage(plateau, depart, depart, depart+12*tailleCase,depart+12*tailleCase, this);
		
	}

	private void dessinerFond(Graphics2D drawable) {
		drawable.drawImage(fond, 0, 0, this.getWidth(), this.getHeight(), this);
		
	}

	private void dessinerMain1(Graphics2D drawable) {
		//Attention, ici c'est toujours la main de base, il faut remplacer par la main du joueur 1
		for(int i = 0;i<5;i++)
		{
			//drawable.drawRect(i*depart+ecart, 820, tailleCase+20, tailleCase+20);
			if(i == 0 || i == 1 || i == 2) drawable.drawImage(tuile001, i*depart+ecart, 820, tailleCase+20, tailleCase+20, this);
			else drawable.drawImage(tuile002, i*depart+ecart, 820, tailleCase+20, tailleCase+20, this);
		}
	}

	private void dessinerMain2(Graphics2D drawable) {
		//Attention, ici c'est toujours la main de base, il faut remplacer par la main du joueur 2
		for(int i = 0;i<5;i++)
		{
			//drawable.drawRect(i*depart+ecart, 20, tailleCase+20, tailleCase+20);
			if(i == 0 || i == 1 || i == 2) drawable.drawImage(tuile001, i*depart+ecart, 20, tailleCase+20, tailleCase+20, this);
			else drawable.drawImage(tuile002, i*depart+ecart, 20, tailleCase+20, tailleCase+20, this);
		}
	}
	
	private void initImage() {
		try {		
			//Chargement des images
			fond = ImageIO.read(new File("images/background/tram.png"));
			plateau = ImageIO.read(new File("images/background/plateau.png"));
			tuile001 = ImageIO.read(new File("images/tuiles/001.jpg"));
			tuile002 = ImageIO.read(new File("images/tuiles/002.jpg"));
			tuile003 = ImageIO.read(new File("images/tuiles/003.jpg"));
			tuile004 = ImageIO.read(new File("images/tuiles/004.jpg"));
			tuile005 = ImageIO.read(new File("images/tuiles/005.jpg"));
			tuile006 = ImageIO.read(new File("images/tuiles/006.jpg"));
			tuile007 = ImageIO.read(new File("images/tuiles/007.jpg"));
			tuile008 = ImageIO.read(new File("images/tuiles/008.jpg"));
			tuile009 = ImageIO.read(new File("images/tuiles/009.jpg"));
			tuile010 = ImageIO.read(new File("images/tuiles/010.jpg"));
			tuile011 = ImageIO.read(new File("images/tuiles/011.jpg"));
		}
		catch (IOException e) { e.printStackTrace();}	
		
	}
	
}
