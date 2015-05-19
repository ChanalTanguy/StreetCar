package mainPackage;

import java.awt.Dimension;

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

	public boolean coupValide (Coup coup){
		if(coup.getType().equals(Constantes.Coup.placement)) {
			// Vérifier que la case est sois vide, sois possède une sous version de la tuile qu'on veut poser
			// Et que les 4 cases adjacente ne font pas conflit
			boolean valide = false;

			Dimension coord = coup.getCoordonnee();
			int x = coord.width;
			int y = coord.height;
			Tuile nouvTuile = getTuileAt(x,y);

			valide = getTuileAt(x,y+1).canConnectTo(nouvTuile, Constantes.Orientation.est)
					&& getTuileAt(x,y-1).canConnectTo(nouvTuile, Constantes.Orientation.ouest)
					&& getTuileAt(x+1,y).canConnectTo(nouvTuile, Constantes.Orientation.nord)
					&& getTuileAt(x-1,y).canConnectTo(nouvTuile, Constantes.Orientation.sud);

			return valide;
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
