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
import objectPackage.tuilePackage.Escale;
import objectPackage.tuilePackage.Tuile;
import constantesPackages.Constantes;
@SuppressWarnings("unused")
public class IADifficile implements InterfaceIA {
	private Moteur moteur;
	private JoueurIA joueur;
	private Random r;
	private CoupEtRotation coupEnAttente;
	private final static boolean trace = false;
	private final static int nbTourAvantIAPlusDevellope = 0;
	public IADifficile(Moteur moteur, JoueurIA joueurIA) {
		this.moteur = moteur;
		joueur = joueurIA;
		r = new Random();
		coupEnAttente = null;
	}
	public void setJoueur(JoueurIA j) {
		joueur = j;
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
		Plateau plateau = (Plateau) moteur.getPlateau().clone();
		MainJoueur main = joueur.getMain();
		CoupEtRotation coupSoloActuel;
		CoupEtRotation coupDuoActuel;
		ArrayList<CoupEtRotation> coupsSoloRetenu = new ArrayList<CoupEtRotation>();
		ArrayList<CoupEtRotation[]> coupsDuoRetenu = new ArrayList<CoupEtRotation[]>();
		int coutActuel = 0;
		int coutSolo = Integer.MAX_VALUE/4;
		int coutDuo = Integer.MAX_VALUE/4;
		// * Tenter un coup
		// ** Evaluer le plateau
		// *** Faire ça avec tout les coups
		// **** Prendre le coup le mieux évaluer.
		// PARTIE 0 : INITIALISATION : VERIFIER SI DEUX TUILE SONT IDENTIQUE DANS LA MAIN
		// POUR REDUIRE LE COUT.
		boolean[] unique = {true, true, true, true, true};
		boolean[][] uniqueRotation = { {true, true, true, true},
				{true, true, true, true},
				{true, true, true, true},
				{true, true, true, true},
				{true, true, true, true}};
		Tuile tCourant, tInnomable;
		for (int i = 0; i < 5; i++) {
			if (main.getTuileAt(i) != null) {
				tCourant = main.getTuileAt(i).clone();
				tInnomable = tCourant.clone();
				tInnomable.rotation();
				for (int r = 0; r < 4; r++) {
					for (int j = i+1; j < 5; j++) {
						if (tCourant.equals(main.getTuileAt(j)))
							unique[j] = false;
					}
					if ((r != 0 && tCourant.equals(main.getTuileAt(i))) || ((r >1) && tCourant.equals(tInnomable)))
						uniqueRotation[i][r] = false;
					tCourant.rotation();
				}
			}
		}
		// PARTIE 1 : SIMULER TOUT LES COUP ET GARDER LE MEILLEUR DE TOUS
		// EN PARALLELE, SIMULER TOUT LES DUO DE COUP ADJACENT ET ENREGISTRER LE MEILLEUR DE TOUS
		for (int x = 1; x < Constantes.Dimensions.dimensionPlateau-1; x++) { // Pour chaque...
			for (int y = 1; y < Constantes.Dimensions.dimensionPlateau-1; y++) { // position...
				for (int numTuile = 0; numTuile < 5; numTuile++) { // Pour chaque Tuile non null dans la main...
					if (main.getTuileAt(numTuile) != null && unique[numTuile]){
						for (int nbRotation = 0; nbRotation < 4; nbRotation++) { // Et pour chaque orientation...
							if (uniqueRotation[numTuile][nbRotation]) {
								coupSoloActuel = new CoupEtRotation(new Coup(Coup.placement, numTuile, new Point(x,y)), nbRotation);
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
									coupDuoActuel = secondCoupAdjacent(plateau, coupSoloActuel);
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
									jouerCoupProvisoire(plateau, x, y, numTuile, main);
								}
							}
							joueur.tournerTuileMain(numTuile);
						}
					}
				}
			}
		}
		CoupEtRotation coupSoloChoisi = coupsSoloRetenu.get(r.nextInt(coupsSoloRetenu.size()));
		// PARTIE 2 : SIMULER LE PREMIER COUP, ET TROUVER LE MEILLEUR DEUXIEME COUP ASSOCIE AU PREMIER.
		// PUIS COMPARER AVEC LE MEILLEUR DUO DE COUP ADJACENT DE LA PREMIERE PHASE
		jouerCoupProvisoire(plateau, coupSoloChoisi.getCoup().getCoordonnee().x, coupSoloChoisi.getCoup().getCoordonnee().y, coupSoloChoisi.getCoup().getTuile(), main);
		for (int x = 1; x < Constantes.Dimensions.dimensionPlateau-1; x++) { // Pour chaque...
			for (int y = 1; y < Constantes.Dimensions.dimensionPlateau-1; y++) { // position...
				for (int numTuile = 0; numTuile < 5; numTuile++) { // Pour chaque Tuile non null dans la main...
					if (main.getTuileAt(numTuile) != null){
						for (int nbRotation = 0; nbRotation < 4; nbRotation++) { // Et pour chaque orientation...
							if (uniqueRotation[numTuile][nbRotation]) {
								coupDuoActuel = new CoupEtRotation(new Coup(Coup.placement, numTuile, new Point(x,y)), nbRotation);
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
	private CoupEtRotation secondCoupAdjacent(Plateau plateau, CoupEtRotation c) {
		int coutActuel = Integer.MAX_VALUE/4;
		CoupEtRotation coupGarde = new CoupEtRotation(null,0); coupGarde.setCout(Integer.MAX_VALUE/4);
		CoupEtRotation coupCourant;
		MainJoueur main = joueur.getMain();
		int x = c.getCoup().getCoordonnee().x, y = c.getCoup().getCoordonnee().y;
		for (int i = 0; i < 4; i++) { // Position autour de la tuile.
			for (int numTuile = 0; numTuile < 5; numTuile++) {
				if (main.getTuileAt(numTuile) != null) {
					for (int nbRotation = 0; nbRotation < 4; nbRotation++) {
						Coup c1;
						if (i == 0)
							c1 = Coup.newPlacement(numTuile, x+1, y);
						else if (i == 0)
							c1 = Coup.newPlacement(numTuile, x-1, y);
						else if (i == 0)
							c1 = Coup.newPlacement(numTuile, x, y+1);
						else
							c1 = Coup.newPlacement(numTuile, x, y-1);
						coupCourant = new CoupEtRotation(c1,nbRotation);
						if (plateau.coupValide(main.getTuileAt(numTuile), coupCourant.getCoup())) {
							jouerCoupProvisoire(plateau, x, y, numTuile, main);
							coupCourant.setCout(evaluationPlateau(plateau));
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
	static void jouerCoupProvisoire(Plateau p, int x, int y, int tuileDansMain, MainJoueur main) {
		Tuile t = p.getTuileAt(x, y);
		p.setTuileAt(x, y, main.getTuileAt(tuileDansMain));
		main.setTuileAt(tuileDansMain, t);
	}
	private int evaluationPlateau(Plateau p) {
		int c1 = coutChemin(joueur.getObjectifs().getLigne(),joueur.getObjectifs().getEscalesCibles(),p);
		int c2 = coutChemin(moteur.getTabPlayers()[(moteur.getcurrentPlayer()+1)%2].getObjectifs().getLigne(),moteur.getTabPlayers()[(moteur.getcurrentPlayer()+1)%2].getObjectifs().getEscalesCibles(),p);
		if (c1 == 0) {
			return Integer.MIN_VALUE/4;
		}
		return (4*c1)-(c2);
	}
	private final static int coutTuileFixe = 0;
	private final static int coutTuileNull = 3;
	private final static int coutTuilePossible = 15;
	private final static int coutHeuristique = 3;
	private final static int coutHeuristiqueSecond = 1;
	private static int calculHeuristique(Point p1, Point p2, Point p3) {
		if (p3 == null)
			return coutHeuristique*(absolu(p1.x-p2.x)+absolu(p1.y-p2.y));
		else
			return coutHeuristique*(absolu(p1.x-p2.x)+absolu(p1.y-p2.y)) + coutHeuristiqueSecond*(absolu(p1.x-p3.x)+absolu(p1.y-p3.y));
	}
	private static int absolu(int i) {
		if (i < 0)
			return -i;
		return i;
	}
	private static int max(int i, int j) {
		if (i < j)
			return j;
		return i;
	}
	static int coutChemin(int ligne, int[] escales, Plateau referencePlateau) {
		int[] escalesBis = new int[3];
		for (int i = 0; i< 3; i++)
			escalesBis[i] = escales[i];
		escalesBis[0] = -1;
		int x = rechercheChemin(referencePlateau.getTerminalPosition(ligne,1), Constantes.Orientation.ouest, referencePlateau.getEscalePosition(escales[0]), ligne, escalesBis, referencePlateau, 0);
		escalesBis[0] = escales[0];
		escalesBis[1] = -1;
		int y = rechercheChemin(referencePlateau.getTerminalPosition(ligne,1), Constantes.Orientation.ouest, referencePlateau.getEscalePosition(escales[1]), ligne, escalesBis, referencePlateau, 0);
		escalesBis[1] = escales[1];
		return max(x,y);
	}
	private static int rechercheChemin(Point depart, String directionDepart, Point arrivee, int ligne, int[] escales, Plateau plateau, int coutInitial) {
		// Initialisation
		boolean trace = false;
		PriorityQueue<TuileChemin> file = new PriorityQueue<TuileChemin>
		(Constantes.Dimensions.dimensionPlateau*Constantes.Dimensions.dimensionPlateau, new ComparateurChemin());
		Point po = depart;
		TuileChemin tuileCheminCourant = new TuileChemin(po,directionDepart,coutInitial,0, null);
		file.add(tuileCheminCourant);
		po = new Point();
		RejectList seens = new RejectList();
		boolean found = false;
		int coutFinal = Integer.MAX_VALUE/4;
		int escaleAdjacente;
		Point prochainObjectif = null;
		// Verifier si l'objectif actuel est une Escale
		// Si oui on récupère son numéro, et on récupère l'objectif suivant
		if (arrivee == null) {
			return coutFinal;
		}
		Tuile tuileArrivee = plateau.getTuileAt(arrivee.x, arrivee.y);
		int escaleCourante = 0;
		if (tuileArrivee instanceof Escale) {
			Escale e = (Escale) tuileArrivee;
			escaleCourante = e.getNumeroEscale();
			int i = 0;
			while (i < 3 && !found) {
				if (escales[i] > 0) {
					prochainObjectif = plateau.getEscalePosition(escales[i]);
				}
				i++;
			}
			if (!found) {
				prochainObjectif = plateau.getTerminalPosition(ligne,2);
			}
			found = false;
		}
		// Debut du A*
		do {
			// Récupérer la tuile suivante,
			tuileCheminCourant = file.remove();
			Point pCourant = tuileCheminCourant.getPosition();
			Tuile t = plateau.getPlateau()[pCourant.x][pCourant.y];
			// On traite la tuile si la tuile + rotation n'a pas déja été vu.
			if (!seens.contain(tuileCheminCourant)) {
				seens.add(tuileCheminCourant);
				escaleAdjacente = plateau.aCoterDUneEscaleNonAssignee(pCourant);
				// Cas de la tuile null
				// On va dans toute les direction sauf celle d'où on vient avec un cout de coutTuileNull
				if (t == null) {
					if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.est)) {
						po.x = pCourant.x+1; po.y = pCourant.y;
						file.add(new TuileChemin(po, Constantes.Orientation.ouest, tuileCheminCourant.getPriority()+coutTuileNull, calculHeuristique(po, arrivee, prochainObjectif), tuileCheminCourant));
					}
					if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.ouest)){
						po.x = pCourant.x-1; po.y = pCourant.y;
						file.add(new TuileChemin(po,Constantes.Orientation.est,tuileCheminCourant.getPriority()+coutTuileNull,calculHeuristique(po, arrivee, prochainObjectif), tuileCheminCourant));
					}
					if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.sud)) {
						po.x = pCourant.x; po.y = pCourant.y+1;
						file.add(new TuileChemin(po,Constantes.Orientation.nord,tuileCheminCourant.getPriority()+coutTuileNull,calculHeuristique(po, arrivee, prochainObjectif), tuileCheminCourant));
					}
					if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.nord)) {
						po.x = pCourant.x; po.y = pCourant.y-1;
						file.add(new TuileChemin(po,Constantes.Orientation.sud,tuileCheminCourant.getPriority()+coutTuileNull,calculHeuristique(po, arrivee, prochainObjectif), tuileCheminCourant));
					}
				}
				// Cas de la tuile d'escale de début;
				// On va dans les deux direction possible de la tuile (Code inutile possible)
				else if (t.getTypeTuile() == 1 && pCourant.equals(depart)) {
					if (t.connectionsExistantes(Constantes.Orientation.est)) {
						po.x = pCourant.x+1; po.y = pCourant.y;
						file.add(new TuileChemin(po,Constantes.Orientation.ouest,tuileCheminCourant.getPriority(),calculHeuristique(po, arrivee, prochainObjectif), tuileCheminCourant));
					}
					if (t.connectionsExistantes(Constantes.Orientation.ouest)) {
						po.x = pCourant.x-1; po.y = pCourant.y;
						file.add(new TuileChemin(po,Constantes.Orientation.est,tuileCheminCourant.getPriority(),calculHeuristique(po, arrivee, prochainObjectif), tuileCheminCourant));
					}
					if (t.connectionsExistantes(Constantes.Orientation.sud)) {
						po.x = pCourant.x; po.y = pCourant.y+1;
						file.add(new TuileChemin(po,Constantes.Orientation.nord,tuileCheminCourant.getPriority(),calculHeuristique(po, arrivee, prochainObjectif), tuileCheminCourant));
					}
					if (t.connectionsExistantes(Constantes.Orientation.nord)) {
						po.x = pCourant.x; po.y = pCourant.y-1;
						file.add(new TuileChemin(po,Constantes.Orientation.sud,tuileCheminCourant.getPriority(),calculHeuristique(po, arrivee, prochainObjectif), tuileCheminCourant));
					}
				}
				// Cas des tuiles avec présence d'arbre.
				// On va dans toutes les directions ou on peut aller depuis la direction d'ou on vient avec un cout de coutTuileFixe
				else if (t.getPresenceArbres()) {
					for (String s : t.getDirectionConnectedTo(tuileCheminCourant.getDirection())) {
						switch (s) {
						case Constantes.Orientation.est :
							po.x = pCourant.x+1; po.y = pCourant.y;
							file.add(new TuileChemin(po,Constantes.Orientation.ouest,tuileCheminCourant.getPriority()+coutTuileFixe,calculHeuristique(po, arrivee, prochainObjectif), tuileCheminCourant));
							break;
						case Constantes.Orientation.ouest :
							po.x = pCourant.x-1; po.y = pCourant.y;
							file.add(new TuileChemin(po,Constantes.Orientation.est,tuileCheminCourant.getPriority()+coutTuileFixe,calculHeuristique(po, arrivee, prochainObjectif), tuileCheminCourant));
							break;
						case Constantes.Orientation.sud :
							po.x = pCourant.x; po.y = pCourant.y+1;
							file.add(new TuileChemin(po,Constantes.Orientation.nord,tuileCheminCourant.getPriority()+coutTuileFixe,calculHeuristique(po, arrivee, prochainObjectif), tuileCheminCourant));
							break;
						case Constantes.Orientation.nord:
							po.x = pCourant.x; po.y = pCourant.y-1;
							file.add(new TuileChemin(po,Constantes.Orientation.sud,tuileCheminCourant.getPriority()+coutTuileFixe,calculHeuristique(po, arrivee, prochainObjectif), tuileCheminCourant));
							break;
						}
					}
				}
				// Cas de base (Une tuile remplaçable)
				// On va dans toutes les directions ou on peut aller depuis la direction d'ou on vient avec un cout de coutTuileFixe
				// + Toute les autres direction d'ou on ne vient pas avec un cout de coutTuilePossible
				else {
					ArrayList<String> listeDirectionPossible = t.getDirectionConnectedTo(tuileCheminCourant.getDirection());
					if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.est)) {
						po.x = pCourant.x+1; po.y = pCourant.y;
						if (listeDirectionPossible.contains(Constantes.Orientation.est))
							file.add(new TuileChemin(po,Constantes.Orientation.ouest,tuileCheminCourant.getPriority()+coutTuileFixe,calculHeuristique(po, arrivee, prochainObjectif), tuileCheminCourant));
						else
							file.add(new TuileChemin(po,Constantes.Orientation.ouest,tuileCheminCourant.getPriority()+coutTuilePossible,calculHeuristique(po, arrivee, prochainObjectif), tuileCheminCourant));
					}
					if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.ouest)) {
						po.x = pCourant.x-1; po.y = pCourant.y;
						if (listeDirectionPossible.contains(Constantes.Orientation.ouest))
							file.add(new TuileChemin(po,Constantes.Orientation.est,tuileCheminCourant.getPriority()+coutTuileFixe,calculHeuristique(po, arrivee, prochainObjectif), tuileCheminCourant));
						else
							file.add(new TuileChemin(po,Constantes.Orientation.est,tuileCheminCourant.getPriority()+coutTuilePossible,calculHeuristique(po, arrivee, prochainObjectif), tuileCheminCourant));
					}
					if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.sud)) {
						po.x = pCourant.x; po.y = pCourant.y+1;
						if (listeDirectionPossible.contains(Constantes.Orientation.sud))
							file.add(new TuileChemin(po,Constantes.Orientation.nord,tuileCheminCourant.getPriority()+coutTuileFixe,calculHeuristique(po, arrivee, prochainObjectif), tuileCheminCourant));
						else
							file.add(new TuileChemin(po,Constantes.Orientation.nord,tuileCheminCourant.getPriority()+coutTuilePossible,calculHeuristique(po, arrivee, prochainObjectif), tuileCheminCourant));
					}
					if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.nord)) {
						po.x = pCourant.x; po.y = pCourant.y-1;
						if (listeDirectionPossible.contains(Constantes.Orientation.nord))
							file.add(new TuileChemin(po,Constantes.Orientation.sud,tuileCheminCourant.getPriority()+coutTuileFixe,calculHeuristique(po, arrivee, prochainObjectif), tuileCheminCourant));
						else
							file.add(new TuileChemin(po,Constantes.Orientation.sud,tuileCheminCourant.getPriority()+coutTuilePossible,calculHeuristique(po, arrivee, prochainObjectif), tuileCheminCourant));
					}
				}
				// On atteint l'objectif :
				// Si l'objectif est le terminus, on arrête l'algo et on renvoi le cout
				if (escaleCourante == 0 && pCourant.equals(arrivee) ) {
					coutFinal = tuileCheminCourant.getPriority();
					found = true;
					// Si c'est une escale, on change d'objectif en prenant le prochain objectif.
					// Pour cela on utilise récursivement l'algorithme
				} else if (escaleCourante != 0 && (escaleAdjacente == escaleCourante || (t != null && t.getEscaleLiee() == escaleCourante))) {
					int i = 0;
					while (i < 3 && !found) {
						if (escales[i] > 0) {
							int e = escales[i];
							escales[i] = -1;
							coutFinal = rechercheChemin(pCourant, tuileCheminCourant.getDirection(), plateau.getEscalePosition(e), ligne, escales, plateau, tuileCheminCourant.getPriority());
							found = true;
							escales[i] = e;
						}
						i++;
					}
					// Si il n'y a plus d'escale, le prochain objectif est le terminus de la ligne
					if (!found) {
						coutFinal = rechercheChemin(pCourant, tuileCheminCourant.getDirection(), plateau.getTerminalPosition(ligne,2), ligne, escales, plateau, tuileCheminCourant.getPriority());
						found = true;
					}
				}
			}
		} while(!found && !file.isEmpty());
		return coutFinal;
	}
}
 