package windowPackage;

import java.awt.Dialog;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import panelPackage.ConfirmPanel;

@SuppressWarnings("serial")
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
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.setVisible(true);
		this.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
	}

	public void setListeners(ActionListener actionYES, ActionListener actionNO){
		this.actionYES = actionYES;
		this.actionNO = actionNO;
	}
	
}
