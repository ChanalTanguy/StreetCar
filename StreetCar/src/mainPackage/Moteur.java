package mainPackage;

import constantesPackages.Constantes;
import joueurPackage.*;

public class Moteur {

	private Joueur[] players;
	private int currentPlayer;
	private Plateau plateauDeJeu;
	private int nbActions;
	
	/*
	 * Constructeur
	 */
	public Moteur(Plateau referencePlateau) {
		players = new Joueur[2];
		players[0] = new JoueurHumain(this);
		players[0] = new JoueurIA(this);
		currentPlayer = 0;
		plateauDeJeu = referencePlateau;
		nbActions = 2;
		players[currentPlayer].attendCoup();
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
	
	
	/*
	 * Methodes du Moteur
	 */
	
	/**
	 * Vérifie si un coup est valide puis l'execute si c'est le cas. 
	 * Change le joueur si besoin puis permet au joueur courant de jouer.
	 * @param c
	 */
	public void jouerCoup(Coup c) {
		if (coupValide(c)) {
			if (c.getType().equals(Constantes.Coup.placement)) {
				players[currentPlayer].jouerTuileSurPlateau(c.getTuile(), c.getCoordonnee().x, c.getCoordonnee().y, plateauDeJeu);
				nbActions--;
			} else if (c.getType().equals(Constantes.Coup.rotation)) {
				players[currentPlayer].tournerTuileMain(c.getTuile());
				// La rotation est une action gratuite
			} else if (c.getType().equals(Constantes.Coup.vol)) {
				players[currentPlayer].volerTuile(c.getTuile(), players[(currentPlayer+1)%2]);
				nbActions--;
			} else if (c.getType().equals(Constantes.Coup.pioche)) {
				players[currentPlayer].piocher();
				nbActions = 0;
			} else {
				System.out.println("Erreur : Cas non géré"); // Cas sensé inateignable
			}
			
			if (nbActions == 0) {
				currentPlayer = (currentPlayer+1)%2;
				nbActions = 4;
			}
		}
		else {
			/*	/!\ ATTENTION /!\
			 *  Sinon mettre un message d'erreur <=> notifications
			 */
			
		}

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
				// Vérifie si le placement est valide
				return plateauDeJeu.coupValide(c);
			} else if (c.getType().equals(Constantes.Coup.rotation)) {
				return true; // Rien d'autre à vérifier
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
