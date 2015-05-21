package graphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
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
			
	public Fenetre (String title){
		super(title);
		Toolkit tk = Toolkit.getDefaultToolkit();
		int largeur = tk.getScreenSize().width;
		int hauteur = tk.getScreenSize().height;
		
		try {		
			//Chargement des images
			BufferedImage icone = ImageIO.read(new File("images/background/logo.png"));
			this.setIconImage(icone);
		}
		catch (IOException e) { e.printStackTrace();}	
		
		setSize(largeur, hauteur);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	public void disposition (Moteur m){
		Panneau zonePlateau = new Panneau(Color.orange, "zone de jeu", Constantes.Panneau.plateau, m);
//		Panneau regroupementLateral = new Panneau(Color.white, "zone de regroupement");
		JPanel regroupementLateral = new JPanel();
		Panneau notifications = new Panneau(Color.cyan, "notifications", Constantes.Panneau.notifications);
		Panneau historique = new Panneau(Color.white, "historiques", Constantes.Panneau.historiques);
		Panneau menus = new Panneau(Color.pink, "boutons de menus", Constantes.Panneau.boutons);
		Bouton but1 = new Bouton("bouton 1", notifications);
		Bouton but2 = new Bouton("bouton 2", notifications);
		
		zonePlateau.addMouseListener(new GestionSouris(zonePlateau, notifications));
		notifications.addMouseListener(new GestionSouris(notifications, notifications));
		historique.addMouseListener(new GestionSouris(historique, notifications));
		menus.addMouseListener(new GestionSouris(menus, notifications));
		
		historique.setLayout(new GridLayout(1, 2));
		historique.add(but1);
		historique.add(but2);
		
		regroupementLateral.setLayout(new GridLayout(3,1));
		regroupementLateral.add(notifications);
		regroupementLateral.add(historique);
		regroupementLateral.add(menus);
		
		add(zonePlateau, BorderLayout.CENTER);
		add(regroupementLateral, BorderLayout.EAST);
		
	}
}
