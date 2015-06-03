package windowPackage;

import graphique.Fenetre;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;

import constantesPackages.Constantes;

public class WindowInterface extends JDialog {
	
	double width = Constantes.Resolution.width;
	double height = Constantes.Resolution.height;
	
	JFrame mainWindow;
	
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
	
	public void setMainWindow(JFrame f){
		this.mainWindow = f;
	}

}
