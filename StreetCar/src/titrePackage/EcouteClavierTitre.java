package titrePackage;
import graphique.Fenetre;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import mainPackage.Moteur;
import mainPackage.Plateau;


public class EcouteClavierTitre implements KeyListener {
	
	PanneauTitre pan;
	FenetreTitre fen;
	
	EcouteClavierTitre(PanneauTitre panneau, FenetreTitre f)
	{
		pan = panneau;
		fen = f;
	}
	
	public void keyPressed(KeyEvent e) {
		
		if(pan.selection)
		{
			
			switch (e.getKeyCode())
			{
				case KeyEvent.VK_UP:
					if(pan.selectionné == 1) pan.selectionné = 10;
					else if(pan.selectionné == 10) pan.selectionné = 5;
					else pan.selectionné = (pan.selectionné-1)%6;
					break;
				case KeyEvent.VK_DOWN:
					if(pan.selectionné == 5) pan.selectionné = 10;
					else pan.selectionné = pan.selectionné%5+1;
					break;
				case KeyEvent.VK_ENTER:
					if(pan.selectionné != -1) effectuerAction(pan.selectionné);
					break;
			}
			pan.repaint();
		}
		
	}

	private void effectuerAction(int selectionné) {
		
		switch(selectionné)
		{
			case 1 : //Nouvelle partie
				Moteur m = new Moteur(new Plateau());
				Fenetre f = new Fenetre("Street Car");
				f.disposition(m, f.getSize());
				fen.fermeture();
				break;
			case 2 : //Charger partie
				
				break;
			case 3 : //Défis
				
				break;
			case 4 : //Options
				
				break;
			case 5 : //Crédits
				
				break;
			case 10 : //Quitter
				fen.fermeture();
				break;
		}
		
	}

	public void keyReleased(KeyEvent e) {}

	public void keyTyped(KeyEvent e) {}

}
