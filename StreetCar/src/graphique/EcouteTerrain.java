package graphique;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import joueurPackage.JoueurHumain;
import mainPackage.Moteur;
import panelPackage.MenuWindow;
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
		
			int buttonDown = e.getButton();
			
			//Coordonéess
			int x = e.getX();
			int y = e.getY();
			
			//
			int piocheX = x-pan.ecart;
			int piocheY = y;
			
			int caseX = (x-pan.depart)/pan.tailleCase;
			int caseY  = (y-pan.depart)/pan.tailleCase;
			
			if(buttonDown == MouseEvent.BUTTON3) {
		        // Bouton DROIT enfoncé
				if(estDansMain(piocheX, piocheY) && (mot.getcurrentPlayer() == mainNo(piocheY)-1))
				{ 	
					pan.piocher = false;
					pan.caseX = -1;
					int numCarte = carteNo(piocheX);
					int numMain = mainNo(piocheY);
					
					j.coupTourner(numCarte);
										
					illuminerMain(numCarte, numMain);
					//illuminerPioche();
				}
				pan.repaint();
				
		    }
			else if(buttonDown == MouseEvent.BUTTON1)
			{
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
				int numCarte = carteNo(piocheX);
				int numMain = mainNo(piocheY);
								
				if(mot.getcurrentPlayer() != numMain-1)
				{
					j.coupVoler(numCarte);
				}
				else
				{
					pan.piocher = false;
					pan.caseX = -1;

					//j.coupSelectionTuile(numCarte);
				}	
				illuminerMain(numCarte, numMain);
				
			}
			else if(boutonRotation(piocheX, piocheY))
			{
				//TODO faire la rotation de la case
				//j.coupTourner();
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
				if(pan.main == mot.getcurrentPlayer()+1)
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
		}
		else if (pan.getTypeZone() == Constantes.Panneau.menuBoutons) {
			int rayon = (pan.getSize().width < pan.getSize().height ) ? pan.getSize().width/8 : pan.getSize().height/8;
			int x = e.getX();
			int y = e.getY();
			
			if ( estSurUndo(x, y, rayon) != null){
				System.out.println("clic sur le bouton Annuler");
			}
			else if ( estSurConseils(x, y, rayon) != null){
				System.out.println("clic sur le bouton Conseils");
			}
			else if ( estSurAide(x, y, rayon) != null){
				System.out.println("clic sur le bouton Aide");
			}
			else if ( estSurMenu(x, y, rayon) != null){
				System.out.println("clic sur le bouton Menu");
				/*JOptionPane pop = new JOptionPane();
				pop.setSize(200, 200);
				pop.setVisible(true);
				pop.showMessageDialog(null, "bonjour", "Titre", JOptionPane.INFORMATION_MESSAGE);*/
				MenuWindow menu = new MenuWindow();
				menu.openMenuWindow();
				
			}
			else { System.out.println("clic vide"); }
		}
		
	}

	private boolean boutonRotation(int piocheX, int piocheY) {
		// TODO Auto-generated method stub
		boolean b = false;
		
		int numMain = mainNo(piocheY);
		if(numMain == mot.getcurrentPlayer()+1)
		{
			if(numMain == 1)
			{
				
			}
			else
			{
				
			}
			
			b = (piocheX >= 0 && piocheX <= 0 && piocheY >= 0 && piocheY <=0);			
		}
		return b;
	
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
	private Point2D estSurUndo(int x, int y, int rayon) {
		boolean resultat;
		Point2D centre = null;
		double difX, difY;
		difX = x - (pan.getSize().width/4 + 0.5*rayon);
		difY = y - (pan.getSize().height/6 + 1.5*rayon);
		
		
		resultat = ( (difX*difX + difY*difY) <= rayon*rayon );
		if (resultat) {
			centre = new Point2D.Double (pan.getWidth()/4 + 0.5*rayon, pan.getHeight()/6 + 1.5*rayon);
		}
		return centre;
	}
	
	private Point2D estSurConseils(int x, int y, int rayon) {
		boolean resultat;
		Point2D centre = null;
		double difX, difY;
		
		double posX_centre = (pan.getWidth()/4 + 3.5*rayon);
		double posY_centre = (pan.getHeight()/6 + 1.5*rayon);

		difX = x - posX_centre;
		difY = y - posY_centre;
		
		resultat = ( (difX*difX + difY*difY) <= rayon*rayon );
		if (resultat) {
			centre = new Point2D.Double (posX_centre, posY_centre);
		}
		return centre;
	}	

	private Point2D estSurAide(int x, int y, int rayon) {
		boolean resultat;
		Point2D centre = null;
		double difX, difY;

		double posX_centre = (pan.getWidth()/3 + 0.5*rayon);
		double posY_centre = (2*pan.getHeight()/3 - 0.5*rayon);
		
		difX = x - posX_centre;
		difY = y - posY_centre;
		
		resultat = ( (difX*difX + difY*difY) <= rayon*rayon );
		if (resultat){
			centre = new Point2D.Double(posX_centre, posY_centre);
		}
		return centre;
	}

	private Point2D estSurMenu(int x, int y, int rayon) {
		boolean resultat;
		Point2D centre = null;
		double difX, difY;
		
		double posX_centre = (pan.getWidth()/3 + 3.5*rayon);
		double posY_centre = (2*pan.getHeight()/3 - 0.5*rayon);
		
		difX = x - posX_centre;
		difY = y - posY_centre;
		resultat = ( (difX*difX + difY*difY) <= rayon*rayon );
		if (resultat){
			centre = new Point2D.Double(posX_centre, posY_centre);
		}
		return centre;
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
		if (pan.getTypeZone() == Constantes.Panneau.menuBoutons) {
			int rayon = (pan.getSize().width < pan.getSize().height ) ? pan.getSize().width/8 : pan.getSize().height/8;
			int x = e.getX();
			int y = e.getY();
			Point2D centre;
			if ( (centre = estSurUndo(x, y, rayon)) != null ){
				System.out.println("passage sur le bouton Annuler");
//				Point centre = new Point(pan.getSize().width/4, pan.getSize().height/6 + rayon);
				pan.setBoutonSurligne(centre);
			}
			else if ( (centre = estSurConseils(x, y, rayon)) != null ){
				System.out.println("passage sur le bouton Conseils");
//				Point centre = new Point(pan.getSize().width/2 + rayon, pan.getSize().height/6 + rayon);
				pan.setBoutonSurligne(centre);
			}
			else if ( (centre = estSurAide(x, y, rayon)) != null ){
				System.out.println("passage sur le bouton Aide");
//				centre = new Point(pan.getSize().width/3 + rayon, 5*pan.getSize().height/6 - rayon);
				pan.setBoutonSurligne(centre);
			}
			else if ( (centre = estSurMenu(x, y, rayon)) != null ){
				System.out.println("passage sur le bouton Menu");
//				centre = new Point(2*pan.getSize().width/3 + rayon, 5*pan.getSize().height/6 - rayon);
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
