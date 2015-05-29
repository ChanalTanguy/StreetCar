package titrePackage;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import constantesPackages.Constantes;


public class PanneauTitre extends JPanel{

	Boolean selection = false;
	int selectionner = -1;
	
	PanneauTitre(FenetreTitre fen)
	{
		super();
		EcouteTitre ecoute = new EcouteTitre(this, fen);
		this.addMouseListener(ecoute);
		this.addMouseMotionListener(ecoute);
	}
	
	public void paintComponent (Graphics g){
		Graphics2D crayon = (Graphics2D) g;
		
		BufferedImage background = Constantes.Images.initBackground("tram.png");
		BufferedImage nouvellePartie = Constantes.Images.initBackground("nouvellePartie.png");
		BufferedImage chargerPartie = Constantes.Images.initBackground("chargerPartie.png");
		BufferedImage defis = Constantes.Images.initBackground("defis.png");
		BufferedImage options = Constantes.Images.initBackground("options.png");
		BufferedImage credits = Constantes.Images.initBackground("credits.png");
		BufferedImage quitter = Constantes.Images.initBackground("quitter.png");
		BufferedImage titre = Constantes.Images.initBackground("titre.png");
		BufferedImage fleche = Constantes.Images.initBackground("fleche.png");
			
		crayon.drawImage(background, 0, 0, getWidth(), getHeight(), this);
				
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
			crayon.drawImage(fleche, getWidth()/4, (selectionner+4)*51, 90, 35, this);
			//crayon.drawRect(getWidth()/3, (selectionner+4)*50, 300, 45);
		}			
	}
	
}
