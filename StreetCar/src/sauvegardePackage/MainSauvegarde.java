package sauvegardePackage;

import objectPackage.Plateau;
import joueurPackage.Joueur;
import mainPackage.Moteur;

public class MainSauvegarde {

	public static void main(String args[])
	{
		Moteur m = new Moteur(new Plateau());
		Sauvegarder s = new Sauvegarder(m);
		
		s.save(s);
	
	}
}
