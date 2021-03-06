package constantesPackages;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Constantes {
	
	public static class Resolution {
		public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		public static final double width = screenSize.getWidth();
		public static final double height = screenSize.getHeight();
	}
	
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
				System.out.println("chemin : " + chemin);
			}
			return background;
		}
		
		/**
		 * retourne une BufferedImage recuperee grace a son nom donne en parametre de la methode
		 * @param nomImage
		 * @return une BufferedImage background si un fichier de nom "nomBackground" existe dans l'arborescence de fichier. Leve une exception sinon.
		 */
		public static BufferedImage initNotification (String nomNotif){
			BufferedImage background = null;
			String chemin = "images/notifications/";
			try {
				chemin = chemin + nomNotif;
				background = ImageIO.read(new File(chemin));
			} catch (IOException e){
				System.out.println("aucun background de ce nom trouve");
			}
			return background;
		}
		
		/**
		 * retourne une BufferedImage recuperee grace a son nom donne en parametre de la methode
		 * @param nomImage
		 * @return une BufferedImage image si un fichier de nom "nomImage" existe dans l'arborescence de fichier. Leve une exception sinon.
		 */
		public static BufferedImage initCarte (String nomImage){
			BufferedImage imageResultat = null;
			String chemin = "images/cartes/";
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
	
		public static BufferedImage initSurbrillance (String nomSurbrillance){
			BufferedImage surbrillance = null;
			String chemin = "images/surbrillance/";
			try {
				chemin = chemin + nomSurbrillance;
				surbrillance = ImageIO.read(new File(chemin));
			} catch (IOException e){
				System.out.println("aucune surbrillance de ce nom trouvee");
			}
			
			return surbrillance;
		}
		
		public static BufferedImage initImageJoueur (String nomJoueur){
			BufferedImage imageJoueur = null;
			String chemin = "images/joueurs/";
			try {
				chemin = chemin + nomJoueur;
				imageJoueur = ImageIO.read(new File(chemin));
			} catch (IOException e){
				System.out.println("aucune image de ce nom trouvee");
			}
			
			return imageJoueur;
		}
	}

	public static class Message {
		public static BufferedImage auTourDe (int numeroJoueur){
			//old
			//String notif = "C'est au tour du joueur ";
			//notif = notif + (numeroJoueur+1);
			BufferedImage notif;
			if (numeroJoueur==0)
			{
				notif = Images.initNotification("tourJ1.png");
			}
			else
			{
				notif = Images.initNotification("tourJ2.png");
			}
			return notif;
		}
		
		public static BufferedImage partieTerminee (int numeroGagnant){
			BufferedImage notif;
			if (numeroGagnant==1)
			{
				notif = Images.initNotification("victoireJ1.png");
			}
			else
			{
				notif = Images.initNotification("victoireJ2.png");
			}
			//String notif = "La partie est remportée par :\n";
			//notif = notif + "\tle joueur " + numeroGagnant;
			return notif;
		}
		
		public static BufferedImage finDeTour (int numeroJoueur){
			//String notif = "Joueur " + (numeroJoueur+1);
			//notif = notif + " piochez pour valide votre tour\n";
			//notif = notif + "(Vous pouvez éventuellement en voler à l'adversaire \n";
			//notif = notif + "s'il a terminé sa ligne";
			BufferedImage notif;
			if (numeroJoueur==0)
			{
				notif = Images.initNotification("piocheJ1.png");
			}
			else
			{
				notif = Images.initNotification("piocheJ2.png");
			}
			return notif;
		}
		
		public static final BufferedImage poseImpossible = Images.initNotification("poseImpossible.png");
		public static final BufferedImage piocheImpossible = Images.initNotification("piocheImpossible.png");
		public static final BufferedImage volImpossible = Images.initNotification("volImpossible.png");
		public static final BufferedImage tramImpossible = Images.initNotification("tramImpossible.png");
		
	}

}
