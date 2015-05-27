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
	
	public void actionPerformed(ActionEvent arg0) {
		if (enabled) {
			try {
				Thread.sleep(750);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Coup c = ia.getCoup();
			enabled = false;
			moteur.jouerCoup(c);
		}
	}

}
