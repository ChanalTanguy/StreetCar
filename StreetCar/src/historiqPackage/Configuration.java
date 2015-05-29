package historiqPackage;

import joueurPackage.Joueur;
import mainPackage.Moteur;
import objectPackage.Pioche;
import objectPackage.Plateau;

public class Configuration {
	private Joueur[] tabJoueurs;
	private int joueurCourantDuTour;
	private Plateau plateauDuTour;
	private Pioche piocheDuTour;
	private int numeroDuTour;
	
	/*
	 * CONSTRUCTEUR
	 */
	public Configuration (Moteur mot){
		tabJoueurs = new Joueur[mot.getTabPlayers().length];
		joueurCourantDuTour = mot.getcurrentPlayer();
		plateauDuTour = mot.getPlateau().clone();
		piocheDuTour = mot.getPioche().clone();
		numeroDuTour = 0;
	}
	
	/*
	 * ACCESSEURS
	 */
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
	
	
}
