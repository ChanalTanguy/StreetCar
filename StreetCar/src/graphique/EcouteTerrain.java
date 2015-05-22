package graphique;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JOptionPane;

import joueurPackage.JoueurHumain;
import mainPackage.Moteur;
import constantesPackages.Constantes;

public class EcouteTerrain implements MouseListener, MouseMotionListener {

	Panneau pan;
	Moteur mot;
	
	EcouteTerrain(Panneau referencePan)
	{
		pan = referencePan;
	}
	
	EcouteTerrain(Panneau referencePannea, Moteur m)
	{
		pan = referencePannea;
		mot = m;
	}
	
	public void mousePressed(MouseEvent e) {
		if (pan.getTypeZone() == Constantes.Panneau.plateau){
			//Joueur actif
			JoueurHumain j = (JoueurHumain) mot.getTabPlayers()[mot.getcurrentPlayer()];
			
			//Coordonéess
			int x = e.getX();
			int y = e.getY();
			
			//
			int piocheX = x-pan.ecart;
			int piocheY = y;
			
			int caseX = (x-pan.depart)/pan.tailleCase;
			int caseY  = (y-pan.depart)/pan.tailleCase;
			
			//Si le joueur tente de cliquer
			if(estPioche(piocheX, piocheY))
			{
				pan.caseX = -1;
				pan.main = -1;
				j.coupPiocher();
				illuminerPioche();
			}
			
			//Si le joueur selectionne une carte dans sa main
			else if(estDansMain(piocheX, piocheY))
			{ 	
				pan.piocher = false;
				pan.caseX = -1;
				int numCarte = carteNo(piocheX);
				int numMain = mainNo(piocheY);
				
				//j.coupSelectionTuile(numCarte);
				
				illuminerMain(numCarte, numMain);
			}
			
			//Si le joueur clique en dehors du plateau de jeu
			else if(!estSurPlateau(caseX, caseY)){ 
				caseX = -1; caseY = -1;
				pan.main = -1;
				pan.piocher = false;
				pan.caseX = -1;
			}
			else {
				pan.piocher = false;
				//Pour voir si l'on a selectionné au préalable la main du joueur
				if(pan.main != -1)
				{
					j.coupPlacerTuile(pan.carte, caseX, caseY);
					pan.main = -1;
				}
				else 
				{
					illuminerCase(caseX, caseY);
				}
			}
			pan.repaint();
		}
		else if (pan.getTypeZone() == Constantes.Panneau.boutons) {
			int rayon = (pan.getSize().width < pan.getSize().height ) ? pan.getSize().width/8 : pan.getSize().height/8;
			int x = e.getX();
			int y = e.getY();
			
			if ( estSurUndo(x, y, rayon) ){
				System.out.println("clic sur le bouton Annuler");
			}
			else if ( estSurConseils(x, y, rayon) ){
				System.out.println("clic sur le bouton Conseils");
			}
			else if ( estSurAide(x, y, rayon) ){
				System.out.println("clic sur le bouton Aide");
			}
			else if ( estSurMenu(x, y, rayon) ){
				System.out.println("clic sur le bouton Menu");
				JOptionPane pop = new JOptionPane();
				pop.setSize(200, 200);
				pop.setVisible(true);
				pop.showMessageDialog(null, "bonjour", "Titre", JOptionPane.INFORMATION_MESSAGE);
				
			}
			else { System.out.println("clic vide"); }
		}
	}

	private void illuminerPioche() {
		pan.piocher = true;	
	}

	private boolean estPioche(int piocheX, int piocheY) {	
		return piocheX>3*pan.depart+pan.ecart && piocheX<3*pan.depart+pan.ecart+pan.tailleCase+20 && piocheY>820 && piocheY<820+pan.tailleCase+20;
	}

	private void illuminerMain(int numCarte, int numMain) {
		pan.main = numMain;
		pan.carte = numCarte;		
	}
	
	private void illuminerCase(int x, int y) {
		pan.caseX = x;
		pan.caseY = y;
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
		
		if(caseX>pan.nbCases || caseY>pan.nbCases){ b = false; }
		if(caseX<=0 || caseY<=0){ b = false; }
			
		return b;
	}

	/*
	 * Methodes Privates liées au Panneau de type Boutons/Menus
	 */
	private boolean estSurUndo(int x, int y, int rayon) {
		boolean resultat = false;
		int difX, difY;
		difX = x - (pan.getSize().width/4);
		difY = y - (pan.getSize().height/6+rayon);
		resultat = ( (difX*difX + difY*difY) <= rayon*rayon );
		return resultat;
	}
	
	private boolean estSurConseils(int x, int y, int rayon) {
		boolean resultat;
		int difX, difY;
		difX = x - (pan.getSize().width/2+rayon);
		difY = y - (pan.getSize().height/6+rayon);
		resultat = ( (difX*difX + difY*difY) <= rayon*rayon );
		return resultat;
	}	

	private boolean estSurAide(int x, int y, int rayon) {
		boolean resultat;
		int difX, difY;
		difX = x - (pan.getSize().width/3+rayon);
		difY = y - (5*pan.getSize().height/6-rayon);
		resultat = ( (difX*difX + difY*difY) <= rayon*rayon );
		return resultat;
	}

	private boolean estSurMenu(int x, int y, int rayon) {
		boolean resultat;
		int difX, difY;
		difX = x - (2*pan.getSize().width/3+rayon);
		difY = y - (5*pan.getSize().height/6-rayon);
		resultat = ( (difX*difX + difY*difY) <= rayon*rayon );
		return resultat;
	}
	/*
	 * FIN Methodes pour Panneau de type Boutons/Menus
	 */
	
	public void mouseEntered(MouseEvent e) {}
	
	public void mouseExited(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}

	public void mouseMoved(MouseEvent e) {
//		System.out.println("Mouse moved at : " + e.getPoint());
		if (pan.getTypeZone() == Constantes.Panneau.boutons) {
			int rayon = (pan.getSize().width < pan.getSize().height ) ? pan.getSize().width/8 : pan.getSize().height/8;
			int x = e.getX();
			int y = e.getY();
			
			if ( estSurUndo(x, y, rayon) ){
				System.out.println("passage sur le bouton Annuler");
				Point centre = new Point(pan.getSize().width/4, pan.getSize().height/6 + rayon);
				pan.setBoutonSurligne(centre);
			}
			else if ( estSurConseils(x, y, rayon) ){
				System.out.println("passage sur le bouton Conseils");
				Point centre = new Point(pan.getSize().width/2 + rayon, pan.getSize().height/6 + rayon);
				pan.setBoutonSurligne(centre);
			}
			else if ( estSurAide(x, y, rayon) ){
				System.out.println("passage sur le bouton Aide");
				Point centre = new Point(pan.getSize().width/3 + rayon, 5*pan.getSize().height/6 - rayon);
				pan.setBoutonSurligne(centre);
			}
			else if ( estSurMenu(x, y, rayon) ){
				System.out.println("passage sur le bouton Menu");
				Point centre = new Point(2*pan.getSize().width/3 + rayon, 5*pan.getSize().height/6 - rayon);
				pan.setBoutonSurligne(centre);
			}
			else { 
				System.out.println("passage vide");
				pan.setBoutonSurligne(null);
			}
		}
		pan.repaint();
	}

}
