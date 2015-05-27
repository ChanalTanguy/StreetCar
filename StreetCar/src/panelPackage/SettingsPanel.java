package panelPackage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import panelPackage.PanelListener.ReturnButtonListener;

public class SettingsPanel extends JPanel {

	JDialog parentDialog;
	JFrame parentFrame;
	JPanel parentPanel;

	PanelListener listener = new PanelListener();
	
	boolean selectDebug = false;

	public SettingsPanel(JDialog parent){
		parentDialog = parent;
		this.buildSettingsPanel();
	}

	public SettingsPanel(JFrame parent){
		parentFrame = parent;
		this.buildSettingsPanel();
	}

	public SettingsPanel(JPanel parent){
		parentPanel = parent;
		this.buildSettingsPanel();
	}

	private void buildSettingsPanel(){

		JPanel zone1 = newCheckBoxZone(400,50);
		JPanel zone2 = newButtonZone(400,50);
		
		addNewCheckBox(zone1, "Activer le mode debug", listener.new DebugCheckBoxListener(), selectDebug);
		
		addNewButton(zone2, "Confirmer", BorderLayout.WEST, null, null);
		
		if(parentPanel != null){addNewButton(zone2, "Annuler", BorderLayout.EAST, listener.new ReturnButtonListener(this, parentPanel),null);}
		if(parentDialog != null){addNewButton(zone2, "Annuler", BorderLayout.EAST, listener.new ReturnButtonListener(parentDialog), null);}
		if(parentFrame != null){addNewButton(zone2, "Annuler", BorderLayout.EAST, listener.new ReturnButtonListener(parentFrame), null);}
		
		this.add(zone1);
		this.add(zone2);
	}

	private JPanel newButtonZone(int x, int y){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(new Dimension(x,y));
		return panel;
	}
	
	private JPanel newCheckBoxZone(int x, int y){
		JPanel panel = new JPanel();
		//panel.setBorder(BorderFactory.createTitledBorder(title));
		panel.setPreferredSize(new Dimension(x,y));
		return panel;
	}
	
	private void addNewButton(JPanel panel, String text, String position, ActionListener action, ImageIcon img){
		Dimension size = new Dimension(175,40);
		JButton button = new JButton(text, img);
		button.setPreferredSize(size);
		button.addActionListener(action);
		panel.add(button, position);
	}
	
	private void addNewCheckBox(JPanel panel, String text, ItemListener action, boolean selected){
		JCheckBox box = new JCheckBox(text);
	    box.setMnemonic(KeyEvent.VK_C); 
	    box.setSelected(selected);
	    box.addItemListener(action);
		panel.add(box);
	}

}
