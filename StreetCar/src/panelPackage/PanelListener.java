package panelPackage;

import graphique.Fenetre;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import joueurPackage.Joueur;
import joueurPackage.JoueurHumain;
import joueurPackage.JoueurIA;
import mainPackage.Moteur;
import objectPackage.Plateau;
import windowPackage.ConfirmWindow;
import windowPackage.CreditsWindow;
import windowPackage.WindowInterface;
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

		ButtonGroup selectedJ1;
		ButtonGroup selectedJ2;
		JDialog parent;
		Fenetre mainGame;

		public StartNewGameButtonListener(ButtonGroup j1, ButtonGroup j2, JDialog parent, Fenetre main){
			this.selectedJ1 = j1; this.selectedJ2 = j2;
			this.parent = parent;
			this.mainGame = main;
		}

		public void actionPerformed(ActionEvent e) {
			parent.dispose();
			if(mainGame != null){mainGame.dispose();}
			Moteur m = new Moteur(new Plateau());
			Joueur joueur1 = createPlayer(selectedJ1, m, 1);
			Joueur joueur2 = createPlayer(selectedJ2, m, 1);
			m.setPlayers(joueur1, joueur2);
			Fenetre f = new Fenetre("Street Car");
			f.disposition_V2(m, f.getSize());
		}
		
		private String getSelectedButtonText(ButtonGroup group) {
			for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
				AbstractButton button = buttons.nextElement();
				if (button.isSelected()) {
					return button.getText();
				}
			}
			return null;
		}
		
		private Joueur createPlayer(ButtonGroup group, Moteur m, int terminus){
			String text = getSelectedButtonText(group);
			System.out.println(text);
			switch (text){
			case "Humain" : { return new JoueurHumain(m, terminus); } 
			case "Facile" : { return new JoueurIA(m, terminus); } 
			case "Moyen" : { return new JoueurIA(m, terminus); } 
			case "Difficile" : { return new JoueurIA(m, terminus); } 
			default : return null;
			}
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

	public class CreditsButtonListener implements ActionListener {

		// Ouvre la fenêtre de configuration d'une nouvelle partie

		JDialog parent;
		boolean openWindow;

		public CreditsButtonListener(JDialog parent, boolean b){
			this.parent = parent;
			this.openWindow = b;
		}

		public void actionPerformed(ActionEvent e) {
			parent.dispose();
			CreditsWindow win = new CreditsWindow();
			win.openWindow(openWindow);
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