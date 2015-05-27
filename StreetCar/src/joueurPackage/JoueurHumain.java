package joueurPackage;

import mainPackage.Moteur;

public class JoueurHumain extends Joueur {

	private boolean enabled;
	Moteur moteur;
	
	public JoueurHumain(Moteur m, int ligne) {
		super(new MainJoueur(), ligne, 0);
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
	 *  Jouer un coup "Placer" si c'est au tour de ce joueur de jouer
	 *  et une tuile a été préalablement selectionnée
	 */
	public void coupPlacerTuile(int carte, int x, int y) {

		if (enabled && carte >= 0 && carte <= 4) {
			moteur.jouerCoup(Coup.newPlacement(carte, x, y));
		}
	}
	
	/**
	 *  Jouer un coup "Tourner" si c'est au tour de ce joueur de jouer
	 */
	public void coupTourner(int tuile) {
		if (enabled)
			moteur.jouerCoup(Coup.newRotation(tuile));
	}
	
	public Joueur clone() {
		JoueurHumain j = new JoueurHumain(moteur, this.ligne);
		j.main = this.main.clone();
		j.phase = this.phase;
		
		return j;
	}
}
