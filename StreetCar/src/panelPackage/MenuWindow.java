package panelPackage;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuWindow {

	public MenuWindow(){
	}
	
	public void openMenuWindow(){
		JDialog win = new JDialog(new JFrame(), "Allan please add title", true);
		MenuPanel menu = new MenuPanel(win);
		win.setSize(300,490);
		win.add(menu);
		win.setResizable(false);
		win.setLocationRelativeTo(null);
		win.setVisible(true);
		win.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		win.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
	}
	
	public void openNewGameWindow(){
		JDialog win = new JDialog(new JFrame(), "Allan please add title", true);
		NewGamePanel menu = new NewGamePanel(win);
		win.setSize(400,150);
		win.add(menu);
		win.setResizable(false);
		win.setLocationRelativeTo(null);
		win.setVisible(true);
		win.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		win.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
	}
	
}
