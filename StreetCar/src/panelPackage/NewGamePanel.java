package panelPackage;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import mainPackage.Moteur;
import joueurPackage.Joueur;
import joueurPackage.JoueurHumain;
import joueurPackage.JoueurIA;
import constantesPackages.Constantes;
import panelPackage.PanelListener.ReturnButtonListener;
import windowPackage.WindowInterface;
import windowPackage.MenuWindow;

public class NewGamePanel extends PanelInterface {

	String selected1 = "Humain";
	String selected2 = "Facile";

	boolean openMenu = false;

	Dimension buttonSize = setNewDimension(175,60);

	public NewGamePanel(JDialog parent){
		parentDialog = parent;
		this.setCursor();
		this.buildNewGamePanel();
	}

	public NewGamePanel(JDialog parent, boolean b){
		parentDialog = parent;
		openMenu = b;
		this.setCursor();
		this.buildNewGamePanel();
	}

	private void buildNewGamePanel(){

		JPanel zone1 = newRadioButtonZone("Niveau du joueur");
		JPanel zone2 = newRadioButtonZone("Niveau de l'adversaire");
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

		addNewButton(zone3, "Démarrer", buttonSize, BorderLayout.WEST, listener.new StartNewGameButtonListener(group1, group2, parentDialog, mainWindow), null);

		if (openMenu){
			addNewButton(zone3, "Annuler", buttonSize, BorderLayout.EAST, listener.new ReturnButtonListener(parentDialog, new MenuWindow()), null);
		} else {
			addNewButton(zone3, "Annuler", buttonSize, BorderLayout.EAST, listener.new ReturnButtonListener(parentDialog, null), null);
		}

		this.add(zone1);
		this.add(zone2);
		this.add(zone3);

	}

	private JPanel newRadioButtonZone(String title){
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(title));
		panel.setPreferredSize(setNewDimension(400,60));
		return panel;
	}

	private void addNewRadioButton(JPanel panel, ButtonGroup group, String text, String selected, ActionListener action){
		JRadioButton rbutton = new JRadioButton(text);
		panel.add(rbutton);
		group.add(rbutton);
		if (text == selected){ rbutton.setSelected(true); };
		rbutton.addActionListener(action);
	}
	
}
