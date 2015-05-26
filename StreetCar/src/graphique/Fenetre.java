package graphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
		
//		setSize(largeur, hauteur);
		setSize(largeur, hauteur);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	public void disposition (Moteur m, Dimension dim){
Panneau zonePlateau = new Panneau (Color.orange, "zone de jeu", Constantes.Panneau.plateau, m);
		
		JPanel regroupementLateral = new JPanel();
		Panneau notifications = new Panneau (Color.cyan, "notifications", Constantes.Panneau.notifications);
		
		JPanel panelHistorique = new JPanel();
		Panneau histo_ouest = new Panneau (Color.white, "histo_ouest", Constantes.Panneau.histo_ouest);
		
		JPanel regroupementHistorique = new JPanel();
		Panneau historique_central = new Panneau (Color.red, "historique_central", Constantes.Panneau.histo_central);
		Panneau histo_nord = new Panneau (Color.orange, "histo_nord", Constantes.Panneau.histo_bouton_nord);
		Panneau histo_sud = new Panneau (Color.blue, "histo_sud", Constantes.Panneau.histo_bouton_sud);
		
		Panneau histo_est = new Panneau (Color.white, "histo_est", Constantes.Panneau.histo_est);
		
		Panneau menus = new Panneau(Color.pink, "boutons de menus", Constantes.Panneau.menuBoutons);
		
		zonePlateau.addMouseListener(new GestionSouris(zonePlateau, notifications));
		
		notifications.addMouseListener(new GestionSouris(notifications, notifications));
		
		historique_central.addMouseListener(new GestionSouris(historique_central, notifications));
		histo_nord.addMouseListener(new GestionSouris(histo_nord, notifications));
		histo_sud.addMouseListener(new GestionSouris(histo_sud, notifications));
		
		menus.addMouseListener(new GestionSouris(menus, notifications));

		
		zonePlateau.setPreferredSize(new Dimension(5*dim.width/6, dim.height));
		regroupementLateral.setPreferredSize(new Dimension(dim.width/6, dim.height));
		notifications.setPreferredSize(new Dimension(dim.width/6, dim.height/5));
		menus.setPreferredSize(new Dimension(dim.width/6, dim.height/5));
		histo_nord.setPreferredSize(new Dimension(dim.width/12, 50));
		histo_sud.setPreferredSize(new Dimension(dim.width/12, 50));
		
		panelHistorique.setLayout(new GridLayout(1, 3));
		panelHistorique.add(histo_ouest);
		panelHistorique.add(regroupementHistorique);
		panelHistorique.add(histo_est);
		
		regroupementHistorique.setLayout(new BorderLayout());
		regroupementHistorique.add(histo_nord, BorderLayout.NORTH);
		regroupementHistorique.add(historique_central, BorderLayout.CENTER);
		regroupementHistorique.add(histo_sud, BorderLayout.SOUTH);
		
		Bouton but_centre = new Bouton("bouton_centre", notifications);
		
		JPanel pan_est = new JPanel();
		JPanel pan_ouest = new JPanel();
		JPanel pan_centre = new JPanel();
		
//		System.out.println("pan_centre.size() : " + pan_centre.getPreferredSize());

		regroupementLateral.setLayout(new BorderLayout());
		regroupementLateral.add(notifications, BorderLayout.NORTH);
		regroupementLateral.add(panelHistorique, BorderLayout.CENTER);
		regroupementLateral.add(menus, BorderLayout.SOUTH);
		
		add(zonePlateau, BorderLayout.CENTER);
		add(regroupementLateral, BorderLayout.EAST);
		
		pack();
		
	}
}
