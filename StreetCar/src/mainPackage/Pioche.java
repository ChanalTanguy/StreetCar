package mainPackage;

import java.util.ArrayList;
import java.util.Random;

import tuilePackage.Tuile;

public class Pioche extends ArrayList<Tuile>{
	
	public Pioche (){
		super();
	}
	
	// A TERMINER <= En attente de l'initialisation des differentes tuiles existantes.
	public void initialisation (){
		System.out.println("\tlancement de l'initialisation de la pioche");
		for (int i = 0; i < 30; i++){
			add(Tuile.newLigneDroite());
		}
		System.out.println("ligne droite faite");
		for (int i = 0; i < 26; i++){
			add(Tuile.newVirage());
		}
		System.out.println("virage faite");
		for (int i = 0; i < 10; i++){
			add(Tuile.newBifurcationDroite());
		}
		System.out.println("bifur droite faite");
		for (int i = 0; i < 10; i++){
			add(Tuile.newBifurcationGauche());
		}
		System.out.println("bifur gauche faite");
		for (int i = 0; i < 6; i++){
			add(Tuile.newDoubleVirage());
		}
		System.out.println("double virage faite");
		for (int i = 0; i < 6; i++){
			add(Tuile.newDoubleBifurcation());
		}
		System.out.println("double bifur");
		for (int i = 0; i < 4; i++){
			add(Tuile.newCroisement());
		}
		System.out.println("croisement");
		for (int i = 0; i < 2; i++){
			add(Tuile.newBifurcationsSeparesGauche());
		}
		System.out.println("bifur sep gauche");
		for (int i = 0; i < 2; i++){
			add(Tuile.newBifurcationsSeparesDroite());
		}
		System.out.println("bifur set droite");
		for (int i = 0; i < 4; i++){
			add(Tuile.newQuadrupleVirages());
		}
		for (int i = 0; i < 6; i++){
			add(Tuile.newBifurcationsEmbrassees());
		}
		System.out.println("\tFin de l'initialisation de la pioche");
	}
	
	/**
	 * Melange les tuiles de la pioche appelante
	 */
		
	public void shuffle (){
		Random generateur = new Random();
		Pioche tampon = new Pioche();
		
		while (!this.isEmpty()){
			int indiceElement = generateur.nextInt(this.size());
			tampon.add(this.get(indiceElement));
			this.remove(this.get(indiceElement));
		}
		this.addAll(tampon);
	}
	
/*	
	public String toString (){
		String resultat = "";
		ListIterator<Tuile> iterateurPioche = this.listIterator();
		while ( iterateurPioche.hasNext() ){
			resultat = resultat + iterateurPioche.next().toString() + " ";
		}
		return resultat;
	}
*/	
	
}
