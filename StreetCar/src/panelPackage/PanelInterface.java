package panelPackage;

import graphique.Fenetre;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import panelPackage.PanelListener.ReturnButtonListener;
import constantesPackages.Constantes;

public class PanelInterface extends JPanel {
	
	JDialog parentDialog;
	
	Fenetre mainGame;
	
	PanelListener listener = new PanelListener();
	
	double width = Constantes.Resolution.width;
	double height = Constantes.Resolution.height;
	
	protected JPanel newButtonZone(int x, int y){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(setNewDimension(x,y));
		return panel;
	}
	
	protected void addNewButton(JPanel panel, String text, Dimension size, String position, ActionListener action, ImageIcon img){
		JButton button = new JButton(text, img);
		button.setPreferredSize(size);
		button.addActionListener(action);
		panel.add(button, position);
	}
	
	protected void addNewButton(JPanel panel, Dimension size, String position, ActionListener action, ImageIcon img){
		ImageIcon sizedImg = resizeImage(img, size);
		JButton button = new JButton("", sizedImg);
		button.setPreferredSize(size);
		button.addActionListener(action);
		panel.add(button, position);
	}

	protected Dimension setNewDimension(double w, double h) {
		double newHeight = height/(1024.0/h);
		double newWidth = (newHeight*w)/h;
		return new Dimension((int)newWidth, (int)newHeight);
	}
	
	protected ImageIcon loadImage(String name){
		ImageIcon img = null;
		try {
			img = new ImageIcon(ImageIO.read(new File("images/background/"+name)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	protected ImageIcon resizeImage(ImageIcon img, Dimension size){
		Image src = img.getImage() ;
		Image sizedSrc = src.getScaledInstance(size.width, size.height, java.awt.Image.SCALE_SMOOTH);  
		ImageIcon sizedImg = new ImageIcon(sizedSrc);
		return sizedImg;
	}
	
	protected void setMainGameWindow(Fenetre f){
		mainGame = f;
	}

}
