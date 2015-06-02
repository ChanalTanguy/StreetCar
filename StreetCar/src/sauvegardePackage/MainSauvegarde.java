package sauvegardePackage;

import objectPackage.Plateau;
import joueurPackage.Joueur;
import mainPackage.Moteur;

public class MainSauvegarde {

	public static void main(String args[])
	{
		Moteur m = new Moteur(new Plateau());
		
		Sauvegarder s = new Sauvegarder(m, "test");
		
		Chargement c = new Chargement();
		c.charger(m, "test.txt");
		
		//Sauvegarder copyC = new Sauvegarder(m, "testCopy");
	}
}
