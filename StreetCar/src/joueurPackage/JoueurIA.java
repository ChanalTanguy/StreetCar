package joueurPackage;

import iaPackage.IAFacile;

import javax.swing.Timer;

import mainPackage.Moteur;

public class JoueurIA extends Joueur {

	public EcouteurBoucle ecouteurBoucle;
	public Timer t;
	
	private static int typeIA = 1;
	
	public JoueurIA(Moteur m, int ligne) {
		super(new MainJoueur(), ligne, typeIA);
		ecouteurBoucle = new EcouteurBoucle(m, new IAFacile(m, this));
		t = new Timer(450, ecouteurBoucle);
	}
	
	public JoueurIA(Moteur m, Objectifs obj) {
		super(new MainJoueur(), obj, typeIA);
		ecouteurBoucle = new EcouteurBoucle(m, new IAFacile(m, this));
		t = new Timer(450, ecouteurBoucle);
	}
	
	public void attendCoup() {
		ecouteurBoucle.enable();
		if (!t.isRunning())
			t.start();
	}

	public void stopPlayer() {
		ecouteurBoucle.disable();
	}
	
	public Joueur clone() {
		JoueurIA joueur_renvoi = new JoueurIA(ecouteurBoucle.moteur, objectif.getLigne());
		
		joueur_renvoi.main = this.main.clone();
		joueur_renvoi.phase = this.phase;
		
		
		return joueur_renvoi;
	}

}
