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
		
		// Ouvre la fenêtre de configuration d'une nouvelle partie

		JDialog parent;
		boolean openWindow;

		public ConfigureNewGameButtonListener(JDialog parent, boolean b){
			this.parent = parent;
			this.openWindow = b;
		}

		public void actionPerformed(ActionEvent e) {
			parent.dispose();
			NewGameWindow win = new NewGameWindow();
			win.openWindow(openWindow);
		}

	}

	public class StartNewGameButtonListener implements ActionListener {
		
		// Démarre une nouvelle partie

		JDialog parent;
		Fenetre mainGame;

		public StartNewGameButtonListener(JDialog parent, Fenetre main){
			this.parent = parent;
			this.mainGame = main;
		}

		public void actionPerformed(ActionEvent e) {
			parent.dispose();
			if(mainGame != null){mainGame.dispose();}
			Moteur m = new Moteur(new Plateau());
			Fenetre f = new Fenetre("Street Car");
			f.disposition_V2(m, f.getSize());
		}
	}

	public class QuitButtonListener implements ActionListener {
		
		// Ouvre la fenêtre demandant de confirmer la fermeture du jeu

		public void actionPerformed(ActionEvent e) {
			ConfirmWindow conf = new ConfirmWindow("Êtes-vous sûr de vouloir quitter ?", null, null);
			PanelListener listener = new PanelListener();
			conf.setListeners(listener.new Quit(), listener.new ReturnButtonListener(conf, null));

			conf.openConfirmWindow();
		}
	}

	public class SettingsButtonListener implements ActionListener {
		
		// Ouvre la fenêtre de configuration des options
		
		JDialog parent;
		boolean openWindow;
		
		public SettingsButtonListener(JDialog parent, boolean b){
			this.parent = parent;
			this.openWindow = b;
		}

		public void actionPerformed(ActionEvent e) {
			parent.dispose();
			SettingsWindow win = new SettingsWindow();
			win.openWindow(openWindow);
		}

	}

	public class Quit implements ActionListener {
		
		// Quitte le jeu

		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}

	}

	public class DebugCheckBoxListener implements ItemListener {
		
		// Agit selon si le mode debug est activé ou non

		public void itemStateChanged(ItemEvent e) {
			int change = e.getStateChange();
			if (change == ItemEvent.SELECTED) {

			} else if (change == ItemEvent.DESELECTED) {

			}
		}
	}

	public class ReturnButtonListener implements ActionListener {

		// Ferme la	fenêtre courante

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