package voyagePackage;

import objectPackage.Plateau;
import sauvegardePackage.Chargement;
import mainPackage.Moteur;

public class MainVoyage {
	
	static void main(String[] args)
	{
		int joueur = 0;
		int depart = 1;
		int ligne = 4;
		
		Moteur m = new Moteur(new Plateau());
		Chargement c = new Chargement();
		c.charger(m, "save/testVoyage.txt");
		voyager(m, joueur, ligne, depart);
	}

	private static void voyager(Moteur m, int joueur, int ligne, int depart) {
		// TODO Auto-generated method stub
		
	}
}
