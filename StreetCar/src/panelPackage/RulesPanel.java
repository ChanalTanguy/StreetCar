package panelPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RulesPanel extends PanelInterface {
	
	Dimension buttonSize = setNewDimension(250,60);
	Dimension imageSize = setNewDimension(1024,740);
	
	JLabel image = null;
	int numImg = 1;

	public RulesPanel(JFrame main, JDialog parent){	
		
		parentDialog = parent;
		mainWindow = main;
		this.setCursor();
		this.buildSettingsPanel();
	}

	private void buildSettingsPanel(){
		
		this.setBackground(Color.getColor("gris_tram", 4607576));
		
		JPanel zone1 = newZone(imageSize.width, imageSize.height);
		
		JPanel zone2 = newButtonZone(buttonSize.width*2+10, buttonSize.height);
		JPanel sep = newZone(1024, 10);
		JPanel zone3 = newButtonZone(buttonSize.width, buttonSize.height);
		
		image = new JLabel(resizeImage(loadImage("tutoriel","tuto"+numImg+".png"), imageSize));
		zone1.add(image);
		
		addNewButton(zone2, buttonSize, BorderLayout.WEST, new PreviousButtonListener(), loadImage("background","precedent_menu.png"));
		addNewButton(zone2, buttonSize, BorderLayout.EAST, new NextButtonListener(), loadImage("background","suivant_menu.png"));
		addNewButton(zone3, buttonSize, BorderLayout.CENTER, listener.new CancelButtonListener(parentDialog, null, this.mainWindow), loadImage("background","retour_menu.png"));
				
		this.add(zone1);
		this.add(zone2);
		this.add(sep);
		this.add(zone3);
	}
	
	private void incrementImageNumber(){
		if (numImg == 8){ numImg = 1; }
		else { numImg++; }
	}
	
	private void decrementImageNumber(){
		if (numImg == 1){ numImg = 8; }
		else { numImg--; }
	}
	
	private class NextButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			incrementImageNumber();
			image.setIcon(resizeImage(loadImage("tutoriel","tuto"+numImg+".png"), imageSize));
		}
	}
	
	private class PreviousButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			decrementImageNumber();
			image.setIcon(resizeImage(loadImage("tutoriel","tuto"+numImg+".png"), imageSize));
		}
	}
	
}
