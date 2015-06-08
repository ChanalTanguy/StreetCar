package joueurPackage;

import java.awt.Point;

import objectPackage.tuilePackage.Tuile;

public class Coup {

	public static final int nbMaxPlacements = 2;
	public static final String pioche = "Pioche";
	public static final String vol = "Vol";
	public static final String placement = "Placement";
	public static final String avanceeTrame = "AvanceeTram";
	
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
	
	public Coup clone (){
		String renvoi_typeCoup = this.typeCoup;
		int renvoi_numeroTuile = this.numeroTuileMain;
		Point renvoi_coordonnees = (Point) this.coordonneePlateau.clone();
		
		return new Coup(renvoi_typeCoup, renvoi_numeroTuile, renvoi_coordonnees);
	}
	public String toString (){
		String chaine_resultat = "";
		
		chaine_resultat += typeCoup + " " + numeroTuileMain + " " + "[" + coordonneePlateau.x + ";" + coordonneePlateau.y + "] ";
				
		return chaine_resultat;
	}
	
	/**
	 * Crée un coup Placement
	 * @param tuile
	 * @return
	 */
	static public Coup newPlacement(int tuile, int x, int y) {
		return new Coup(placement,tuile,new Point(x,y));
	}
	
	/**
	 * Crée un coup AvanceeTram
	 * @param x
	 * @param y
	 * @return
	 */
	static public Coup newAvanceeTram (int x, int y){
		return new Coup(avanceeTrame, 0, new Point(x, y));
	}
	
	/**
	 * Crée un coup Pioche
	 * @param tuile
	 * @return
	 */
	static public Coup newPioche() {
		return new Coup(pioche,0,null);
	}
	
	/**
	 * Crée un coup Vol
	 * @param tuile
	 * @return
	 */
	static public Coup newVol(int tuile) {
		return new Coup(vol,tuile,null);
	}
	
}
