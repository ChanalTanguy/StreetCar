package graphique;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import mainPackage.Moteur;
import constantesPackages.Constantes;

public class Panneau_Historique extends Pan_Abstract{
	private BufferedImage boutonHaut, boutonBas, historiqueCentral;
	
	// Variable permettant de separer le Panneau en 3 parties pour les 3 images;
	private int diviseurDeDimension;
	private int encadrerZone;
	
	/*
	 * Attributs d'Entiers de positionnement
	 */
	private int coordX, coordY;
	
	private int coordXOnglet, coordYOnglet;
	private int dimensionOnglet;
	
	private int petitDecalageY = 20;
	private int ecart = 10;
	private int elargissementX = 15;
	
	private int largeurImage, hauteurImage;
	/*
	 * FIN Attributs d'Entiers de positionnement
	 */
	
	private int nbOngletsActifs;
	private boolean retourConfirme;
	
	/*
	 * FIN Attributs
	 */
	
	private Moteur moteur;
	
	public Panneau_Historique (Color newCouleur, Moteur referenceMoteur){
		super(newCouleur);
		moteur = referenceMoteur;
		encadrerZone = 0;
		initialiserImages();
		Ecouteur_Historique ecouteur = new Ecouteur_Historique(this, moteur);
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
	public boolean getRetourConfirme (){
		return retourConfirme;
	}
	public int getCoordXOnglet (){
		return coordXOnglet;
	}
	public int getCoordYOnglet (){
		return coordYOnglet;
	}
	public int getPetitDecalageY (){
		return petitDecalageY;
	}
	public int getEcart (){
		return ecart;
	}
	public int getDimensionOnglet (){
		return dimensionOnglet;
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
	 * Methodes Public de Panneau_Historique
	 */
	/**
	 * Methode ouvrant une pop-up de confirmation pour naviguer vers un autre tour
	 */
	public void demandeConfirmation (){
		
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
		crayon.drawImage(historiqueCentral, coordX - elargissementX, coordY, largeurImage + 2*elargissementX, hauteurImage, this);

		coordXOnglet = coordX;
		coordYOnglet = coordY + petitDecalageY;
		dimensionOnglet = largeurImage;
		
		int nbMaxOnglets = (hauteurImage - 2*petitDecalageY) / (dimensionOnglet + ecart);
		nbOngletsActifs = ( moteur.getHistorique().size() < nbMaxOnglets ) ? moteur.getHistorique().size() : nbMaxOnglets;
		
		moteur.getHistorique().setNbMaxOnglets(nbMaxOnglets);
		
		dessinerOnglet(crayon, nbOngletsActifs);
	}
	private void dessinerPartieBasse (Graphics2D crayon){
		coordY = (diviseurDeDimension-1)*hauteur/diviseurDeDimension;
		hauteurImage = hauteur/diviseurDeDimension;
		crayon.drawImage(boutonBas, coordX, coordY, largeurImage, hauteurImage, this);
	}
	
	private void dessinerOnglet (Graphics2D crayon, int nombreOnglet){
		for (int numOnglet = 0; numOnglet < nombreOnglet; numOnglet++){
			crayon.setColor(Color.white);
			crayon.fillRect(coordXOnglet, coordYOnglet, dimensionOnglet, dimensionOnglet);
			
			int posXString = coordXOnglet + 2*dimensionOnglet/5;
			int posYString = coordYOnglet + 2*dimensionOnglet/3;
			String numTour = "" + (numOnglet+moteur.getHistorique().getNbConfigsPrecedentes());
			crayon.setColor(Color.black);
			crayon.drawString(numTour , posXString, posYString);
			
			coordYOnglet += dimensionOnglet + ecart;
			
			
		}
	}
	
	protected void initialiserImages (){
		boutonHaut = Constantes.Images.initBackground("histo_haut.png");
		historiqueCentral = Constantes.Images.initBackground("histo_centre.png");
		boutonBas = Constantes.Images.initBackground("histo_bas.png");
		
	}
}
