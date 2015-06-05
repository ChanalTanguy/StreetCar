package graphique;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import joueurPackage.JoueurHumain;
import mainPackage.Moteur;

public class Ecouteur_Plateau implements MouseListener, MouseMotionListener{
	Panneau_Plateau panneauDeJeu;
	Moteur mot;
	
	public Ecouteur_Plateau (Panneau_Plateau referencePan, Moteur referenceMoteur){
		panneauDeJeu = referencePan;
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
			int piocheX = x - panneauDeJeu.getDepart();
			int piocheY = y;
			
			int caseX = (x - panneauDeJeu.getDepart() - 8) / panneauDeJeu.getTailleCase();
			int caseY = (y - panneauDeJeu.getDepart() - 10) / panneauDeJeu.getTailleCase();
			
			switch (buttonDown){
			// bouton droit de la souris
			case MouseEvent.BUTTON3:
				if ( estDansMain(piocheX, piocheY) && (mot.getcurrentPlayer() == mainNo(piocheY) - 1) 
					&& joueur.getMain().getTuileAt(tuileNo(piocheX)) != null
					&&  (panneauDeJeu.getCoupSimultaneActif() == null 
					|| panneauDeJeu.getCoupSimultaneActif() != null && panneauDeJeu.getCoupSimultaneActif().getTuile() != tuileNo(piocheX)) ){
					
					panneauDeJeu.setPiocheSelectionnee(false);
					panneauDeJeu.setCaseSelectionnee(false);
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
					
					panneauDeJeu.setCaseSelectionnee(false);
					panneauDeJeu.setMainSelectionnee(-1);
					joueur.coupPiocher();
					selectionnerPioche();
				}

				//TODO faire la rotation de la case
				else if ( estSurRotation(piocheX, piocheY) && joueur.getMain().getTuileAt(tuileNo(piocheX)) != null){
					joueur.coupTourner(tuileNo(piocheX));
					panneauDeJeu.repaint();
				}
				
				// si le joueur selectionne une carte dans sa main
				else if ( estDansMain(piocheX, piocheY) 
					   && mot.getTabPlayers()[mainNo(piocheY)-1].getMain().getTuileAt(tuileNo(piocheX)) != null ){
					System.out.println("clic sur une main de joueur");
					
					int numeroTuile = tuileNo(piocheX);
					int numeroMain = mainNo(piocheY);
					
					if ( mot.getcurrentPlayer() != numeroMain-1 ){
						joueur.coupVoler(numeroTuile);
					}
					else {
						panneauDeJeu.setPiocheSelectionnee(false);
						panneauDeJeu.setCaseSelectionnee(false);
						
						// joueur.coupSelectionTuile(numeroTuile);
					}
					selectionnerMain(numeroTuile, numeroMain);
				}
				// si le joueur clique en dehors du plateau
				else if ( estSurPlateau(caseX, caseY) ){
					System.out.println("clic sur le plateau");
					
					panneauDeJeu.setPiocheSelectionnee(false);
					// Pour voir si l'on a selectionne au prealable 
					// la main du joueur
					if (panneauDeJeu.getMainSelectionnee() == mot.getcurrentPlayer()+1){
						joueur.coupPlacerTuile(panneauDeJeu.getTuileMainSelectionnee(), caseX, caseY);
						
						panneauDeJeu.setMainSelectionnee(-1);
					}
					else {
						selectionnerCase(caseX, caseY);
						panneauDeJeu.setCaseSelectionnee(true);
					}
				}
				else {
					System.out.println("clic hors de toute zone particuliere");
					
					caseX = -1;
					caseY = -1;
					panneauDeJeu.setMainSelectionnee(-1);
					panneauDeJeu.setPiocheSelectionnee(false);
					panneauDeJeu.setCaseSelectionnee(false);
					panneauDeJeu.repaint();
				}
				break;
			}
		}
	}
	
	public void mouseMoved(MouseEvent e) {}
	
	public void mouseReleased(MouseEvent e) {}
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
		int borneGauche = panneauDeJeu.getPositionXPioche() +10;
		int borneDroite = borneGauche + panneauDeJeu.getDimensionPioche();
		int borneHaute = panneauDeJeu.getPositionYPioche() +10;
		int borneBasse = borneHaute + panneauDeJeu.getDimensionPioche();
		
		if ( piocheX >= borneGauche && piocheX <= borneDroite
		&&	 piocheY >= borneHaute && piocheY <= borneBasse ){
			clicValideSurPioche = true;
		}
		return clicValideSurPioche;
	}
	private boolean estDansMain(int piocheX, int piocheY) {
		boolean clicValide = true;
		
		piocheX = piocheX + panneauDeJeu.getDepart();
		
		// indique dans quelle main le clic a ete effectue
		int numeroMain = mainNo(piocheY);
		int zoneDeLaMain = panneauDeJeu.getDecalageMain() + 4*( panneauDeJeu.getTailleCaseMain() + panneauDeJeu.getPetitEcartMain() ) + panneauDeJeu.getTailleCaseMain();
		
		if (piocheX >= panneauDeJeu.getDecalageMain() && piocheX <= zoneDeLaMain && numeroMain != -1){
			int nombreEcarts = 4;
			
			for (int numeroEcart = 0; numeroEcart < nombreEcarts; numeroEcart++){
				int borneGauche = panneauDeJeu.getDecalageMain() + panneauDeJeu.getTailleCaseMain() + numeroEcart * (panneauDeJeu.getTailleCaseMain() + panneauDeJeu.getPetitEcartMain());
				int borneDroite = borneGauche + panneauDeJeu.getPetitEcartMain();
				
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
	private boolean estSurRotation(int piocheX, int piocheY) {
		int coordX = panneauDeJeu.getCoordXTuile();
		int mainSelected = panneauDeJeu.getMainSelectionnee();
		int tailleCaseM = panneauDeJeu.getTailleCaseMain();
		int tailleCase = panneauDeJeu.getTailleCase();
		int depart = panneauDeJeu.getDepart();
		boolean b = false;
		
		if(mainSelected == 1) //Main basse
		{
			int main = panneauDeJeu.getMaintDuBas();
			
			if ( piocheX >= (coordX - tailleCase/3 + 10) - depart && piocheX <= (coordX-tailleCase/3 + 10 + tailleCase/2 - depart) 
			  && piocheY >= main - tailleCaseM/3 + 10 && piocheY <= main - tailleCaseM/3 + 10 + tailleCaseM/2 )
			{
				b = true;
			}
		}
		else
		{
			int main = panneauDeJeu.getMainDuHaut();
			if ( piocheX >= (coordX - tailleCase/3 + 10) - depart && piocheX <= ( coordX - tailleCase/3 + 10 + tailleCase/2 - depart) 
			  && piocheY >= main - tailleCaseM/3 + 10 && piocheY <= main - tailleCaseM/3 + 10 + tailleCaseM/2 )
			{
				b = true;
			}
		}
		
		return b;
	}
	
	private void selectionnerMain (int numeroTuile, int numeroMain){
		panneauDeJeu.setMainSelectionnee(numeroMain);
		panneauDeJeu.setTuileSelectionnee(numeroTuile);
		panneauDeJeu.repaint();
	}
	private void selectionnerPioche() {
		panneauDeJeu.setPiocheSelectionnee(true);
		panneauDeJeu.repaint();
	}
	private void selectionnerCase(int caseX, int caseY) {
		panneauDeJeu.setCoordXSelection(caseX);
		panneauDeJeu.setCoordYSelection(caseY);
		panneauDeJeu.repaint();
	}
	
	private int mainNo(int piocheY) {
		int numeroMain;
		if ( piocheY >= panneauDeJeu.getMaintDuBas() && piocheY <= panneauDeJeu.getMaintDuBas() + panneauDeJeu.getTailleCaseMain() ){
			numeroMain = 1;
		}
		else if ( piocheY >= panneauDeJeu.getMainDuHaut() && piocheY <= panneauDeJeu.getMainDuHaut() + panneauDeJeu.getTailleCaseMain() ){
			numeroMain = 2;
		}
		else {
			numeroMain = -1;
		}
		return numeroMain;
	}
	private int tuileNo(int piocheX) {
		piocheX = piocheX + panneauDeJeu.getDepart();
		// on definit les deux bornes d'une tuile
		int borneGauche = panneauDeJeu.getDecalageMain();
		int borneDroite = borneGauche + panneauDeJeu.getTailleCaseMain();
		
		for (int numeroTuile = 0; numeroTuile < 5; numeroTuile++){
			// Si la position X se trouve entre les 2 bornes, on retourne le numero de la tuile
			if ( piocheX >= borneGauche && piocheX <= borneDroite ){
				return numeroTuile;
			}
			// Sinon on decale les bornes pour le prochain test
			else {
				borneGauche = borneDroite + panneauDeJeu.getPetitEcartMain();
				borneDroite = borneGauche + panneauDeJeu.getTailleCaseMain();
			}
		}
		return -1;
	}
}
