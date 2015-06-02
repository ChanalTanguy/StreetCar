package windowPackage;

import graphique.Fenetre;

import java.awt.Dimension;

import javax.swing.JDialog;

import constantesPackages.Constantes;

public class WindowInterface extends JDialog {
	
	double width = Constantes.Resolution.width;
	double height = Constantes.Resolution.height;
	
	Fenetre mainGame;
	
	protected Dimension setNewDimension(double w, double h) {
		double newHeight = height/(1024.0/h);
		double newWidth = (newHeight*w)/h;
		return new Dimension((int)newWidth, (int)newHeight);
	}
	
	protected void setNewSize(JDialog win, double w, double h) {
		double newHeight = height/(1024.0/h);
		double newWidth = (newHeight*w)/h;
		win.setSize((int)newWidth, (int)newHeight);
	}
	
	protected void setMainGameWindow(Fenetre f){
		mainGame = f;
	}

}
