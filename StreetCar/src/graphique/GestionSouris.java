package graphique;

import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class GestionSouris implements MouseListener{
	Panneau pan = null;
	Fenetre fen = null;
	JButton but = null;
	
	public GestionSouris (Panneau referencePan){
		pan = referencePan;
	}
	
	public GestionSouris (Fenetre referenceFen){
		fen = referenceFen;
	}
	
	public GestionSouris (JButton referenceBut){
		but = referenceBut;
	}
	
	@Override
	public void mouseClicked(MouseEvent event) {
		String resultat = "";
		if (pan != null){
			resultat = resultat + pan.getName() + "\n";
		}
		if (fen != null){
			resultat = resultat + fen.getName() + "\n";
		}
		if (but != null){
			resultat = resultat + but.getText() + "\n";
		}
		System.out.println(resultat);
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
