package mainPackage;

import graphique.Panneau_Historique;
import graphique.Panneau_Plateau;
import historiqPackage.Configuration;
import historiqPackage.Historique;

import java.awt.Point;

import joueurPackage.Coup;
import joueurPackage.Joueur;
import joueurPackage.JoueurHumain;
import joueurPackage.JoueurIA;
import joueurPackage.MainJoueur;
import objectPackage.Pioche;
import objectPackage.Plateau;
import constantesPackages.Constantes;

public class Moteur {
	private Joueur[] tabPlayers;
	private int currentPlayer;
	private Plateau plateauDeJeu;
	private int nbActions;
	private Pioche pioche;
	private Panneau_Plateau panJeu;
	private Panneau_Historique panHistorique;
	private Coup coupSimultane;
	
	private int numeroDeTour = 0;
	private Historique historiqueDeTours;
	
	/*
	 * Constructeur
	 */
	public Moteur(Plateau referencePlateau) {
		tabPlayers = new Joueur[2];
		tabPlayers[0] = new JoueurHumain(this,1);
		tabPlayers[0].setLigne(1);
		tabPlayers[1] = new JoueurHumain(this,4);
		tabPlayers[1].setLigne(4);
		currentPlayer = 0;
		plateauDeJeu = referencePlateau;
		nbActions = 4;
		pioche = new Pioche();
		pioche.initialisation();
		pioche.shuffle();
		
		historiqueDeTours = new Historique();
		Configuration configurationInitiale = new Configuration (tabPlayers, currentPlayer, plateauDeJeu, pioche, numeroDeTour++, historiqueDeTours);
		historiqueDeTours.add(configurationInitiale);
		historiqueDeTours.get(0).setHistorique(historiqueDeTours);
		
	}
	/*
	 * FIN Constructeur
	 */
	
	/* 
	 * Accesseurs
	 */
	public int getcurrentPlayer (){
		return currentPlayer;
	}
	public Joueur[] getTabPlayers (){
		return tabPlayers;
	}
	public Plateau getPlateau (){
		return plateauDeJeu;
	}
	public int getNbActions (){
		return nbActions;
	}
	public Pioche getPioche (){
		return pioche;
	}

	public int getNumTour(){
		return numeroDeTour;
	}
	public Historique getHistorique (){
		return historiqueDeTours;
	}
	
	public void setPanJeu (Panneau_Plateau referencePanJeu){
		panJeu = referencePanJeu;
	}
	public void setPanHistorique (Panneau_Historique referencePanHistorique){
		panHistorique = referencePanHistorique;
	}
	
	public void setcurrentPlayer (int referenceCurrentPlayer){
		currentPlayer = referenceCurrentPlayer;
	}
	public void setMainPlayers (MainJoueur referenceMainJoueur, int player){
		tabPlayers[player].setMain(referenceMainJoueur);
	}

	public void setNbActions (int referenceNbActions){
		nbActions = referenceNbActions;
	}
	public void setPioche (Pioche referencePioche){
		pioche = referencePioche;
	}
	public void setNumTour(int referenceNumTour)
	{
		numeroDeTour = referenceNumTour;
	}
	public void setPlateau(Plateau referencePlateau) {
		plateauDeJeu = referencePlateau;
	}
	
	
	
	/*
	 * FIN Accesseurs
	 */
	
