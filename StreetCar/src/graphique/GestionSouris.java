package graphique;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class GestionSouris implements MouseListener{
	Panneau pan = null;
	Fenetre fen = null;
	JButton but = null;
	Panneau panNotif = null;
	Pan_Abstract pan_secondaire = null;
	Pan_Abstract pan_secondaire_notif = null;
	
	public GestionSouris (Panneau referencePan, Panneau referenceNotif){
		pan = referencePan;
		panNotif = referenceNotif;
	}
	public GestionSouris (Pan_Abstract referencePan_Abs){
		pan_secondaire = referencePan_Abs;
	}
	public GestionSouris (Fenetre referenceFen, Panneau referenceNotif){
		fen = referenceFen;
		panNotif = referenceNotif;
	}
	
	public GestionSouris (JButton referenceBut, Panneau referenceNotif){
		but = referenceBut;
		panNotif = referenceNotif;
	}
	
	public void mouseClicked(MouseEvent event) {}

	public void mouseEntered(MouseEvent event) {
		if (pan_secondaire != null){
			pan_secondaire.activerContours();
		}
	}

	public void mouseExited(MouseEvent event) {
/*
		if (pan != null){
			pan.desactiverContours();
		}
*/		
		if (pan_secondaire != null){
			pan_secondaire.desactiverContours();
		}
	}

	public void mousePressed(MouseEvent event) {}
	public void mouseReleased(MouseEvent event) {}

}
