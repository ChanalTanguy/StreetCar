package windowPackage;

import java.awt.Dialog;

import javax.swing.JDialog;
import javax.swing.JFrame;

import panelPackage.NewGamePanel;

public class NewGameWindow extends WindowInterface {

	public NewGameWindow(){
	}
	
	public void openWindow(){
		JDialog win = new JDialog(new JFrame(), "Allan please add title", true);
		NewGamePanel menu = new NewGamePanel(mainWindow, win);
		setNewSize(win,500,230);
		win.add(menu);
		win.setResizable(false);
		win.setLocationRelativeTo(null);
		win.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		win.setVisible(true);
		win.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
	}
	
	public void openWindow(boolean b){
		JDialog win = new JDialog(new JFrame(), "Allan please add title", true);
		NewGamePanel menu = new NewGamePanel(mainWindow, win, b);
		setNewSize(win,500,230);
		win.add(menu);
		win.setResizable(false);
		win.setLocationRelativeTo(null);
		win.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		win.setVisible(true);
		win.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
	}
	
}
