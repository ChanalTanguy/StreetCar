package constantesPackages;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
	
	public static class Panneau {
		public static final int plateau = 1;
		public static final int notifications = 2;
		public static final int historiques = 3;
		public static final int boutons = 4;
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
		public static final String vol = "Vol";
		public static final String placement = "Placement";
		public static final String rotation = "Rotation";
	}
	
	public static class Images {
		
		/**
		 * retourne une BufferedImage recuperee grace a son nom donne en parametre de la methode
		 * @param nomImage
		 * @return une BufferedImage image si un fichier de nom "nomImage" existe dans l'arborescence de fichier. Leve une exception sinon.
		 */
		public static BufferedImage initTuile (String nomImage){
			BufferedImage imageResultat = null;
			String chemin = "images/tuiles/";
			try {
				chemin = chemin + nomImage;
				imageResultat = ImageIO.read(new File(chemin));
			} catch (IOException e){
				System.out.println("aucune image de ce nom trouvee");
			}
			return imageResultat;
		}
		
		/**
		 * retourne une BufferedImage recuperee grace a son nom donne en parametre de la methode
		 * @param nomImage
		 * @return une BufferedImage background si un fichier de nom "nomBackground" existe dans l'arborescence de fichier. Leve une exception sinon.
		 */
		public static BufferedImage initBackground (String nomBackground){
			BufferedImage background = null;
			String chemin = "images/background/";
			try {
				chemin = chemin + nomBackground;
				background = ImageIO.read(new File(chemin));
			} catch (IOException e){
				System.out.println("aucun background de ce nom trouve");
			}
			return background;
		}
		
		/**
		 * retourne une BufferedImage recuperee grace a son nom donne en parametre de la methode
		 * @param nomBouton
		 * @return une BufferedImage bouton si un fichier de nom "nomBouton" existe dans l'arborescence de fichier. Leve une exception sinon
		 */
		public static BufferedImage initBouton (String nomBouton){
			BufferedImage bouton = null;
			String chemin = "images/boutons/";
			try {
				chemin = chemin + nomBouton;
				bouton = ImageIO.read(new File(chemin));
			} catch (IOException e){
				System.out.println("aucun bouton de ce nom trouve");
			}
			return bouton;
		}
	}

}
