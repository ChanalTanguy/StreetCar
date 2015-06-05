package historiqPackage;

import java.awt.Point;

import joueurPackage.Joueur;
import objectPackage.Pioche;
import objectPackage.Plateau;

public class Configuration {
	private Joueur[] tabJoueurs;
	private int joueurCourantDuTour;
	private Plateau plateauDuTour;
	private Pioche piocheDuTour;
	private int numeroDuTour;
	private Historique historiqueDuTour;
	private Point[] coupsPrecedents;
	
	/*
	 * CONSTRUCTEUR
	 */
	public Configuration (Joueur[] referenceTabJoueurs, int currentPlayer, Plateau plateauDeJeu, Pioche pioche, int numeroDeTour, Historique referenceHistorique, Point[] referenceCoupsPrecedents){
		tabJoueurs = new Joueur[referenceTabJoueurs.length];
		instanciationJoueurs(referenceTabJoueurs);
		joueurCourantDuTour = currentPlayer;
		plateauDuTour = plateauDeJeu.clone();
		piocheDuTour = pioche.clone();
		numeroDuTour = numeroDeTour;
		historiqueDuTour = referenceHistorique.clone();
		copie(referenceCoupsPrecedents);
	}
	
	/*
	 * ACCESSEURS
	 */
	public int getNombreJoueurs (){
		return tabJoueurs.length;
	}
	public Joueur getJoueurAt (int numeroJoueur){
		return tabJoueurs[numeroJoueur];
	}
	public int getJoueurCourant (){
		return joueurCourantDuTour;
	}
	public Plateau getPlateauDuTour (){
		return plateauDuTour;
	}
	public Pioche getPiocheDuTour (){
		return piocheDuTour;
	}
	public int getNumeroTour (){
		return numeroDuTour;
	}
	public Historique getHistorique (){
		return historiqueDuTour;
	}
	public Point[] getCoupsPrecedents (){
		return coupsPrecedents;
	}
	
	public void setHistorique (Historique newHistorique){
		historiqueDuTour = newHistorique.clone();
	}

	/*
	 * Methodes Public de Configuration
	 */
	public boolean equals (Configuration autreConfig){
		boolean configurationIdentique = false;
		
		
		
		return configurationIdentique;
	}
	public String toString (){
		String chaine_resultat = "";
		chaine_resultat += joueurCourantDuTour + "\n";
		chaine_resultat += numeroDuTour + "\n";
		for (int numJoueur = 0; numJoueur < tabJoueurs.length; numJoueur++){
			chaine_resultat += tabJoueurs[numJoueur].getMain().toString() + "\n";
		}
		chaine_resultat += piocheDuTour.toString();
		chaine_resultat += plateauDuTour.toString();
		for (int numCoupPrecedent = 0; numCoupPrecedent < coupsPrecedents.length; numCoupPrecedent++){
			chaine_resultat += coupsPrecedents[numCoupPrecedent].toString();
		}
		return chaine_resultat;
	}
	public void copie (Point[] tabCoups){
		coupsPrecedents = new Point[tabCoups.length];
		int numeroCoup = 0;
		while (numeroCoup < coupsPrecedents.length && tabCoups[numeroCoup] != null){
			coupsPrecedents[numeroCoup] = (Point) tabCoups[numeroCoup].clone();
			numeroCoup++;
		}
	}
	
	/*
	 * Methodes Private de Configuration
	 */
	private void instanciationJoueurs (Joueur[] referenceTabJoueurs){
		for (int numeroJoueur = 0; numeroJoueur < tabJoueurs.length; numeroJoueur++){
			tabJoueurs[numeroJoueur] = referenceTabJoueurs[numeroJoueur].clone();
		}
	}
}
