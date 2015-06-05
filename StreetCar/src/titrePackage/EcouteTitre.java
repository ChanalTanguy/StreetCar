package titrePackage;

import graphique.Fenetre;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import objectPackage.Plateau;
import windowPackage.NewGameWindow;
import mainPackage.Moteur;

public class EcouteTitre implements MouseListener, MouseMotionListener {

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

		switch(pan.actif)
		{
			case 0: //Ecran titre
				menusAction(x, y);
				quitterAction(x,y);
				break;
			case 2: //Chargement
				charger(x,y);
				retour(x,y);
				break;
			case 3: //Defis
	
				retour(x,y);
				break;
			case 4: //Options
				
				retour(x,y);
				break;
			case 5: //Crédits
				
				retour(x,y);
				break;
		}

		pan.repaint();
	}
	
	private void charger(int x, int y) {
		// TODO Auto-generated method stub
		int borneX = pan.getWidth()/3;
		int borneY =  pan.getHeight()/4 ;
		
		for(int i = 0; i<10; i++)
		{
			// getWidth()/3, getHeight()/4+i*50);
			if(x>borneX && x<borneX+100 && y>borneY+i*50 && y<borneY+(i+1)*50)
			{
				pan.chargementPartie = i;
			}
		}
	}

	private void retour(int x, int y) {
		// TODO Auto-generated method stub
		System.out.println("X " + x + " Y " + y);
		if(x>980 && x<980+300 && y>850 && y<850+45) pan.actif = 0;
		
	}

	private void quitterAction(int x, int y) {
		if(x>pan.getWidth()/3 && x<pan.getWidth()/3+300 && y>(10+4)*50 && y<((10+4)*50+45))
		{
			if(pan.selectionner == 10) effectuerAction(pan.selectionner);
		}	
	}

	private void menusAction(int x, int y) {
		boolean b = true;
		
		for(int i = 1; i<6 && b; i++)
		{
			if(x>pan.getWidth()/3 && x<pan.getWidth()/3+300 && y>(i+4)*50 && y<((i+4)*50+45)) 
			{
				if(i == pan.selectionner) effectuerAction(pan.selectionner);
			}
		}
		
	}

	private void quitter(int x, int y) {
		if(x>pan.getWidth()/3 && x<pan.getWidth()/3+300 && y>(10+4)*50 && y<((10+4)*50+45))
		{
			//if(pan.selectionner == 10) effectuerAction(pan.selectionner);
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
				//if(i == pan.selectionner) effectuerAction(pan.selectionner);
				pan.selectionner = i;
				pan.selection = true;
				b = false;
			}
		}	
	}

	
	private void effectuerAction(int selectionner) {
		
		switch(selectionner)
		{
			case 1 : //Nouvelle partie
				
				NewGameWindow g = new NewGameWindow();
				g.setMainWindow(fen);
				g.openWindow(); //voir si on choppe le bonton annuler
				//fen.fermeture();

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
			case 10 :
				fen.fermeture();
				break;
		}
	}
	
	public void mouseReleased(MouseEvent e) {}

	public void mouseDragged(MouseEvent e) {}

	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		menus(x, y);
		quitter(x,y);
		
		pan.repaint();
	}

}
