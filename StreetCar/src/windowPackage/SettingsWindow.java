package windowPackage;

import java.awt.Dialog;

import javax.swing.JDialog;
import javax.swing.JFrame;

import panelPackage.SettingsPanel;

public class SettingsWindow extends InterfaceWindow {
	
	public SettingsWindow(){
		
	}
	
	public void openSettingsWindow(){
		JDialog win = new JDialog(new JFrame(), "Allan please add title", true);
		SettingsPanel menu = new SettingsPanel(win);
		setNewSize(win,500,160);
		win.add(menu);
		win.setResizable(false);
		win.setLocationRelativeTo(null);
		win.setVisible(true);
		win.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		win.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
	}

}
