package graphique;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import constantesPackages.Constantes;

public class Panneau_Boutons extends Pan_Abstract{
	private BufferedImage undo, conseils, assistance, menu;
	private Point centreUndo, centreConseils, centreAssistance, centreMenu;
	private int rayon;
	private int dimBouton;
	private int axeHorizontalHaut, axeHorizontalBas;
	private int petitDecalage = 10;
	private JFrame mainWindow;
	
	Ecouteur_Boutons ecouteur;
	
	public Panneau_Boutons (JFrame mainWindow, Color newCouleur, Panneau_Plateau referencePanneauDeJeu){
		super(newCouleur); 
		this.mainWindow = mainWindow;
		ecouteur = new Ecouteur_Boutons(this, this.mainWindow, referencePanneauDeJeu);
		addMouseListener(ecouteur);
		addMouseMotionListener(ecouteur);
		initialiserImages();
	}
	
	public Point getCentreUndo (){
		return centreUndo;
	}
	public Point getCentreConseils (){
		return centreConseils;
	}
	public Point getCentreAssistance (){
		return centreAssistance;
	}
	public Point getCentreMenu (){
		return centreMenu;
	}
	public int getDimBouton (){
		return dimBouton;
	}
	public int getPetitDecalage (){
		return petitDecalage;
	}
	
	public void paintComponent (Graphics g){
		crayon = (Graphics2D) g;
		largeur = getSize().width;
		hauteur = getSize().height;
		
		crayon.setColor(couleur);
		crayon.fillRect(0, 0, largeur, hauteur);
		
		rayon = (largeur < hauteur) ? largeur/6 : hauteur/6;
		dimBouton = 3*rayon;
		
		ecouteur.setRayon(dimBouton/2);
		
		axeHorizontalHaut = hauteur/4;
		axeHorizontalBas = 2*hauteur/3;
		initialiserCentresBoutons();
		
		dessinerUndo(crayon, rayon);
		dessinerConseils(crayon, rayon);
		dessinerAssistance(crayon, rayon);
		dessinerMenu(crayon, rayon);
		
	}
	
	/*
	 * Methodes Public pour changer l'aspect des boutons
	 */
	//change le bouton undo
	public void changeImageUndo(String nomImage){
		undo = Constantes.Images.initBouton(nomImage);
	}
	//change le bouton conseil
	public void changeImageConseil(String nomImage){
		conseils = Constantes.Images.initBouton(nomImage);
	}
	//change le bouton aide
	public void changeImageAssistance(String nomImage){
		assistance = Constantes.Images.initBouton(nomImage);
	}
	//change le bouton menu
	public void changeImageMenu(String nomImage){
		menu = Constantes.Images.initBouton(nomImage);
	}
	
	/*
	 * Methodes Private pour dessiner les 4 boutons : undo, conseils, assistance, menu
	 */
	private void dessinerUndo (Graphics2D crayon, int rayon){
		crayon.setColor(Color.black);
		crayon.drawImage(undo, centreUndo.x-dimBouton/2, centreUndo.y-dimBouton/2, dimBouton, dimBouton, this);
	}
	private void dessinerConseils (Graphics2D crayon, int rayon){
		crayon.setColor(Color.black);
		crayon.drawImage(conseils, centreConseils.x-dimBouton/2, centreConseils.y-dimBouton/2, dimBouton, dimBouton, this);
	}	
	private void dessinerAssistance (Graphics2D crayon, int rayon){
		crayon.setColor(Color.black);
		crayon.drawImage(assistance, centreAssistance.x-dimBouton/2, centreAssistance.y-dimBouton/2, dimBouton, dimBouton, this);
	}
	private void dessinerMenu (Graphics2D crayon, int rayon){
		crayon.setColor(Color.black);
		crayon.drawImage(menu, centreMenu.x-dimBouton/2, centreMenu.y-dimBouton/2, dimBouton, dimBouton, this);
	}

	
	/*
	 * Methode Private d'initialisation d'image
	 */
	protected void initialiserImages (){
		undo = Constantes.Images.initBouton("bouton_undo.png");
		conseils = Constantes.Images.initBouton("bouton_conseil.png");
		assistance = Constantes.Images.initBouton("bouton_aide.png");
		menu = Constantes.Images.initBouton("bouton_menu.png");
	}
	
	private void initialiserCentresBoutons (){
		centreUndo = new Point( largeur/3-petitDecalage, axeHorizontalHaut );
		centreConseils = new Point( centreUndo.x + dimBouton, axeHorizontalHaut );
		centreAssistance = new Point( largeur/3+petitDecalage, axeHorizontalBas );
		centreMenu = new Point( centreAssistance.x + dimBouton, axeHorizontalBas );
	}
}
