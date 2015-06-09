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
import panelPackage.PanelListener.CancelButtonListener;
import windowPackage.MenuWindow;

public class RulesPanel extends PanelInterface {
	
	Dimension buttonSize = setNewDimension(250,60);
	Dimension imageSize = setNewDimension(1024,740);

	public RulesPanel(JFrame main, JDialog parent){	
		
		parentDialog = parent;
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
		
		JLabel image = new JLabel(resizeImage(loadImage("tutoriel","tuto1.png"), imageSize));
		zone1.add(image);

		addNewButton(zone2, buttonSize, BorderLayout.CENTER, listener.new CancelButtonListener(parentDialog, null, this.mainWindow), loadImage("background","retour_menu.png"));
				
		this.add(zone1);
		this.add(zone2);
	}
}
