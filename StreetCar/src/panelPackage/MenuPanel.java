package panelPackage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

		JPanel zone1 = newZone(1);
		JPanel zone2 = newZone(6);
		JPanel zone3 = newZone(1);
		
		if(parentPanel != null){addNewButton(zone1, "Retour", listener.new ReturnButtonListener(this, parentPanel));}
		if(parentDialog != null){addNewButton(zone1, "Retour", listener.new ReturnButtonListener(parentDialog));}
		if(parentFrame != null){addNewButton(zone1, "Retour", listener.new ReturnButtonListener(parentFrame));}

		addNewButton(zone2, "Nouvelle partie", listener.new ConfigureNewGameButtonListener());
		addNewButton(zone2, "Sauvegarder la partie", null);
		addNewButton(zone2, "Charger une partie", null);
		addNewButton(zone2, "Succès", null);
		addNewButton(zone2, "Options", null);
		addNewButton(zone2, "Crédits", null);
		addNewButton(zone3, "Quitter", listener.new QuitButtonListener());
				
		this.add(zone1);
		this.add(zone2);
		this.add(zone3);
	}

	private JPanel newZone(int size){
		JPanel panel = new JPanel();
		FlowLayout layout = new FlowLayout();
		layout.setVgap(0);
		panel.setLayout(layout);
		panel.setPreferredSize(new Dimension(200,50*size+20));
		return panel;

	}

	private void addNewButton(JPanel panel, String text, ActionListener action){
		Dimension size = new Dimension(200,50);
		JButton button = new JButton(text);
		button.setPreferredSize(size);
		button.addActionListener(action);
		panel.add(button);
	}

}