	/*
	 * Methodes Public du Moteur
	 */
	public void start (){
		tabPlayers[currentPlayer].attendCoup();
	}
	public void stop () {
		tabPlayers[currentPlayer].stopPlayer();
	}
	/**
	 * Vérifie si un coup est valide puis l'execute si c'est le cas. 
	 * Change le joueur si besoin puis permet au joueur courant de jouer.
	 * @param c
	 */
	public void jouerCoup(Coup c) {
		String msg = "";
		String r;
		if (coupValide(c)) {
			if (c.getType().equals(Constantes.Coup.placement)) {
				tabPlayers[currentPlayer].jouerTuileSurPlateau(c.getTuile(), c.getCoordonnee().x, c.getCoordonnee().y, plateauDeJeu);
				nbActions--;
				coupSimultane = null;
			} else if (c.getType().equals(Constantes.Coup.vol)) {
				tabPlayers[currentPlayer].volerTuile(c.getTuile(), tabPlayers[(currentPlayer+1)%2]);
				nbActions--;
				coupSimultane = null;
			} else if (c.getType().equals(Constantes.Coup.pioche)) {
				if (!pioche.isEmpty())
					tabPlayers[currentPlayer].piocher(pioche);
				nbActions = 0;
				coupSimultane = null;
			} else {
				System.out.println("Erreur : Cas non géré"); // Cas sensé inateignable
			}
			
			if (nbActions == 0) {
				currentPlayer = (currentPlayer+1)%2;
				nbActions = 4;
				
				// Mise a Jour de l'historique de tours
				historiqueDeTours.ajouter(new Configuration (tabPlayers, currentPlayer, plateauDeJeu, pioche, numeroDeTour++, historiqueDeTours));
				panHistorique.repaint();
			}
			if (nbActions > 2)
				msg = Constantes.Message.auTourDe(currentPlayer+1);
			else 
				msg = Constantes.Message.finDeTour(currentPlayer+1);
		}
		else if ((r = plateauDeJeu.coupSimultaneValide(tabPlayers[currentPlayer].getMain().getTuileAt(c.getTuile()),c)) != null && nbActions == 4)
		{
			if (coupSimultane == null) {
				coupSimultane = c;
				msg = "Coup simultanée possible";
			}
			else if (coupSimultane.getTuile() == c.getTuile()) {
				// Erreur même tuile selectionné pour la mettre au même endroit
				msg = Constantes.Message.poseImpossible;;
				coupSimultane = null;
			}
			else {
				boolean b;
				Point p1 = coupSimultane.getCoordonnee();
				Point p2 = c.getCoordonnee();
				
				switch (r) {
					case Constantes.Orientation.nord :  b = (p1.x == p2.x && p1.y == p2.y+1); break;
					case Constantes.Orientation.sud :   b = (p1.x == p2.x && p1.y == p2.y-1); break;
					case Constantes.Orientation.est :	b = (p1.x == p2.x-1 && p1.y == p2.y); break;
					case Constantes.Orientation.ouest : b = (p1.x == p2.x+1 && p1.y == p2.y); break;
					default : b = false;
				}
						
				if (b) {
					tabPlayers[currentPlayer].jouerTuileSurPlateau(c.getTuile(), c.getCoordonnee().x, c.getCoordonnee().y, plateauDeJeu);
					tabPlayers[currentPlayer].jouerTuileSurPlateau(coupSimultane.getTuile(), coupSimultane.getCoordonnee().x, coupSimultane.getCoordonnee().y, plateauDeJeu);
					nbActions -= 2;
					msg = Constantes.Message.finDeTour(currentPlayer+1);
				} else {
					// Erreur même tuile selectionné pour la mettre au même endroit
					msg = Constantes.Message.poseImpossible;
				}
				coupSimultane = null;
			}
		}
		else {
			if (nbActions < 3)
				msg = Constantes.Message.finDeTour(currentPlayer+1);
			else if (c.getType().equals(Constantes.Coup.placement))
				msg = Constantes.Message.poseImpossible;
			else if (c.getType().equals(Constantes.Coup.vol))
				msg = Constantes.Message.volImpossible;
			else if (c.getType().equals(Constantes.Coup.pioche))
				msg = Constantes.Message.piocheImpossible;
			else {
				msg = Constantes.Message.tramImpossible; // N'EST PAS SENSE ARRIVER !
			}
			System.out.println("Mauvais coup");
			coupSimultane = null;
		}
		panJeu.setNotifications(msg);
		panJeu.repaint();
		
		tabPlayers[currentPlayer].attendCoup();
		
	}
// Methodes de Kévin
	public void annulerTour (){
		System.out.println("annulation du tour");
		Configuration dernierTour = historiqueDeTours.last();

		for (int numeroPlayer = 0; numeroPlayer < tabPlayers.length; numeroPlayer++){
			tabPlayers[numeroPlayer] = dernierTour.getJoueurAt(numeroPlayer).clone();
		}
		plateauDeJeu = dernierTour.getPlateauDuTour().clone();
		nbActions = 4;
		panJeu.repaint();
		tabPlayers[currentPlayer].attendCoup();
	}
	public void chargerTour (int numeroTourACharger){
		int numTourActif = numeroTourACharger + historiqueDeTours.getNbConfigsPrecedentes();
		
//		System.out.println("vrai tour a charger : " + numTourActif);
		
		Configuration configACharger = historiqueDeTours.get(numTourActif);
/*		
		System.out.println("numeroTour dans Moteur : " + configACharger.getNumeroTour());
		System.out.println("configs precedentes : " + configACharger.getHistorique().getNbConfigsPrecedentes());
*/		
		plateauDeJeu = configACharger.getPlateauDuTour().clone();
		pioche = configACharger.getPiocheDuTour().clone();
		tabPlayers = new Joueur[configACharger.getNombreJoueurs()];
		for (int numeroJoueur = 0; numeroJoueur < tabPlayers.length; numeroJoueur++){
			tabPlayers[numeroJoueur] = configACharger.getJoueurAt(numeroJoueur).clone();
		}
		currentPlayer = configACharger.getJoueurCourant();
		historiqueDeTours = configACharger.getHistorique().clone();

		panJeu.setNotifications(Constantes.Message.auTourDe(currentPlayer+1));
		panJeu.repaint();
		panHistorique.repaint();

		tabPlayers[currentPlayer].attendCoup();

	}
	
	/*
	 * Methodes Private du Moteur
	 */
	/**
	 * Vérifie si le coup c peut être jouer dans la configuration actuelle
	 * @param c
	 * @return
	 */
	private boolean coupValide(Coup c) {
		if (nbActions > 2) {
			if (c.getType().equals(Constantes.Coup.placement)) {
					// Vérifie si le joueur prend une tuile existante et vérifie si le placement est valide
				return (tabPlayers[currentPlayer].getMain().getTuileAt(c.getTuile()) != null) && 
						(plateauDeJeu.coupValide(tabPlayers[currentPlayer].getMain().getTuileAt(c.getTuile()),c));
			} else
				return false;
		}
		else {
			if (c.getType().equals(Constantes.Coup.vol)) {
				// true si l'autre joueur est en phase 2, que la carte volé est différente de null
				// et que la main du joueur n'est pas pleine (2 remplacements ont été fait)
				// false sinon
				return ((!tabPlayers[(currentPlayer+1)%2].getMain().isFull()) && (tabPlayers[(currentPlayer+1)%2].getPhase() == 2)
					&&	 tabPlayers[(currentPlayer+1)%2].getMain().getTuileAt(c.getTuile()) != null);
			} else if (c.getType().equals(Constantes.Coup.pioche)) {
				return true; // Rien d'autre à vérifier (Quand la main est pleine, piocher mettra fin au tour)
			} else
				return false;
		}
	}

}
