package panelPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import windowPackage.MenuWindow;

@SuppressWarnings("serial")
public class NewGamePanel extends PanelInterface {

	String selected1 = "Humain";
	String selected2 = "Facile";

	boolean openMenu = false;

	Dimension buttonSize = setNewDimension(175,60);

	public NewGamePanel(JFrame main, JDialog parent){
		parentDialog = parent;
		mainWindow = main;
		this.setCursor();
		this.buildNewGamePanel();
		this.setBackground(Color.getColor("gris_tram", 4607576));
	}

	public NewGamePanel(JFrame main, JDialog parent, boolean b){
		parentDialog = parent;
		openMenu = b;
		mainWindow = main;
		this.setCursor();
		this.buildNewGamePanel();
		this.setBackground(Color.getColor("gris_tram", 4607576));
	}

	private void buildNewGamePanel(){

		JPanel zone1 = newRadioButtonZone("Niveau du Joueur 1");
		JPanel zone2 = newRadioButtonZone("Niveau du Joueur 2");
		JPanel zone3 = newButtonZone(400,60);

		ButtonGroup group1 = new ButtonGroup();

		addNewRadioButton(zone1, group1, "Humain", selected1, null);
		addNewRadioButton(zone1, group1, "Facile", selected1, null);
		addNewRadioButton(zone1, group1, "Moyen", selected1, null);
		addNewRadioButton(zone1, group1, "Difficile", selected1, null);

		ButtonGroup group2 = new ButtonGroup();

		addNewRadioButton(zone2, group2, "Humain", selected2, null);
		addNewRadioButton(zone2, group2, "Facile", selected2, null);
		addNewRadioButton(zone2, group2, "Moyen", selected2, null);
		addNewRadioButton(zone2, group2, "Difficile", selected2, null);
		addNewButton(zone3, buttonSize, BorderLayout.WEST, listener.new StartNewGameButtonListener(group1, group2, parentDialog, mainWindow), loadImage("background","lancer.png"));

		if (openMenu){
			addNewButton(zone3, buttonSize, BorderLayout.EAST, listener.new CancelButtonListener(parentDialog, new MenuWindow(), mainWindow), loadImage("background","annuler.png"));
		} else {
			addNewButton(zone3, buttonSize, BorderLayout.EAST, listener.new CancelButtonListener(parentDialog, null), loadImage("background","annuler.png"));
		}

		this.add(zone1);
		this.add(zone2);
		this.add(zone3);
		

	}

	private JPanel newRadioButtonZone(String title){
		JPanel panel = new JPanel();
		TitledBorder border = BorderFactory.createTitledBorder(title);
		border.setTitleColor(Color.white);
		panel.setBorder(border);
		panel.setPreferredSize(setNewDimension(400,60));
		panel.setBackground(Color.getColor("gris_tram", 4607576));
		return panel;
	}

	private void addNewRadioButton(JPanel panel, ButtonGroup group, String text, String selected, ActionListener action){
		JRadioButton rbutton = new JRadioButton(text);
		panel.add(rbutton);
		group.add(rbutton);
		rbutton.setBackground(Color.getColor("gris_tram", 4607576));
		rbutton.setForeground(Color.white);
		if (text == selected){ rbutton.setSelected(true); };
		rbutton.addActionListener(action);
	}
	
}
