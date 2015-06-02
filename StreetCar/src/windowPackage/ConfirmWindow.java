package windowPackage;

import java.awt.BorderLayout;
import java.awt.Color;
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

import panelPackage.ConfirmPanel;
import panelPackage.MenuPanel;
import panelPackage.NewGamePanel;
import constantesPackages.Constantes;

public class ConfirmWindow extends WindowInterface {
	
	ActionListener actionYES;
	ActionListener actionNO;
	String message;
	
	public ConfirmWindow(String msg, ActionListener actionYES, ActionListener actionNO){
		this.message = msg;
		this.actionYES = actionYES;
		this.actionNO = actionNO;
	}
	
	public void openConfirmWindow(){
		ConfirmPanel menu = new ConfirmPanel(message, actionYES, actionNO);
		setNewSize(this,275,125);
		this.add(menu);
		this.setResizable(false);
		this.setTitle("Allan please add title");
		this.setLocationRelativeTo(null);
		this.setModal(true);
		this.setVisible(true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
	}

	public void setListeners(ActionListener actionYES, ActionListener actionNO){
		this.actionYES = actionYES;
		this.actionNO = actionNO;
	}
	
}
