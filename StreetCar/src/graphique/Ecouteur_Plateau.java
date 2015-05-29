package graphique;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import joueurPackage.JoueurHumain;
import mainPackage.Moteur;

public class Ecouteur_Plateau implements MouseListener, MouseMotionListener{
	Panneau_Plateau pan;
	Moteur mot;
	
	public Ecouteur_Plateau (Panneau_Plateau referencePan, Moteur referenceMoteur){
		pan = referencePan;
		mot = referenceMoteur;
	}
	
	public void mousePressed(MouseEvent e) {
		
		if (mot.getTabPlayers()[mot.getcurrentPlayer()].getType() == 0){
			// Joueur actif
			JoueurHumain joueur = (JoueurHumain) mot.getTabPlayers()[mot.getcurrentPlayer()];
			int buttonDown = e.getButton();
			
			// Coordonnees
			int x = e.getX();
			int y = e.getY();
			
			// on supprime le decalage de dessin par rapport a la bordure gauche.
			int piocheX = x - pan.getDepart();
			int piocheY = y;
			
			int caseX = (x - pan.getDepart()) / pan.getTailleCase();
			int caseY = (y - pan.getDepart()) / pan.getTailleCase();
			
//			System.out.println("casesX/Y : " + caseX + " " + caseY);
			
			switch (buttonDown){
			// bouton droit de la souris
			case MouseEvent.BUTTON3:
				if ( estDansMain(piocheX, piocheY) && (mot.getcurrentPlayer() == mainNo(piocheY) - 1) ){
					pan.setPiocheSelectionnee(false);
					pan.setCaseSelectionnee(false);
					int numeroTuile = tuileNo(piocheX);
					int numeroMain = mainNo(piocheY);
					joueur.coupTourner(numeroTuile);
					
					selectionnerMain(numeroTuile, numeroMain);
					//selectionnerPioche();
				}
				break;
			// bouton gauche de la souris
			case MouseEvent.BUTTON1:
				// si le joueur tente de cliquer
				if ( estSurPioche(x, y) ){
					System.out.println("clic sur pioche");
					
					pan.setCaseSelectionnee(false);
					pan.setMainSelectionnee(-1);
					joueur.coupPiocher();
					selectionnerPioche();
				}
/*
//TODO faire la rotation de la case
				else if ( estSurBoutonRotation(piocheX, piocheY) ){
					System.out.println("Je suis passé par là");
					j.coupTourner(tuileNo(piocheX));
				}
*/				
				// si le joueur selectionne une carte dans sa main
				else if ( estDansMain(piocheX, piocheY) ){
					System.out.println("clic sur une main de joueur");
					
					int numeroTuile = tuileNo(piocheX);
					int numeroMain = mainNo(piocheY);
					
					if ( mot.getcurrentPlayer() != numeroMain-1 ){
						joueur.coupVoler(numeroTuile);
					}
					else {
						pan.setPiocheSelectionnee(false);
						pan.setCaseSelectionnee(false);
						
						// joueur.coupSelectionTuile(numeroTuile);
					}
					selectionnerMain(numeroTuile, numeroMain);
				}
				// si le joueur clique en dehors du plateau
				else if ( estSurPlateau(caseX, caseY) ){
					System.out.println("clic sur le plateau");
					
					pan.setPiocheSelectionnee(false);
					// Pour voir si l'on a selectionne au prealable 
					// la main du joueur
					if (pan.getMainSelectionnee() == mot.getcurrentPlayer()+1){
						joueur.coupPlacerTuile(pan.getTuileMainSelectionnee(), caseX, caseY);
						pan.setMainSelectionnee(-1);
					}
					else {
						selectionnerCase(caseX, caseY);
						pan.setCaseSelectionnee(true);
					}
				}
				else {
					System.out.println("clic hors de toute zone particuliere");
					
					caseX = -1;
					caseY = -1;
					pan.setMainSelectionnee(-1);
					pan.setPiocheSelectionnee(false);
					pan.setCaseSelectionnee(false);
					pan.repaint();
				}
				break;
			}
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}

	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}
	
	
	/*
	 * Methodes Private de EcouteurSouris_Plateau
	 */
	private boolean estSurPlateau(int caseX, int caseY) {
		boolean clicValide = true;
		
		if ( caseX <= 0 || caseX > mot.getPlateau().length()-2 
		||	 caseY <= 0 || caseY > mot.getPlateau().length()-2 ){
			clicValide = false;
		}
		return clicValide;
	}
	private boolean estSurPioche(int piocheX, int piocheY) {
		boolean clicValideSurPioche = false;
		int borneGauche = pan.getPositionXPioche();
		int borneDroite = borneGauche + pan.getDimensionPioche();
		int borneHaute = pan.getPositionYPioche();
		int borneBasse = borneHaute + pan.getDimensionPioche();
		
		if ( piocheX >= borneGauche && piocheX <= borneDroite
		&&	 piocheY >= borneHaute && piocheY <= borneBasse ){
			clicValideSurPioche = true;
		}
		return clicValideSurPioche;
	}
	private boolean estDansMain(int piocheX, int piocheY) {
		boolean clicValide = true;
		
		piocheX = piocheX + pan.getDepart();
		
		// indique dans quelle main le clic a ete effectue
		int numeroMain = mainNo(piocheY);
		int zoneDeLaMain = pan.getDecalageMain() + 4*( pan.getTailleCaseMain() + pan.getPetitEcartMain() ) + pan.getTailleCaseMain();
		
		if (piocheX >= pan.getDecalageMain() && piocheX <= zoneDeLaMain && numeroMain != -1){
			int nombreEcarts = 4;
			
			for (int numeroEcart = 0; numeroEcart < nombreEcarts; numeroEcart++){
				int borneGauche = pan.getDecalageMain() + pan.getTailleCaseMain() + numeroEcart * (pan.getTailleCaseMain() + pan.getPetitEcartMain());
				int borneDroite = borneGauche + pan.getPetitEcartMain();
				
				if (piocheX > borneGauche && piocheX < borneDroite){
					clicValide = false;
				}
			}
		}
		else {
			clicValide = false;
		}
		return clicValide;
	}

