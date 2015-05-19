package joueurPackage;

import constantesPackages.Constantes.Plateau;
import mainPackage.Moteur;

public class JoueurHumain extends Joueur {

	private EcouteTerrain ecouteurPlateau;
	private boolean enabled;
	Moteur moteur;
	
	public JoueurHumain(Moteur m) {
		ecouteurPlateau = new EcouteTerrain(this);
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
	 *  Jouer un coup "Placer" si c'est au tour de ce joueur de jouer
	 */
	public void coupPlacer(int tuile, int x, int y) {
		if (enabled)
			moteur.jouerCoup(Coup.newPlacement(tuile, x, y));
	}
	
	/**
	 *  Jouer un coup "Tourner" si c'est au tour de ce joueur de jouer
	 */
	public void coupTourner(int tuile) {
		if (enabled)
			moteur.jouerCoup(Coup.newPioche());
	}
	
}
