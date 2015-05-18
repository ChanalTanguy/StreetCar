package mainPackage;

import tuilePackage.Tuile;
import constantesPackages.Constantes;

public class Plateau {
	private Tuile[][] plateau;
	
	public Plateau (){
		plateau = new Tuile[Constantes.Dimensions.dimensionPlateau][Constantes.Dimensions.dimensionPlateau];
	}
	
	public Tuile[][] getPlateau (){
		return plateau;
	}
	
	public boolean coupValide (/*parametre a fournir*/){
		boolean resultat = false;
		
		return resultat;
	}
	
	public void executerCoup (/*parametre a fournir*/){
		
	}
}
