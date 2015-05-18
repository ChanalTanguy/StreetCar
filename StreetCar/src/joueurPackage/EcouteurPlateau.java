package joueurPackage;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import mainPackage.Moteur;

public class EcouteurPlateau implements MouseListener {

	boolean enabled;
	Moteur moteur;
	
	public EcouteurPlateau(Moteur m) {
		enabled = false;
		moteur = m;
	}
	
	public void enable() {
		enabled = true;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (enabled) {
			// Coup c = new Coup();
			moteur.jouerCoup(/* c */);
			enabled = false;
		}
	}

	
	
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}

}
