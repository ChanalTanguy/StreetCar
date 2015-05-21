package panelPackage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class NewGamePanel extends JPanel {
	
	String selected = "Facile";
	
	public NewGamePanel(){
		this.buildNewGamePanel();
	}
	
	public NewGamePanel(String niv){
		selected = niv;
		this.buildNewGamePanel();
	}
	
	private void buildNewGamePanel(){
		
		JPanel zone1 = newZone("Niveau de l'adversaire");
		
		ButtonGroup group = new ButtonGroup();
		
		addNewRadioButton(zone1, group, "Humain", null);
		addNewRadioButton(zone1, group, "Facile", null);
		addNewRadioButton(zone1, group, "Moyen", null);
		addNewRadioButton(zone1, group, "Difficile", null);
		
		this.add(zone1);
		
	}
	
	private JPanel newZone(String title){
		JPanel panel = new JPanel();
		//panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder(title));
		panel.setPreferredSize(new Dimension(350,100));
		return panel;
		
	}
	
	private void addNewRadioButton(JPanel panel, ButtonGroup group, String text, ActionListener action){
		JRadioButton rbutton = new JRadioButton(text);
		panel.add(rbutton);
		group.add(rbutton);
		if (text == selected){ rbutton.setSelected(true); };
		rbutton.addActionListener(action);
	}
	
}
