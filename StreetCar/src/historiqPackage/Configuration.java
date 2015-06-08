package historiqPackage;

import joueurPackage.Coup;
import joueurPackage.Joueur;
import objectPackage.Pioche;
import objectPackage.Plateau;

public class Configuration {
	private Joueur[] tabJoueurs;
	private int joueurCourantDuTourSuivant;
	private Plateau plateauDuTour;
	private Pioche piocheDuTour;
	private int numeroDuTourCourant;
	private Historique historiqueDuTour;
	private Coup[] coupsPrecedents;
	
	/*
	 * CONSTRUCTEUR
	 */
	public Configuration (Joueur[] referenceTabJoueurs, int newCurrentPlayer, Plateau plateauDeJeu, Pioche pioche, int numeroDeTour, Coup[] referenceCoupsPrecedents){
		tabJoueurs = new Joueur[referenceTabJoueurs.length];
		instanciationJoueurs(referenceTabJoueurs);
		joueurCourantDuTourSuivant = newCurrentPlayer;
		plateauDuTour = plateauDeJeu.clone();
		piocheDuTour = pioche.clone();
		numeroDuTourCourant = numeroDeTour;
		copie(referenceCoupsPrecedents);
	}
	public Configuration (Joueur[] referenceTabJoueurs, int newCurrentPlayer, Plateau plateauDeJeu, Pioche pioche, int numeroDeTour, Historique referenceHistorique, Coup[] referenceCoupsPrecedents){
		tabJoueurs = new Joueur[referenceTabJoueurs.length];
		instanciationJoueurs(referenceTabJoueurs);
		joueurCourantDuTourSuivant = newCurrentPlayer;
		plateauDuTour = plateauDeJeu.clone();
		piocheDuTour = pioche.clone();
		numeroDuTourCourant = numeroDeTour;
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
		return joueurCourantDuTourSuivant;
	}
	public Plateau getPlateauDuTour (){
		return plateauDuTour;
	}
	public Pioche getPiocheDuTour (){
		return piocheDuTour;
	}
	public int getNumeroTour (){
		return numeroDuTourCourant;
	}
	public Historique getHistorique (){
		return historiqueDuTour;
	}
	public Coup[] getCoupsPrecedents (){
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
		chaine_resultat += joueurCourantDuTourSuivant + "\n";
		chaine_resultat += numeroDuTourCourant + "\n";
		for (int numJoueur = 0; numJoueur < tabJoueurs.length; numJoueur++){
			chaine_resultat += tabJoueurs[numJoueur].getMain().toString() + "\n";
		}
		chaine_resultat += piocheDuTour.toString();
		chaine_resultat += plateauDuTour.toString();
		for (int numCoupPrecedent = 0; numCoupPrecedent < coupsPrecedents.length; numCoupPrecedent++){
			if(coupsPrecedents[numCoupPrecedent] != null) chaine_resultat += coupsPrecedents[numCoupPrecedent].toString();
		}
		return chaine_resultat;
	}
	public void copie (Coup[] tabCoups){
		coupsPrecedents = new Coup[tabCoups.length];
		int numeroCoup = 0;
		while (numeroCoup < coupsPrecedents.length && tabCoups[numeroCoup] != null){
			coupsPrecedents[numeroCoup] = tabCoups[numeroCoup].clone();
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
