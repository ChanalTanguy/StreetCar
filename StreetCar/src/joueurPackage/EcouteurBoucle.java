package joueurPackage;

import iaPackage.InterfaceIA;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import mainPackage.Moteur;

public class EcouteurBoucle implements ActionListener {

	boolean enabled;
	Moteur moteur;
	InterfaceIA ia;
	
	public EcouteurBoucle(Moteur m) {
		enabled = false;
		moteur = m;
	}
	
	public void enable() {
		enabled = true;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if (enabled) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Coup c = ia.getCoup();
			moteur.jouerCoup(c);
			enabled = false;
		}
	}

}
