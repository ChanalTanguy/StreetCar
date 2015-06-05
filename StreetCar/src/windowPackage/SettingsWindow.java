package windowPackage;

import java.awt.Dialog;

import javax.swing.JDialog;
import javax.swing.JFrame;

import panelPackage.SettingsPanel;

public class SettingsWindow extends WindowInterface {
	
	public SettingsWindow(){
		
	}
	
	public void openWindow(){
		JDialog win = new JDialog(new JFrame(), "Allan please add title", true);
		SettingsPanel menu = new SettingsPanel(mainWindow, win);
		setNewSize(win,500,160);
		win.add(menu);
		win.setResizable(false);
		win.setLocationRelativeTo(null);
		win.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		win.setVisible(true);
		win.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
	}
	
	public void openWindow(boolean b){
		JDialog win = new JDialog(new JFrame(), "Allan please add title", true);
		SettingsPanel menu = new SettingsPanel(mainWindow, win, b);
		setNewSize(win,500,160);
		win.add(menu);
		win.setResizable(false);
		win.setLocationRelativeTo(null);
		win.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		win.setVisible(true);
		win.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
	}

}
