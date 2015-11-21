package panelPackage;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import graphique.Fenetre;

@SuppressWarnings("serial")
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
		
		addNewButton(zone1, buttonSize, null, listener.new CancelButtonListener(parentDialog, null, this.mainWindow), loadImage("background","reprendre_menu.png"));
		
		addNewButton(zone2, buttonSize, null, listener.new ConfigureNewGameButtonListener(parentDialog, this.mainWindow, true), loadImage("background","nouvellePartie_menu.png"));
		addNewButton(zone2, buttonSize, null, listener.new SaveGameButtonListener(((Fenetre)this.mainWindow).moteurParent, parentDialog, this.mainWindow), loadImage("background","sauvegarderPartie_menu.png"));
		addNewButton(zone2, buttonSize, null, listener.new LoadGameButtonListener(parentDialog, this.mainWindow, true), loadImage("background","chargerPartie_menu.png"));
		addNewButton(zone2, buttonSize, null, null, loadImage("background","defis_menu.png"));
		addNewButton(zone2, buttonSize, null, listener.new SettingsButtonListener(parentDialog, true, this.mainWindow), loadImage("background","options_menu.png"));
		addNewButton(zone2, buttonSize, null, listener.new CreditsButtonListener(parentDialog, true, this.mainWindow), loadImage("background","credits_menu.png"));
		
		addNewButton(zone3, buttonSize, null, listener.new QuitButtonListener(), loadImage("background","quitter_menu.png"));
		
		this.add(zone1);
		this.add(zone2);
		this.add(zone3);
		
		this.setBackground(Color.getColor("gris_tram", 4607576));
		
	}
	
}
