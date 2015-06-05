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


@SuppressWarnings("unused")
public class IADifficile implements InterfaceIA {

	private Moteur moteur;
	private JoueurIA joueur;
	private Random r;
	private CoupEtRotation coupEnAttente;

	private final static boolean trace = true;
	private final static int nbTourAvantIAPlusDevellope = 100;

	public IADifficile(Moteur moteur, JoueurIA joueurIA) {
		this.moteur = moteur;
		joueur = joueurIA;
		r = new Random();
		coupEnAttente = null;
	}

	public void setCoupEnAttente(Coup c) {
		if (coupEnAttente != null)
			coupEnAttente = new CoupEtRotation(c, 0);
	}

	public Coup getCoup() {

		if(moteur.getNbActions()<3) {
			coupEnAttente = null;
			return Coup.newPioche();
		}

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

		MainJoueur main = joueur.getMain();
		int evaluationInitiale = evaluationPlateau(plateau);

		CoupEtRotation coupSoloActuel;
		CoupEtRotation coupDuoActuel;

		ArrayList<CoupEtRotation> coupsSoloRetenu = new ArrayList<CoupEtRotation>(); 
		ArrayList<CoupEtRotation[]> coupsDuoRetenu = new ArrayList<CoupEtRotation[]>();

		int coutActuel = 0;
		int coutSolo = Integer.MAX_VALUE;
		int coutDuo  = Integer.MAX_VALUE;

		// * Tenter un coup
		// ** Evaluer le plateau
		// *** Faire ça avec tout les coups
		// **** Prendre le coup le mieux évaluer.

		// PARTIE 1 : SIMULER TOUT LES COUP ET GARDER LE MEILLEUR DE TOUS
		// EN PARALLELE, SIMULER TOUT LES DUO DE COUP ADJACENT ET ENREGISTRER LE MEILLEUR DE TOUS
		for (int x = 1; x < Constantes.Dimensions.dimensionPlateau-1; x++) { 	 	// Pour chaque...
			for (int y = 1; y < Constantes.Dimensions.dimensionPlateau-1; y++) { 	// position...
				for (int numTuile = 0; numTuile < 5; numTuile++) { 					// Pour chaque Tuile non null dans la main...
					if (main.getTuileAt(numTuile) != null){	
						for (int nbRotation = 0; nbRotation < 4; nbRotation++) { 	// Et pour chaque orientation...
							coupSoloActuel = new CoupEtRotation(new Coup(Constantes.Coup.placement, numTuile, new Point(x,y)), nbRotation);
							if (plateau.coupValide(main.getTuileAt(coupSoloActuel.getCoup().getTuile()), coupSoloActuel.getCoup())) {
								jouerCoupProvisoire(plateau, x, y, numTuile, main);

								coutActuel = evaluationPlateau(plateau);
								if (coutSolo > coutActuel) {
									coupsSoloRetenu.clear();
									coupsSoloRetenu.add(coupSoloActuel);
									coutSolo = coutActuel;
								}
								else if (coutSolo == coutActuel) {
									coupsSoloRetenu.add(coupSoloActuel);
								}

								if (moteur.getNumTour() > nbTourAvantIAPlusDevellope) {
									coupDuoActuel = secondCoupAdjacent(plateau, coupSoloActuel, nbRotation);
									if (coupDuoActuel.getCoup() != null) {

										if (coutDuo >= coupDuoActuel.getCout()) {
											if (coutDuo > coupDuoActuel.getCout()) {
												coupsDuoRetenu.clear();
												coutDuo = coupDuoActuel.getCout();
											}
											CoupEtRotation[] crs = new CoupEtRotation[2];
											crs[0] = coupSoloActuel;
											crs[1] = coupDuoActuel;
											coupsDuoRetenu.add(crs);
										}
									}
								}
								jouerCoupProvisoire(plateau, x, y, numTuile, main);
							}
							joueur.tournerTuileMain(numTuile);
						}
					}
				}
			}
		}

		CoupEtRotation coupSoloChoisi = coupsSoloRetenu.get(r.nextInt(coupsSoloRetenu.size()));

		if (trace == true)
			System.out.println("Temps d'execution première partie (ms) : "+(System.currentTimeMillis()-t));

		// PARTIE 2 : SIMULER LE PREMIER COUP, ET TROUVER LE MEILLEUR DEUXIEME COUP ASSOCIE AU PREMIER.
		// PUIS COMPARER AVEC LE MEILLEUR DUO DE COUP ADJACENT DE LA PREMIERE PHASE

		jouerCoupProvisoire(plateau, coupSoloChoisi.getCoup().getCoordonnee().x, coupSoloChoisi.getCoup().getCoordonnee().y, coupSoloChoisi.getCoup().getTuile(), main);

		for (int x = 1; x < Constantes.Dimensions.dimensionPlateau-1; x++) { 	 	// Pour chaque...
			for (int y = 1; y < Constantes.Dimensions.dimensionPlateau-1; y++) { 	// position...
				for (int numTuile = 0; numTuile < 5; numTuile++) { 					// Pour chaque Tuile non null dans la main...
					if (main.getTuileAt(numTuile) != null){
						for (int nbRotation = 0; nbRotation < 4; nbRotation++) { 	// Et pour chaque orientation...

							coupDuoActuel = new CoupEtRotation(new Coup(Constantes.Coup.placement, numTuile, new Point(x,y)), nbRotation);

							if (plateau.coupValide(main.getTuileAt(coupDuoActuel.getCoup().getTuile()), coupDuoActuel.getCoup())) {

								jouerCoupProvisoire(plateau, x, y, numTuile, main);
								coupDuoActuel.setCout(evaluationPlateau(plateau));

								if (duoValideEtMeilleur(coupDuoActuel, coupSoloChoisi, coutDuo)) {
									if (coutDuo > coupDuoActuel.getCout()) {
										coupsDuoRetenu.clear();
										coutDuo = coupDuoActuel.getCout();
									}
									CoupEtRotation[] crs = new CoupEtRotation[2];
									crs[0] = coupSoloChoisi;
									crs[1] = coupDuoActuel;
									coupsDuoRetenu.add(crs);
								}
								jouerCoupProvisoire(plateau, x, y, numTuile, main);
							}
							joueur.tournerTuileMain(numTuile);
						}
					}
				}
			}
		}

		jouerCoupProvisoire(plateau, coupSoloChoisi.getCoup().getCoordonnee().x, coupSoloChoisi.getCoup().getCoordonnee().y, coupSoloChoisi.getCoup().getTuile(), main);

		CoupEtRotation[] coupChoisi = coupsDuoRetenu.get(r.nextInt(coupsDuoRetenu.size()));

		coupEnAttente = coupChoisi[1];

		if (trace == true) {
			System.out.println("Temps d'execution total (ms) : "+(System.currentTimeMillis()-t));
			System.out.println("Résultat : ");
			System.out.println("Coup 1 : "+coupChoisi[0].getCoup().getTuile()+" "+coupChoisi[0].getCoup().getCoordonnee()+"; nbRot : "+coupChoisi[0].getNbRotation()+"; cout : "+coupChoisi[0].getCout());
			System.out.println("Coup 2 : "+coupChoisi[1].getCoup().getTuile()+" "+coupChoisi[1].getCoup().getCoordonnee()+"; nbRot : "+coupChoisi[1].getNbRotation()+"; cout : "+coupChoisi[1].getCout());
			System.out.println();
		}

		for (int i = 0; i < coupChoisi[0].getNbRotation(); i++)
			joueur.tournerTuileMain(coupChoisi[0].getCoup().getTuile());


		return coupChoisi[0].getCoup();

	}
	private boolean duoValideEtMeilleur(CoupEtRotation cr1, CoupEtRotation cr2, int coutDuo) {
		return (cr1.getCoup().getTuile() != cr2.getCoup().getTuile() 
				&& cr1.getCout() <= coutDuo
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
		//long t = System.nanoTime()/1000;
		int c1 = coutCheminBis(joueur.getObjectifs().getLigne(),joueur.getObjectifs().getEscalesCibles(),p);
		int c2 = coutCheminBis(moteur.getTabPlayers()[(moteur.getcurrentPlayer()+1)%2].getObjectifs().getLigne(),moteur.getTabPlayers()[(moteur.getcurrentPlayer()+1)%2].getObjectifs().getEscalesCibles(),p);


		//if (trace == true) 
		//	System.out.println("Temps d'execution 2 recherche (µs) : "+(System.nanoTime()/1000-t));
		if (c1 == 0) {
			return Integer.MIN_VALUE/4;
		}
		return (3*c1)-(c2);
	}

	private final static int coutTuileFixe = 0;
	private final static int coutTuileNull = 1;
	private final static int coutTuilePossible = 5;
	private final static int coutHeuristique = 1;

	private int coutCheminBis(int ligne, int[] escales, Plateau p) {
		int[] escalesBis = new int[3];
		for (int i = 0; i< 3; i++)
			escalesBis[i] = escales[i];

		int x = rechercheChemin(p.getTerminalPosition(ligne,1),Constantes.Orientation.ouest,p.getTerminalPosition(ligne,2), escales, p, 0);
		return x;
	}

	private int rechercheChemin(Point depart, String directionDepart, Point arrivee, int[] escales, Plateau plateau, int coutInitial) {

		PriorityQueue<TuileChemin> file = new PriorityQueue<TuileChemin>
		(Constantes.Dimensions.dimensionPlateau*Constantes.Dimensions.dimensionPlateau, new ComparateurChemin());
		Point po = depart;
		TuileChemin tuileCheminCourant = new TuileChemin(po,directionDepart,coutInitial,0, null);
		file.add(tuileCheminCourant);
		po = new Point();
		RejectList seens = new RejectList();
		boolean found = false;
		int coutFinal = Integer.MAX_VALUE;
		int escaleAdjacente;

		boolean plusDEscale;
		plusDEscale = (escales[0] == -1 && escales[1] == -1 && escales[2] == -1);

		do {
			tuileCheminCourant = file.remove();
			Point pCourant = tuileCheminCourant.getPosition();
			Tuile t = plateau.getPlateau()[pCourant.x][pCourant.y];

			if (!seens.contain(tuileCheminCourant)) {
				seens.add(tuileCheminCourant);

				escaleAdjacente = plateau.aCoterDUneEscaleNonAssignee(pCourant);

				// Cas de la tuile null
				if (t == null) {
					if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.est)) {
						po.x = pCourant.x+1; po.y = pCourant.y;
						file.add(new TuileChemin(po,Constantes.Orientation.ouest,tuileCheminCourant.getPriority()+coutTuileNull,calculHeuristique(po, arrivee, !plusDEscale), tuileCheminCourant));
					}
					if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.ouest)){
						po.x = pCourant.x-1; po.y = pCourant.y;
						file.add(new TuileChemin(po,Constantes.Orientation.est,tuileCheminCourant.getPriority()+coutTuileNull,calculHeuristique(po, arrivee, !plusDEscale), tuileCheminCourant));
					}
					if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.sud)) {
						po.x = pCourant.x; po.y = pCourant.y+1;
						file.add(new TuileChemin(po,Constantes.Orientation.nord,tuileCheminCourant.getPriority()+coutTuileNull,calculHeuristique(po, arrivee, !plusDEscale), tuileCheminCourant));
					}
					if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.nord)) {
						po.x = pCourant.x; po.y = pCourant.y-1;
						file.add(new TuileChemin(po,Constantes.Orientation.sud,tuileCheminCourant.getPriority()+coutTuileNull,calculHeuristique(po, arrivee, !plusDEscale), tuileCheminCourant));
					}
				}

				// Cas de la tuile d'escale de début;
				else if (t.getTypeTuile() == 1 && pCourant.equals(depart)) {
					if (t.connectionsExistantes(Constantes.Orientation.est)) {
						po.x = pCourant.x+1; po.y = pCourant.y;
						file.add(new TuileChemin(po,Constantes.Orientation.ouest,tuileCheminCourant.getPriority(),calculHeuristique(po, arrivee, !plusDEscale), tuileCheminCourant));
					}
					if (t.connectionsExistantes(Constantes.Orientation.ouest)) {
						po.x = pCourant.x-1; po.y = pCourant.y;
						file.add(new TuileChemin(po,Constantes.Orientation.est,tuileCheminCourant.getPriority(),calculHeuristique(po, arrivee, !plusDEscale), tuileCheminCourant));
					}
					if (t.connectionsExistantes(Constantes.Orientation.sud)) {
						po.x = pCourant.x; po.y = pCourant.y+1;
						file.add(new TuileChemin(po,Constantes.Orientation.nord,tuileCheminCourant.getPriority(),calculHeuristique(po, arrivee, !plusDEscale), tuileCheminCourant));
					}
					if (t.connectionsExistantes(Constantes.Orientation.nord)) {
						po.x = pCourant.x; po.y = pCourant.y-1;
						file.add(new TuileChemin(po,Constantes.Orientation.sud,tuileCheminCourant.getPriority(),calculHeuristique(po, arrivee, !plusDEscale), tuileCheminCourant));
					}
				}

				// Cas des tuiles avec présence d'arbre.
				else if (t.getPresenceArbres()) {
					for (String s : t.getDirectionConnectedTo(tuileCheminCourant.getDirection())) {
						switch (s) {
						case Constantes.Orientation.est : 
							po.x = pCourant.x+1; po.y = pCourant.y;
							file.add(new TuileChemin(po,Constantes.Orientation.ouest,tuileCheminCourant.getPriority()+coutTuileFixe,calculHeuristique(po, arrivee, !plusDEscale), tuileCheminCourant));
							break;
						case Constantes.Orientation.ouest : 
							po.x = pCourant.x-1; po.y = pCourant.y;
							file.add(new TuileChemin(po,Constantes.Orientation.est,tuileCheminCourant.getPriority()+coutTuileFixe,calculHeuristique(po, arrivee, !plusDEscale), tuileCheminCourant));
							break;
						case Constantes.Orientation.sud : 
							po.x = pCourant.x; po.y = pCourant.y+1;
							file.add(new TuileChemin(po,Constantes.Orientation.nord,tuileCheminCourant.getPriority()+coutTuileFixe,calculHeuristique(po, arrivee, !plusDEscale), tuileCheminCourant));
							break;
						case Constantes.Orientation.nord: 
							po.x = pCourant.x; po.y = pCourant.y-1;
							file.add(new TuileChemin(po,Constantes.Orientation.sud,tuileCheminCourant.getPriority()+coutTuileFixe,calculHeuristique(po, arrivee, !plusDEscale), tuileCheminCourant));
							break;
						}
					}
				}

				// Cas de base
				else {
					ArrayList<String> listeDirectionPossible = t.getDirectionConnectedTo(tuileCheminCourant.getDirection());
					if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.est)) {
						po.x = pCourant.x+1; po.y = pCourant.y;
						if (listeDirectionPossible.contains(Constantes.Orientation.est))
							file.add(new TuileChemin(po,Constantes.Orientation.ouest,tuileCheminCourant.getPriority()+coutTuileFixe,calculHeuristique(po, arrivee, !plusDEscale), tuileCheminCourant));
						else
							file.add(new TuileChemin(po,Constantes.Orientation.ouest,tuileCheminCourant.getPriority()+coutTuilePossible,calculHeuristique(po, arrivee, !plusDEscale), tuileCheminCourant));
					}
					if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.ouest)) {
						po.x = pCourant.x-1; po.y = pCourant.y;
						if (listeDirectionPossible.contains(Constantes.Orientation.ouest))
							file.add(new TuileChemin(po,Constantes.Orientation.est,tuileCheminCourant.getPriority()+coutTuileFixe,calculHeuristique(po, arrivee, !plusDEscale), tuileCheminCourant));
						else
							file.add(new TuileChemin(po,Constantes.Orientation.est,tuileCheminCourant.getPriority()+coutTuilePossible,calculHeuristique(po, arrivee, !plusDEscale), tuileCheminCourant));
					}
					if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.sud)) {
						po.x = pCourant.x; po.y = pCourant.y+1;
						if (listeDirectionPossible.contains(Constantes.Orientation.sud))
							file.add(new TuileChemin(po,Constantes.Orientation.nord,tuileCheminCourant.getPriority()+coutTuileFixe,calculHeuristique(po, arrivee, !plusDEscale), tuileCheminCourant));
						else
							file.add(new TuileChemin(po,Constantes.Orientation.nord,tuileCheminCourant.getPriority()+coutTuilePossible,calculHeuristique(po, arrivee, !plusDEscale), tuileCheminCourant));
					}
					if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.nord)) {
						po.x = pCourant.x; po.y = pCourant.y-1;
						if (listeDirectionPossible.contains(Constantes.Orientation.nord))
							file.add(new TuileChemin(po,Constantes.Orientation.sud,tuileCheminCourant.getPriority()+coutTuileFixe,calculHeuristique(po, arrivee, !plusDEscale), tuileCheminCourant));
						else
							file.add(new TuileChemin(po,Constantes.Orientation.sud,tuileCheminCourant.getPriority()+coutTuilePossible,calculHeuristique(po, arrivee, !plusDEscale), tuileCheminCourant));
					}
				}

				// La tuile qu'on regarde est lié à une escale : 
				if ((escaleAdjacente != 0 || (t != null && t.getEscale() != 0)) && !plusDEscale) {
					int e;
					if (t != null && t.getEscale() != 0) {
						e = t.getEscale();
					} else {
						e = escaleAdjacente;
					}

					for (int i = 0; i < 3; i++) {
						if (escales[i] == e) {
							escales[i] = -1;
							coutFinal = rechercheChemin(pCourant, tuileCheminCourant.getDirection(), arrivee, escales, plateau, tuileCheminCourant.getPriority());
							found = true;
							escales[i] = e;
						}
					}
				}

				if (pCourant.equals(arrivee) && plusDEscale) {
					coutFinal = tuileCheminCourant.getPriority();
					found = true;
				}

			}

		} while(!found && !file.isEmpty());

		/*TuileChemin lastOne = tuileCheminCourant;
		while(lastOne != null) {
			System.out.println(lastOne.getPosition()+" "+lastOne.getDirection()+" | cout :"+lastOne.getPriority());
			lastOne = lastOne.getPrevious();
		}
		System.out.println("---------");*/

		return coutFinal;
	}

	private int calculHeuristique(Point p1, Point p2, boolean enFaitOnSEnFoutDeLHeuristique) {
		if (enFaitOnSEnFoutDeLHeuristique)
			return 0;
		return coutHeuristique*(absolu(p1.x-p2.x)+absolu(p1.y-p2.y));
	}

	private int absolu(int i) {
		if (i < 0)
			return -i;
		return i;
	}

}
