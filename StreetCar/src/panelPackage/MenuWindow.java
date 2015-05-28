package panelPackage;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import constantesPackages.Constantes;

public class MenuWindow {
	double width = Constantes.Resolution.width;
	double height = Constantes.Resolution.height;

	public MenuWindow(){
	}
	
	public void openMenuWindow(){
		JDialog win = new JDialog(new JFrame(), "Allan please add title", true);
		MenuPanel menu = new MenuPanel(win);
		win.setSize((int)(width/3.01),(int)(height/1.48));
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
		win.setSize((int)(width/2.56),(int)(height/4.45));
		win.add(menu);
		win.setResizable(false);
		win.setLocationRelativeTo(null);
		win.setVisible(true);
		win.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		win.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
	}
	
	public void openSettingsWindow(){
		JDialog win = new JDialog(new JFrame(), "Allan please add title", true);
		SettingsPanel menu = new SettingsPanel(win);
		win.setSize((int)(width/2.56),(int)(height/6.4));
		win.add(menu);
		win.setResizable(false);
		win.setLocationRelativeTo(null);
		win.setVisible(true);
		win.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		win.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
	}
	
}
