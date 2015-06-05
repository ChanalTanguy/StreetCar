package panelPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import mainPackage.Moteur;
import objectPackage.Plateau;
import panelPackage.PanelListener.DebugCheckBoxListener;
import panelPackage.PanelListener.ReturnButtonListener;
import windowPackage.MenuWindow;

public class LoadGamePanel extends PanelInterface {

	Dimension buttonSize = setNewDimension(175,40);

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

		JPanel zone1 = newButtonZone(400,50);
		JPanel zone2 = newButtonZone(400,50);

		addNewButton(zone1, "LEBOUTON", buttonSize, BorderLayout.CENTER, listener.new LoadGameListener(new Moteur(new Plateau()), parentDialog, mainWindow), null);

		addNewButton(zone2, "Confirmer", buttonSize, BorderLayout.WEST, null, null);

		if (openMenu){
			addNewButton(zone2, "Annuler", buttonSize, BorderLayout.EAST, listener.new ReturnButtonListener(parentDialog, new MenuWindow(), mainWindow), null);
		} else {
			addNewButton(zone2, "Annuler", buttonSize, BorderLayout.EAST, listener.new ReturnButtonListener(parentDialog, null), null);
		}

		this.add(zone1);
		this.add(zone2);
	}

}
