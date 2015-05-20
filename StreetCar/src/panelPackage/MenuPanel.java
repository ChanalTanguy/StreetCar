package panelPackage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {
	
	public MenuPanel(){
		this.buildMenuPanel();
	}
	
	private void buildMenuPanel(){
		
		JPanel zone1 = newZone();
		JPanel zone2 = newZone();
		JPanel zone3 = newZone();
		JPanel zone4 = newZone();
		
		addNewButton(zone1, "Nouvelle partie", BorderLayout.NORTH, null);
		addNewButton(zone4, "Retour", BorderLayout.SOUTH, null);
		
		addNewButton(zone1, "Sauvegarder la partie",  BorderLayout.CENTER, null);
		addNewButton(zone1, "Charger une partie",  BorderLayout.SOUTH, null);
		
		addNewButton(zone3, "Succès",  BorderLayout.NORTH, null);
		addNewButton(zone3, "Options",  BorderLayout.SOUTH, null);
		
		addNewButton(zone4, "Quitter",  BorderLayout.CENTER, null);
		
		this.add(zone1);
		this.add(zone2);
		this.add(zone3);
		this.add(zone4);
	}
	
	private JPanel newZone(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		return panel;
		
	}
	
	private void addNewButton(JPanel panel, String text, String position, ActionListener action){
		Dimension size = new Dimension(200,50);
		JButton button = new JButton(text);
		button.setPreferredSize(size);
		button.addActionListener(action);
		panel.add(button, position);
	}
	
}
