package iaPackage;

import java.awt.Point;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

import joueurPackage.Coup;
import joueurPackage.JoueurIA;
import joueurPackage.MainJoueur;
import mainPackage.Moteur;
import objectPackage.Plateau;
import objectPackage.tuilePackage.Tuile;
import constantesPackages.Constantes;

public class IADifficile implements InterfaceIA {

	Moteur moteur;
	JoueurIA joueur;
	Random r;
	CoupEtRotation coupEnAttente;

	public IADifficile(Moteur moteur, JoueurIA joueurIA) {
		this.moteur = moteur;
		joueur = joueurIA;
		r = new Random();
		coupEnAttente = null;
	}

	public Coup getCoup() {

		if(moteur.getNbActions()<3)
			return Coup.newPioche();

		if (coupEnAttente != null) {
			for (int i = 0; i < coupEnAttente.getNbRotation(); i++) {
				joueur.tournerTuileMain(coupEnAttente.getCoup().getTuile());
			}
			Coup c = coupEnAttente.getCoup();
			coupEnAttente = null;
			return c;
		}

		long t = System.currentTimeMillis();

		Plateau plateau = (Plateau) moteur.getPlateau().clone();

		CoupEtRotation coupCourant;
		CoupEtRotation[] coupsActuels;
		ArrayList<CoupEtRotation[]> coupsAJouer = new ArrayList<CoupEtRotation[]>(); 
		ArrayList<CoupEtRotation> toutLesCoupUnique = new ArrayList<CoupEtRotation>();
		MainJoueur main = joueur.getMain();
		int evaluationInitiale = evaluationPlateau(plateau);
		int coutActuel = Integer.MAX_VALUE;

		// * Tenter un coup
		// ** Evaluer le plateau
		// *** Faire ça avec tout les coups
		// **** Prendre le coup le mieux évaluer.

		// EVALUATION DE TOUT LES COUPS POSSIBLES
		for (int x = 1; x < Constantes.Dimensions.dimensionPlateau-1; x++) { 	 	// Pour chaque...
			for (int y = 1; y < Constantes.Dimensions.dimensionPlateau-1; y++) { 	// position...
				for (int numTuile = 0; numTuile < 5; numTuile++) { 					// Pour chaque Tuile non null dans la main...
					if (main.getTuileAt(numTuile) != null){	
						for (int nbRotation = 0; nbRotation < 4; nbRotation++) { 	// Et pour chaque orientation...
							coupCourant = new CoupEtRotation(Coup.newPlacement(numTuile, x, y),nbRotation);
							if (plateau.coupValide(main.getTuileAt(numTuile), coupCourant.getCoup())) {
								jouerCoupProvisoire(plateau, x, y, numTuile, main);
								coupCourant.setCout(evaluationPlateau(plateau)-evaluationInitiale);
								CoupEtRotation coupSecond = secondCoupAdjacent(plateau, coupCourant, evaluationInitiale);
								if (coupSecond.getCout() <= coutActuel) {

									if (coupSecond.getCout()+coupCourant.getCout() < coutActuel)
										coupsAJouer.clear();

									coupsActuels = new CoupEtRotation[2];
									coupsActuels[0] = coupCourant;
									coupsActuels[1] = coupSecond;
									coupsAJouer.add(coupsActuels);

									coutActuel = coupCourant.getCout()+coupSecond.getCout();
								}
								
								jouerCoupProvisoire(plateau, x, y, numTuile, main);
								toutLesCoupUnique.add(coupCourant);
							} 
							joueur.tournerTuileMain(numTuile);
						}
					}
				}
			}
		}
		System.out.println("Temps d'execution prémière partie (ms) : "+(System.currentTimeMillis()-t));

		for (int i = 0; i < toutLesCoupUnique.size(); i++) {
			for (int j = i+1; j < toutLesCoupUnique.size(); j++) {
				if (duoValideEtMeilleur(toutLesCoupUnique.get(i),toutLesCoupUnique.get(j),coutActuel)) {

					if (toutLesCoupUnique.get(i).getCout()+toutLesCoupUnique.get(j).getCout() < coutActuel) {
						coupsAJouer.clear();
					}
					coupsActuels = new CoupEtRotation[2];
					coupsActuels[0] = toutLesCoupUnique.get(i);
					coupsActuels[1] = toutLesCoupUnique.get(j);
					coupsAJouer.add(coupsActuels);

					coutActuel = toutLesCoupUnique.get(i).getCout()+toutLesCoupUnique.get(j).getCout();
					
				}
			}
		}

		CoupEtRotation[] coupChoisi = coupsAJouer.get(r.nextInt(coupsAJouer.size()));

		coupEnAttente = coupChoisi[1];

		for (int i = 0; i < coupChoisi[0].getNbRotation(); i++) {
			joueur.tournerTuileMain(coupChoisi[0].getCoup().getTuile());
		}

		System.out.println("Temps d'execution total (ms) : "+(System.currentTimeMillis()-t));
		System.out.println("Résultat : ");
		System.out.println("Coup 1 : "+coupChoisi[0].getCoup().getTuile()+" "+coupChoisi[0].getCoup().getCoordonnee()+"; nbRot : "+coupChoisi[0].getNbRotation()+"; cout : "+coupChoisi[0].getCout());
		System.out.println("Coup 2 : "+coupChoisi[1].getCoup().getTuile()+" "+coupChoisi[1].getCoup().getCoordonnee()+"; nbRot : "+coupChoisi[1].getNbRotation()+"; cout : "+coupChoisi[1].getCout());
		System.out.println();
		

		return coupChoisi[0].getCoup();

	}
	private boolean duoValideEtMeilleur(CoupEtRotation cr1, CoupEtRotation cr2, int coutActuel) {
		return (cr1.getCoup().getTuile() != cr2.getCoup().getTuile() 
			&& cr1.getCout()+cr2.getCout() <= coutActuel
			&& !(cr1.getCoup().getCoordonnee().x <= cr2.getCoup().getCoordonnee().x+1 && cr1.getCoup().getCoordonnee().x >= cr2.getCoup().getCoordonnee().x-1 && cr1.getCoup().getCoordonnee().y == cr2.getCoup().getCoordonnee().y)
			&& !(cr1.getCoup().getCoordonnee().y <= cr2.getCoup().getCoordonnee().y+1 && cr1.getCoup().getCoordonnee().y >= cr2.getCoup().getCoordonnee().y-1 && cr1.getCoup().getCoordonnee().x == cr2.getCoup().getCoordonnee().x));
	}
	
