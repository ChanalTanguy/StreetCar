package panelPackage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuPanel {
	
	public static void addMenuToFrame(JFrame frame){
		MenuPanel menuBuilder = new MenuPanel();
		JPanel menu = menuBuilder.buildMenuPanel();
		frame.add(menu);
	}
	
	public static void addMenuToFrame(JFrame frame, String position){
		MenuPanel menuBuilder = new MenuPanel();
		JPanel menu = menuBuilder.buildMenuPanel();
		frame.setLayout(new BorderLayout());
		frame.add(menu, position);
	}
	
	public static void addMenuToPanel(JPanel panel){
		MenuPanel menuBuilder = new MenuPanel();
		JPanel menu = menuBuilder.buildMenuPanel();
		panel.add(menu);
	}
	
	public static void addMenuToPanel(JPanel panel, String position){
		MenuPanel menuBuilder = new MenuPanel();
		JPanel menu = menuBuilder.buildMenuPanel();
		panel.setLayout(new BorderLayout());
		panel.add(menu, position);
	}
	
	private JPanel buildMenuPanel(){
		
		JPanel content = new JPanel();
		
		JPanel zone1 = newZone();
		JPanel zone2 = newZone();
		JPanel zone3 = newZone();
		JPanel zone4 = newZone();
		
		addNewButton(zone1, "Nouvelle partie", BorderLayout.NORTH, null);
		addNewButton(zone1, "Retour", BorderLayout.SOUTH, null);
		
		addNewButton(zone2, "Sauvegarder la partie",  BorderLayout.NORTH, null);
		addNewButton(zone2, "Charger une partie",  BorderLayout.SOUTH, null);
		
		addNewButton(zone3, "Succès",  BorderLayout.NORTH, null);
		addNewButton(zone3, "Options",  BorderLayout.SOUTH, null);
		
		addNewButton(zone4, "Quitter",  BorderLayout.CENTER, null);
		
		content.add(zone1);
		content.add(zone2);
		content.add(zone3);
		content.add(zone4);
		
		return content;
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
