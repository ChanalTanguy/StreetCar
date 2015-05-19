package joueurPackage;

import java.awt.Point;

import constantesPackages.Constantes;

public class Coup {

	private String typeCoup; // Tourner Tuile, Poser tuile, Piocher, Voler
	
	private int numeroTuileMain; // Numéro de tuile si on a besoin d'une tuile (1-5)
							// 0 si on a pas besoin de tuiles
	
	private Point coordonneePlateau; // Coordonnée si on a besoin d'une coordonnée sur le tableau
								 // null si on a pas besoin de tuile.
	
	public Coup(String type, int tuile, Point coordonnee) {
		this.typeCoup = type;
		this.numeroTuileMain = tuile;
		this.coordonneePlateau = coordonnee;
	}
	
	public String getType() {
		return typeCoup;
	}

	public int getTuile() {
		return numeroTuileMain;
	}
	
	public Point getCoordonnee() {
		return (Point) coordonneePlateau.clone();
	}
	
	/**
	 * Crée un coup Rotation
	 * @param tuile
	 * @return
	 */
	static public Coup newRotation(int tuile) {
		return new Coup(Constantes.Coup.rotation,tuile,null);
	}
	
	/**
	 * Crée un coup Placement
	 * @param tuile
	 * @return
	 */
	static public Coup newPlacement(int tuile, int x, int y) {
		return new Coup(Constantes.Coup.placement,tuile,new Point(x,y));
	}
	
	/**
	 * Crée un coup Pioche
	 * @param tuile
	 * @return
	 */
	static public Coup newPioche() {
		return new Coup(Constantes.Coup.pioche,0,null);
	}
	
	/**
	 * Crée un coup Vol
	 * @param tuile
	 * @return
	 */
	static public Coup newVol(int tuile) {
		return new Coup(Constantes.Coup.vol,tuile,null);
	}
	
}
