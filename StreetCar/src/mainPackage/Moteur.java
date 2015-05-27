package mainPackage;

import java.awt.Point;

import graphique.Panneau;
import constantesPackages.Constantes;
import joueurPackage.*;

public class Moteur {

	private Joueur[] players;
	private int currentPlayer;
	private Plateau plateauDeJeu;
	private int nbActions;
	private Pioche pioche;
	private Panneau panNotif;
	private Panneau panJeu;
	private Coup coupSimultane;
	
	
	/*
	 * Constructeur
	 */
	public Moteur(Plateau referencePlateau) {
		System.out.println("\tconstructeur de moteur");
		players = new Joueur[2];
		players[0] = new JoueurIA(this,1);
		players[1] = new JoueurHumain(this,4);
		currentPlayer = 0;
		plateauDeJeu = referencePlateau;
		nbActions = 4;
		pioche = new Pioche();
		pioche.initialisation();
		pioche.shuffle();
	}
	
	/* 
	 * Accesseurs
	 */
	public int getcurrentPlayer (){
		return currentPlayer;
	}
	
	public Joueur[] getTabPlayers (){
		return players;
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
	
	public void setPanNotif(Panneau p) {
		panNotif = p;
	}
	
	public void setPanJeu (Panneau p){
		panJeu = p;
	}
	
	public void start (){
		players[currentPlayer].attendCoup();
	}
	
	/*
	 * Methodes du Moteur
	 */
	
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
				players[currentPlayer].jouerTuileSurPlateau(c.getTuile(), c.getCoordonnee().x, c.getCoordonnee().y, plateauDeJeu);
				nbActions--;
				coupSimultane = null;
			} else if (c.getType().equals(Constantes.Coup.rotation)) {
				players[currentPlayer].tournerTuileMain(c.getTuile());
				// La rotation est une action gratuite
			} else if (c.getType().equals(Constantes.Coup.vol)) {
				players[currentPlayer].volerTuile(c.getTuile(), players[(currentPlayer+1)%2]);
				nbActions--;
				coupSimultane = null;
			} else if (c.getType().equals(Constantes.Coup.pioche)) {
				if (!pioche.isEmpty())
					players[currentPlayer].piocher(pioche);
				nbActions = 0;
				coupSimultane = null;
			} else {
				System.out.println("Erreur : Cas non géré"); // Cas sensé inateignable
			}
			
			if (nbActions == 0) {
				currentPlayer = (currentPlayer+1)%2;
				nbActions = 4;
			}
			if (nbActions > 2)
				msg = Constantes.Message.auTourDe(currentPlayer+1);
			else 
				msg = Constantes.Message.finDeTour(currentPlayer+1);
		}
		else if ((r = plateauDeJeu.coupSimultaneValide(players[currentPlayer].getMain().getTuileAt(c.getTuile()),c)) != null && nbActions == 4)
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
					players[currentPlayer].jouerTuileSurPlateau(c.getTuile(), c.getCoordonnee().x, c.getCoordonnee().y, plateauDeJeu);
					players[currentPlayer].jouerTuileSurPlateau(coupSimultane.getTuile(), coupSimultane.getCoordonnee().x, coupSimultane.getCoordonnee().y, plateauDeJeu);
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
		panNotif.updateMessage(msg);
		panJeu.repaint();
		
		players[currentPlayer].attendCoup();
		
	}

	/*
	 * Methodes privee du Moteur
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
				return (players[currentPlayer].getMain().getTuileAt(c.getTuile()) != null) && 
						(plateauDeJeu.coupValide(players[currentPlayer].getMain().getTuileAt(c.getTuile()),c));
			} else if (c.getType().equals(Constantes.Coup.rotation)) {
				return ( players[currentPlayer].getMain().getTuileAt(c.getTuile()) != null ); // Rien d'autre à vérifier
			} else
				return false;
		}
		else {
			if (c.getType().equals(Constantes.Coup.vol)) {
				// true si l'autre joueur est en phase 2, que la carte volé est différente de null
				// et que la main du joueur n'est pas pleine (2 remplacements ont été fait)
				// false sinon
				return ((!players[(currentPlayer+1)%2].getMain().isFull()) && (players[(currentPlayer+1)%2].getPhase() == 2)
					&&	 players[(currentPlayer+1)%2].getMain().getTuileAt(c.getTuile()) != null);
			} else if (c.getType().equals(Constantes.Coup.pioche)) {
				return true; // Rien d'autre à vérifier (Quand la main est pleine, piocher mettra fin au tour)
			} else if (c.getType().equals(Constantes.Coup.rotation)) {
				return true; // Rien d'autre à vérifier
			} else
				return false;
		}
	}
	
}
