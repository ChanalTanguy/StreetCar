package mainPackage;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

import tuilePackage.Tuile;

public class Pioche extends ArrayList<Tuile>{
	
	public Pioche (){
		super();
	}
	
	// A TERMINER <= En attente de l'initialisation des differentes tuiles existantes.
	public void initialisation (){
		
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
