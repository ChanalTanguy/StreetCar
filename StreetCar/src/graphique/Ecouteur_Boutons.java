package graphique;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import windowPackage.MenuWindow;

public class Ecouteur_Boutons implements MouseListener, MouseMotionListener{
	Panneau_Boutons panneauDeBoutons;
	Panneau_Plateau panneauDeJeu;
	int rayon;
	
	public Ecouteur_Boutons (Panneau_Boutons referencePanneauBoutons, Panneau_Plateau referencePanneauDeJeu){
		panneauDeBoutons = referencePanneauBoutons;
		panneauDeJeu = referencePanneauDeJeu;
	}
	
	/*
	 * ACCESSEURS
	 */
	public void setRayon (int newRayon){
		rayon = newRayon;
	}
	/*
	 * FIN ACCESSEURS
	 */
	
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		if ( estSurUndo(x, y, rayon) ){
			panneauDeBoutons.changeImageUndo("bouton_undo_a.png");
			System.out.println("clic sur le bouton Annuler");
			panneauDeJeu.getMoteur().annulerTour();
		}
		else if ( estSurConseils(x, y, rayon) ){
			panneauDeBoutons.changeImageConseil("bouton_conseil_a.png");
			System.out.println("clic sur le bouton Conseils");
		}
		else if ( estSurAssistance(x, y, rayon) ){
			panneauDeBoutons.changeImageAssistance("bouton_aide_a.png");
			System.out.println("clic sur le bouton Assistance");
		}
		else if ( estSurMenu(x, y, rayon) ){
			panneauDeBoutons.changeImageMenu("bouton_menu_a.png");
			System.out.println("clic sur le bouton Menu");
			MenuWindow menu = new MenuWindow();
			menu.openWindow();
			panneauDeBoutons.changeImageMenu("bouton_menu.png");
		}
		else { System.out.println("clic vide"); }
		panneauDeBoutons.repaint();
	}
	
	public void mouseReleased(MouseEvent e) {
		panneauDeBoutons.changeImageUndo("bouton_undo.png");
		panneauDeBoutons.changeImageMenu("bouton_menu.png");
		panneauDeBoutons.changeImageAssistance("bouton_aide.png");
		panneauDeBoutons.changeImageConseil("bouton_conseil.png");
	}
	
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		if ( estSurUndo(x, y, rayon) ){
			System.out.println("passage sur le bouton Annuler");
		}
		else if ( estSurConseils(x, y, rayon) ){
			System.out.println("passage sur le bouton Conseils");
		}
		else if ( estSurAssistance(x, y, rayon) ){
			System.out.println("passage sur le bouton Assistance");
		}
		else if ( estSurMenu(x, y, rayon) ){
			System.out.println("passage sur le bouton Menu");
		}
		else { 
			System.out.println("passage vide");
		}
	}
	
	public void mouseDragged(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	/*
	 * Methodes Private de EcouteurSouris_Boutons
	 */
	private boolean estSurUndo (int x, int y, int rayon){
		boolean resultat;
		int diffX = x - panneauDeBoutons.getCentreUndo().x;
		int diffY = y - panneauDeBoutons.getCentreUndo().y;
		
		// "Redimensionner" le rayon car l'image a ete 1.5 fois celle de base.
		double valeurExacteRayon = 2*rayon/3;
		
		resultat = ( (diffX*diffX + diffY*diffY) <= valeurExacteRayon*valeurExacteRayon);
		
		if ( resultat ){
			panneauDeBoutons.changeImageUndo("bouton_undo_s.png");
		}
		else {
			panneauDeBoutons.changeImageUndo("bouton_undo.png");
		}
		return resultat;
	}
	private boolean estSurConseils(int x, int y, int rayon) {
		boolean resultat;
		int diffX = x - panneauDeBoutons.getCentreConseils().x;
		int diffY = y - panneauDeBoutons.getCentreConseils().y;

		// "Redimensionner" le rayon car l'image a ete 1.5 fois celle de base.
		double valeurExacteRayon = 2*rayon/3;
		
		resultat = ( (diffX*diffX + diffY*diffY) <= valeurExacteRayon*valeurExacteRayon );
		if (resultat) {
			panneauDeBoutons.changeImageConseil("bouton_conseil_s.png");
		}
		else{
			panneauDeBoutons.changeImageConseil("bouton_conseil.png");
		}
		return resultat;
	}
	private boolean estSurAssistance(int x, int y, int rayon) {
		boolean resultat;
		int diffX = x - panneauDeBoutons.getCentreAssistance().x;
		int diffY = y - panneauDeBoutons.getCentreAssistance().y;

		// "Redimensionner" le rayon car l'image a ete 1.5 fois celle de base.
		double valeurExacteRayon = 2*rayon/3;

		resultat = ( (diffX*diffX + diffY*diffY) <= valeurExacteRayon*valeurExacteRayon );
		if (resultat) {
			panneauDeBoutons.changeImageAssistance("bouton_aide_s.png");
		}
		else{
			panneauDeBoutons.changeImageAssistance("bouton_aide.png");
		}
		return resultat;
	}
	private boolean estSurMenu(int x, int y, int rayon) {
		boolean resultat;
		int diffX = x - panneauDeBoutons.getCentreMenu().x;
		int diffY = y - panneauDeBoutons.getCentreMenu().y;

		// "Redimensionner" le rayon car l'image a ete 1.5 fois celle de base.
		double valeurExacteRayon = 2*rayon/3;
		
		resultat = ( (diffX*diffX + diffY*diffY) <= valeurExacteRayon*valeurExacteRayon );
		if (resultat) {
			panneauDeBoutons.changeImageMenu("bouton_menu_s.png");
		}
		else{
			panneauDeBoutons.changeImageMenu("bouton_menu.png");
		}
		return resultat;
	}
	
}
