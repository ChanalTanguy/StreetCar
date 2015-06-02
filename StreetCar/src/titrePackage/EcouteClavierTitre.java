package titrePackage;
import graphique.Fenetre;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import objectPackage.Plateau;
import windowPackage.NewGameWindow;
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
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_UP:
				if(pan.selectionner == -1) {pan.selectionner = 1; pan.selection = true;}
				if(pan.selectionner == 1) pan.selectionner = 10;
				else if(pan.selectionner == 10) pan.selectionner = 5;
				else pan.selectionner = (pan.selectionner-1)%6;
				break;
			case KeyEvent.VK_DOWN:
				if(pan.selectionner == -1) {pan.selectionner = 10; pan.selection = true;}
				if(pan.selectionner == 5) pan.selectionner = 10;
				else pan.selectionner = pan.selectionner%5+1;
				break;
			case KeyEvent.VK_ENTER:
				if(pan.selectionner != -1) effectuerAction(pan.selectionner);
				break;
			case KeyEvent.VK_BACK_SPACE:
				if(pan.actif != 0) pan.actif = 0; //On retourne au menu avec un return
				break;
		}
		pan.repaint();
	
	}

	private void effectuerAction(int selectionner) {
		
		switch(selectionner)
		{
			case 1 : //Nouvelle partie
				
				NewGameWindow g = new NewGameWindow();
				g.openWindow();
				/*
				fen.fermeture();
				Moteur m = new Moteur(new Plateau());
				Fenetre f = new Fenetre("Street Car");
				f.disposition_V2(m, f.getSize());
				*/
				break;
			case 2 : //Charger partie
				pan.actif = 2;
				break;
			case 3 : //Défis
				pan.actif = 3;
				break;
			case 4 : //Options
				pan.actif = 4;
				break;
			case 5 : //Crédits
				pan.actif = 5;
				break;
			case 10 : //Quitter
				fen.fermeture();
				break;
		}
		
	}

	public void keyReleased(KeyEvent e) {}

	public void keyTyped(KeyEvent e) {}

}
