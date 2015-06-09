package graphique;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import mainPackage.Moteur;
import constantesPackages.Constantes;

public class Panneau_Historique extends Pan_Abstract{
	private BufferedImage defilementHaut, defilementBas, historiqueCentral, ongletHistorique;
	private BufferedImage surbrillanceSelectionnee, surbrillanceCourante;
	
	// Variable permettant de separer le Panneau en 3 parties pour les 3 images;
	private int diviseurDeDimension;
	
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
	private int numeroTourCourant;
	private int numeroTourSelectionne;
	private boolean autorisationInteraction;
	
	/*
	 * FIN Attributs
	 */
	
	private Moteur moteur;
	
	/*
	 * Constructeur
	 */
	public Panneau_Historique (Color newCouleur, Moteur referenceMoteur){
		super(newCouleur);
		moteur = referenceMoteur;
		initialiserImages();
		Ecouteur_Historique ecouteur = new Ecouteur_Historique(this, moteur);
		numeroTourCourant = 0;
		numeroTourSelectionne = -1;
		autorisationInteraction = true;
		addMouseListener(ecouteur);
		addMouseMotionListener(ecouteur);
	}
	/*
	 * FIN Constructeur
	 */
	
	/*
	 * ACCESSEURS
	 */
	public int getDiviseur (){
		return diviseurDeDimension;
	}
	public int getNumeroTourCourant (){
		return numeroTourCourant;
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
	public int getNbOngletsActifs (){
		return nbOngletsActifs;
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
	
	public boolean getAutorisation (){
		return autorisationInteraction;
	}
	
	public void setNumeroTourCourant (int newTourCourant){
		numeroTourCourant = newTourCourant;
	}
	public void setNumeroTourSelectionne (int newTourSelectionne){
		numeroTourSelectionne = newTourSelectionne;
	}
	public void setAutorisation (boolean newAutorisation){
		autorisationInteraction = newAutorisation;
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
		
	}
	
	/*
	 * Methodes Public de Panneau_Historique
	 */
	/**
	 * Methode ouvrant une pop-up de confirmation pour naviguer vers un autre tour
	 */
	public void demandeConfirmation (){
		
	}
	public void changeImageDefilementHaut (String nomImage){
		defilementHaut = Constantes.Images.initBackground(nomImage);
	}
	public void changeImageDefilementBas (String nomImage){
		defilementBas = Constantes.Images.initBackground(nomImage);
	}
	
	/*
	 * Methodes Private de Panneau_Historique
	 */
	private void dessinerPartieHaute (Graphics2D crayon){
		coordY = 0;
		hauteurImage = hauteur/diviseurDeDimension;
		crayon.drawImage(defilementHaut, coordX - elargissementX, coordY, largeurImage + 2*elargissementX, hauteurImage, this);
		
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
		
		dessinerOnglets(crayon, nbOngletsActifs);
	}
	private void dessinerPartieBasse (Graphics2D crayon){
		coordY = (diviseurDeDimension-1)*hauteur/diviseurDeDimension;
		hauteurImage = hauteur/diviseurDeDimension;
		crayon.drawImage(defilementBas, coordX - elargissementX, coordY, largeurImage + 2*elargissementX, hauteurImage, this);
		
		borneHaute_BoutonInferieur = coordY;
		borneBasse_BoutonInferieur = borneHaute_BoutonInferieur + hauteurImage;
	}
	
	private void dessinerOnglets (Graphics2D crayon, int nombreMaxTour){
		int coordYOnglet = borneHaute_Onglet;
		for (int numTour = 0; numTour < nombreMaxTour; numTour++){
			crayon.drawImage(ongletHistorique, borneGauche_Onglet, coordYOnglet, dimensionOnglet, dimensionOnglet, this);
			
			if ( numTour == numeroTourCourant - moteur.getHistorique().getNbConfigsPrecedentes() ){
				crayon.drawImage(surbrillanceCourante, borneGauche_Onglet, coordYOnglet, dimensionOnglet, dimensionOnglet, this);
			}
			if ( numTour == numeroTourSelectionne ){
				crayon.drawImage(surbrillanceSelectionnee, borneGauche_Onglet, coordYOnglet, dimensionOnglet, dimensionOnglet, this);
			}
			
			int posXString = borneGauche_Onglet + 2*dimensionOnglet/5+1;
			int posYString = coordYOnglet + 3*dimensionOnglet/5;
			
			int numTourActif = numTour + moteur.getHistorique().getNbConfigsPrecedentes();

			String chaineTour = "" + numTourActif;
			
			crayon.setColor(Color.white);
			crayon.drawString(chaineTour , posXString, posYString);
			
			coordYOnglet += dimensionOnglet + ecart;
		}
	}
	
	protected void initialiserImages (){
		defilementHaut = Constantes.Images.initBackground("histo_haut.png");
		historiqueCentral = Constantes.Images.initBackground("histo_centre.png");
		ongletHistorique = Constantes.Images.initBackground("histo_case.png");
		defilementBas = Constantes.Images.initBackground("histo_bas.png");
		
		surbrillanceSelectionnee = Constantes.Images.initSurbrillance("surbrillanceJaune.png");
		surbrillanceCourante = Constantes.Images.initSurbrillance("surbrillanceVerte.png");
	}
}
