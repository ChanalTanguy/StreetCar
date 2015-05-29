package graphique;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Ecouteur_Historique implements MouseListener, MouseMotionListener{
	Panneau_Historique pan;
	
	public Ecouteur_Historique (Panneau_Historique referencePan){
		pan = referencePan;
	}

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {
		int coordX = e.getX();
		int coordY = e.getY();
		if ( estSurBoutonHaut(coordX, coordY) ){
			System.out.print("sur bouton du haut ");
			pan.setEncadrer(1);
		}
		else if ( estSurBoutonBas(coordX, coordY) ){
			System.out.print("sur bouton du bas ");
			pan.setEncadrer(2);
		}
		else if ( estSurHistoriqueCentral(coordX, coordY) ){
			System.out.print("sur historique central ");
			pan.setEncadrer(3);
		}
		else {
			pan.setEncadrer(0);
		}
	}

	public void mouseExited(MouseEvent e) {}
	
	public void mouseDragged(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	
	/*
	 * Methodes Private de Ecouteur_Historique
	 */
	private boolean estSurBoutonHaut (int coordX, int coordY){
		boolean clicSurBoutonHaut = false;
		int limiteBasse = pan.getHauteur()/pan.getDiviseur();
		clicSurBoutonHaut = coordY >= 0 && coordY <= limiteBasse;
		return clicSurBoutonHaut;
	}
	private boolean estSurBoutonBas (int coordX, int coordY){
		boolean clicSurCentral = false;
		clicSurCentral = coordY > pan.getHauteur()/pan.getDiviseur() && coordY <= (pan.getDiviseur()-1) * pan.getHauteur()/pan.getDiviseur();
		return clicSurCentral;
	}
	private boolean estSurHistoriqueCentral (int coordX, int coordY){
		boolean clicSurBoutonBas = false;
		clicSurBoutonBas = coordY > (pan.getDiviseur()-2) * pan.getHauteur()/pan.getDiviseur() && coordY <= pan.getHauteur();
		return clicSurBoutonBas;
	}

}
