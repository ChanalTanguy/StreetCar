package joueurPackage;

import constantesPackages.Constantes;
import java.awt.Dimension;

public class Coup {

	private String typeCoup; // Tourner Tuile, Poser tuile, Piocher, Voler
	
	private int numeroTuileMain; // Numéro de tuile si on a besoin d'une tuile (1-5)
							// 0 si on a pas besoin de tuiles
	
	private Dimension coordonneePlateau; // Coordonnée si on a besoin d'une coordonnée sur le tableau
								 // null si on a pas besoin de tuile.
	
	public Coup(String type, int tuile, Dimension coordonnee) {
		this.typeCoup = type;
		this.numeroTuileMain = tuile;
		this.coordonneePlateau = coordonnee;
	}
	
	static public Coup newRotation(int tuile) {
		return new Coup(Constantes.Coup.rotation,tuile,null);
	}
	
	static public Coup newPlacement(int tuile, int x, int y) {
		return new Coup(Constantes.Coup.placement,tuile,new Dimension(x,y));
	}
	
	static public Coup newPioche() {
		return new Coup(Constantes.Coup.pioche,0,null);
	}
	
	static public Coup newVol(int tuile) {
		return new Coup(Constantes.Coup.vol,tuile,null);
	}
	
	public String getType() {
		return typeCoup;
	}

	public int getTuile() {
		return numeroTuileMain;
	}
	
	public Dimension getCoordonnee() {
		return (Dimension) coordonneePlateau.clone();
	}
	
}
