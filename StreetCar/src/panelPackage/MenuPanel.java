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

import mainPackage.Moteur;
import constantesPackages.Constantes;

public class MenuPanel extends PanelInterface {
	
	Dimension buttonSize = setNewDimension(400,75);

	public MenuPanel(JDialog parent){
		parentDialog = parent;
		this.setCursor();
		this.buildMenuPanel();
	}
	
	public MenuPanel(JFrame main, JDialog parent){
		parentDialog = parent;
		mainWindow = main;
		this.setCursor();
		this.buildMenuPanel();
	}

	private void buildMenuPanel(){

		JPanel zone1 = newZone(1, 400, 75);
		JPanel zone2 = newZone(6, 400, 75);
		JPanel zone3 = newZone(1, 400, 75);
		
		addNewButton(zone1, buttonSize, null, listener.new ReturnButtonListener(parentDialog, null, this.mainWindow), loadImage("reprendre_menu.png"));
		
		addNewButton(zone2, buttonSize, null, listener.new ConfigureNewGameButtonListener(parentDialog, this.mainWindow, true), loadImage("nouvellePartie_menu.png"));
		addNewButton(zone2, buttonSize, null, listener.new SaveGameButtonListener(((Fenetre)this.mainWindow).moteurParent, parentDialog, this.mainWindow), loadImage("sauvegarderPartie_menu.png"));
		addNewButton(zone2, buttonSize, null, listener.new LoadGameButtonListener(parentDialog, this.mainWindow, true), loadImage("chargerPartie_menu.png"));
		addNewButton(zone2, buttonSize, null, null, loadImage("defis_menu.png"));
		addNewButton(zone2, buttonSize, null, listener.new SettingsButtonListener(parentDialog, true, this.mainWindow), loadImage("options_menu.png"));
		addNewButton(zone2, buttonSize, null, listener.new CreditsButtonListener(parentDialog, true, this.mainWindow), loadImage("credits_menu.png"));
		
		addNewButton(zone3, buttonSize, null, listener.new QuitButtonListener(), loadImage("quitter_menu.png"));
		
		this.add(zone1);
		this.add(zone2);
		this.add(zone3);
		
		this.setBackground(Color.getColor("gris_tram", 4607576));
		
	}
	
}
