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

public class MenuPanel extends InterfacePanel {
	
	Dimension buttonSize = setNewDimension(400,75);

	public MenuPanel(JDialog parent){
		parentDialog = parent;
		this.buildMenuPanel();
	}

	private void buildMenuPanel(){

		JPanel zone1 = newZone(1);
		JPanel zone2 = newZone(6);
		JPanel zone3 = newZone(1);
	
		addNewButton(zone1, "Reprendre", buttonSize, null, listener.new ReturnButtonListener(parentDialog, null), null);

		addNewButton(zone2, "Nouvelle partie", buttonSize, null, listener.new ConfigureNewGameButtonListener(parentDialog, true), null);

		addNewButton(zone2, "Sauvegarder la partie", buttonSize, null, null, null);
		addNewButton(zone2, "Charger une partie", buttonSize, null, null, null);
		addNewButton(zone2, "Défis", buttonSize, null, null, null);
		addNewButton(zone2, "Options", buttonSize, null, listener.new SettingsButtonListener(), null);
		addNewButton(zone2, "Crédits", buttonSize, null, null, null);
		addNewButton(zone3, "Quitter", buttonSize, null, listener.new QuitButtonListener(), null);
				
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
	
}
