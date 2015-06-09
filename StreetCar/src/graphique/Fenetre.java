package graphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import mainPackage.Moteur;
import constantesPackages.Constantes;

public class Fenetre extends JFrame{
	
	public Moteur moteurParent;
			
	public Fenetre (String title){
		super(title);
		int largeur = (int)(9.0*Constantes.Resolution.width/10.0);
		int hauteur = (int)(9.0*Constantes.Resolution.height/10.0);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img=Toolkit.getDefaultToolkit().getImage("images/background/pointeur.png");
		Cursor monCurseur = tk.createCustomCursor(img, new Point(8, 8),"images/background/pointeur.png");
		this.setCursor(monCurseur);
		
		if (largeur > hauteur){
			largeur = 80*hauteur/57;
		}
		else {
			hauteur = 57*largeur/80;
		}
		
		try {		
			//Chargement des images
			BufferedImage icone = ImageIO.read(new File("images/background/logo.png"));
			this.setIconImage(icone);
		}
		catch (IOException e) { e.printStackTrace();}	
	
		setSize(largeur, hauteur);
//		setSize(800, 800);

		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setUndecorated(true);
		setVisible(true);

	}
	
	public void disposition_V2 (Moteur referenceMoteur, Dimension dim){
		// Variables Kévin
		int quotient = 9;
		int largeurPanneauPrincipal = ( (quotient-1) * dim.width/quotient);
		int largeurPanneauLateral = dim.width/quotient;
		int hauteur = dim.height;
		int hauteurBoutons = hauteur/9;
		// FIN Variables
		
		// Panneau de jeu principal
		Panneau_Plateau zonePlateau = new Panneau_Plateau (Color.orange, referenceMoteur);
		// Liaison Moteur <-> Panneau_Plateau
		referenceMoteur.setPanneau_Jeu(zonePlateau);
		
		// JPanel Lateral
		JPanel regroupementLateral = new JPanel();
		
		// Panneau de navigations via l'historique
		Panneau_Historique zoneHistorique = new Panneau_Historique (Color.gray, referenceMoteur);
		// Liaison Moteur <-> Panneau_Historique
		referenceMoteur.setPanneau_Historique(zoneHistorique);
		
		// Panneau de menus des boutons
		Panneau_Boutons zoneBoutons = new Panneau_Boutons (this, Color.DARK_GRAY, zonePlateau);
		
		// Redimensionnement des differents Panneaux
		zonePlateau.setPreferredSize( new Dimension( largeurPanneauPrincipal, hauteur) );
		regroupementLateral.setPreferredSize( new Dimension( largeurPanneauLateral, hauteur ) );
		zoneBoutons.setPreferredSize( new Dimension( largeurPanneauLateral, hauteurBoutons ) );
		
		// Disposition dans le JPanel Lateral
		regroupementLateral.setLayout(new BorderLayout());
		regroupementLateral.add(zoneHistorique, BorderLayout.CENTER);
		regroupementLateral.add(zoneBoutons, BorderLayout.SOUTH);
		
		// Disposition du Panneau de Jeu et du JPanel Lateral
		add(zonePlateau, BorderLayout.CENTER);
		add(regroupementLateral, BorderLayout.EAST);
		
		pack();
		this.moteurParent = referenceMoteur;
		referenceMoteur.start();
	}
	
}
