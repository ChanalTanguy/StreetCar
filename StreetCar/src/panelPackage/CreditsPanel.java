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
import javax.swing.JLabel;
import javax.swing.JPanel;

import constantesPackages.Constantes;
import panelPackage.PanelListener.ReturnButtonListener;
import windowPackage.MenuWindow;

public class CreditsPanel extends PanelInterface {
	
	Dimension buttonSize = setNewDimension(250,60);
	Dimension imageSize = setNewDimension(750,475);
	
	boolean openMenu = false;

	public CreditsPanel(JFrame main, JDialog parent){
		parentDialog = parent;
		mainWindow = main;
		this.setCursor();
		this.buildSettingsPanel();
	}
	
	public CreditsPanel(JFrame main, JDialog parent, boolean b){
		parentDialog = parent;
		openMenu = b;
		mainWindow = main;
		this.setCursor();
		this.buildSettingsPanel();
	}

	private void buildSettingsPanel(){
		
		this.setBackground(Color.getColor("gris_tram", 4607576));
		
		//int panelWidth = (int)((8.0/10.0)*width);
		//int panelHeight = (int)((8.0/10.0)*height);
		
		JPanel zone1 = newZone(imageSize.width, imageSize.height+20);
		
		JPanel zone2 = newButtonZone(buttonSize.width, buttonSize.height);
		
		JLabel image = new JLabel(resizeImage(loadImage("tramCred.png"), imageSize));
		zone1.add(image);
		
		if (openMenu){
			addNewButton(zone2, "Retour", buttonSize, BorderLayout.CENTER, listener.new ReturnButtonListener(parentDialog, new MenuWindow(), mainWindow), null);
		} else {
			addNewButton(zone2, "Retour", buttonSize, BorderLayout.CENTER, listener.new ReturnButtonListener(parentDialog, null), null);
		}
				
		this.add(zone1);
		this.add(zone2);
	}
}
