package joueurPackage;

import graphique.EcouteTerrain;
import constantesPackages.Constantes.Plateau;
import mainPackage.Moteur;

public class JoueurHumain extends Joueur {

	private EcouteTerrain ecouteurPlateau;
	private boolean enabled;
	Moteur moteur;
	int tuileSelectionnee;
	
	public JoueurHumain(Moteur m, int ligne) {
		super(null, ligne);
		//ecouteurPlateau = new EcouteTerrain(this);
		enabled = false;
		this.moteur = m;
		// TODO : Ajouter la main en paramètre
	}
	
	public void attendCoup() {
		enabled = true;
	}
	
	/**
	 *  Jouer un coup "Piocher" si c'est au tour de ce joueur de jouer
	 */
	public void coupPiocher() {
		if (enabled)
			moteur.jouerCoup(Coup.newPioche());
	}
	
	/**
	 *  Jouer un coup "Voler" si c'est au tour de ce joueur de jouer
	 */
	public void coupVoler(int tuile) {
		if (enabled)
			moteur.jouerCoup(Coup.newVol(tuile));
	}
	
	/**
	 *  Selectionee une tuile
	 */
	public void coupSelectionTuile(int tuile) {
		if (enabled)
			tuileSelectionnee = tuile;
	}
	
	/**
	 *  Jouer un coup "Placer" si c'est au tour de ce joueur de jouer
	 *  et une tuile a été préalablement selectionnée
	 */
	public void coupPlacerTuile(int carte, int x, int y) {
		if (enabled && tuileSelectionnee != 0) {
			moteur.jouerCoup(Coup.newPlacement(tuileSelectionnee, x, y));
			tuileSelectionnee = 0;
		}
	}
	
	/**
	 *  Jouer un coup "Tourner" si c'est au tour de ce joueur de jouer
	 */
	public void coupTourner(int tuile) {
		if (enabled)
			moteur.jouerCoup(Coup.newPioche());
	}
	
}
