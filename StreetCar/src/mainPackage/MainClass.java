package mainPackage;

import javax.swing.SwingUtilities;

public class MainClass implements Runnable{

	public static void main(String[] args) {
		SwingUtilities.invokeLater( new MainClass() );
	}
	
	public void run(){
		
	}

}
