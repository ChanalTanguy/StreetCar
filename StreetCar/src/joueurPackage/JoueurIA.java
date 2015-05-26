package joueurPackage;

import graphique.Panneau;
import iaPackage.IAFacile;
import iaPackage.InterfaceIA;

import javax.swing.Timer;

import mainPackage.Moteur;

public class JoueurIA extends Joueur {

	public EcouteurBoucle ecouteurBoucle;
	
	public JoueurIA(Moteur m, int ligne) {
		super(new MainJoueur(), ligne);
		ecouteurBoucle = new EcouteurBoucle(m, new IAFacile(m));
		new Timer(50, ecouteurBoucle);
	}
	
	public void attendCoup() {
		ecouteurBoucle.enable();
	}

}
