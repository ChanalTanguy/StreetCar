package iaPackage;

import java.util.Random;

import joueurPackage.Coup;
import joueurPackage.JoueurIA;
import joueurPackage.MainJoueur;
import mainPackage.Moteur;
import objectPackage.Plateau;
import objectPackage.tuilePackage.Tuile;

public class IAFacile implements InterfaceIA {

	Moteur moteur;
	JoueurIA joueur;
	
	public IAFacile(Moteur moteur, JoueurIA joueurIA) {
		this.moteur = moteur;
		joueur = joueurIA;
	}
	
	public void setJoueur(JoueurIA j) {
		joueur = j;
	}
	
	@Override
	public Coup getCoup() {
		if(moteur.getNbActions()<3)
			return Coup.newPioche();
		
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
			for (int i = 0; i < nbRot; i++){
				t.rotation();
			}
		} while (!p.coupValide(t, coup));
		
		for (int i = 0; i < nbRot; i++) {
			joueur.tournerTuileMain(coup.getTuile());
		}
		return coup;
		
	}

}
