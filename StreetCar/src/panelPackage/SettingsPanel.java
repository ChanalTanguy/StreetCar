package panelPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import constantesPackages.Constantes;
import panelPackage.PanelListener.CancelButtonListener;
import windowPackage.MenuWindow;

public class SettingsPanel extends PanelInterface {
	
	Dimension buttonSize = setNewDimension(175,40);
	
	boolean openMenu = false;
	
	boolean selectDebug = true;

	public SettingsPanel(JFrame main, JDialog parent){
		parentDialog = parent;
		mainWindow = main;
		this.setCursor();
		this.buildSettingsPanel();
		this.setBackground(Color.getColor("gris_tram", 4607576));
	}
	
	public SettingsPanel(JFrame main, JDialog parent, boolean b){
		parentDialog = parent;
		openMenu = b;
		mainWindow = main;
		this.setCursor();
		this.buildSettingsPanel();
		this.setBackground(Color.getColor("gris_tram", 4607576));
	}

	private void buildSettingsPanel(){

		JPanel zone1 = newCheckBoxZone(400,50);
		JPanel zone2 = newButtonZone(400,50);
		
		addNewCheckBox(zone1, "Activer l'historique", listener.new DebugCheckBoxListener(), selectDebug);
		
		addNewButton(zone2, "Confirmer", buttonSize, BorderLayout.WEST, null, null);
		
		if (openMenu){
			addNewButton(zone2, "Annuler", buttonSize, BorderLayout.EAST, listener.new CancelButtonListener(parentDialog, new MenuWindow(), mainWindow), null);
		} else {
			addNewButton(zone2, "Annuler", buttonSize, BorderLayout.EAST, listener.new CancelButtonListener(parentDialog, null), null);
		}
				
		this.add(zone1);
		this.add(zone2);
	}
	
	private JPanel newCheckBoxZone(int x, int y){
		JPanel panel = new JPanel();
		panel.setPreferredSize(setNewDimension(x,y));
		panel.setBackground(Color.getColor("gris_tram", 4607576));
		return panel;
	}
	
	private void addNewCheckBox(JPanel panel, String text, ItemListener action, boolean selected){
		JCheckBox box = new JCheckBox(text);
	    box.setMnemonic(KeyEvent.VK_C); 
	    box.setSelected(selected);
	    box.addItemListener(action);
	    box.setBackground(Color.getColor("gris_tram", 4607576));
	    box.setForeground(Color.white);
		panel.add(box);
	}

}
