package titrePackage;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.Box.Filler;

import constantesPackages.Constantes;


public class PanneauTitre extends JPanel{

	Boolean selection = false;
	int selectionner = -1;
	public int actif = 0;
	
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
	
	PanneauTitre(FenetreTitre fen)
	{
		super();
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
		// TODO Auto-generated method stub
	}

	public void options(Graphics2D crayon) {
		// TODO Auto-generated method stub
		
	}
	
	private void chargerPartie(Graphics2D crayon) {
		crayon.setColor(Color.white);
		if(saves != null)
		{
			crayon.drawString("Choisissez une sauvegarde", getWidth()/3, getHeight()/4);
		
			for(int i = 0; i<saves.length && i<10; i++)
			{
				crayon.drawString(saves[i], getWidth()/3, getHeight()/4+(i+1)*50);
			}
	
		}
		
		else crayon.drawString("Pas de sauvegarde", getWidth()/3, getHeight()/4);
	
	}

	//Pas encore complet
	public void credits(Graphics2D crayon) {
		//TODO inclure le bouton de retour
		crayon.drawImage(backgroundCredits, 0, 0, getWidth(), getHeight(), this);
		crayon.setColor(Color.white);
		crayon.drawRect(getWidth()-300, getHeight()-175, 175, 55);
	
	}
	
}
