package panelPackage;

import graphique.Fenetre;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import panelPackage.PanelListener.CancelButtonListener;
import constantesPackages.Constantes;

public class PanelInterface extends JPanel {
	
	JDialog parentDialog;
	
	JFrame mainWindow;
	
	PanelListener listener = new PanelListener();
	
	double width = Constantes.Resolution.width;
	double height = Constantes.Resolution.height;
	
	protected JPanel newZone(int x, int y){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.getColor("gris_tram", 4607576));
		panel.setPreferredSize(setNewDimension(x,y));
		return panel;
	}
	
	protected JPanel newZone(int size, int width, int height){
		JPanel panel = new JPanel();
		FlowLayout layout = new FlowLayout();
		layout.setVgap(0);
		panel.setBackground(Color.getColor("gris_tram", 4607576)); 
		panel.setLayout(layout);
		panel.setPreferredSize(setNewDimension(width ,(height*size)+20));
		return panel;
	}
	
	protected JPanel newButtonZone(int x, int y){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(setNewDimension(x,y));
		panel.setBackground(Color.getColor("gris_tram", 4607576));
		return panel;
	}
	
	protected void addNewButton(JPanel panel, String text, Dimension size, String position, ActionListener action, ImageIcon img){
		JButton button = new JButton(text, img);
		button.setPreferredSize(size);
		button.setBackground(Color.getColor("gris_tram", 4607576));
		button.setForeground(Color.white);
		button.addActionListener(action);
		panel.add(button, position);
	}
	
	protected void addNewButton(JPanel panel, Dimension size, String position, ActionListener action, ImageIcon img){
		ImageIcon sizedImg = resizeImage(img, size);
		JButton button = new JButton("", sizedImg);
		button.setPreferredSize(size);
		button.setBackground(Color.getColor("gris_tram", 4607576));
		button.setForeground(Color.white);
		button.addActionListener(action);
		panel.add(button, position);
	}

	protected Dimension setNewDimension(double w, double h) {
		double newHeight = height/(1024.0/h);
		double newWidth = (newHeight*w)/h;
		return new Dimension((int)newWidth, (int)newHeight);
	}
	
	protected ImageIcon loadImage(String folder, String name){
		ImageIcon img = null;
		try {
			img = new ImageIcon(ImageIO.read(new File("images/"+folder+"/"+name)));
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
	
	protected void setCursor(){
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img=Toolkit.getDefaultToolkit().getImage("images/background/pointeur.png");
		Cursor monCurseur = tk.createCustomCursor(img, new Point(8, 8),"images/background/pointeur.png");
		this.setCursor(monCurseur);
	}
	
	public void setMainWindow(JFrame f){
		mainWindow = f;
	}

}
