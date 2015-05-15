package mainPackage;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

import tuilePackage.Tuile;

public class Pioche extends ArrayList<Tuile>{
	
	public Pioche (){
		super();
	}
	
	public void initialisation (){
		
	}
	
	// Methode qui retourne une pioche melangee
	public Pioche mixe (){
		Random generateur = new Random();
		Pioche paquet = new Pioche();
		
		while (!this.isEmpty()){
			int indiceElement = generateur.nextInt(this.size());
			paquet.add(this.get(indiceElement));
			this.remove(this.get(indiceElement));
		}
		
		return paquet;
	}
	
	// variante de mixe
	// modifie directement la pioche appelante
	public void mixe_v2 (){
		Random generateur = new Random();
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
		ListIterator<Tuile> iterateurPioche = this.listIterator(this.size());
		while ( iterateurPioche.hasPrevious() ){
			resultat = resultat + iterateurPioche.previous().toString() + " ";
		}
		return resultat;
	}
	
	
}
