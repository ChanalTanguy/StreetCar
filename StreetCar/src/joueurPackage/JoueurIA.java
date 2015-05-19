package joueurPackage;

import iaPackage.InterfaceIA;

import javax.swing.Timer;

import mainPackage.Moteur;

public class JoueurIA extends Joueur {

	public EcouteurBoucle ecouteurBoucle;
	
	public JoueurIA(Moteur m) {
		ecouteurBoucle = new EcouteurBoucle(m);
		new Timer(50, ecouteurBoucle);
	}
	
	public void attendCoup() {
		ecouteurBoucle.enable();
	}

}
