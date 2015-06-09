package panelPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import mainPackage.Moteur;
import objectPackage.Plateau;
import panelPackage.PanelListener.DebugCheckBoxListener;
import panelPackage.PanelListener.ReturnButtonListener;
import titrePackage.ChargementTitre;
import windowPackage.MenuWindow;

public class LoadGamePanel extends PanelInterface {

	Dimension saveButtonSize = setNewDimension(400,40);
	Dimension buttonSize = setNewDimension(175,60);

	boolean openMenu = false;

	boolean selectDebug = false;

	public LoadGamePanel(JFrame main, JDialog parent){
		parentDialog = parent;
		mainWindow = main;
		this.setCursor();
		this.buildSettingsPanel();
		this.setBackground(Color.getColor("gris_tram", 4607576));
	}

	public LoadGamePanel(JFrame main, JDialog parent, boolean b){
		parentDialog = parent;
		mainWindow = main;
		openMenu = b;
		this.setCursor();
		this.buildSettingsPanel();
		this.setBackground(Color.getColor("gris_tram", 4607576));
	}

	private void buildSettingsPanel(){

		JPanel zone1 = newZone(5, 400, 40);
		JPanel zone2 = newZone(5, 400, 40);
		JPanel zone3 = newButtonZone(175,60);
		
		ChargementTitre title = new ChargementTitre();
		
		String[] table = title.listerRepertoire();

		addNewButton(zone1, setSaveName(table,0), saveButtonSize, BorderLayout.CENTER, listener.new LoadGameListener(setSaveName(table,0), parentDialog, mainWindow), null);
		addNewButton(zone1, setSaveName(table,1), saveButtonSize, BorderLayout.CENTER, listener.new LoadGameListener(setSaveName(table,1), parentDialog, mainWindow), null);
		addNewButton(zone1, setSaveName(table,2), saveButtonSize, BorderLayout.CENTER, listener.new LoadGameListener(setSaveName(table,2), parentDialog, mainWindow), null);
		addNewButton(zone1, setSaveName(table,3), saveButtonSize, BorderLayout.CENTER, listener.new LoadGameListener(setSaveName(table,3), parentDialog, mainWindow), null);
		addNewButton(zone1, setSaveName(table,4), saveButtonSize, BorderLayout.CENTER, listener.new LoadGameListener(setSaveName(table,4), parentDialog, mainWindow), null);

		addNewButton(zone2, setSaveName(table,5), saveButtonSize, BorderLayout.CENTER, listener.new LoadGameListener(setSaveName(table,5), parentDialog, mainWindow), null);
		addNewButton(zone2, setSaveName(table,6), saveButtonSize, BorderLayout.CENTER, listener.new LoadGameListener(setSaveName(table,6), parentDialog, mainWindow), null);
		addNewButton(zone2, setSaveName(table,7), saveButtonSize, BorderLayout.CENTER, listener.new LoadGameListener(setSaveName(table,7), parentDialog, mainWindow), null);
		addNewButton(zone2, setSaveName(table,8), saveButtonSize, BorderLayout.CENTER, listener.new LoadGameListener(setSaveName(table,8), parentDialog, mainWindow), null);
		addNewButton(zone2, setSaveName(table,9), saveButtonSize, BorderLayout.CENTER, listener.new LoadGameListener(setSaveName(table,9), parentDialog, mainWindow), null);

		//addNewButton(zone2, "Confirmer", buttonSize, BorderLayout.WEST, null, null);

		if (openMenu){
			addNewButton(zone3, buttonSize, BorderLayout.EAST, listener.new ReturnButtonListener(parentDialog, new MenuWindow(), mainWindow), loadImage("annuler.png"));
		} else {
			addNewButton(zone3, buttonSize, BorderLayout.EAST, listener.new ReturnButtonListener(parentDialog, null), loadImage("annuler.png"));
		}

		this.add(zone1);
		this.add(zone2);
		this.add(zone3);
	}
	
	
	private String setSaveName(String[] table, int i){
		if (table.length <= i) {
		    return "Vide";
		} else {
			return table[i];
		}
	}

}
