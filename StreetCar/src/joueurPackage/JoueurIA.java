package joueurPackage;

import iaPackage.InterfaceIA;

import javax.swing.Timer;

public class JoueurIA implements Joueur {

	public EcouteurBoucle ecouteurBoucle;
	
	public JoueurIA(Moteur m, InterfaceIA ia) {
		ecouteurBoucle = new EcouteurBoucle(m);
		new Timer(50, ecouteurBoucle);
	}
	
	public void attendCoup() {
		ecouteurBoucle.enable();
	}

}
