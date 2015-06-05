package graphique;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

import mainPackage.Moteur;

public class Ecouteur_Historique implements MouseListener, MouseMotionListener{
	private Panneau_Historique panneauHistorique;
	private Moteur moteur;
	
	private int aucunTour = -1;
	
	public Ecouteur_Historique (Panneau_Historique referencePan, Moteur referenceMoteur){
		panneauHistorique = referencePan;
		moteur = referenceMoteur;
	}

	public void mousePressed(MouseEvent e) {
		int coordX = e.getX();
		int coordY = e.getY();
		int tourSelectionne;
		
		if ( (tourSelectionne = estSurOnglets(coordX, coordY)) != aucunTour){
			
//			panneauHistorique.demandeConfirmation();
//			if ( panneauHistorique.getRetourConfirme() ){
				moteur.chargerTour(tourSelectionne);
//			}
		}
		else if ( estSurDefilementHaut(coordX, coordY) ){
			moteur.getHistorique().defilementVersHaut();
			panneauHistorique.changeImageDefilementHaut("histo_haut_a.png");
			panneauHistorique.repaint();
		}
		else if ( estSurDefilementBas(coordX, coordY) ){
			moteur.getHistorique().defilementVersBas();
			panneauHistorique.changeImageDefilementBas("histo_bas_a.png");
			panneauHistorique.repaint();
		}
	}
	public void mouseMoved(MouseEvent e) {
		int coordX = e.getX();
		int coordY = e.getY();
		int tourSurligne;
		if ( estSurDefilementHaut(coordX, coordY) ){
			panneauHistorique.changeImageDefilementHaut("histo_haut_s.png");
		}
		else if ( estSurDefilementBas(coordX, coordY) ){
			panneauHistorique.changeImageDefilementBas("histo_bas_s.png");
		}
		else if ( (tourSurligne = estSurOnglets(coordX, coordY)) != aucunTour ){
			moteur.montrerCoupsJoues(tourSurligne);
		}
		else {
			panneauHistorique.changeImageDefilementHaut("histo_haut.png");
			panneauHistorique.changeImageDefilementBas("histo_bas.png");
			moteur.effacerCoupsJoues();
		}
		panneauHistorique.repaint();
	}
	public void mouseReleased(MouseEvent e) {
		panneauHistorique.changeImageDefilementHaut("histo_haut.png");
		panneauHistorique.changeImageDefilementBas("histo_bas.png");
		panneauHistorique.repaint();
	}
	public void mouseExited(MouseEvent e) {
		panneauHistorique.changeImageDefilementHaut("histo_haut.png");
		panneauHistorique.changeImageDefilementBas("histo_bas.png");
		moteur.effacerCoupsJoues();
		panneauHistorique.repaint();
	}
	
	public void mouseDragged(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	
	/*
	 * Methodes Private de Ecouteur_Historique
	 */
	private boolean estSurDefilementHaut (int coordX, int coordY){
		boolean clicValide;
		
		clicValide = coordX > panneauHistorique.getBorneGauche_Bouton()+10 && coordX < panneauHistorique.getBorneDroite_Bouton()+4
				&&	 coordY > panneauHistorique.getBorneHaute_BoutonSuperieur()+15 && coordY < panneauHistorique.getBorneBasse_BoutonSuperieur();
		
		return clicValide;
	}
	private boolean estSurDefilementBas (int coordX, int coordY){
		boolean clicValide;
		
		clicValide = coordX > panneauHistorique.getBorneGauche_Bouton()+10 && coordX < panneauHistorique.getBorneDroite_Bouton()+4
				&&	 coordY > panneauHistorique.getBorneHaute_BoutonInferieur()+12 && coordY < panneauHistorique.getBorneBasse_BoutonInferieur()-1;
		
		return clicValide;
	}
	private int estSurOnglets (int coordX, int coordY){
		int numeroTour = aucunTour;
		boolean clicValide = false;
		int limiteGauche = panneauHistorique.getBorneGauche_Onglet() + 7;
		if ( coordX >= limiteGauche
		  && coordX <= limiteGauche + panneauHistorique.getDimensionOnglet() ){
			int compteur = 0;
			int limiteHaute = panneauHistorique.getBorneHaute_Onglet() + 10;
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
