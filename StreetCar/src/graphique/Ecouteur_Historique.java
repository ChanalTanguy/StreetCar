package graphique;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

import mainPackage.Moteur;

public class Ecouteur_Historique implements MouseListener, MouseMotionListener{
	Panneau_Historique panneauHistorique;
	Moteur moteur;
	
	public Ecouteur_Historique (Panneau_Historique referencePan, Moteur referenceMoteur){
		panneauHistorique = referencePan;
		moteur = referenceMoteur;
	}

	public void mousePressed(MouseEvent e) {
		int coordX = e.getX();
		int coordY = e.getY();
		int tourSelectionne;
		
		if ( (tourSelectionne = estSurOnglets(coordX, coordY)) != -1){
			
//			panneauHistorique.demandeConfirmation();
//			if ( panneauHistorique.getRetourConfirme() ){
				moteur.chargerTour(tourSelectionne);
//			}
		}
		else if ( estSurDefilementHaut(coordX, coordY) ){
			moteur.getHistorique().defilementVersHaut();
			panneauHistorique.repaint();
		}
		else if ( estSurDefilementBas(coordX, coordY) ){
			moteur.getHistorique().defilementVersBas();
			panneauHistorique.repaint();
		}
		else {}
	}
	public void mouseMoved(MouseEvent e) {
		int coordX = e.getX();
		int coordY = e.getY();
		if ( estSurDefilementHaut(coordX, coordY) && panneauHistorique.getZoneEncadree() != 1 ){
			panneauHistorique.setEncadrer(1);
		}
		else if ( estSurDefilementBas(coordX, coordY) && panneauHistorique.getZoneEncadree() != 2 ){
			panneauHistorique.setEncadrer(2);
		}
		else if ( panneauHistorique.getZoneEncadree() != -1){
			panneauHistorique.setEncadrer(-1);
		}
//		panneauHistorique.repaint();
	}
	public void mouseExited(MouseEvent e) {
		panneauHistorique.setEncadrer(-1);
	}

	public void mouseReleased(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	
	/*
	 * Methodes Private de Ecouteur_Historique
	 */
	private boolean estSurDefilementHaut (int coordX, int coordY){
		boolean clicValide;
		
		clicValide = coordX > panneauHistorique.getBorneGauche_Bouton() && coordX < panneauHistorique.getBorneDroite_Bouton()
				&&	 coordY > panneauHistorique.getBorneHaute_BoutonSuperieur() && coordY < panneauHistorique.getBorneBasse_BoutonSuperieur();
		
		return clicValide;
	}
	private boolean estSurDefilementBas (int coordX, int coordY){
		boolean clicValide;
		
		clicValide = coordX > panneauHistorique.getBorneGauche_Bouton() && coordX < panneauHistorique.getBorneDroite_Bouton()
				&&	 coordY > panneauHistorique.getBorneHaute_BoutonInferieur() && coordY < panneauHistorique.getBorneBasse_BoutonInferieur();
		
		return clicValide;
	}
	private int estSurOnglets (int coordX, int coordY){
		int numeroTour = -1;
		boolean clicValide = false;
		
		if ( coordX >= panneauHistorique.getBorneGauche_Onglet() 
		  && coordX <= panneauHistorique.getBorneGauche_Onglet() + panneauHistorique.getDimensionOnglet()){
			int compteur = 0;
			int limiteHaute = panneauHistorique.getBorneHaute_Onglet();
			while ( compteur < panneauHistorique.getNbOngletsActifs() && !clicValide ){
				clicValide = coordY >= limiteHaute && coordY <= limiteHaute + panneauHistorique.getDimensionOnglet();
				if ( !clicValide ){
					limiteHaute += panneauHistorique.getDimensionOnglet() + panneauHistorique.getEcart();
					compteur++;
				}
				else {
					numeroTour = compteur;
				}
			}
		}
		
		return numeroTour;
	}
	
}
