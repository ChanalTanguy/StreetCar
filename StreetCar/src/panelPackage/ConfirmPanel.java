package panelPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ConfirmPanel extends InterfacePanel {
	
	ActionListener actionYES;
	ActionListener actionNO;
	String message;
	
	Dimension buttonSize = setNewDimension(85,40);
	
	public ConfirmPanel(String msg, ActionListener actionYES, ActionListener actionNO){
		this.message = msg;
		this.actionYES = actionYES;
		this.actionNO = actionNO;
		this.setLayout(new BorderLayout());
		this.setBackground(Color.getColor("gris_tram", 4607576));
		this.buildConfirmPanel();
	}
	
	protected void buildConfirmPanel(){
		JPanel confirm = new JPanel();
		confirm.setBackground(Color.getColor("gris_tram", 4607576));
		JPanel zone1 = newZone(250, 40);
		JPanel zone2 = newZone(200, 40);
		
		JLabel label = new JLabel(message, SwingConstants.CENTER);
		
		zone1.add(label);
		addNewButton(zone2, buttonSize, BorderLayout.WEST, actionYES, loadImage("oui.png"));
		addNewButton(zone2, buttonSize, BorderLayout.EAST, actionNO, loadImage("non.png"));
		
		confirm.add(zone1, BorderLayout.NORTH);
		confirm.add(zone2, BorderLayout.SOUTH);
		this.add(confirm);
		this.setBackground(Color.getColor("gris_tram", 4607576));
	}
	
	private JPanel newZone(int x, int y){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.getColor("gris_tram", 4607576));
		panel.setPreferredSize(setNewDimension(x,y));
		return panel;
	}

	public void setListeners(ActionListener actionYES, ActionListener actionNO){
		this.actionYES = actionYES;
		this.actionNO = actionNO;
	}
	
}
