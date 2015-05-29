package iaPackage;

import java.awt.Point;
import java.util.ArrayList;
import java.util.PriorityQueue;

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

	public IADifficile(Moteur moteur, JoueurIA joueurIA) {
		this.moteur = moteur;
		joueur = joueurIA;
	}

	public Coup getCoup() {


		if(moteur.getNbActions()<3)
			return Coup.newPioche();
		
		MainJoueur main = joueur.getMain();
		Plateau plateau = (Plateau) moteur.getPlateau().clone();
		int coutCoup = Integer.MAX_VALUE;
		Coup coupAJouer = null, coupCourant;
		int nbRotationMeilleurCoup = 0;
		int c;

		for (int numTuile = 0; numTuile < 5; numTuile++) { 								// Pour chaque Tuile non null dans la main...
			if (main.getTuileAt(numTuile) != null)
			{
				for (int nbRotation = 0; nbRotation < 4; nbRotation++) { 					 // Et pour chaque orientation...
					for (int x = 1; x < Constantes.Dimensions.dimensionPlateau-1; x++) { 	 // pour chaque...
						for (int y = 1; y < Constantes.Dimensions.dimensionPlateau-1; y++) { // position...
							// On tente le coup correspondant et donne un cout à ce coup.
							// Si ce coup est meilleur que celui enregistré, alors on l'enregistre
							coupCourant = Coup.newPlacement(numTuile, x, y);
							if (plateau.coupValide(main.getTuileAt(numTuile), coupCourant)) {
								jouerCoupProvisoire(plateau, x, y, numTuile, main);
								if ((c = evaluationPlateau(plateau)) < coutCoup) {
									coupAJouer = coupCourant;
									nbRotationMeilleurCoup = nbRotation;
									coutCoup = c;
								}
								jouerCoupProvisoire(plateau, x, y, numTuile, main);
							}
						}
					}
					joueur.tournerTuileMain(numTuile);
				}
			}
		}



		for (int i = 0; i < nbRotationMeilleurCoup; i++) {
			joueur.tournerTuileMain(coupAJouer.getTuile());
		}
		return coupAJouer;
		// * Tenter un coup
		// ** Evaluer le plateau
		// *** Faire ça avec tout les coups
		// **** Prendre le coup le mieux évaluer.

	}

	private void jouerCoupProvisoire(Plateau p, int x, int y, int tuileDansMain, MainJoueur main) {
		Tuile t = p.getTuileAt(x, y);
		p.setTuileAt(x, y, main.getTuileAt(tuileDansMain));
		main.setTuileAt(tuileDansMain, t);
	}

	private int evaluationPlateau(Plateau p) {
		return coutChemin(joueur.getLigne(),null,p);
	}
	
	private final static int coutTuileFixe = 1;
	private final static int coutTuileNull = 2;
	private final static int coutTuilePossible = 4;
	
	
	private int coutChemin(int ligne, int[] escales, Plateau p) {
		
		PriorityQueue<TuileChemin> file = new PriorityQueue<TuileChemin>
			(Constantes.Dimensions.dimensionPlateau*Constantes.Dimensions.dimensionPlateau, new ComparateurChemin());
		
		Point po = p.getTerminalPosition(ligne, 1);
		Point objectif = p.getTerminalPosition(ligne, 2);
		TuileChemin tuileCheminCourant = new TuileChemin(po,Constantes.Orientation.nord,0,magellanDistance(po,objectif));
				
		// TODO : Prendre en compte les escales
		
		do {
			Point pCourant = tuileCheminCourant.getPosition();
			Tuile t = p.getTuileAt(pCourant.x, pCourant.y);
			
			// Cas null : Pas de tuile présente, on cherche à voir dans toutes les autres directions
			if (t == null) {
				if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.est)) {
					po = new Point(pCourant.x+1,pCourant.y);
					file.add(new TuileChemin(po,Constantes.Orientation.ouest,tuileCheminCourant.getPriority()+coutTuileNull,magellanDistance(po,objectif)));
				}
				if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.ouest)){
					po = new Point(pCourant.x-1,pCourant.y);
					file.add(new TuileChemin(po,Constantes.Orientation.est,tuileCheminCourant.getPriority()+coutTuileNull,magellanDistance(po,objectif)));
				}
				if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.sud)) {
					po = new Point(pCourant.x,pCourant.y+1);
					file.add(new TuileChemin(po,Constantes.Orientation.nord,tuileCheminCourant.getPriority()+coutTuileNull,magellanDistance(po,objectif)));
				}
				if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.nord)) {
					po = new Point(pCourant.x,pCourant.y-1);
					file.add(new TuileChemin(po,Constantes.Orientation.sud,tuileCheminCourant.getPriority()+coutTuileNull,magellanDistance(po,objectif)));
				}
			}
			
			// Cas du Terminus (Gérée de cette façon, car on commence le chemin sur une escale...
			// Sur une direction ou elle n'est pas connectée.) Le cout d'une escale est nul.
			else if (t.getTypeTuile() == 1) {
				if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.est) && t.connectionsExistantes(Constantes.Orientation.est)) {
					po = new Point(pCourant.x+1,pCourant.y);
					file.add(new TuileChemin(po,Constantes.Orientation.ouest,tuileCheminCourant.getPriority(),magellanDistance(po,objectif)));
				}
				if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.ouest) && t.connectionsExistantes(Constantes.Orientation.ouest)) {
					po = new Point(pCourant.x-1,pCourant.y);
					file.add(new TuileChemin(po,Constantes.Orientation.est,tuileCheminCourant.getPriority(),magellanDistance(po,objectif)));
				}
				if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.sud) && t.connectionsExistantes(Constantes.Orientation.sud)) {
					po = new Point(pCourant.x,pCourant.y+1);
					file.add(new TuileChemin(po,Constantes.Orientation.nord,tuileCheminCourant.getPriority(),magellanDistance(po,objectif)));
				}
				if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.nord) && t.connectionsExistantes(Constantes.Orientation.nord)) {
					po = new Point(pCourant.x,pCourant.y-1);
					file.add(new TuileChemin(po,Constantes.Orientation.sud,tuileCheminCourant.getPriority(),magellanDistance(po,objectif)));
				}
			}
			
			// Cas des tuiles avec présence d'arbre (fixe) : On cherche à voir
			// Dans quel direction celle ou on est actuellement est connectée
			else if (t.getPresenceArbres()) {
				for (String s : t.getDirectionConnectedTo(tuileCheminCourant.getDirection())) {
					switch (s) {
					case Constantes.Orientation.est : 
						po = new Point(pCourant.x+1,pCourant.y);
						file.add(new TuileChemin(po,Constantes.Orientation.ouest,tuileCheminCourant.getPriority()+coutTuileFixe,magellanDistance(po,objectif)));
						break;
					case Constantes.Orientation.ouest : 
						po = new Point(pCourant.x-1,pCourant.y);
						file.add(new TuileChemin(po,Constantes.Orientation.est,tuileCheminCourant.getPriority()+coutTuileFixe,magellanDistance(po,objectif)));
						break;
					case Constantes.Orientation.sud : 
						po = new Point(pCourant.x,pCourant.y+1);
						file.add(new TuileChemin(po,Constantes.Orientation.nord,tuileCheminCourant.getPriority()+coutTuileFixe,magellanDistance(po,objectif)));
						break;
					case Constantes.Orientation.nord: 
						po = new Point(pCourant.x,pCourant.y-1);
						file.add(new TuileChemin(po,Constantes.Orientation.sud,tuileCheminCourant.getPriority()+coutTuileFixe,magellanDistance(po,objectif)));
						break;
					}
				}
			}
			
			// Cas d'une tuile qu'on peut remplacer : On met une priorité sur les connexion existantes
			// Et on met un cout plus important pour celle qui peuvent exister.
			else {
				ArrayList<String> listeDirectionPossible = t.getDirectionConnectedTo(tuileCheminCourant.getDirection());
				if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.est)) {
					po = new Point(pCourant.x+1,pCourant.y);
					if (listeDirectionPossible.contains(Constantes.Orientation.est))
						file.add(new TuileChemin(po,Constantes.Orientation.ouest,tuileCheminCourant.getPriority()+coutTuileFixe,magellanDistance(po,objectif)));
					else 
						file.add(new TuileChemin(po,Constantes.Orientation.ouest,tuileCheminCourant.getPriority()+coutTuilePossible,magellanDistance(po,objectif)));
				}
				if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.ouest)) {
					po = new Point(pCourant.x-1,pCourant.y);
					if (listeDirectionPossible.contains(Constantes.Orientation.ouest))
						file.add(new TuileChemin(po,Constantes.Orientation.est,tuileCheminCourant.getPriority()+coutTuileFixe,magellanDistance(po,objectif)));
					else
						file.add(new TuileChemin(po,Constantes.Orientation.est,tuileCheminCourant.getPriority()+coutTuilePossible,magellanDistance(po,objectif)));
				}
				if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.sud)) {
					po = new Point(pCourant.x,pCourant.y+1);
					if (listeDirectionPossible.contains(Constantes.Orientation.sud))
						file.add(new TuileChemin(po,Constantes.Orientation.nord,tuileCheminCourant.getPriority()+coutTuileFixe,magellanDistance(po,objectif)));
					else
						file.add(new TuileChemin(po,Constantes.Orientation.nord,tuileCheminCourant.getPriority()+coutTuilePossible,magellanDistance(po,objectif)));
				}
				if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.nord)) {
					po = new Point(pCourant.x,pCourant.y-1);
					if (listeDirectionPossible.contains(Constantes.Orientation.nord))
						file.add(new TuileChemin(po,Constantes.Orientation.sud,tuileCheminCourant.getPriority()+coutTuileFixe,magellanDistance(po,objectif)));
					else

						file.add(new TuileChemin(po,Constantes.Orientation.sud,tuileCheminCourant.getPriority()+coutTuilePossible,magellanDistance(po,objectif)));
				}
			}
			//for (TuileChemin tc : file)
			//	System.out.println(tc.position+ " " + tc.priority + "|" + tc.heuristique + " " + tc.direction);
			
			//System.out.println("-------------------------------------------------");
			tuileCheminCourant = file.remove();
		} while (!tuileCheminCourant.getPosition().equals(objectif));

		return tuileCheminCourant.getPriority();
	}
	
	
	private int magellanDistance(Point p1, Point p2) {
		return 2*(absolu(p1.x-p2.x)+absolu(p1.y-p2.y));
	}
	
	private int absolu(int i) {
		if (i < 0)
			return -i;
		return i;
	}
}
