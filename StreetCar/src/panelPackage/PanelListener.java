package panelPackage;

import graphique.Fenetre;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFrame;

import joueurPackage.Joueur;
import joueurPackage.JoueurHumain;
import joueurPackage.JoueurIA;
import mainPackage.Moteur;
import objectPackage.Plateau;
import sauvegardePackage.Chargement;
import sauvegardePackage.Sauvegarder;
import windowPackage.ConfirmWindow;
import windowPackage.CreditsWindow;
import windowPackage.LoadGameWindow;
import windowPackage.MenuWindow;
import windowPackage.NewGameWindow;
import windowPackage.SettingsWindow;

public class PanelListener {


	// Ouvre la fenêtre de configuration d'une nouvelle partie

	public class ConfigureNewGameButtonListener implements ActionListener {

		JDialog parent;
		boolean openWindow;
		JFrame mainWindow;

		public ConfigureNewGameButtonListener(JDialog parent, JFrame main, boolean b){
			this.parent = parent;
			this.openWindow = b;
			this.mainWindow = main;
		}

		public void actionPerformed(ActionEvent e) {
			parent.dispose();
			NewGameWindow win = new NewGameWindow();
			win.setMainWindow(mainWindow);
			win.openWindow(openWindow);
		}

	}


	// Démarre une nouvelle partie

	public class StartNewGameButtonListener implements ActionListener {

		ButtonGroup selectedJ1;
		ButtonGroup selectedJ2;
		JDialog parent;
		JFrame mainWindow;

		public StartNewGameButtonListener(ButtonGroup j1, ButtonGroup j2, JDialog parent, JFrame main){
			this.selectedJ1 = j1; this.selectedJ2 = j2;
			this.parent = parent;
			this.mainWindow = main;
		}

		public void actionPerformed(ActionEvent e) {
			Moteur m = new Moteur(new Plateau());
			Joueur joueur1 = createPlayer(selectedJ1, m, 1);
			Joueur joueur2 = createPlayer(selectedJ2, m, 1);
			m.setPlayers(joueur1, joueur2);
			parent.dispose();
			if(mainWindow != null){mainWindow.dispose();}
			if(mainWindow instanceof Fenetre){((Fenetre)mainWindow).moteurParent.stop();} //Modifier pour détruire l'instance ?
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
			switch (text){
			case "Humain" : { return new JoueurHumain(m, terminus); } 
			case "Facile" : { return new JoueurIA(m, terminus); } 
			case "Moyen" : { return new JoueurIA(m, terminus); } 
			case "Difficile" : { return new JoueurIA(m, terminus); } 
			default : return null;
			}
		}

	}


	// Ouvre la fenêtre demandant de confirmer la fermeture du jeu

	public class QuitButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			ConfirmWindow conf = new ConfirmWindow("Êtes-vous sûr de vouloir quitter ?", null, null);
			PanelListener listener = new PanelListener();
			conf.setListeners(listener.new Quit(), listener.new ReturnButtonListener(conf, null));

