package mainPackage;

import graphique.Fenetre;

import javax.swing.SwingUtilities;

public class MainClass implements Runnable{

	public static void main(String[] args) {
		SwingUtilities.invokeLater( new MainClass() );
	}
	
	//Pour passer par l'écran titre, faire FenetreTitre f = new FenetreTitre("Street Car"); 
	public void run(){
		Moteur m = new Moteur(new Plateau());
		Fenetre f = new Fenetre("Street Car"); 
		f.disposition(m, f.getSize());
	}
}
