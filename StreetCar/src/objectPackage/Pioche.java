package objectPackage;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

import objectPackage.tuilePackage.Tuile;

public class Pioche extends ArrayList<Tuile>{
	
	/*
	 * Constructeur
	 */
	public Pioche (){
		super();
	}
	/*
	 * FIN Constructeur
	 */
	
	/*
	 * Methode Public de Pioche
	 */
	public void initialisation (){
		for (int i = 0; i < 30; i++){
			add(Tuile.newLigneDroite());
		}
		for (int i = 0; i < 26; i++){
			add(Tuile.newVirage());
		}
		for (int i = 0; i < 10; i++){
			add(Tuile.newBifurcationDroite());
		}
		for (int i = 0; i < 10; i++){
			add(Tuile.newBifurcationGauche());
		}
		for (int i = 0; i < 10; i++){
			add(Tuile.newSeparation());
		}
		for (int i = 0; i < 6; i++){
			add(Tuile.newDoubleVirage());
		}
		for (int i = 0; i < 6; i++){
			add(Tuile.newDoubleBifurcation());
		}
		for (int i = 0; i < 4; i++){
			add(Tuile.newCroisement());
		}
		for (int i = 0; i < 2; i++){
			add(Tuile.newBifurcationsSeparesGauche());
		}
		for (int i = 0; i < 2; i++){
			add(Tuile.newBifurcationsSeparesDroite());
		}
		for (int i = 0; i < 4; i++){
			add(Tuile.newQuadrupleVirages());
		}
		for (int i = 0; i < 6; i++){
			add(Tuile.newBifurcationsEmbrassees());
		}
	}
	/**
	 * Melange les tuiles de la pioche appelante
	 */
	public void shuffle (){
		Random generateur = new Random(0);
		Pioche tampon = new Pioche();
		
		while (!this.isEmpty()){
			int indiceElement = generateur.nextInt(this.size());
			tampon.add(this.get(indiceElement));
			this.remove(this.get(indiceElement));
		}
		this.addAll(tampon);
	}
	
	public String toString (){
		String resultat = "";
		ListIterator<Tuile> iterateurPioche = this.listIterator();
		resultat = resultat + this.size() + System.getProperty("line.separator");
		while ( iterateurPioche.hasNext() ){
			resultat = resultat + iterateurPioche.next().toString() + " ";
		}
		return resultat;
	}	
	public Pioche clone (){
		Pioche renvoi = new Pioche();
		for (int indexTuile = 0; indexTuile < this.size(); indexTuile++){
			renvoi.add(this.get(indexTuile).clone());
		}
		return renvoi;
	}
	
}
