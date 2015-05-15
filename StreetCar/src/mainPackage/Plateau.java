package mainPackage;

import tuilePackage.Tuile;
import constantesPackages.Constantes;

public class Plateau {
	Tuile[][] plateau;
	
	public Plateau (){
		plateau = new Tuile[Constantes.Dimensions.dimensionPlateau][Constantes.Dimensions.dimensionPlateau];
	}
	
}
