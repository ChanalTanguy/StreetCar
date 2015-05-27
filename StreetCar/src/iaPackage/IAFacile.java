package iaPackage;

import java.util.ArrayList;
import java.util.Random;

import joueurPackage.Coup;
import joueurPackage.JoueurIA;
import joueurPackage.MainJoueur;
import mainPackage.Moteur;
import mainPackage.Plateau;
import tuilePackage.Tuile;
import constantesPackages.Constantes;

public class IAFacile implements InterfaceIA {

	Moteur moteur;
	ArrayList<Coup> listeCoup;
	JoueurIA joueur;
	
	public IAFacile(Moteur moteur, JoueurIA joueurIA) {
		this.moteur = moteur;
		listeCoup = new ArrayList<Coup>();
		joueur = joueurIA;
	}
	
	@Override
	public Coup getCoup() {
		if(moteur.getNbActions()<3)
			return Coup.newPioche();
		
		if (!listeCoup.isEmpty())
			return listeCoup.remove(0);
		
		Coup coup;
		int nbRot;
		MainJoueur maMain = joueur.getMain();
		Tuile t;
		Plateau p = moteur.getPlateau();
			
		Random r = new Random();
			
		do {
			do {
			coup = Coup.newPlacement(r.nextInt(5), r.nextInt(12)+1, r.nextInt(12)+1);
		    } while (maMain.getTuileAt(coup.getTuile()) == null);

			
			nbRot = r.nextInt(3);
			t = maMain.getTuileAt(coup.getTuile()).clone();
			for (int i = 0; i < nbRot; i++)
				t.rotation(Constantes.Rotation.rotationDroite);
			System.out.println(t+" : "+p.coupValide(t, coup)+" | "+maMain.getTuileAt(coup.getTuile())+" : "+p.coupValide(maMain.getTuileAt(coup.getTuile()), coup));
			
		} while (!p.coupValide(t, coup));
		
		for (int i = 0; i < nbRot; i++) {
			listeCoup.add(Coup.newRotation(coup.getTuile()));
		}
		listeCoup.add(coup);
		return listeCoup.remove(0);
		
	}

}
