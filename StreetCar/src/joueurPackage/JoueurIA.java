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
		t = new Timer(50, ecouteurBoucle);
	}
	
	public void attendCoup() {
		ecouteurBoucle.enable();try {
			Thread.sleep(450);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (!t.isRunning())
			t.start();
	}

}