	private CoupEtRotation secondCoupAdjacent(Plateau plateau, CoupEtRotation c, int evaluationInitiale) {
		int coutActuel = evaluationInitiale;
		CoupEtRotation coupGarde = new CoupEtRotation(null,0); coupGarde.setCout(Integer.MAX_VALUE/2);
		CoupEtRotation coupCourant;
		MainJoueur main = joueur.getMain();
		int x = c.getCoup().getCoordonnee().x, y = c.getCoup().getCoordonnee().y;
		for (int i = 0; i < 4; i++) { // Position autour de la tuile.
			for (int numTuile = 0; numTuile < 5; numTuile++) {
				if (main.getTuileAt(numTuile) != null) {
					for (int nbRotation = 0; nbRotation < 4; nbRotation++) { 	
						switch (i) {
						case 0 : 
							coupCourant = new CoupEtRotation(Coup.newPlacement(numTuile, x+1, y),nbRotation); break;
						case 1 :
							coupCourant = new CoupEtRotation(Coup.newPlacement(numTuile, x-1, y),nbRotation); break;
						case 2 :
							coupCourant = new CoupEtRotation(Coup.newPlacement(numTuile, x, y+1),nbRotation); break;
						default :
							coupCourant = new CoupEtRotation(Coup.newPlacement(numTuile, x, y-1),nbRotation); break;		
						}
						if (plateau.coupValide(main.getTuileAt(numTuile), coupCourant.getCoup())) {
							jouerCoupProvisoire(plateau, x, y, numTuile, main);
							coupCourant.setCout(evaluationPlateau(plateau)-evaluationInitiale);
							if (coupCourant.getCout() < coutActuel) {
								coupGarde = coupCourant;
								coutActuel = coupCourant.getCout();
							}
							jouerCoupProvisoire(plateau, x, y, numTuile, main);
						}
						joueur.tournerTuileMain(numTuile);
					}
				}
			}
		}
		return coupGarde;
	}

	private void jouerCoupProvisoire(Plateau p, int x, int y, int tuileDansMain, MainJoueur main) {
		Tuile t = p.getTuileAt(x, y);
		p.setTuileAt(x, y, main.getTuileAt(tuileDansMain));
		main.setTuileAt(tuileDansMain, t);
	}

