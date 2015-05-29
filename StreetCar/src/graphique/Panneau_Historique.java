package graphique;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import mainPackage.Moteur;
import constantesPackages.Constantes;

public class Panneau_Historique extends Pan_Abstract{
	BufferedImage boutonHaut, boutonBas, historiqueCentral;
	// Variable permettant de separer le Panneau en 3 parties pour les 3 images;
	private int diviseurDeDimension;
	private int encadrerZone;
	
	Moteur mot;
	
	public Panneau_Historique (Color newCouleur, Moteur referenceMoteur){
		super(newCouleur);
		mot = referenceMoteur;
		encadrerZone = 0;
		initialiserImages();
		Ecouteur_Historique ecouteur = new Ecouteur_Historique(this);
		addMouseListener(ecouteur);
		addMouseMotionListener(ecouteur);
	}
	
	/*
	 * ACCESSEURS
	 */
	public int getDiviseur (){
		return diviseurDeDimension;
	}
	
	public void setEncadrer (int newZone){
		encadrerZone = newZone;
	}
	/*
	 * FIN ACCESSEURS
	 */
	
	public void paintComponent (Graphics g){
		crayon = (Graphics2D) g;
		largeur = getSize().width;
		hauteur = getSize().height;
		
		crayon.setColor(couleur);
		crayon.fillRect(0, 0, largeur, hauteur);
		int coordX = largeur/3;
		int coordY = 0;
		int dimX = largeur/3;
		diviseurDeDimension = 9;
		int dimY = hauteur/diviseurDeDimension;
		crayon.drawImage(boutonHaut, coordX, coordY, dimX, dimY, this);
		
		coordY += dimY;
		dimY = (diviseurDeDimension-2)*hauteur/diviseurDeDimension;
		crayon.drawImage(historiqueCentral, coordX, coordY, dimX, dimY, this);
		
		coordY += dimY;
		dimY = hauteur/diviseurDeDimension;
		crayon.drawImage(boutonBas, coordX, coordY, dimX, dimY, this);
		
		if (contoursSurlignes){
			crayon.setColor(Color.red);
			crayon.drawRect(0, 0, largeur-1, hauteur-1);
		}
		if (encadrerZone > 0){
			activerCadreZone(crayon);
		}
	}
	
	private void activerCadreZone (Graphics2D crayon){
		crayon.setColor(Color.white);
		switch (encadrerZone){
		case 1:
			crayon.drawRect(1, 1, largeur-1, hauteur/diviseurDeDimension);
			break;
		case 2:
			crayon.drawRect(1, hauteur/diviseurDeDimension, largeur-1, (diviseurDeDimension-2)*hauteur/diviseurDeDimension);
			break;
		case 3:
			crayon.drawRect(1, (diviseurDeDimension-1)*hauteur/diviseurDeDimension, largeur-1, hauteur/diviseurDeDimension);
			break;
		}
		repaint();
	}
	
	protected void initialiserImages (){
		boutonHaut = Constantes.Images.initBackground("histo_haut.png");
		historiqueCentral = Constantes.Images.initBackground("histo_centre.png");
		boutonBas = Constantes.Images.initBackground("histo_bas.png");
		
	}
}
