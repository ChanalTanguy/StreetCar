package titrePackage;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import constantesPackages.Constantes;


@SuppressWarnings("serial")
public class FenetreTitre extends JFrame {
	
	PanneauTitre pan = new PanneauTitre(this);
		
	public FenetreTitre(String nom)
	{
		super(nom);
		Toolkit tk = Toolkit.getDefaultToolkit();
		int largeur = tk.getScreenSize().width;
		int hauteur = tk.getScreenSize().height;
		
		BufferedImage icone = Constantes.Images.initBackground("logo.png");
		this.setIconImage(icone);
		Image img=Toolkit.getDefaultToolkit().getImage("images/background/pointeur.png");
		Cursor monCurseur = tk.createCustomCursor(img, new Point(8, 8),"images/background/pointeur.png");
		this.setCursor(monCurseur);
		
		this.getContentPane().add(new JScrollPane(pan), BorderLayout.CENTER);
		addKeyListener(new EcouteClavierTitre(pan, this));
		
		setSize(largeur, hauteur); 
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void fermeture()
	{
		dispose();
	}

}
