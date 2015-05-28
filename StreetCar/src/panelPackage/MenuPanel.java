package panelPackage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import constantesPackages.Constantes;

public class MenuPanel extends JPanel {

	JDialog parentDialog;
	JFrame parentFrame;
	JPanel parentPanel;

	PanelListener listener = new PanelListener();
	
	double width = Constantes.Resolution.width;
	double height = Constantes.Resolution.height;

	public MenuPanel(JDialog parent){
		parentDialog = parent;
		this.buildMenuPanel();
	}
	
	public MenuPanel(JFrame parent){
		parentFrame = parent;
		this.buildMenuPanel();
	}

	public MenuPanel(JPanel parent){
		parentPanel = parent;
		this.buildMenuPanel();
	}

	private void buildMenuPanel(){

		JPanel zone1 = newZone(1);
		JPanel zone2 = newZone(6);
		JPanel zone3 = newZone(1);
		
		if(parentPanel != null){addNewButton(zone1, "Reprendre", listener.new ReturnButtonListener(this, parentPanel),null);}
		if(parentDialog != null){addNewButton(zone1, "Reprendre", listener.new ReturnButtonListener(parentDialog), null);}
		if(parentFrame != null){addNewButton(zone1, "Reprendre", listener.new ReturnButtonListener(parentFrame), null);}

		addNewButton(zone2, "Nouvelle partie", listener.new ConfigureNewGameButtonListener(), null);
		addNewButton(zone2, "Sauvegarder la partie", null, null);
		addNewButton(zone2, "Charger une partie", null, null);
		addNewButton(zone2, "Défis", null, null);
		addNewButton(zone2, "Options", listener.new SettingsButtonListener(), null);
		addNewButton(zone2, "Crédits", null, null);
		addNewButton(zone3, "Quitter", listener.new QuitButtonListener(), null);
				
		this.add(zone1);
		this.add(zone2);
		this.add(zone3);
	}

	private JPanel newZone(int size){
		JPanel panel = new JPanel();
		FlowLayout layout = new FlowLayout();
		layout.setVgap(0);
		panel.setLayout(layout);
		panel.setPreferredSize(setNewDimension(400,75*size+20));
		return panel;

	}

	private void addNewButton(JPanel panel, String text, ActionListener action, ImageIcon img){
		Dimension size = setNewDimension(400,75);
		JButton button = new JButton(text, img);
		button.setPreferredSize(size);
		button.addActionListener(action);
		panel.add(button);
	}
	
	private ImageIcon loadImage(String name){
		ImageIcon img = null;
		try {
			img = new ImageIcon(ImageIO.read(new File("images/menu/"+name)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	private Dimension setNewDimension(double w, double h) {
		double newHeight = height/(1024.0/h);
		double newWidth = (newHeight*w)/h;
		return new Dimension((int)newWidth, (int)newHeight);
	}
	
}
