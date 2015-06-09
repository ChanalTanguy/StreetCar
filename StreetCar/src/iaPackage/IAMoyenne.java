package iaPackage;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import joueurPackage.Coup;
import joueurPackage.Joueur;
import joueurPackage.JoueurIA;
import joueurPackage.MainJoueur;
import mainPackage.Moteur;
import objectPackage.Plateau;
import constantesPackages.Constantes;

@SuppressWarnings("unused")
public class IAMoyenne implements InterfaceIA {

	private Moteur moteur;
	private JoueurIA joueur;
	private Random r;
	
	public IAMoyenne(Moteur moteur, JoueurIA joueurIA) {
		this.moteur = moteur;
		joueur = joueurIA;
		r = new Random();
	}
	
	public void setJoueur(JoueurIA j) {
		joueur = j;
	}
	
	@Override
	public Coup getCoup() {
		if(moteur.getNbActions()<3)
			return Coup.newPioche();
		
		MainJoueur main = joueur.getMain();
		Plateau plateau = moteur.getPlateau();
		CoupEtRotation coupActuel;
		int coutActuel;
		ArrayList<CoupEtRotation> coupsRetenu = new ArrayList<CoupEtRotation>(); 
		int coutRetenu = Integer.MAX_VALUE;
		
		for (int x = 1; x < Constantes.Dimensions.dimensionPlateau-1; x++) { 	 	// Pour chaque...
			for (int y = 1; y < Constantes.Dimensions.dimensionPlateau-1; y++) { 	// position...
				for (int numTuile = 0; numTuile < 5; numTuile++) { 					// Pour chaque Tuile non null dans la main...
					if (main.getTuileAt(numTuile) != null){	
						for (int nbRotation = 0; nbRotation < 4; nbRotation++) { 	// Et pour chaque orientation...
							coupActuel = new CoupEtRotation(new Coup(Coup.placement, numTuile, new Point(x,y)), nbRotation);
							if (plateau.coupValide(main.getTuileAt(coupActuel.getCoup().getTuile()), coupActuel.getCoup())) {
								IADifficile.jouerCoupProvisoire(plateau, x, y, numTuile, main);
								
								coutActuel = evaluationPlateau(plateau);
								if (coutRetenu > coutActuel) {
									coupsRetenu.clear();
									coupsRetenu.add(coupActuel);
									coutRetenu = coutActuel;
								}
								else if (coutRetenu == coutActuel) {
									coupsRetenu.add(coupActuel);
								}
								
								IADifficile.jouerCoupProvisoire(plateau, x, y, numTuile, main);
							}
							joueur.tournerTuileMain(numTuile);
						}
					}
				}
			}
		}
		
		CoupEtRotation coupChoisi = coupsRetenu.get(r.nextInt(coupsRetenu.size()));
		
		for (int i = 0; i < coupChoisi.getNbRotation(); i++)
			joueur.tournerTuileMain(coupChoisi.getCoup().getTuile());
		
		return coupChoisi.getCoup();
		
	}
	
	public int evaluationPlateau(Plateau p) {
		long t = System.nanoTime()/1000;
		int c1 = IADifficile.coutChemin(joueur.getObjectifs().getLigne(),joueur.getObjectifs().getEscalesCibles(),p);
		int c2 = IADifficile.coutChemin(moteur.getTabPlayers()[(moteur.getcurrentPlayer()+1)%2].getObjectifs().getLigne(),moteur.getTabPlayers()[(moteur.getcurrentPlayer()+1)%2].getObjectifs().getEscalesCibles(),p);

		//System.out.println("Temps d'execution 2 recherche (µs) : "+(System.nanoTime()/1000-t));
		if (c1 == 0) {
			return Integer.MIN_VALUE/4;
		}
		return (4*c1)-(c2);
	}

}
