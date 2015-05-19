package mainPackage;

import joueurPackage.Coup;
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
	
	public boolean coupValide (Coup c){
		if(c.getType().equals(Constantes.Coup.placement)) {
			// Vérifier que la case est sois vide, sois possède une sous version de la tuile qu'on veut poser
			// Et que les 4 cases adjacente ne font pas conflit
			return false;
		} else {
			return true; // Un coup qui n'a rien a voir avec le plateau est forcément valide au yeux du plateau (Duh)
		}
	}
	
	public Tuile getTuileAt(int x, int y) {
		return plateau[x][y];
	}
	
	public void setTuileAt(int x, int y, Tuile t) {
		plateau[x][y] = t;
	}
	
}
