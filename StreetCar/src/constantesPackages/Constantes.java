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
		public static final int histo_central = 3;
		public static final int menuBoutons = 4;
		public static final int histo_bouton_nord = 5;
		public static final int histo_bouton_sud = 6;
		public static final int histo_est = 7;
		public static final int histo_ouest = 8;
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

	public static class Message {
		public static String auTourDe (int numeroJoueur){
			String notif = "C'est au tour du joueur ";
			notif = notif + numeroJoueur;
			return notif;
		}
		
		public static String partieTerminee (int numeroGagnant){
			String notif = "La partie est remportée par :\n";
			notif = notif + "\tle joueur " + numeroGagnant;
			return notif;
		}
		
		public static String finDeTour (int numeroJoueur){
			String notif = "Joueur " + numeroJoueur;
			notif = notif + " piochez pour avoir 5 cartes en main;\n";
			notif = notif + "(Vous pouvez éventuellement en voler à l'adversaire \n";
			notif = notif + "s'il a terminé sa ligne";
			return notif;
		}
		
		public static final String poseImpossible = "Vous ne pouvez pas poser \nvotre tuile ici";
		public static final String piocheImpossible = "Vous ne pouvez piocher \nqu'à la fin de votre tour";
		public static final String volImpossible = "Vous ne pouvez pas voler ce joueur : \nCe joueur n'a pas fini sa ligne";
		public static final String tramImpossible = "Votre tramway ne peut pas aller ici";
		
	}

}
