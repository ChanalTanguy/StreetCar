package joueurPackage;

import tuilePackage.Tuile;
import constantesPackages.Constantes;

public class MainJoueur {
	Tuile[] cartesJoueur;
	
	public MainJoueur (){
		cartesJoueur = new Tuile[Constantes.Plateau.nbCartesJoueur];
		instanciation(cartesJoueur);
	}
	
	public String toString(){
		String resultat = "";
		
		return resultat;
	}
	
	
	private void instanciation (Tuile[] tab){
		for (int i = 0; i < tab.length; i++){
			tab[i] = new Tuile();
		}
	}

	/**
	 * Récupère la p ième tuile de la main
	 * @param p
	 * @return
	 */
	public Tuile getTuileAt(int p) {
		return cartesJoueur[p];
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
		cartesJoueur[p].rotation(Constantes.Rotation.rotationGauche);
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
}
