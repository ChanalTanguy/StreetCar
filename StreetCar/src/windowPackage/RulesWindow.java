package windowPackage;

import java.awt.Dialog;

import javax.swing.JDialog;
import javax.swing.JFrame;

import panelPackage.RulesPanel;

@SuppressWarnings("serial")
public class RulesWindow extends WindowInterface {
	
	public RulesWindow(){
	}
	
	public void openWindow(){
		
		JDialog win = new JDialog(new JFrame(), "Allan please add title", true);
		RulesPanel menu = new RulesPanel(mainWindow, win);
		setNewSize(win,1024,940);
		win.add(menu);
		win.setResizable(false);
		win.setLocationRelativeTo(null);
		win.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		win.setVisible(true);
		win.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
	}

}
