package graphique;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class Panneau extends JPanel{
	String name;
	Color couleur;
	
	boolean zoneDeJeu;
	boolean contoursDessines;
	
	public Panneau (Color newCouleur, String newName){
		super();
		name = newName;
		couleur = newCouleur;
		zoneDeJeu = false;
		contoursDessines = false;
		addMouseListener(new GestionSouris(this));
	}
	
	public Panneau (Color newCouleur, String newName, boolean panneauDeJeu){
		super();
		name = newName;
		couleur = newCouleur;
		zoneDeJeu = panneauDeJeu;
		contoursDessines = false;
		addMouseListener(new GestionSouris(this));
	}
	
	public String getName (){
		return name;
	}
	
	public void paintComponent (Graphics g){
		Graphics2D crayon = (Graphics2D) g;
		
		int largeur = getSize().width;
		int hauteur = getSize().height;
		
		crayon.setColor(couleur);
		crayon.fillRect(0, 0, largeur, hauteur);
		
		if (contoursDessines){
			crayon.setColor(Color.black);
			crayon.drawRect(0, 0, largeur-1, hauteur-1);
		}
		
	}
	
	public void activerContours() {
		contoursDessines = true;
		repaint();
	}
	
	public void desactiverContours() {
		contoursDessines = false;
		repaint();
	}

	
}
