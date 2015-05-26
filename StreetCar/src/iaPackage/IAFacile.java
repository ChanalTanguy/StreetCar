package iaPackage;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import tuilePackage.Tuile;
import constantesPackages.Constantes;
import mainPackage.Moteur;
import joueurPackage.Coup;
import joueurPackage.MainJoueur;

public class IAFacile implements InterfaceIA {

	Moteur moteur;
	ArrayList<Coup> listeCoup;
	
	public IAFacile(Moteur moteur) {
		this.moteur = moteur;
		listeCoup = new ArrayList<Coup>();
	}
	
	@Override
	public Coup getCoup() {
		if (!listeCoup.isEmpty()) {
			return listeCoup.remove(0);
		}
		else {
			Coup coup;
			int nbRot;
			MainJoueur maMain = moteur.getTabPlayers()[moteur.getcurrentPlayer()].getMain().clone();
			Tuile t;
			
			Random r = new Random();
			
			do {
				coup = Coup.newPlacement(r.nextInt(5), r.nextInt(12)+1, r.nextInt(12)+1);
				nbRot = r.nextInt(3);
				t = maMain.getTuileAt(coup.getTuile()).clone();
				for (int i = 0; i < nbRot; i++)
					t.rotation(Constantes.Rotation.rotationDroite);
				
			} while (moteur.getPlateau().coupValide(t, coup));
			
			for (int i = 0; i < nbRot; i++) {
				listeCoup.add(Coup.newRotation(coup.getTuile()));
			}
			listeCoup.add(coup);
			return listeCoup.remove(0);
		}
	}

}
