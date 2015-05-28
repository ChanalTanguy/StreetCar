package joueurPackage;

import iaPackage.IAFacile;

import javax.swing.Timer;

import mainPackage.Moteur;

public class JoueurIA extends Joueur {

	public EcouteurBoucle ecouteurBoucle;
	public Timer t;
	
	public JoueurIA(Moteur m, int ligne) {
		super(new MainJoueur(), ligne, 1);
		ecouteurBoucle = new EcouteurBoucle(m, new IAFacile(m, this));
		t = new Timer(450, ecouteurBoucle);
	}
	
	public void attendCoup() {
		ecouteurBoucle.enable();
		if (!t.isRunning())
			t.start();
	}
	
	public Joueur clone() {

		JoueurIA j = new JoueurIA(ecouteurBoucle.moteur, this.ligne);
		j.main = this.main.clone();
		j.phase = this.phase;
		return j;
	}

}
