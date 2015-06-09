package titrePackage;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import windowPackage.NewGameWindow;

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
		int borneX = pan.getWidth()/3;
		int borneY =  pan.getHeight()/4 ;
		
		for(int i = 0; i<10; i++)
		{
			if(x>borneX && x<borneX+100 && y>borneY+i*50 && y<borneY+(i+1)*50)
			{
				pan.chargementPartie = i;
			}
		}
	}

	private void retour(int x, int y) {
		if(x>fen.getWidth()-200 && x<fen.getWidth()-200+300 && y>fen.getHeight()-100-25 && y<fen.getHeight()-100+45) pan.actif = 0;		
	}

	private void quitterAction(int x, int y) {
		if(x>pan.getWidth()/3 && x<pan.getWidth()/3+300 && y>(10+4)*fen.getHeight()/20  && y<((10+4)*fen.getHeight()/20 +45))
		{
			if(pan.selectionner == 10) effectuerAction(pan.selectionner);
		}	
	}

	private void menusAction(int x, int y) {
		boolean b = true;
		
		for(int i = 1; i<6 && b; i++)
		{
			if(x>pan.getWidth()/3 && x<pan.getWidth()/3+300 && y>(i+4)*fen.getHeight()/20 && y<((i+4)*fen.getHeight()/20+45)) 
			{
				if(i == pan.selectionner) effectuerAction(pan.selectionner);
			}
		}
		
	}

	private void quitter(int x, int y) {
		if(x>pan.getWidth()/3 && x<pan.getWidth()/3+300 && y>(10+4)*50 && y<((10+4)*50+45))
		{
			pan.selectionner = 10;
			pan.selection = true;
		}	
	}

	private void menus(int x, int y) {
		boolean b = true;
		
		for(int i = 1; i<6 && b; i++)
		{
			if(x>pan.getWidth()/3 && x<pan.getWidth()/3+300 && y>(i+4)*fen.getHeight()/20 && y<((i+4)*fen.getHeight()/20+45)) 
			{
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
				g.openWindow();
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
