package joueurPackage;

import mainPackage.Plateau;

public class Moteur {

	Joueur players[];
	int currentPlayer;
	Plateau plateauDeJeu;
	
	public Moteur(Plateau referencePlateau) {
		players = new Joueur[2];
		players[0] = new JoueurHumain(this);
		players[0] = new JoueurIA(this);
		currentPlayer = 0;
		players[currentPlayer].attendCoup();
		plateauDeJeu = referencePlateau;
	}
	
	void jouerCoup(/* Coup c */) {
		if (plateauDeJeu.coupValide(/*c*/)) {
			plateauDeJeu.executeCoup(/*c*/);
			currentPlayer = (currentPlayer+1)%2;
		}
		else {
			/*	/!\ ATTENTION /!\
			 *  Sinon mettre un message d'erreur <=> notifications
			 */
			
		}
	}
	
}
