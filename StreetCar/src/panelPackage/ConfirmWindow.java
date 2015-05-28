package panelPackage;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import constantesPackages.Constantes;

public class ConfirmWindow extends JDialog {
	
	double width = Constantes.Resolution.width;
	double height = Constantes.Resolution.height;
	
	ActionListener actionYES;
	ActionListener actionNO;
	String message;
	
	public ConfirmWindow(String msg, ActionListener actionYES, ActionListener actionNO){
		this.message = msg;
		this.actionYES = actionYES;
		this.actionNO = actionNO;
	}
	
	public void openConfirmWindow(){
		this.setWindow();
		this.setVisible(true);
	}
	
	private void setWindow(){
		this.setSize(275,125);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("Allan please add title");
		
		JPanel panel = buildConfirmPanel();
		this.add(panel);
		
		this.setModal(true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
	}
	
	private JPanel buildConfirmPanel(){
		JPanel confirm = new JPanel();
		
		JPanel zone1 = newZone(250, 40);
		JPanel zone2 = newZone(200, 40);
		
		JLabel label = new JLabel(message, SwingConstants.CENTER);
		
		zone1.add(label);
		addNewButton(zone2, "OUI", BorderLayout.WEST, actionYES, null);
		addNewButton(zone2, "NON", BorderLayout.EAST, actionNO, null);
		
		confirm.add(zone1);
		confirm.add(zone2);
		
		return confirm;
	}
	
	private JPanel newZone(int x, int y){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(setNewDimension(x,y));
		return panel;
	}
	
	private void addNewButton(JPanel panel, String text, String position, ActionListener action, ImageIcon img){
		Dimension size = setNewDimension(85,40);
		JButton button = new JButton(text, img);
		button.setPreferredSize(size);
		button.addActionListener(action);
		panel.add(button, position);
	}
	
	public void setListeners(ActionListener actionYES, ActionListener actionNO){
		this.actionYES = actionYES;
		this.actionNO = actionNO;
	}
	
	private Dimension setNewDimension(double w, double h) {
		double newHeight = height/(1024.0/h);
		double newWidth = (newHeight*w)/h;
		return new Dimension((int)newWidth, (int)newHeight);
	}
}
