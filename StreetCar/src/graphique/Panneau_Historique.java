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
	
	private int borneGauche_Onglet, borneHaute_Onglet;
	private int dimensionOnglet;
	
	private int petitDecalageY = 20;
	private int ecart = 10;
	private int elargissementX = 15;
	
	private int largeurImage, hauteurImage;
	
	private int borneGauche_Bouton, borneDroite_Bouton;
	private int borneHaute_BoutonSuperieur, borneBasse_BoutonSuperieur;
	
	private int borneHaute_BoutonInferieur, borneBasse_BoutonInferieur;
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
	
	public int getBorneGauche_Bouton (){
		return borneGauche_Bouton;
	}
	public int getBorneDroite_Bouton (){
		return borneDroite_Bouton;
	}
	
	public int getBorneHaute_BoutonSuperieur (){
		return borneHaute_BoutonSuperieur;
	}
	public int getBorneBasse_BoutonSuperieur (){
		return borneBasse_BoutonSuperieur;
	}
	
	public int getBorneHaute_BoutonInferieur (){
		return borneHaute_BoutonInferieur;
	}
	public int getBorneBasse_BoutonInferieur (){
		return borneBasse_BoutonInferieur;
	}
	
	public int getBorneGauche_Onglet (){
		return borneGauche_Onglet;
	}
	public int getBorneHaute_Onglet (){
		return borneHaute_Onglet;
	}
	public int getDimensionOnglet (){
		return dimensionOnglet;
	}
	
	public int getCoordX (){
		return coordX;
	}
	public int getPetitDecalageY (){
		return petitDecalageY;
	}
	public int getEcart (){
		return ecart;
	}
	
	public int getLargeurImage (){
		return largeurImage;
	}
	public int getHauteurImage (){
		return hauteurImage;
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
		crayon.drawImage(boutonHaut, coordX - elargissementX, coordY, largeurImage + 2*elargissementX, hauteurImage, this);
		
		borneGauche_Bouton = coordX - elargissementX;
		borneDroite_Bouton = borneGauche_Bouton + largeurImage + 2*elargissementX;
		borneHaute_BoutonSuperieur = coordY;
		borneBasse_BoutonSuperieur = borneHaute_BoutonSuperieur + hauteurImage;
	}
	private void dessinerPartieCentrale (Graphics2D crayon){
		coordY = hauteur/diviseurDeDimension;
		hauteurImage = (diviseurDeDimension-2)*hauteur/diviseurDeDimension;
		crayon.drawImage(historiqueCentral, coordX - elargissementX, coordY, largeurImage + 2*elargissementX, hauteurImage, this);

		borneGauche_Onglet = coordX;
		borneHaute_Onglet = coordY + petitDecalageY;
		dimensionOnglet = largeurImage;
		
		int nbMaxOnglets = (hauteurImage - 2*petitDecalageY) / (dimensionOnglet + ecart);
		nbOngletsActifs = ( moteur.getHistorique().size() < nbMaxOnglets ) ? moteur.getHistorique().size() : nbMaxOnglets;
		
		moteur.getHistorique().setNbMaxOnglets(nbMaxOnglets);
		
		dessinerOnglet(crayon, nbOngletsActifs);
	}
	private void dessinerPartieBasse (Graphics2D crayon){
		coordY = (diviseurDeDimension-1)*hauteur/diviseurDeDimension;
		hauteurImage = hauteur/diviseurDeDimension;
		crayon.drawImage(boutonBas, coordX - elargissementX, coordY, largeurImage + 2*elargissementX, hauteurImage, this);
		
		borneHaute_BoutonInferieur = coordY;
		borneBasse_BoutonInferieur = borneHaute_BoutonInferieur + hauteurImage;
	}
	
	private void dessinerOnglet (Graphics2D crayon, int nombreMaxTour){
		int coordYOnglet = borneHaute_Onglet;
		for (int numTour = 0; numTour < nombreMaxTour; numTour++){
			
			crayon.setColor(Color.white);
			crayon.fillRect(borneGauche_Onglet, coordYOnglet, dimensionOnglet, dimensionOnglet);
			
			int posXString = borneGauche_Onglet + 2*dimensionOnglet/5;
			int posYString = coordYOnglet + 2*dimensionOnglet/3;
			
			int numTourActif = numTour + moteur.getHistorique().getNbConfigsPrecedentes();
			String chaineTour = "" + moteur.getHistorique().get(numTourActif).getNumeroTour();
			
			crayon.setColor(Color.black);
			crayon.drawString(chaineTour , posXString, posYString);
			
			coordYOnglet += dimensionOnglet + ecart;
			
			
		}
	}
	
	protected void initialiserImages (){
		boutonHaut = Constantes.Images.initBackground("histo_haut.png");
		historiqueCentral = Constantes.Images.initBackground("histo_centre.png");
		boutonBas = Constantes.Images.initBackground("histo_bas.png");
		
	}
}
