package joueurPackage;

import mainPackage.*;
import tuilePackage.*;

public abstract class Joueur {
	
	MainJoueur main;
	int phase = 1;
	int ligne;
	
	public Joueur(MainJoueur m, int ligne) {
		main = m;
		this.ligne = ligne;
	}
	
	public abstract void attendCoup();
	
	public MainJoueur getMain() {
		return main;
	}
	
	public int getPhase() {
		return phase;
	}
	
	public int getLigne() {
		return ligne;
	}
	
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
	}

	/**
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
		while (!main.isFull())
			main.ajouterCarte(pioche.remove(0));
	}
	
}
