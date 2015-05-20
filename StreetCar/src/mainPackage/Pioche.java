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
		System.out.println("init des 30 lignes droites ok");
		for (int i = 0; i < 26; i++){
			add(Tuile.newVirage());
		}
		System.out.println("init des 26 virages ok");
		for (int i = 0; i < 10; i++){
			add(Tuile.newBifurcationDroite());
		}
		for (int i = 0; i < 10; i++){
			add(Tuile.newBifurcationGauche());
		}
		for (int i = 0; i < 6; i++){
			add(Tuile.newDoubleVirage());
		}
		for (int i = 0; i < 6; i++){
			add(Tuile.newDoubleBiffurcation());
		}
		for (int i = 0; i < 4; i++){
			add(Tuile.newCroisement());
		}
		for (int i = 0; i < 2; i++){
			add(Tuile.newBiffurcationsSeparesGauche());
		}
		for (int i = 0; i < 2; i++){
			add(Tuile.newBiffurcationsSeparesDroite());
		}
		for (int i = 0; i < 4; i++){
			add(Tuile.newQuadrupleVirages());
		}
		for (int i = 0; i < 6; i++){
			add(Tuile.newBiffurcationsEmbrassees());
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
