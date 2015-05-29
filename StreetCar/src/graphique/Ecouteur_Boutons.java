package graphique;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import windowPackage.MenuWindow;

public class Ecouteur_Boutons implements MouseListener, MouseMotionListener{
	Panneau_Boutons pan;
	int rayon;
	
	public Ecouteur_Boutons (Panneau_Boutons referencePan){
		pan = referencePan;
	}
	
	public void setRayon (int newRayon){
		rayon = newRayon;
	}
	
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		if ( estSurUndo(x, y, rayon) ){
			pan.changeImageUndo("bouton_undo_a.png");
			System.out.println("clic sur le bouton Annuler");
		}
		else if ( estSurConseils(x, y, rayon) ){
			pan.changeImageConseil("bouton_conseil_a.png");
			System.out.println("clic sur le bouton Conseils");
		}
		else if ( estSurAssistance(x, y, rayon) ){
			pan.changeImageAssistance("bouton_aide_a.png");
			System.out.println("clic sur le bouton Assistance");
		}
		else if ( estSurMenu(x, y, rayon) ){
			pan.changeImageMenu("bouton_menu_a.png");
			System.out.println("clic sur le bouton Menu");
			MenuWindow menu = new MenuWindow();
			menu.openMenuWindow();
			pan.changeImageMenu("bouton_menu.png");
		}
		else { System.out.println("clic vide"); }
		pan.repaint();
	}
	
	public void mouseReleased(MouseEvent e) {
		pan.changeImageUndo("bouton_undo.png");
		pan.changeImageMenu("bouton_menu.png");
		pan.changeImageAssistance("bouton_aide.png");
		pan.changeImageConseil("bouton_conseil.png");
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
		int diffX = x - pan.getCentreUndo().x;
		int diffY = y - pan.getCentreUndo().y;
		
		// "Redimensionner" le rayon car l'image a ete 1.5 fois celle de base.
		double valeurExacteRayon = 2*rayon/3;
		
		resultat = ( (diffX*diffX + diffY*diffY) <= valeurExacteRayon*valeurExacteRayon);
		
		if ( resultat ){
			pan.changeImageUndo("bouton_undo_s.png");
		}
		else {
			pan.changeImageUndo("bouton_undo.png");
		}
		return resultat;
	}
	private boolean estSurConseils(int x, int y, int rayon) {
		boolean resultat;
		int diffX = x - pan.getCentreConseils().x;
		int diffY = y - pan.getCentreConseils().y;

		// "Redimensionner" le rayon car l'image a ete 1.5 fois celle de base.
		double valeurExacteRayon = 2*rayon/3;
		
		resultat = ( (diffX*diffX + diffY*diffY) <= valeurExacteRayon*valeurExacteRayon );
		if (resultat) {
			pan.changeImageConseil("bouton_conseil_s.png");
		}
		else{
			pan.changeImageConseil("bouton_conseil.png");
		}
		return resultat;
	}
	private boolean estSurAssistance(int x, int y, int rayon) {
		boolean resultat;
		int diffX = x - pan.getCentreAssistance().x;
		int diffY = y - pan.getCentreAssistance().y;

		// "Redimensionner" le rayon car l'image a ete 1.5 fois celle de base.
		double valeurExacteRayon = 2*rayon/3;

		resultat = ( (diffX*diffX + diffY*diffY) <= valeurExacteRayon*valeurExacteRayon );
		if (resultat) {
			pan.changeImageAssistance("bouton_aide_s.png");
		}
		else{
			pan.changeImageAssistance("bouton_aide.png");
		}
		return resultat;
	}
	private boolean estSurMenu(int x, int y, int rayon) {
		boolean resultat;
		int diffX = x - pan.getCentreMenu().x;
		int diffY = y - pan.getCentreMenu().y;

		// "Redimensionner" le rayon car l'image a ete 1.5 fois celle de base.
		double valeurExacteRayon = 2*rayon/3;
		
		resultat = ( (diffX*diffX + diffY*diffY) <= valeurExacteRayon*valeurExacteRayon );
		if (resultat) {
			pan.changeImageMenu("bouton_menu_s.png");
		}
		else{
			pan.changeImageMenu("bouton_menu.png");
		}
		return resultat;
	}
	
}
