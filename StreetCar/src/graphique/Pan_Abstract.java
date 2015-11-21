package graphique;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public abstract class Pan_Abstract extends JComponent{
	protected int largeur, hauteur;
	protected Graphics2D crayon;
	protected Color couleur;
	
	public Pan_Abstract (Color newCouleur){
		couleur = newCouleur;
	}
	
	protected int getLargeur (){
		return largeur;
	}
	protected int getHauteur (){
		return hauteur;
	}
	
	public abstract void paintComponent (Graphics g);
	protected abstract void initialiserImages ();
}
