package panelPackage;

import java.awt.BorderLayout;
import java.awt.Color;
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

public class MenuPanel extends PanelInterface {
	
	Dimension buttonSize = setNewDimension(400,75);

	public MenuPanel(JDialog parent){
		parentDialog = parent;
		this.buildMenuPanel();
	}

	private void buildMenuPanel(){

		JPanel zone1 = newZone(1);
		JPanel zone2 = newZone(6);
		JPanel zone3 = newZone(1);
		
		addNewButton(zone1, buttonSize, null, listener.new ReturnButtonListener(parentDialog, null), loadImage("reprendre_menu.png"));
		
		addNewButton(zone2, buttonSize, null, listener.new ConfigureNewGameButtonListener(parentDialog, true), loadImage("nouvellePartie_menu.png"));
		addNewButton(zone2, buttonSize, null, null, loadImage("sauvegarderPartie_menu.png"));
		addNewButton(zone2, buttonSize, null, null, loadImage("chargerPartie_menu.png"));
		addNewButton(zone2, buttonSize, null, null, loadImage("defis_menu.png"));
		addNewButton(zone2, buttonSize, null, listener.new SettingsButtonListener(parentDialog, true), loadImage("options_menu.png"));
		addNewButton(zone2, buttonSize, null, listener.new CreditsButtonListener(parentDialog, true), loadImage("credits_menu.png"));
		
		addNewButton(zone3, buttonSize, null, listener.new QuitButtonListener(), loadImage("quitter_menu.png"));
		
		
		this.add(zone1);
		this.add(zone2);
		this.add(zone3);
		
		this.setBackground(Color.getColor("gris_tram", 4607576));
		
	}

	private JPanel newZone(int size){
		JPanel panel = new JPanel();
		FlowLayout layout = new FlowLayout();
		layout.setVgap(0);
		panel.setBackground(Color.getColor("gris_tram", 4607576)); 
		panel.setLayout(layout);
		panel.setPreferredSize(setNewDimension(400,75*size+20));
		return panel;

	}
	
}
