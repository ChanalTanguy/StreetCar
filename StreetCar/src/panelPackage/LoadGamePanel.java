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

	Dimension buttonSize = setNewDimension(400,40);

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

		JPanel zone1 = newZone(5);
		JPanel zone2 = newZone(5);
		JPanel zone3 = newZone(1);
		
		ChargementTitre title = new ChargementTitre();
		
		String[] table = title.listerRepertoire();

		addNewButton(zone1, setSaveName(table,0), buttonSize, BorderLayout.CENTER, listener.new LoadGameListener(setSaveName(table,0), parentDialog, mainWindow), null);
		addNewButton(zone1, setSaveName(table,1), buttonSize, BorderLayout.CENTER, listener.new LoadGameListener(setSaveName(table,1), parentDialog, mainWindow), null);
		addNewButton(zone1, setSaveName(table,2), buttonSize, BorderLayout.CENTER, listener.new LoadGameListener(setSaveName(table,2), parentDialog, mainWindow), null);
		addNewButton(zone1, setSaveName(table,3), buttonSize, BorderLayout.CENTER, listener.new LoadGameListener(setSaveName(table,3), parentDialog, mainWindow), null);
		addNewButton(zone1, setSaveName(table,4), buttonSize, BorderLayout.CENTER, listener.new LoadGameListener(setSaveName(table,4), parentDialog, mainWindow), null);

		addNewButton(zone2, setSaveName(table,5), buttonSize, BorderLayout.CENTER, listener.new LoadGameListener(setSaveName(table,5), parentDialog, mainWindow), null);
		addNewButton(zone2, setSaveName(table,6), buttonSize, BorderLayout.CENTER, listener.new LoadGameListener(setSaveName(table,6), parentDialog, mainWindow), null);
		addNewButton(zone2, setSaveName(table,7), buttonSize, BorderLayout.CENTER, listener.new LoadGameListener(setSaveName(table,7), parentDialog, mainWindow), null);
		addNewButton(zone2, setSaveName(table,8), buttonSize, BorderLayout.CENTER, listener.new LoadGameListener(setSaveName(table,8), parentDialog, mainWindow), null);
		addNewButton(zone2, setSaveName(table,9), buttonSize, BorderLayout.CENTER, listener.new LoadGameListener(setSaveName(table,9), parentDialog, mainWindow), null);

		//addNewButton(zone2, "Confirmer", buttonSize, BorderLayout.WEST, null, null);

		if (openMenu){
			addNewButton(zone3, "Annuler", buttonSize, BorderLayout.EAST, listener.new ReturnButtonListener(parentDialog, new MenuWindow(), mainWindow), null);
		} else {
			addNewButton(zone3, "Annuler", buttonSize, BorderLayout.EAST, listener.new ReturnButtonListener(parentDialog, null), null);
		}

		this.add(zone1);
		this.add(zone2);
		this.add(zone3);
	}
	
	
	private String setSaveName(String[] table, int i){
		if (table.length <= i) {
		    return "Vide";
		} else {
			System.out.println(i+" : "+table[i]);
			return table[i];
		}
	}
	
	private JPanel newZone(int size){
		JPanel panel = new JPanel();
		FlowLayout layout = new FlowLayout();
		layout.setVgap(0);
		panel.setBackground(Color.getColor("gris_tram", 4607576)); 
		panel.setLayout(layout);
		panel.setPreferredSize(setNewDimension(400,(40*size)+20));
		return panel;

	}

}
