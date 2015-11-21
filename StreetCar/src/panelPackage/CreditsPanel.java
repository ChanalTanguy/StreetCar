package panelPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import windowPackage.MenuWindow;

@SuppressWarnings("serial")
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
		
		JLabel image = new JLabel(resizeImage(loadImage("background","tramCred.png"), imageSize));
		zone1.add(image);
		
		if (openMenu){
			addNewButton(zone2, buttonSize, BorderLayout.CENTER, listener.new CancelButtonListener(parentDialog, new MenuWindow(), mainWindow), loadImage("background","retour_menu.png"));
		} else {
			addNewButton(zone2, buttonSize, BorderLayout.CENTER, listener.new CancelButtonListener(parentDialog, null), loadImage("background","retour_menu.png"));
		}
				
		this.add(zone1);
		this.add(zone2);
	}
}
