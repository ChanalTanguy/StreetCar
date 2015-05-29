package titrePackage;
import graphique.Fenetre;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import objectPackage.Plateau;
import mainPackage.Moteur;

public class EcouteTitre implements MouseListener {

	PanneauTitre pan;
	FenetreTitre fen;
	
	EcouteTitre(PanneauTitre panneau, FenetreTitre f)
	{
		pan = panneau;
		fen = f;
		
	}
		
	public void mouseClicked(MouseEvent e) {}
	
	public void mouseEntered(MouseEvent e) {}
	
	public void mouseExited(MouseEvent e) {}
	
	public void mousePressed(MouseEvent e) {
		
		int x = e.getX();
		int y = e.getY();
		
		//if(pan.selectionner != -1)
			
		menus(x, y);
		quitter(x,y);
		
		pan.repaint();
	}
	
	private void quitter(int x, int y) {
		if(x>pan.getWidth()/3 && x<pan.getWidth()/3+300 && y>(10+4)*50 && y<((10+4)*50+45))
		{
			if(pan.selectionner == 10) effectuerAction(pan.selectionner);
			pan.selectionner = 10;
			pan.selection = true;
		}	
	}

	private void menus(int x, int y) {
		boolean b = true;
		
		for(int i = 1; i<6 && b; i++)
		{
			if(x>pan.getWidth()/3 && x<pan.getWidth()/3+300 && y>(i+4)*50 && y<((i+4)*50+45)) 
			{
				if(i == pan.selectionner) effectuerAction(pan.selectionner);
				pan.selectionner = i;
				pan.selection = true;
				b = false;
			}
			else
			{
				pan.selection = false;
			}
		}	
	}

	
	private void effectuerAction(int selectionner) {
		
		switch(selectionner)
		{
			case 1 : //Nouvelle partie
				fen.fermeture();
				Moteur m = new Moteur(new Plateau());
				Fenetre f = new Fenetre("Street Car");
				f.disposition(m, f.getSize());
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
	
	public void mouseReleased(MouseEvent e) {}

}
