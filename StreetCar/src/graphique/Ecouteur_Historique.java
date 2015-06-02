package graphique;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

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
		
		System.out.println("coords : " + coordX + " " + coordY);
		
		if ( (tourSelectionne = estSurOnglets(coordX, coordY)) != -1){
			panneauHistorique.demandeConfirmation();
			if ( panneauHistorique.getRetourConfirme() ){
				moteur.chargerTour(tourSelectionne);
			}
		}
		else if ( estSurDefilementHaut(coordX, coordY) ){
			System.out.println("sur Defilement Haut");
//			moteur.getHistorique().defilementVersHaut();
		}
		else if ( estSurDefilementBas(coordX, coordY) ){
			System.out.println("sur Defilement Bas");
//			moteur.getHistorique().defilementVersBas();
		}
		else {
			System.out.println("clic vide");
		}
	}
	public void mouseMoved(MouseEvent e) {
		int coordX = e.getX();
		int coordY = e.getY();
		if ( estSurBoutonHaut(coordX, coordY) ){ //&& pan.getZoneEncadree() != 1){
//			System.out.println("sur bouton du haut ");
			panneauHistorique.setEncadrer(1);
		}
		else if ( estSurBoutonBas(coordX, coordY) ){ //&& pan.getZoneEncadree() != 2){
//			System.out.println("sur bouton du bas ");
			panneauHistorique.setEncadrer(2);
		}
		else if ( estSurHistoriqueCentral(coordX, coordY) ){ //&& pan.getZoneEncadree() != 3){
//			System.out.println("sur historique central ");
			panneauHistorique.setEncadrer(3);
		}
		else {
			panneauHistorique.setEncadrer(0);
		}
	}
	public void mouseExited(MouseEvent e) {
		panneauHistorique.setEncadrer(0);
	}

	public void mouseReleased(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	
	/*
	 * Methodes Private de Ecouteur_Historique
	 */
	private boolean estSurBoutonHaut (int coordX, int coordY){
		boolean clicSurBoutonHaut = false;
		int limiteBasse = panneauHistorique.getHauteur()/panneauHistorique.getDiviseur();
		clicSurBoutonHaut = coordY >= 0 && coordY <= limiteBasse;
		return clicSurBoutonHaut;
	}
	private boolean estSurBoutonBas (int coordX, int coordY){
		boolean clicSurCentral = false;
		clicSurCentral = coordY > panneauHistorique.getHauteur()/panneauHistorique.getDiviseur() && coordY <= (panneauHistorique.getDiviseur()-1) * panneauHistorique.getHauteur()/panneauHistorique.getDiviseur();
		return clicSurCentral;
	}
	private boolean estSurHistoriqueCentral (int coordX, int coordY){
		boolean clicSurBoutonBas = false;
		clicSurBoutonBas = coordY > (panneauHistorique.getDiviseur()-2) * panneauHistorique.getHauteur()/panneauHistorique.getDiviseur() && coordY <= panneauHistorique.getHauteur();
		return clicSurBoutonBas;
	}
	
	private boolean estSurDefilementHaut (int coordX, int coordY){
		boolean clicValide = false;
		
		clicValide = coordX >= panneauHistorique.getBorneGauche_Bouton() && coordX <= panneauHistorique.getBorneDroite_Bouton();
		
//		System.out.println("largeur/hauteur : " + panneauHistorique.getLargeurImage() + " " + panneauHistorique.getHauteurImage() );
		
		int resultat = (2 - 3*coordX) * panneauHistorique.getHauteurImage();
		
		clicValide = clicValide && coordY >= resultat;
		
		
		return clicValide;
	}
	private boolean estSurDefilementBas (int coordX, int coordY){
		boolean clicValide = false;
		
		
		
		return clicValide;
	}
	private int estSurOnglets (int coordX, int coordY){
		int numeroTour = -1;
		
		
		return numeroTour;
	}
}
