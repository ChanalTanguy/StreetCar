package graphique;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import windowPackage.MenuWindow;

public class Ecouteur_Boutons implements MouseListener, MouseMotionListener{
	Panneau_Boutons panneauDeBoutons;
	Panneau_Plateau panneauDeJeu;
	JFrame mainWindow;
	int rayon;
	
	public Ecouteur_Boutons (Panneau_Boutons referencePanneauBoutons, JFrame mainWindow, Panneau_Plateau referencePanneauDeJeu){
		panneauDeBoutons = referencePanneauBoutons;
		panneauDeJeu = referencePanneauDeJeu;
		this.mainWindow = mainWindow;
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
			panneauDeJeu.getMoteur().annulerTour();
			panneauDeJeu.effacerCoupsJoues();
		}
		else if ( estSurConseils(x, y, rayon) ){
			panneauDeBoutons.changeImageConseil("bouton_conseil_a.png");
		}
		else if ( estSurAssistance(x, y, rayon) ){
			panneauDeBoutons.changeImageAssistance("bouton_aide_a.png");
		}
		else if ( estSurMenu(x, y, rayon) ){
			panneauDeBoutons.changeImageMenu("bouton_menu_a.png");
			MenuWindow menu = new MenuWindow();
			menu.setMainWindow(mainWindow);
			menu.openWindow();
			panneauDeBoutons.changeImageMenu("bouton_menu.png");
		}
		panneauDeBoutons.repaint();
	}
	
	public void mouseReleased(MouseEvent e) {
		panneauDeBoutons.changeImageUndo("bouton_undo.png");
		panneauDeBoutons.changeImageMenu("bouton_menu.png");
		panneauDeBoutons.changeImageAssistance("bouton_aide.png");
		panneauDeBoutons.changeImageConseil("bouton_conseil.png");
		panneauDeBoutons.repaint();
	}
	
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		if ( estSurUndo(x, y, rayon) ){}
		else if ( estSurConseils(x, y, rayon) ){}
		else if ( estSurAssistance(x, y, rayon) ){}
		else if ( estSurMenu(x, y, rayon) ){}
		else {}
		panneauDeBoutons.repaint();
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
		int diffX = x - panneauDeBoutons.getCentreUndo().x - panneauDeBoutons.getPetitDecalage();
		int diffY = y - panneauDeBoutons.getCentreUndo().y - panneauDeBoutons.getPetitDecalage();;
		
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
		int diffX = x - panneauDeBoutons.getCentreConseils().x - panneauDeBoutons.getPetitDecalage();
		int diffY = y - panneauDeBoutons.getCentreConseils().y - panneauDeBoutons.getPetitDecalage();

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
		int diffX = x - panneauDeBoutons.getCentreAssistance().x - panneauDeBoutons.getPetitDecalage();
		int diffY = y - panneauDeBoutons.getCentreAssistance().y - panneauDeBoutons.getPetitDecalage();

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
		int diffX = x - panneauDeBoutons.getCentreMenu().x - panneauDeBoutons.getPetitDecalage();
		int diffY = y - panneauDeBoutons.getCentreMenu().y - panneauDeBoutons.getPetitDecalage();

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
