package joueurPackage;

import java.awt.Point;

import constantesPackages.Constantes;
import objectPackage.Pioche;
import objectPackage.Plateau;
import objectPackage.tuilePackage.Escale;
import objectPackage.tuilePackage.Tuile;

public abstract class Joueur {
	
	protected MainJoueur main;
	protected Objectifs objectif;
	protected int phase = 1;
	protected int typeJoueur;
	protected Point positionCouranteTram;
	protected String orientationCouranteTram;
	protected Escale[] escalesAtteintes;
	protected boolean voyagePossible = false;
	protected boolean voyageActive = false;
	
	
	public Joueur (MainJoueur referenceMain, int ligne, int newTypeJoueur) {
		main = referenceMain;
		typeJoueur = newTypeJoueur;
		objectif = new Objectifs(ligne);
	}
	public Joueur (MainJoueur referenceMain, int newTypeJoueur){
		main = referenceMain;
		typeJoueur = newTypeJoueur;
		objectif = new Objectifs();
	}
	
	public Joueur (MainJoueur referenceMain, Objectifs obj, int newTypeJoueur)
	{
		main = referenceMain;
		typeJoueur = newTypeJoueur;
		objectif = obj;
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
	public boolean getVoyagePossible (){
		return voyagePossible;
	}
	public boolean getVoyageActive (){
		return voyageActive;
	}
	
	public void setLigne (int newLigne){
		objectif.setLigne(newLigne);
	}
	public void setType(int type){
		typeJoueur = type;
	}
	
	public void setMain(MainJoueur referenceMain)
	{
		main = referenceMain;
	}
	public void setObjectifs(Objectifs referenceObjectif)
	{
		objectif = referenceObjectif;
	}
	public void setVoyagePossible (boolean newPossibilite){
		voyagePossible = newPossibilite;
	}
	public void setVoyageActive (boolean newActivite){
		voyageActive = newActivite;
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
			int escaleLieeTuile = main.getTuileAt(tuileDansMain).getEscaleLiee();
			main.getTuileAt(tuileDansMain).setEscaleLiee(t.getEscaleLiee());
			t.setEscaleLiee(escaleLieeTuile);
			plateauDeJeu.setTuileAt(x, y, main.getTuileAt(tuileDansMain));
			main.setTuileAt(tuileDansMain, t);
		}
		
		t = plateauDeJeu.getTuileAt(x, y);
		Tuile tAdjacente;
		for (int i = 0; i < 4; i++) {

			tAdjacente = null;
			switch (i) {
			case 0 : if (x != Constantes.Dimensions.dimensionPlateau-1) tAdjacente = plateauDeJeu.getPlateau()[x+1][y]; break;
			case 1 : if (x != 0) 										tAdjacente = plateauDeJeu.getPlateau()[x-1][y]; break;
			case 2 : if (y != Constantes.Dimensions.dimensionPlateau-1) tAdjacente = plateauDeJeu.getPlateau()[x][y+1]; break;
			default : if (y != 0) 										tAdjacente = plateauDeJeu.getPlateau()[x][y-1]; break;
			}
			if (tAdjacente != null && tAdjacente.getTypeTuile() == 2 && t.getTypeTuile() != 3) {
				Escale e = (Escale) tAdjacente;
				if (e.getStop() == null) {
					e.setStop(new Point(x,y));
					t.setEscaleLiee(e.getNumeroEscale());
				}
			}

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

}