			conf.openConfirmWindow();
		}
	}


	// Ouvre le menu de chargement d'une partie

	public class LoadGameButtonListener implements ActionListener {

		JDialog parent;
		boolean openWindow;
		JFrame mainWindow;

		public LoadGameButtonListener(JDialog parent, JFrame main, boolean b){
			this.parent = parent;
			this.openWindow = b;
			this.mainWindow = main;
		}

		public void actionPerformed(ActionEvent e) {
			parent.dispose();
			LoadGameWindow win = new LoadGameWindow();
			win.setMainWindow(mainWindow);
			win.openWindow(openWindow);
		}

	}

	// Charge une partie

	public class LoadGameListener implements ActionListener {

		String saveName;
		JDialog parent;
		JFrame mainWindow;

		public LoadGameListener(String name, JDialog parent, JFrame main){
			this.saveName = name;
			this.parent = parent;
			this.mainWindow = main;
		}

		public void actionPerformed(ActionEvent e) {
			if(saveName != "Vide"){
				Chargement load = new Chargement();
				Moteur newEngine = new Moteur(new Plateau());
				load.charger(newEngine, saveName);

				Fenetre f = new Fenetre("Street Car");
				f.disposition_V2(newEngine, f.getSize());
				
				if(parent != null){parent.dispose();}
				if(mainWindow != null){mainWindow.dispose();}
				if(mainWindow instanceof Fenetre){((Fenetre)mainWindow).moteurParent.stop();} //Modifier pour détruire l'instance ?

			}
		}

	}
	
	
	// Propose la suavegarde d'une partie
	
	public class SaveGameButtonListener implements ActionListener {

		Moteur savedEngine;
		JDialog parent;
		JFrame mainWindow;

		public SaveGameButtonListener(Moteur engine, JDialog parent, JFrame main){
			this.savedEngine = engine;
			this.parent = parent;
			this.mainWindow = main;
		}

		public void actionPerformed(ActionEvent e) {
			ConfirmWindow conf = new ConfirmWindow("Sauvegarder la partie courante ?", null, null);
			PanelListener listener = new PanelListener();
			conf.setListeners(listener.new SaveGameListener(savedEngine, conf, null), listener.new ReturnButtonListener(conf, null));

			conf.openConfirmWindow();
		}

	}


	// Charge une partie

	public class SaveGameListener implements ActionListener {

		Moteur savedEngine;
		JDialog parent;
		JFrame mainWindow;

		public SaveGameListener(Moteur engine, JDialog parent, JFrame main){
			this.savedEngine = engine;
			this.parent = parent;
			this.mainWindow = main;
		}

		public void actionPerformed(ActionEvent e) {
			if(parent != null){parent.dispose();}
			//if(mainWindow != null){mainWindow.dispose();}
			//if(mainWindow instanceof Fenetre){((Fenetre)mainWindow).moteurParent.stop();} //Modifier pour détruire l'instance ?

			Sauvegarder save = new Sauvegarder(savedEngine);
		}

	}


	// Ouvre la fenêtre de configuration d'une nouvelle partie

	public class CreditsButtonListener implements ActionListener {

		JDialog parent;
		boolean openWindow;
		JFrame mainWindow;

		public CreditsButtonListener(JDialog parent, boolean b, JFrame main){
			this.parent = parent;
			this.openWindow = b;
			this.mainWindow = main;
		}

		public void actionPerformed(ActionEvent e) {
			parent.dispose();
			CreditsWindow win = new CreditsWindow();
			win.setMainWindow(mainWindow);
			win.openWindow(openWindow);
		}

	}


	// Ouvre la fenêtre de configuration des options

	public class SettingsButtonListener implements ActionListener {

		JDialog parent;
		boolean openWindow;
		JFrame mainWindow;

		public SettingsButtonListener(JDialog parent, boolean b, JFrame main){
			this.parent = parent;
			this.openWindow = b;
			this.mainWindow = main;
		}

		public void actionPerformed(ActionEvent e) {
			parent.dispose();
			SettingsWindow win = new SettingsWindow();
			win.setMainWindow(mainWindow);
			win.openWindow(openWindow);
		}

	}


	// Quitte le jeu

	public class Quit implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}

	}


	// Agit selon si le mode debug est activé ou non

	public class DebugCheckBoxListener implements ItemListener {

		public void itemStateChanged(ItemEvent e) {
			int change = e.getStateChange();
			if (change == ItemEvent.SELECTED) {

			} else if (change == ItemEvent.DESELECTED) {

			}
		}
	}


	// Ferme la	fenêtre courante

	public class ReturnButtonListener implements ActionListener {

		JDialog parentDialog;
		MenuWindow window;
		JFrame mainWindow;

		public ReturnButtonListener(JDialog parent, MenuWindow window){
			parentDialog = parent;
			this.window = window;
		}

		public ReturnButtonListener(JDialog parent, MenuWindow window, JFrame mainWindow){
			parentDialog = parent;
			this.window = window;
			this.mainWindow = mainWindow;
		}

		public void actionPerformed(ActionEvent e) {
			parentDialog.dispose();
			if (window != null) { window.setMainWindow(mainWindow); window.openWindow(); }
			if(mainWindow != null && mainWindow instanceof Fenetre){((Fenetre)mainWindow).moteurParent.start();}
		}

	}

}