	private void selectionnerMain (int numeroTuile, int numeroMain){
		pan.setMainSelectionnee(numeroMain);
		pan.setTuileSelectionnee(numeroTuile);
		pan.repaint();
	}
	private void selectionnerPioche() {
		pan.setPiocheSelectionnee(true);
		pan.repaint();
	}
	private void selectionnerCase(int caseX, int caseY) {
		pan.setCoordXSelection(caseX);
		pan.setCoordYSelection(caseY);
		pan.repaint();
	}

	private int mainNo(int piocheY) {
		int numeroMain;
		if ( piocheY >= pan.getMaintDuBas() && piocheY <= pan.getMaintDuBas() + pan.getTailleCaseMain() ){
			numeroMain = 1;
		}
		else if ( piocheY >= pan.getMainDuHaut() && piocheY <= pan.getMainDuHaut() + pan.getTailleCaseMain() ){
			numeroMain = 2;
		}
		else {
			numeroMain = -1;
		}
		return numeroMain;
	}
	private int tuileNo(int piocheX) {
		piocheX = piocheX + pan.getDepart();
		// on definit les deux bornes d'une tuile
		int borneGauche = pan.getDecalageMain();
		int borneDroite = borneGauche + pan.getTailleCaseMain();
		
		for (int numeroTuile = 0; numeroTuile < 5; numeroTuile++){
			// Si la position X se trouve entre les 2 bornes, on retourne le numero de la tuile
			if ( piocheX >= borneGauche && piocheX <= borneDroite ){
				return numeroTuile;
			}
			// Sinon on decale les bornes pour le prochain test
			else {
				borneGauche = borneDroite + pan.getPetitEcartMain();
				borneDroite = borneGauche + pan.getTailleCaseMain();
			}
		}
		return -1;
	}
}
