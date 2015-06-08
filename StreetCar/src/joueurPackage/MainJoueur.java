package joueurPackage;

import objectPackage.tuilePackage.Tuile;
import constantesPackages.Constantes;

public class MainJoueur {
	Tuile[] cartesJoueur;
	
	public MainJoueur (){
		cartesJoueur = new Tuile[Constantes.Plateau.nbCartesJoueur];
		instanciation();
	}
	
	public String toString(){
		String resultat = nombreDeTuilesJouables() + "\n"; //On peut ajouter "nombre de tuiles : " +
		for (int numTuile = 0; numTuile < cartesJoueur.length; numTuile++){
			//resultat += "\t Debut Tuile \n";
			resultat += cartesJoueur[numTuile].toString() + "\n";
			//resultat += "\t Fin Tuile \n";
		}
		return resultat;
	}
	
	
	public void instanciation (){
		cartesJoueur[0] = Tuile.newLigneDroite(); // La main est créée de cette manière.
		cartesJoueur[1] = Tuile.newLigneDroite(); // Pour pouvoir la modifier facilement.
		cartesJoueur[2] = Tuile.newLigneDroite();
		cartesJoueur[3] = Tuile.newVirage();
		cartesJoueur[4] = Tuile.newVirage();
	}
	private int nombreDeTuilesJouables (){
		int compteur = 0;
		for (int numTuile = 0; numTuile < cartesJoueur.length; numTuile++){
			if ( cartesJoueur[numTuile] != null ){
				compteur++;
			}
		}
		return compteur;
	}

	/**
	 * Récupère la p ième tuile de la main
	 * @param p
	 * @return
	 */
	public Tuile getTuileAt(int p) {
		return cartesJoueur[p];
	}

	public int length (){
		return cartesJoueur.length;
	}
	/**
	 * Modifie la p ième tuile de la main
	 * @param p
	 * @param t
	 */
	public void setTuileAt(int p, Tuile t) {
		cartesJoueur[p] = t;
	}
	
	/**
	 * Vérifie si la main est pleine ou non.
	 * @return
	 */
	public boolean isFull() {
		int i = 0; boolean b = true;
		while (i < 5 && b) {
			if (cartesJoueur[i] == null) {
				b = false;
			}
			i++;
		}
		return b;
	}
	
	/**
	 * Tourne la p ième tuile de la main
	 * @param p
	 */
	public void tourneTuileAt(int p) {
		cartesJoueur[p].rotation();
	}
	
	/**
	 * Récupère et supprime la p ième tuile de la main
	 * @param p
	 * @return
	 */
	public Tuile volerTuile(int p) {
		Tuile t = cartesJoueur[p];
		cartesJoueur[p] = null;
		return t;
	}
	
	/**
	 * Remplace un espace vide dans la main (null) par la tuile pris en paramètre
	 * ne fais rien si la main est pleine
	 * @param t
	 */
	public void ajouterCarte(Tuile t) {
		int i = 0; boolean b = true;
		while (i < 5 && b) {
			if (cartesJoueur[i] == null) {
				cartesJoueur[i] = t;
				b = false;
			}
			i++;
		}
	}
	
	public MainJoueur clone() {
		MainJoueur m = new MainJoueur();
		for (int i = 0; i < 5; i++) {
			if (cartesJoueur[i] != null)
				m.setTuileAt(i, cartesJoueur[i].clone());
			else 
				m.setTuileAt(i, null);
		}
		return m;
	}
	
}
