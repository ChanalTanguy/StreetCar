package joueurPackage;

import iaPackage.InterfaceIA;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import mainPackage.Moteur;

public class EcouteurBoucle implements ActionListener {

	boolean enabled;
	Moteur moteur;
	InterfaceIA ia;
	
	public EcouteurBoucle(Moteur m, InterfaceIA IA) {
		enabled = false;
		moteur = m;
		ia = IA;
	}
	
	public void enable() {
		enabled = true;
	}

	public void disable() {
		enabled = false;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if (enabled) {
			try {
				Thread.sleep(750);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Coup c = ia.getCoup();
			if (enabled) { // Ce if là existe pour stopper un bot en pleine reflexion
						   // Si le joueur va dans le menu entre temps.
				enabled = false;
				moteur.jouerCoup(c);
			}
		}
	}

}
