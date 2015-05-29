package titrePackage;
import graphique.Fenetre;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import objectPackage.Plateau;
import mainPackage.Moteur;


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
					if(pan.selectionner == 1) pan.selectionner = 10;
					else if(pan.selectionner == 10) pan.selectionner = 5;
					else pan.selectionner = (pan.selectionner-1)%6;
					break;
				case KeyEvent.VK_DOWN:
					if(pan.selectionner == 5) pan.selectionner = 10;
					else pan.selectionner = pan.selectionner%5+1;
					break;
				case KeyEvent.VK_ENTER:
					if(pan.selectionner != -1) effectuerAction(pan.selectionner);
					break;
			}
			pan.repaint();
		}
		
	}

	private void effectuerAction(int selectionner) {
		
		switch(selectionner)
		{
			case 1 : //Nouvelle partie
				fen.fermeture();
				Moteur m = new Moteur(new Plateau());
				Fenetre f = new Fenetre("Street Car");
				f.disposition_V2(m, f.getSize());

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
