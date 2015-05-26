package graphique;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class GestionSouris implements MouseListener{
	Panneau pan = null;
	Fenetre fen = null;
	JButton but = null;
	Panneau panNotif = null; 
	
	public GestionSouris (Panneau referencePan, Panneau referenceNotif){
		pan = referencePan;
		panNotif = referenceNotif;
	}
	
	public GestionSouris (Fenetre referenceFen, Panneau referenceNotif){
		fen = referenceFen;
		panNotif = referenceNotif;
	}
	
	public GestionSouris (JButton referenceBut, Panneau referenceNotif){
		but = referenceBut;
		panNotif = referenceNotif;
	}
	
	@Override
	public void mouseClicked(MouseEvent event) {
		String resultat = "";
		if (pan != null){
			resultat = resultat + pan.getName() + "\n";
			resultat = resultat + "type panneau : " + pan.getTypeZone() + "\n";
		}
		if (fen != null){
			resultat = resultat + fen.getName() + "\n";
		}
		if (but != null){
			resultat = resultat + but.getText() + "\n";
		}
		//panNotif.updateMessage(resultat);
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		if (pan != null){
			pan.activerContours();
		}
	}

	@Override
	public void mouseExited(MouseEvent event) {
		if (pan != null){
			pan.desactiverContours();
		}
	}

	@Override
	public void mousePressed(MouseEvent event) {
		
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		
	}

}
