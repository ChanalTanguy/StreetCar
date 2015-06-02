package windowPackage;

import java.awt.Dialog;

import javax.swing.JDialog;
import javax.swing.JFrame;

import panelPackage.CreditsPanel;
import panelPackage.SettingsPanel;

public class CreditsWindow extends WindowInterface {
	
	public CreditsWindow(){
		
	}
	
	public void openWindow(){
		JDialog win = new JDialog(new JFrame(), "Allan please add title", true);
		CreditsPanel menu = new CreditsPanel(win);
		setNewSize(win,750,600);
		win.add(menu);
		win.setResizable(false);
		win.setLocationRelativeTo(null);
		win.setVisible(true);
		win.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		win.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
	}
	
	public void openWindow(boolean b){
		JDialog win = new JDialog(new JFrame(), "Allan please add title", true);
		CreditsPanel menu = new CreditsPanel(win, b);
		setNewSize(win,750,600);
		win.add(menu);
		win.setResizable(false);
		win.setLocationRelativeTo(null);
		win.setVisible(true);
		win.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		win.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
	}

}
