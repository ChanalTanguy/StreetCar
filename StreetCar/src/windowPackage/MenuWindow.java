package windowPackage;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import panelPackage.MenuPanel;
import panelPackage.NewGamePanel;
import panelPackage.SettingsPanel;
import constantesPackages.Constantes;

public class MenuWindow extends InterfaceWindow {

	public MenuWindow(){
	}
	
	public void openWindow(){
		JDialog win = new JDialog(new JFrame(), "Allan please add title", true);
		MenuPanel menu = new MenuPanel(win);
		setNewSize(win,425,690);
		win.add(menu);
		win.setResizable(false);
		win.setLocationRelativeTo(null);
		win.setVisible(true);
		win.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		win.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
	}
	
}
