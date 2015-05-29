package panelPackage;

import graphique.Fenetre;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import mainPackage.Moteur;
import objectPackage.Plateau;
import windowPackage.ConfirmWindow;
import windowPackage.InterfaceWindow;
import windowPackage.MenuWindow;
import windowPackage.NewGameWindow;
import windowPackage.SettingsWindow;

public class PanelListener {

	public class ConfigureNewGameButtonListener implements ActionListener {

		JDialog parent;
		
		public ConfigureNewGameButtonListener(JDialog parent){
			this.parent = parent;
		}

		public void actionPerformed(ActionEvent e) {
			parent.dispose();
			NewGameWindow win = new NewGameWindow();
			win.openWindow();
		}

	}
	
	public class StartNewGameButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Moteur m = new Moteur(new Plateau());
			Fenetre f = new Fenetre("Street Car");
			f.disposition_V2(m, f.getSize());
		}
	}

	public class QuitButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			ConfirmWindow conf = new ConfirmWindow("Êtes-vous sûr de vouloir quitter ?", null, null);
			PanelListener listener = new PanelListener();
			conf.setListeners(listener.new Quit(), listener.new ReturnButtonListener(conf, null));
			
			conf.openConfirmWindow();
		}
	}
	
	public class SettingsButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			SettingsWindow win = new SettingsWindow();
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
		MenuWindow window;

		public ReturnButtonListener(JDialog parent, MenuWindow window){
			parentDialog = parent;
			this.window = window;
		}

		public void actionPerformed(ActionEvent e) {
			parentDialog.dispose();
			if (window != null) { window.openWindow(); }
		}

	}

}