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

import panelPackage.PanelListener.ReturnButtonListener;

public class NewGamePanel extends JPanel {
	
	JDialog parentDialog;
	JFrame parentFrame;
	JPanel parentPanel;
	
	PanelListener listener = new PanelListener();
	String selected = "Facile";
	
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
	
	public NewGamePanel(String niv){
		selected = niv;
		this.buildNewGamePanel();
	}
	
	private void buildNewGamePanel(){
		
		JPanel zone1 = newRadioButtonZone("Niveau de l'adversaire");
		JPanel zone2 = newButtonZone();
		
		ButtonGroup group = new ButtonGroup();
		
		addNewRadioButton(zone1, group, "Humain", null);
		addNewRadioButton(zone1, group, "Facile", null);
		addNewRadioButton(zone1, group, "Moyen", null);
		addNewRadioButton(zone1, group, "Difficile", null);
		
		addNewButton(zone2, "Démarrer", BorderLayout.WEST, null);
		
		if(parentPanel != null){addNewButton(zone2, "Annuler", BorderLayout.EAST, listener.new ReturnButtonListener(this, parentPanel));}
		if(parentDialog != null){addNewButton(zone2, "Annuler", BorderLayout.EAST, listener.new ReturnButtonListener(parentDialog));}
		if(parentFrame != null){addNewButton(zone2, "Annuler", BorderLayout.EAST, listener.new ReturnButtonListener(parentFrame));}
		
		this.add(zone1);
		this.add(zone2);
		
	}
	
	private JPanel newButtonZone(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(new Dimension(350,40));
		return panel;
	}
	
	private JPanel newRadioButtonZone(String title){
		JPanel panel = new JPanel();
		//panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder(title));
		panel.setPreferredSize(new Dimension(350,60));
		return panel;
	}
	
	private void addNewRadioButton(JPanel panel, ButtonGroup group, String text, ActionListener action){
		JRadioButton rbutton = new JRadioButton(text);
		panel.add(rbutton);
		group.add(rbutton);
		if (text == selected){ rbutton.setSelected(true); };
		rbutton.addActionListener(action);
	}
	
	private void addNewButton(JPanel panel, String text, String position, ActionListener action){
		Dimension size = new Dimension(150,50);
		JButton button = new JButton(text);
		button.setPreferredSize(size);
		button.addActionListener(action);
		panel.add(button, position);
	}
	
}
