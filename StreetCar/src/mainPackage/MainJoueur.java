package mainPackage;

import constantesPackages.Constantes;

public class MainJoueur {
	Tuile[] cartesJoueur;
	
	public MainJoueur (){
		cartesJoueur = new Tuile[Constantes.nbCartesJoueur];
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
}
