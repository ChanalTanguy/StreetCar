package graphique;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public abstract class Pan_Abstract extends JComponent{
	protected int largeur, hauteur;
	protected Graphics2D crayon;
	protected Color couleur;
	protected boolean contoursSurlignes;
	
	public Pan_Abstract (Color newCouleur){
		couleur = newCouleur;
		contoursSurlignes = false;
		addMouseListener(new GestionSouris(this));
	}
	
	protected int getLargeur (){
		return largeur;
	}
	protected int getHauteur (){
		return hauteur;
	}
	
	protected void activerContours (){
		contoursSurlignes = true;
		repaint();
	}
	protected void desactiverContours (){
		contoursSurlignes = false;
		repaint();
	}
	
	public abstract void paintComponent (Graphics g);
	protected abstract void initialiserImages ();
}
