package graphique;

import historiqPackage.Historique;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import mainPackage.Moteur;
import constantesPackages.Constantes;

public class Panneau_Historique extends Pan_Abstract{
	BufferedImage boutonHaut, boutonBas, historiqueCentral;
	Historique historiqueDeConfigs;
	
	// Variable permettant de separer le Panneau en 3 parties pour les 3 images;
	private int diviseurDeDimension;
	private int encadrerZone;
	
	/*
	 * Attributs d'Entier de positionnement
	 */
	private int coordX, coordY;
	private int coordXOnglet, coordYOnglet;
	private int dimensionOnglet;
	private int petitDecalageX = 1;
	private int petitDecalageY = 10;
	private int ecart = 10;
	private int largeurImage, hauteurImage;
	/*
	 * FIN Attributs
	 */
	
	Moteur mot;
	
	public Panneau_Historique (Color newCouleur, Moteur referenceMoteur){
		super(newCouleur);
		mot = referenceMoteur;
		encadrerZone = 0;
		initialiserImages();
		Ecouteur_Historique ecouteur = new Ecouteur_Historique(this);
		addMouseListener(ecouteur);
		addMouseMotionListener(ecouteur);
	}
	
	/*
	 * ACCESSEURS
	 */
	public int getDiviseur (){
		return diviseurDeDimension;
	}
	public int getZoneEncadree (){
		return encadrerZone;
	}
	
	public void setEncadrer (int newZone){
		encadrerZone = newZone;
	}
	/*
	 * FIN ACCESSEURS
	 */
	
	public void paintComponent (Graphics g){
		crayon = (Graphics2D) g;
		largeur = getSize().width;
		hauteur = getSize().height;
		
		crayon.setColor(couleur);
		crayon.fillRect(0, 0, largeur, hauteur);

		coordX = largeur/3;
		largeurImage = largeur/3;
		diviseurDeDimension = 9;
		
		dessinerPartieHaute(crayon);
		dessinerPartieCentrale(crayon);
		dessinerPartieBasse(crayon);
		
		if (contoursSurlignes){
			crayon.setColor(Color.red);
			crayon.drawRect(0, 0, largeur-1, hauteur-1);
		}
		if (encadrerZone > 0){
			activerCadreZone(crayon);
		}
	}
	
	/*
	 * Methodes Private de Panneau_Historique
	 */
	private void activerCadreZone (Graphics2D crayon){
		crayon.setColor(Color.white);
		switch (encadrerZone){
		case 1:
			crayon.drawRect(1, 1, largeur-1, hauteur/diviseurDeDimension);
			break;
		case 2:
			crayon.drawRect(1, hauteur/diviseurDeDimension, largeur-1, (diviseurDeDimension-2)*hauteur/diviseurDeDimension);
			break;
		case 3:
			crayon.drawRect(1, (diviseurDeDimension-1)*hauteur/diviseurDeDimension, largeur-1, hauteur/diviseurDeDimension);
			break;
		}
		repaint();
	}
	private void dessinerPartieHaute (Graphics2D crayon){
		coordY = 0;
		hauteurImage = hauteur/diviseurDeDimension;
		crayon.drawImage(boutonHaut, coordX, coordY, largeurImage, hauteurImage, this);
	}
	private void dessinerPartieCentrale (Graphics2D crayon){
		coordY = hauteur/diviseurDeDimension;
		hauteurImage = (diviseurDeDimension-2)*hauteur/diviseurDeDimension;
		crayon.drawImage(historiqueCentral, coordX-10, coordY, largeurImage+2*10, hauteurImage, this);

		coordXOnglet = coordX + petitDecalageX;
		coordYOnglet = coordY + petitDecalageY;
		dimensionOnglet = largeurImage - 2*petitDecalageX;
		int compteur = (hauteurImage - 2*petitDecalageY) / (dimensionOnglet + ecart);
		
		System.out.println();
		System.out.println("largeur/hauteur dispo: " + dimensionOnglet + " " + (hauteurImage - 2*petitDecalageY));
		System.out.println("dimensionOnglet : " + dimensionOnglet);
		System.out.println("compteur : " + compteur);
		System.out.println();
		
		dessinerOnglet(crayon, compteur);
		
	}
	private void dessinerPartieBasse (Graphics2D crayon){
		coordY = (diviseurDeDimension-1)*hauteur/diviseurDeDimension;
		hauteurImage = hauteur/diviseurDeDimension;
		crayon.drawImage(boutonBas, coordX, coordY, largeurImage, hauteurImage, this);
	}
	
	private void dessinerOnglet (Graphics2D crayon, int nombreOnglet){
		crayon.setColor(Color.white);
		for (int numOnglet = 0; numOnglet < nombreOnglet; numOnglet++){
			crayon.fillRect(coordXOnglet, coordYOnglet, dimensionOnglet, dimensionOnglet);
			coordYOnglet += dimensionOnglet + petitDecalageY;
		}
	}
	
	protected void initialiserImages (){
		boutonHaut = Constantes.Images.initBackground("histo_haut.png");
		historiqueCentral = Constantes.Images.initBackground("histo_centre.png");
		boutonBas = Constantes.Images.initBackground("histo_bas.png");
		
	}
}