	private int evaluationPlateau(Plateau p) {
		int c1 = coutChemin(joueur.getObjectifs().getLigne(),null,p);
		int c2 = coutChemin(moteur.getTabPlayers()[(moteur.getcurrentPlayer()+1)%2].getObjectifs().getLigne(),null,p);
		if (c1 == 0) {
			return Integer.MIN_VALUE/4;
		}
		return (3*c1)-(c2);
	}

	private final static int coutTuileFixe = 0;
	private final static int coutTuileNull = 1;
	private final static int coutTuilePossible = 5;
	private final static int coutHeuristique = 1;


	private int coutChemin(int ligne, int[] escales, Plateau p) {

		//ListeTuilePossible file = new ListeTuilePossible();
		PriorityQueue<TuileChemin> file = new PriorityQueue<TuileChemin>
		(Constantes.Dimensions.dimensionPlateau*Constantes.Dimensions.dimensionPlateau, new ComparateurChemin());

		Point po = p.getTerminalPosition(ligne, 1);
		Point depart = po;
		Point objectif = p.getTerminalPosition(ligne, 2);
		TuileChemin tuileCheminCourant = new TuileChemin(po,Constantes.Orientation.ouest,0,magellanDistance(po,objectif), null);
		RejectList seens = new RejectList();
		po = new Point();

		// TODO : Prendre en compte les escales

		do {
			Point pCourant = tuileCheminCourant.getPosition();
			Tuile t = p.getTuileAt(pCourant.x, pCourant.y);

			if (!seens.contain(tuileCheminCourant)) {


				seens.add(tuileCheminCourant);

				// Cas null : Pas de tuile présente, on cherche à voir dans toutes les autres directions
				if (t == null) {
					if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.est)) {
						po.x = pCourant.x+1; po.y = pCourant.y;
						file.add(new TuileChemin(po,Constantes.Orientation.ouest,tuileCheminCourant.getPriority()+coutTuileNull,magellanDistance(po,objectif), tuileCheminCourant));
					}
					if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.ouest)){
						po.x = pCourant.x-1; po.y = pCourant.y;
						file.add(new TuileChemin(po,Constantes.Orientation.est,tuileCheminCourant.getPriority()+coutTuileNull,magellanDistance(po,objectif), tuileCheminCourant));
					}
					if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.sud)) {
						po.x = pCourant.x; po.y = pCourant.y+1;
						file.add(new TuileChemin(po,Constantes.Orientation.nord,tuileCheminCourant.getPriority()+coutTuileNull,magellanDistance(po,objectif), tuileCheminCourant));
					}
					if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.nord)) {
						po.x = pCourant.x; po.y = pCourant.y-1;
						file.add(new TuileChemin(po,Constantes.Orientation.sud,tuileCheminCourant.getPriority()+coutTuileNull,magellanDistance(po,objectif), tuileCheminCourant));
					}
				}

				// Cas du Terminus (Gérée de cette façon, car on commence le chemin sur une escale...
				// Sur une direction ou elle n'est pas connectée.) Le cout d'une escale est nul.
				else if (t.getTypeTuile() == 1 && pCourant.equals(depart)) {
					if (t.connectionsExistantes(Constantes.Orientation.est)) {
						po.x = pCourant.x+1; po.y = pCourant.y;
						file.add(new TuileChemin(po,Constantes.Orientation.ouest,tuileCheminCourant.getPriority(),magellanDistance(po,objectif), tuileCheminCourant));
					}
					if (t.connectionsExistantes(Constantes.Orientation.ouest)) {
						po.x = pCourant.x-1; po.y = pCourant.y;
						file.add(new TuileChemin(po,Constantes.Orientation.est,tuileCheminCourant.getPriority(),magellanDistance(po,objectif), tuileCheminCourant));
					}
					if (t.connectionsExistantes(Constantes.Orientation.sud)) {
						po.x = pCourant.x; po.y = pCourant.y+1;
						file.add(new TuileChemin(po,Constantes.Orientation.nord,tuileCheminCourant.getPriority(),magellanDistance(po,objectif), tuileCheminCourant));
					}
					if (t.connectionsExistantes(Constantes.Orientation.nord)) {
						po.x = pCourant.x; po.y = pCourant.y-1;
						file.add(new TuileChemin(po,Constantes.Orientation.sud,tuileCheminCourant.getPriority(),magellanDistance(po,objectif), tuileCheminCourant));
					}
				}

				// Cas des tuiles avec présence d'arbre (fixe) : On cherche à voir
				// Dans quel direction celle ou on est actuellement est connectée
				else if (t.getPresenceArbres()) {
					for (String s : t.getDirectionConnectedTo(tuileCheminCourant.getDirection())) {
						switch (s) {
						case Constantes.Orientation.est : 
							po.x = pCourant.x+1; po.y = pCourant.y;
							file.add(new TuileChemin(po,Constantes.Orientation.ouest,tuileCheminCourant.getPriority()+coutTuileFixe,magellanDistance(po,objectif), tuileCheminCourant));
							break;
						case Constantes.Orientation.ouest : 
							po.x = pCourant.x-1; po.y = pCourant.y;
							file.add(new TuileChemin(po,Constantes.Orientation.est,tuileCheminCourant.getPriority()+coutTuileFixe,magellanDistance(po,objectif), tuileCheminCourant));
							break;
						case Constantes.Orientation.sud : 
							po.x = pCourant.x; po.y = pCourant.y+1;
							file.add(new TuileChemin(po,Constantes.Orientation.nord,tuileCheminCourant.getPriority()+coutTuileFixe,magellanDistance(po,objectif), tuileCheminCourant));
							break;
						case Constantes.Orientation.nord: 
							po.x = pCourant.x; po.y = pCourant.y-1;
							file.add(new TuileChemin(po,Constantes.Orientation.sud,tuileCheminCourant.getPriority()+coutTuileFixe,magellanDistance(po,objectif), tuileCheminCourant));
							break;
						}
					}
				}

				// Cas d'une tuile qu'on peut remplacer : On met une priorité sur les connexion existantes
				// Et on met un cout plus important pour celle qui peuvent exister.
				else {
					ArrayList<String> listeDirectionPossible = t.getDirectionConnectedTo(tuileCheminCourant.getDirection());
					if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.est)) {
						po.x = pCourant.x+1; po.y = pCourant.y;
						if (listeDirectionPossible.contains(Constantes.Orientation.est))
							file.add(new TuileChemin(po,Constantes.Orientation.ouest,tuileCheminCourant.getPriority()+coutTuileFixe,magellanDistance(po,objectif), tuileCheminCourant));
						else 
							file.add(new TuileChemin(po,Constantes.Orientation.ouest,tuileCheminCourant.getPriority()+coutTuilePossible,magellanDistance(po,objectif), tuileCheminCourant));
					}
					if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.ouest)) {
						po.x = pCourant.x-1; po.y = pCourant.y;
						if (listeDirectionPossible.contains(Constantes.Orientation.ouest))
							file.add(new TuileChemin(po,Constantes.Orientation.est,tuileCheminCourant.getPriority()+coutTuileFixe,magellanDistance(po,objectif), tuileCheminCourant));
						else
							file.add(new TuileChemin(po,Constantes.Orientation.est,tuileCheminCourant.getPriority()+coutTuilePossible,magellanDistance(po,objectif), tuileCheminCourant));
					}
					if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.sud)) {
						po.x = pCourant.x; po.y = pCourant.y+1;
						if (listeDirectionPossible.contains(Constantes.Orientation.sud))
							file.add(new TuileChemin(po,Constantes.Orientation.nord,tuileCheminCourant.getPriority()+coutTuileFixe,magellanDistance(po,objectif), tuileCheminCourant));
						else
							file.add(new TuileChemin(po,Constantes.Orientation.nord,tuileCheminCourant.getPriority()+coutTuilePossible,magellanDistance(po,objectif), tuileCheminCourant));
					}
					if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.nord)) {
						po.x = pCourant.x; po.y = pCourant.y-1;
						if (listeDirectionPossible.contains(Constantes.Orientation.nord))
							file.add(new TuileChemin(po,Constantes.Orientation.sud,tuileCheminCourant.getPriority()+coutTuileFixe,magellanDistance(po,objectif), tuileCheminCourant));
						else
							file.add(new TuileChemin(po,Constantes.Orientation.sud,tuileCheminCourant.getPriority()+coutTuilePossible,magellanDistance(po,objectif), tuileCheminCourant));
					}
				}
			}
			tuileCheminCourant = file.remove();

		} while (!tuileCheminCourant.getPosition().equals(objectif));

		TuileChemin lastOne = tuileCheminCourant; 

		return tuileCheminCourant.getPriority();
	}


	private int magellanDistance(Point p1, Point p2) {
		return coutHeuristique*(absolu(p1.x-p2.x)+absolu(p1.y-p2.y));
	}

	private int absolu(int i) {
		if (i < 0)
			return -i;
		return i;
	}

}
