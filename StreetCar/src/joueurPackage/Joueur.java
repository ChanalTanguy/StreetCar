package joueurPackage;

import objectPackage.Pioche;
import objectPackage.Plateau;
import objectPackage.tuilePackage.Tuile;

public abstract class Joueur {
	
	MainJoueur main;
	Objectifs objectif;
	int phase = 1;
	int typeJoueur;
	
	public Joueur (MainJoueur referenceMain, int ligne, int newTypeJoueur) {
		main = referenceMain;
		typeJoueur = newTypeJoueur;
		objectif = new Objectifs(ligne);
		initBidonObjectifs();
	}
	public Joueur (MainJoueur referenceMain, int newTypeJoueur){
		main = referenceMain;
		typeJoueur = newTypeJoueur;
		objectif = new Objectifs();
	}
	
	/*
	 * Methodes Abstract de Joueur
	 */
	public abstract void attendCoup();
	public abstract void stopPlayer();
	public abstract Joueur clone();
	
	/*
	 * Accesseurs
	 */
	public MainJoueur getMain() {
		return main;
	}
	public int getPhase() {
		return phase;
	}
	public int getType (){
		return typeJoueur;
	}
	public Objectifs getObjectifs (){
		return objectif;
	}
	
	public void setLigne (int newLigne){
		objectif.setLigne(newLigne);
	}
	
	public void setMain(MainJoueur referenceMain)
	{
		main = referenceMain;
	}
	
	
	/*
	 * FIN Accesseurs
	 */
	
	/*
	 * Methodes Public de Joueur
	 */
	/**
	 * Poser une tuile sur le plateau au coordonnée X,Y
	 * Si il y a deja une tuile à ces coordonnées
	 * on la met dans la main du joueur
	 * @param tuileDansMain
	 * @param x
	 * @param y
	 * @param plateauDeJeu
	 */
	public void jouerTuileSurPlateau (int tuileDansMain, int x, int y, Plateau plateauDeJeu) {
		Tuile t = plateauDeJeu.getTuileAt(x, y);
		if (t == null) {
			plateauDeJeu.setTuileAt(x, y, main.getTuileAt(tuileDansMain));
			main.setTuileAt(tuileDansMain, null);
		} else {
			plateauDeJeu.setTuileAt(x, y, main.getTuileAt(tuileDansMain));
			main.setTuileAt(tuileDansMain, t);
		}
	}	/**
	 * Tourne la p ième tuile de la main du joueur
	 * @param tuile
	 */
	public void tournerTuileMain(int tuile) {
		main.tourneTuileAt(tuile);
	}
	/**
	 * enlève la p ième tuile de la main de l'autre joueur et la met dans la main du joueur courant
	 * @param tuile
	 * @param joueur
	 */
	public void volerTuile(int tuile, Joueur joueur) {
		main.ajouterCarte(joueur.getMain().volerTuile(tuile));
	}
	/**
	 * Enlève la carte au dessus de la pioche et la met dans la main du joueur
	 * @param pioche 
	 */
	public void piocher(Pioche pioche) {
		if (!pioche.isEmpty())
			while (!main.isFull() && !pioche.isEmpty())
				main.ajouterCarte(pioche.remove(0));
	}

	/*
	 * Methodes Private de Joueur
	 */
	private void initBidonObjectifs (){
		objectif.ajouterEscales(1);
		objectif.ajouterEscales(5);
	}
}
