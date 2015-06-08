package titrePackage;
import graphique.Fenetre;
import graphique.Panneau_Plateau;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.Box.Filler;

import objectPackage.Plateau;
import panelPackage.PanelListener;
import mainPackage.Moteur;
import sauvegardePackage.Chargement;
import constantesPackages.Constantes;


public class PanneauTitre extends JPanel{

	Boolean selection = false;
	int selectionner = -1;
	public int actif = 0;
	public int chargementPartie = -1;
	FenetreTitre fenetre;
	
	ChargementTitre chargement = new ChargementTitre();
	String[] saves = chargement.listerRepertoire();
	
	BufferedImage background ;
	BufferedImage backgroundCredits;
	BufferedImage nouvellePartie ;
	BufferedImage chargerPartie;
	BufferedImage defis;
	BufferedImage options ;
	BufferedImage credits ;
	BufferedImage quitter ;
	BufferedImage titre ;
	BufferedImage fleche ;
	BufferedImage retour ;
	
	PanneauTitre(FenetreTitre fen)
	{
		super();
		fenetre = fen;
		EcouteTitre ecoute = new EcouteTitre(this, fen);
		this.addMouseListener(ecoute);
		this.addMouseMotionListener(ecoute);
		initImage();
	}

	private void initImage() {
		background = Constantes.Images.initBackground("tram.png");
		nouvellePartie = Constantes.Images.initBackground("nouvellePartie.png");
		chargerPartie = Constantes.Images.initBackground("chargerPartie.png");
		defis = Constantes.Images.initBackground("defis.png");
		options = Constantes.Images.initBackground("options.png");
		credits = Constantes.Images.initBackground("credits.png");
		quitter = Constantes.Images.initBackground("quitter.png");
		titre = Constantes.Images.initBackground("titre.png");
		fleche = Constantes.Images.initBackground("fleche.png");
		backgroundCredits = Constantes.Images.initBackground("tramCred.png");
		retour = Constantes.Images.initBackground("retour.png");
	}

	public void paintComponent (Graphics g){
		Graphics2D crayon = (Graphics2D) g;

		crayon.drawImage(background, 0, 0, getWidth(), getHeight(), this);
		
		if(actif == 0) ecranTitre(crayon);
		if(actif == 2) chargerPartie(crayon);
		if(actif == 3) defis(crayon);
		if(actif == 4) options(crayon);
		if(actif == 5) credits(crayon);
	}

	private void ecranTitre(Graphics2D crayon) {
		crayon.drawImage(titre, getWidth()/5, 50, 900, 90, this);
		crayon.drawImage(nouvellePartie, getWidth()/3, (1+4)*50, 300, 45, this);
		crayon.drawImage(chargerPartie, getWidth()/3, (2+4)*50, 300, 45, this);
		crayon.drawImage(defis, getWidth()/3, (3+4)*50, 300, 45, this);
		crayon.drawImage(options, getWidth()/3, (4+4)*50, 300, 45, this);
		crayon.drawImage(credits, getWidth()/3, (5+4)*50, 300, 45, this);
		crayon.drawImage(quitter, getWidth()/3, (10+4)*50, 300, 45, this);
			
		if(selection)
		{
			crayon.setColor(Color.white);
			crayon.drawImage(fleche, getWidth()/4, (selectionner+4)*51, 84, 50, this);
		}
	}

	public void defis(Graphics2D crayon) {
		crayon.setColor(Color.white);
		crayon.drawString("Cette section n'a pas encore été faite.", getWidth()/3, getHeight()/4-50);
		crayon.drawImage(retour,getWidth()-200, getHeight()-100, 300, 45, this);
	}

	public void options(Graphics2D crayon) {
		crayon.setColor(Color.white);
		crayon.drawString("Cette section n'a pas encore été faite.", getWidth()/3, getHeight()/4-50);
		crayon.drawImage(retour,getWidth()-200, getHeight()-100, 300, 45, this);
		
	}
	
	private void chargerPartie(Graphics2D crayon) {
		crayon.setColor(Color.white);
		crayon.drawImage(retour,getWidth()-200, getHeight()-100, 300, 45, this);
		if(saves != null)
		{
			crayon.setFont(new Font(Font.SANS_SERIF,0, getHeight()/50));
			crayon.drawString("Choisissez une sauvegarde", getWidth()/3, getHeight()/4-50);
			
			for(int i = 0; i<saves.length && i<10; i++)
			{
				crayon.drawString(saves[i], getWidth()/3, getHeight()/4+i*50);
			}
			
			if(chargementPartie != -1)
			{
				crayon.drawRect(getWidth()/3,getHeight()/4+chargementPartie*50-25 , 200, 25);
				
				Chargement c = new Chargement();
				Moteur m = new Moteur(new Plateau());			
				c.charger(m, saves[chargementPartie]);
				fenetre.fermeture();
				Fenetre f = new Fenetre("Street Car"); 
				f.disposition_V2(m, f.getSize());}
				
		}
		
		else crayon.drawString("Pas de sauvegarde", getWidth()/3, getHeight()/4);
	
	}

	//Pas encore complet
	public void credits(Graphics2D crayon) {
		//TODO inclure le bouton de retour
		crayon.drawImage(backgroundCredits, 0, 0, getWidth(), getHeight(), this);
		crayon.drawImage(retour,getWidth()-200, getHeight()-100, 300, 45, this);
		//crayon.drawRect(getWidth()-200, getHeight()-100, 300, 45);
	}
	
}
