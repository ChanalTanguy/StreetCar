package panelPackage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import constantesPackages.Constantes;
import panelPackage.PanelListener.ReturnButtonListener;

public class NewGamePanel extends InterfacePanel {

	String selected1 = "Humain";
	String selected2 = "Facile";

	Dimension buttonSize = setNewDimension(175,60);

	public NewGamePanel(JDialog parent){
		parentDialog = parent;
		this.buildNewGamePanel();
	}

	public NewGamePanel(String niv1, String niv2){
		selected1 = niv1;
		selected2 = niv2;
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

		addNewButton(zone3, "Démarrer", buttonSize, BorderLayout.WEST, listener.new StartNewGameButtonListener(), null);
		addNewButton(zone3, "Annuler", buttonSize, BorderLayout.EAST, listener.new ReturnButtonListener(parentDialog, null), null);

		this.add(zone1);
		this.add(zone2);
		this.add(zone3);

	}

	private JPanel newRadioButtonZone(String title){
		JPanel panel = new JPanel();
		//panel.setLayout(new BorderLayout());
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
