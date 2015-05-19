package constantesPackages;

public class Constantes {
	
	public static class Dimensions {
		public static final int dimensionTuile = 50;
		public static final int dimensionTuileZoom = 80;
		public static final int dimensionPlateau = 14;
	}
	
	public static class Plateau {	
		public static final int nbCartesJoueur = 5;
		public static final int nbMaxTuiles = 126;
	}
	
	public static class Tuile {
		public static final int pointGauche = 1;
		public static final int pointHaut = 2;
		public static final int pointDroite = 3;
		public static final int pointBas = 4;
	}
	
	public static class Rotation {
		public static final String rotationGauche = "gauche";
		public static final String rotationDroite = "droite";
	}
	
	public static class Orientation {
		public static final String nord = "Nord";
		public static final String sud = "Sud";
		public static final String est = "Est";
		public static final String ouest = "Ouest";
	}
	
	public static class Coup {
		public static final String pioche = "Pioche";
		public static final String echange = "Echange";
		public static final String placement = "Placement";
		public static final String rotation = "Rotation";
	}
	
}
