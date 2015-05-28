package panelPackage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import constantesPackages.Constantes;
import panelPackage.PanelListener.ReturnButtonListener;

public class NewGamePanel extends JPanel {
	
	double width = Constantes.Resolution.width;
	double height = Constantes.Resolution.height;
	
	JDialog parentDialog;
	JFrame parentFrame;
	JPanel parentPanel;
	
	PanelListener listener = new PanelListener();
	String selected1 = "Humain";
	String selected2 = "Facile";
	
	public NewGamePanel(JDialog parent){
		parentDialog = parent;
		this.buildNewGamePanel();
	}
	
	public NewGamePanel(JFrame parent){
		parentFrame = parent;
		this.buildNewGamePanel();
	}

	public NewGamePanel(JPanel parent){
		parentPanel = parent;
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
		JPanel zone3 = newButtonZone();
		
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
		
		addNewButton(zone3, "Démarrer", BorderLayout.WEST, null);
		
		if(parentPanel != null){addNewButton(zone3, "Annuler", BorderLayout.EAST, listener.new ReturnButtonListener(this, parentPanel));}
		if(parentDialog != null){addNewButton(zone3, "Annuler", BorderLayout.EAST, listener.new ReturnButtonListener(parentDialog));}
		if(parentFrame != null){addNewButton(zone3, "Annuler", BorderLayout.EAST, listener.new ReturnButtonListener(parentFrame));}
		
		this.add(zone1);
		this.add(zone2);
		this.add(zone3);
		
	}
	
	private JPanel newButtonZone(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(setNewDimension(400,60));
		return panel;
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
	
	private void addNewButton(JPanel panel, String text, String position, ActionListener action){
		Dimension size = setNewDimension(175,60);
		JButton button = new JButton(text);
		button.setPreferredSize(size);
		button.addActionListener(action);
		panel.add(button, position);
	}
	
	private Dimension setNewDimension(double w, double h) {
		double newHeight = height/(1024.0/h);
		double newWidth = (newHeight*w)/h;
		return new Dimension((int)newWidth, (int)newHeight);
	}
	
}
