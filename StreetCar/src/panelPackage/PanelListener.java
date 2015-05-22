package panelPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PanelListener {
	
	public class ConfigureNewGameButtonListener implements ActionListener {

		// Clic sur le bouton "Nouvelle partie" du menu principal
		
		public void actionPerformed(ActionEvent e) {}
		
	}
	
	public class QuitButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
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