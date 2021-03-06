package windowPackage;

import java.awt.Dialog;

import javax.swing.JDialog;
import javax.swing.JFrame;

import panelPackage.LoadGamePanel;

@SuppressWarnings("serial")
public class LoadGameWindow extends WindowInterface {
	
	public void openWindow(){
		JDialog win = new JDialog(new JFrame(), "Allan please add title", true);
		LoadGamePanel menu = new LoadGamePanel(mainWindow, win);
		setNewSize(win,900,340);
		win.add(menu);
		win.setResizable(false);
		win.setLocationRelativeTo(null);
		win.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		win.setVisible(true);
		win.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
	}
	
	public void openWindow(boolean b){
		JDialog win = new JDialog(new JFrame(), "Allan please add title", true);
		LoadGamePanel menu = new LoadGamePanel(mainWindow, win, b);
		setNewSize(win,900,340);
		win.add(menu);
		win.setResizable(false);
		win.setLocationRelativeTo(null);
		win.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		win.setVisible(true);
		win.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
	}

}
