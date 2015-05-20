package graphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Fenetre extends JFrame{
	
	public Fenetre()
	{
		super();
		Panneau p = new Panneau();
		p.addMouseListener(new EcouteTerrain(p));
		
		//Avoir la taille standard de l'écran
		Toolkit tk = Toolkit.getDefaultToolkit(); 
		int largeur = tk.getScreenSize().width;
		int hauteur = tk.getScreenSize().height; 
		
		this.setSize(largeur, hauteur);	
		this.setTitle("Street Car");
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(p);
		this.setVisible(true);
	}
		
	public Fenetre (String title){
		super(title);
		Toolkit tk = Toolkit.getDefaultToolkit();
		int largeur = tk.getScreenSize().width;
		int hauteur = tk.getScreenSize().height;
		setSize(500, 500);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addMouseListener(new GestionSouris(this));
		
	}
	
	public void disposition (){
		Panneau panel1 = new Panneau(Color.orange, "zone de jeu");
		Panneau panel2 = new Panneau(Color.white, "zone d'interface");
		Panneau panel3 = new Panneau(Color.cyan, "notifications");
		Panneau panel4 = new Panneau(Color.white, "historiques");
		Panneau panel5 = new Panneau(Color.pink, "onglets d'options/navigation");
		Bouton but1 = new Bouton("bouton 1");
		Bouton but2 = new Bouton("bouton 2");
		
		panel4.setLayout(new GridLayout(1, 2));
		panel4.add(but1);
		panel4.add(but2);
		
		panel2.setLayout(new GridLayout(3,1));
		panel2.add(panel3);
		panel2.add(panel4);
		panel2.add(panel5);
		
		add(panel1, BorderLayout.CENTER);
		add(panel2, BorderLayout.EAST);
		
	}
}
