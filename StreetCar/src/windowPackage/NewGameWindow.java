package windowPackage;

import java.awt.Dialog;

import javax.swing.JDialog;
import javax.swing.JFrame;

import panelPackage.NewGamePanel;

public class NewGameWindow extends InterfaceWindow {

	public NewGameWindow(){
	}
	
	public void openWindow(){
		JDialog win = new JDialog(new JFrame(), "Allan please add title", true);
		NewGamePanel menu = new NewGamePanel(win);
		setNewSize(win,500,230);
		win.add(menu);
		win.setResizable(false);
		win.setLocationRelativeTo(null);
		win.setVisible(true);
		win.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		win.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
	}
	
}
