package sauvegardePackage;

import objectPackage.Plateau;
import mainPackage.Moteur;

public class MainSauvegarde {

	public static void main(String args[])
	{
		Moteur m = new Moteur(new Plateau());
		
		//new Sauvegarder(m, "save/test");  
		
		Chargement c = new Chargement();
		c.charger(m, "save/testCopy.txt");
		
		new Sauvegarder(m, "save/test");
	}
}
