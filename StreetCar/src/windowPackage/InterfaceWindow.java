package windowPackage;

import java.awt.Dimension;

import javax.swing.JDialog;

import constantesPackages.Constantes;

public class InterfaceWindow extends JDialog {
	
	double width = Constantes.Resolution.width;
	double height = Constantes.Resolution.height;
	
	protected Dimension setNewDimension(double w, double h) {
		double newHeight = height/(1024.0/h);
		double newWidth = (newHeight*w)/h;
		return new Dimension((int)newWidth, (int)newHeight);
	}

}
