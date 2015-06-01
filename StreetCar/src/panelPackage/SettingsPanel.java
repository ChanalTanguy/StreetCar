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

import constantesPackages.Constantes;
import panelPackage.PanelListener.ReturnButtonListener;
import windowPackage.MenuWindow;

public class SettingsPanel extends InterfacePanel {
	
	Dimension buttonSize = setNewDimension(175,40);
	
	boolean openMenu = false;
	
	boolean selectDebug = false;

	public SettingsPanel(JDialog parent){
		parentDialog = parent;
		this.buildSettingsPanel();
	}
	
	public SettingsPanel(JDialog parent, boolean b){
		parentDialog = parent;
		openMenu = b;
		this.buildSettingsPanel();
	}

	private void buildSettingsPanel(){

		JPanel zone1 = newCheckBoxZone(400,50);
		JPanel zone2 = newButtonZone(400,50);
		
		addNewCheckBox(zone1, "Activer le mode debug", listener.new DebugCheckBoxListener(), selectDebug);
		
		addNewButton(zone2, "Confirmer", buttonSize, BorderLayout.WEST, null, null);
		
		if (openMenu){
			addNewButton(zone2, "Annuler", buttonSize, BorderLayout.EAST, listener.new ReturnButtonListener(parentDialog, new MenuWindow()), null);
		} else {
			addNewButton(zone2, "Annuler", buttonSize, BorderLayout.EAST, listener.new ReturnButtonListener(parentDialog, null), null);
		}
		
		//addNewButton(zone2, "Annuler", buttonSize, BorderLayout.EAST, listener.new ReturnButtonListener(parentDialog, null), null);
		
		this.add(zone1);
		this.add(zone2);
	}
	
	private JPanel newCheckBoxZone(int x, int y){
		JPanel panel = new JPanel();
		//panel.setBorder(BorderFactory.createTitledBorder(title));
		panel.setPreferredSize(setNewDimension(x,y));
		return panel;
	}
	
	private void addNewCheckBox(JPanel panel, String text, ItemListener action, boolean selected){
		JCheckBox box = new JCheckBox(text);
	    box.setMnemonic(KeyEvent.VK_C); 
	    box.setSelected(selected);
	    box.addItemListener(action);
		panel.add(box);
	}

}
