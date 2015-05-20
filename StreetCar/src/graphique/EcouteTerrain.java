package graphique;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EcouteTerrain implements MouseListener {

	Panneau p;
	
	EcouteTerrain(Panneau panneau)
	{
		p = panneau;
	}
	
	public void mousePressed(MouseEvent e) {
		
		int x = e.getX();
		int y = e.getY();
		
		int piocheX = x-p.ecart;
		int piocheY = y;
		
		int caseX = (x-p.depart)/p.tailleCase;
		int caseY  = (y-p.depart)/p.tailleCase;
		
		//Carte dans la main
		if(estDansMain(piocheX, piocheY))
		{ 	
			int numCarte = carteNo(piocheX);
			int numMain = mainNo(piocheY);
			//p.message = "Je suis dans la main du joueur " + numMain + " à la carte " + numCarte;
			illuminerMain(numCarte, numMain);
		}
		
		//Plateau
		if(!estSurPlateau(caseX, caseY)){ caseX = -1; caseY = -1; }
		else
		{
			//p.message = "X = " + caseX + ", Y = " + caseY;
			illuminerCase(caseX, caseY);
		}
		
		p.repaint();
	}

	private void illuminerMain(int numCarte, int numMain) {
		p.main = numMain;
		p.carte = numCarte;		
	}
	
	private void illuminerCase(int x, int y) {
		p.caseX = x;
		p.caseY = y;
	}

	//Savoir quel joueur actif
	private int mainNo(int piocheY) {
		if(piocheY>20 && piocheY<90) return 2;
		else if(piocheY>820 && piocheY<890) return 1;
		else return -1;
	}

	//Savoir quel carte de la main
	private int carteNo(int piocheX) {
		
		for(int i = 0; i<=400; i+=100)
		{
			if(piocheX>i && piocheX<i+70) return i/100;
		}
		
		return -1;
	}

	//Fonction principale déterminant si l'on se trouve dans la main d'une des deux joueurs
	private boolean estDansMain(int piocheX, int piocheY) {
		boolean b = true;
		
		//p.message = "X = " + piocheX + ", Y = " + piocheY;
		if(piocheX>=0 && piocheX<470 && piocheY>=20 && piocheY<=90)  //Main du haut
		{
			for(int i = 70; i<=370;i+=100)
			{
				if(piocheX>=i && piocheX<=i+30) b = false;
			}
		}
		
		else if(piocheX>=0 && piocheX<470 && piocheY>820 && piocheY<890) //Main du bas
		{
			for(int i = 70; i<=370;i+=100)
			{
				if(piocheX>=i && piocheX<=i+30) b = false;
			}	
		}
		
		else b = false;
			
		return b;
	}

	//Fonction principale déterminant si l'on se trouve sur le plateau
	private boolean estSurPlateau(int caseX, int caseY) {
		boolean b = true;
		
		if(caseX>p.nbCases || caseY>p.nbCases){ b = false; }
		if(caseX<=0 || caseY<=0){ b = false; }
			
		return b;
	}

	public void mouseEntered(MouseEvent e) {}
	
	public void mouseExited(MouseEvent e) {}

	public void mouseClicked(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

}
