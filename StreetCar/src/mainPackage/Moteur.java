package mainPackage;

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
	
	public void jouerCoup(/* Coup c */) {
		if (plateauDeJeu.coupValide(/*c*/)) {
			plateauDeJeu.executerCoup(/*c*/);
			if (nbActions == 0){
				/*
				 * le joueur Courant doit piocher
				 */
				currentPlayer = (currentPlayer+1)%2;
				nbActions = 2;
			}
			else {
				nbActions--;
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
	
	
}
