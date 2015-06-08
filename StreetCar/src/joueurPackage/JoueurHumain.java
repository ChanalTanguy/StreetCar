	package joueurPackage;

import mainPackage.Moteur;

public class JoueurHumain extends Joueur {

	private boolean enabled;
	Moteur moteur;

	private static int typeHumain = 0;
	
	public JoueurHumain(Moteur m, int ligne) {
		super(new MainJoueur(), ligne, typeHumain);
		//ecouteurPlateau = new EcouteTerrain(this);
		enabled = false;
		this.moteur = m;
		// TODO : Ajouter la main en paramètre
	}
	
	public JoueurHumain(Moteur m, Objectifs obj) {
		super(new MainJoueur(), obj, typeHumain);
		enabled = false;
		this.moteur = m;
	}
	
	public void attendCoup() {
		enabled = true;
	}

	public void stopPlayer() {
		enabled = false;
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
			tournerTuileMain(tuile);
	}
	
	public Joueur clone() {
		JoueurHumain joueur_renvoi = new JoueurHumain(moteur, objectif.getLigne());
		
		joueur_renvoi.main = this.main.clone();
		joueur_renvoi.phase = this.phase;
		joueur_renvoi.objectif = this.objectif.clone();
		
		return joueur_renvoi;
	}
}
