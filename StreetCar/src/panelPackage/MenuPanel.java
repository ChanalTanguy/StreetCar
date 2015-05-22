package panelPackage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {

	JDialog parentDialog;
	JFrame parentFrame;
	JPanel parentPanel;

	PanelListener listener = new PanelListener();

	public MenuPanel(JDialog parent){
		parentDialog = parent;
		this.buildMenuPanel();
	}
	
	public MenuPanel(JFrame parent){
		parentFrame = parent;
		this.buildMenuPanel();
	}

	public MenuPanel(JPanel parent){
		parentPanel = parent;
		this.buildMenuPanel();
	}

	private void buildMenuPanel(){

		JPanel zone1 = newZone(3);
		JPanel zone2 = newZone(2);
		JPanel zone3 = newZone(2);

		addNewButton(zone1, "Nouvelle partie", BorderLayout.NORTH, listener.new ConfigureNewGameButtonListener());

		addNewButton(zone1, "Sauvegarder la partie",  BorderLayout.CENTER, null);
		addNewButton(zone1, "Charger une partie",  BorderLayout.SOUTH, null);

		addNewButton(zone2, "Succès",  BorderLayout.NORTH, null);
		addNewButton(zone2, "Options",  BorderLayout.SOUTH, null);

		addNewButton(zone3, "Quitter",  BorderLayout.CENTER, listener.new QuitButtonListener());
		
		if(parentPanel != null){addNewButton(zone3, "Retour", BorderLayout.SOUTH, listener.new ReturnButtonListener(this, parentPanel));}
		if(parentDialog != null){addNewButton(zone3, "Retour", BorderLayout.SOUTH, listener.new ReturnButtonListener(parentDialog));}
		if(parentFrame != null){addNewButton(zone3, "Retour", BorderLayout.SOUTH, listener.new ReturnButtonListener(parentFrame));}
		
		this.add(zone1);
		this.add(zone2);
		this.add(zone3);
	}

	private JPanel newZone(int size){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(new Dimension(200,50*size));
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
