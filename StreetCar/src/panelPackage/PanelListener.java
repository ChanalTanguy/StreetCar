package panelPackage;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import windowPackage.FUCKINGConfirmWindow;
import windowPackage.ConfirmWindow;
import windowPackage.MenuWindow;

public class PanelListener {

	public class ConfigureNewGameButtonListener implements ActionListener {

		// Clic sur le bouton "Nouvelle partie" du menu principal

		public void actionPerformed(ActionEvent e) {
			MenuWindow win = new MenuWindow();
			win.openNewGameWindow();
		}

	}

	public class QuitButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			ConfirmWindow conf = new ConfirmWindow("Êtes-vous sûr de vouloir quitter ?", null, null);
			PanelListener listener = new PanelListener();
			conf.setListeners(listener.new Quit(), listener.new ReturnButtonListener(conf));
			
			conf.openConfirmWindow();
		}
	}
	
	public class SettingsButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			MenuWindow win = new MenuWindow();
			win.openSettingsWindow();
		}
		
	}
	
	public class Quit implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
		
	}
	
	public class DebugCheckBoxListener implements ItemListener {

		public void itemStateChanged(ItemEvent e) {
			int change = e.getStateChange();
			if (change == ItemEvent.SELECTED) {
				
		    } else if (change == ItemEvent.DESELECTED) {
				
		    }
		}
	}

	public class ReturnButtonListener implements ActionListener {

		// Clic sur le bouton "Cancel" d'un des menus

		JDialog parentDialog;
		JPanel parentPanel;
		JFrame parentFrame;
		JPanel panel;

		public ReturnButtonListener(JDialog parent){
			parentDialog = parent;
		}

		public ReturnButtonListener(JPanel panel, JPanel parent){
			parentPanel = parent;
		}

		public ReturnButtonListener(JFrame parent){
			parentFrame = parent;
		}

		public void actionPerformed(ActionEvent e) {
			if(parentDialog != null){ parentDialog.dispose(); }
			if(parentPanel != null){ parentPanel.remove(panel); }
			if(parentFrame != null){ parentFrame.dispose(); }
		}

	}

}