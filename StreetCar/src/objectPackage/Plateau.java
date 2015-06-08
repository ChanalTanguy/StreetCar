package objectPackage;

import iaPackage.ComparateurChemin;
import iaPackage.RejectList;
import iaPackage.TuileChemin;

import java.awt.Point;
import java.util.ArrayList;
import java.util.PriorityQueue;

import joueurPackage.Coup;
import joueurPackage.Objectifs;
import objectPackage.tuilePackage.Connection;
import objectPackage.tuilePackage.Escale;
import objectPackage.tuilePackage.Terminus;
import objectPackage.tuilePackage.Tuile;
import constantesPackages.Constantes;

public class Plateau {
	private Tuile[][] plateau;

	private static final int[][] PositionEscale = {  { 1, 5},
		{ 4, 2},
		{ 8, 1},
		{11, 4},
		{12, 8},
		{ 9,11},
		{ 5,12},
		{ 2, 9},
		{ 4, 7},
		{ 6, 4},
		{ 9, 6},
		{ 7, 9}
	};

	public Plateau (){
		plateau = new Tuile[Constantes.Dimensions.dimensionPlateau][Constantes.Dimensions.dimensionPlateau];
		initialisationTuile();
	}

	private void initialisationTuile() {
		// Mettre toutes les tuiles de bord vides à la vertical
		Tuile t;
		for (int i = 0; i<14;i++) {
			setTuileAt(0, i, t = new Tuile(true));
			t.setType(3);
			setTuileAt(13, i, t = new Tuile(true));
			t.setType(3);
		}
		// à l'horizontal
		for (int i = 1; i<13;i++) {
			setTuileAt(i, 0, t = new Tuile(true));
			t.setType(3);
			setTuileAt(i, 13, t = new Tuile(true));
			t.setType(3);
		}

		// Mettre les escales
		setTuileAt( PositionEscale[ 0][0], PositionEscale[ 0][1], new Escale( 1));
		setTuileAt( PositionEscale[ 1][0], PositionEscale[ 1][1], new Escale( 2));
		setTuileAt( PositionEscale[ 2][0], PositionEscale[ 2][1], new Escale( 3));
		setTuileAt( PositionEscale[ 3][0], PositionEscale[ 3][1], new Escale( 4));
		setTuileAt( PositionEscale[ 4][0], PositionEscale[ 4][1], new Escale( 5));
		setTuileAt( PositionEscale[ 5][0], PositionEscale[ 5][1], new Escale( 6));
		setTuileAt( PositionEscale[ 6][0], PositionEscale[ 6][1], new Escale( 7));
		setTuileAt( PositionEscale[ 7][0], PositionEscale[ 7][1], new Escale( 8));
		setTuileAt( PositionEscale[ 8][0], PositionEscale[ 8][1], new Escale( 9));
		setTuileAt( PositionEscale[ 9][0], PositionEscale[ 9][1], new Escale(10));
		setTuileAt( PositionEscale[10][0], PositionEscale[10][1], new Escale(11));
		setTuileAt( PositionEscale[11][0], PositionEscale[11][1], new Escale(12));

		// Mettre les Terminus

		// Terminus du Nord
		for(int i = 0; i<3;i++) {
			// Tuile gauche
			t = new Terminus(i+4,1); t.addConnection(new Connection(Constantes.Orientation.est,Constantes.Orientation.sud));
			setTuileAt(2+4*i, 0, t);
			// Tuile droite
			t = new Terminus(i+4,1); t.addConnection(new Connection(Constantes.Orientation.ouest,Constantes.Orientation.sud));
			setTuileAt(3+4*i, 0, t);
		}

		// Terminus du Sud
		for(int i = 0; i<3;i++) {
			int j = (i-1)%3;
			// Tuile gauche
			t = new Terminus(j+4,2); t.addConnection(new Connection(Constantes.Orientation.est,Constantes.Orientation.nord));
			setTuileAt(2+4*i, 13, t);
			// Tuile droite
			t = new Terminus(j+4,2); t.addConnection(new Connection(Constantes.Orientation.ouest,Constantes.Orientation.nord));
			setTuileAt(3+4*i, 13, t);
		}

		// Terminus de l'est
		for(int i = 0; i<3;i++) {
			// Tuile haute
			t = new Terminus(i+1,1); t.addConnection(new Connection(Constantes.Orientation.sud,Constantes.Orientation.est));
			setTuileAt(0, 10-(4*i), t);
			// Tuile basse
			t = new Terminus(i+1,1); t.addConnection(new Connection(Constantes.Orientation.nord,Constantes.Orientation.est));
			setTuileAt(0, 11-(4*i), t);
		}

		// Terminus de l'ouest
		for(int i = 0; i<3;i++) {
			int j = (i-1)%3;
			// Tuile haute
			t = new Terminus(j+1,2); t.addConnection(new Connection(Constantes.Orientation.sud,Constantes.Orientation.ouest));
			setTuileAt(13, 10-(4*i), t);
			// Tuile basse
			t = new Terminus(j+1,2); t.addConnection(new Connection(Constantes.Orientation.nord,Constantes.Orientation.ouest));
			setTuileAt(13, 11-(4*i), t);
		}

	}

	/*
	 * Accesseurs
	 */
	public Tuile[][] getPlateau (){
		return plateau;
	}
	
	public void setTuileAt(int x, int y, Tuile t) {
		plateau[x][y] = t;
	}

	/*
	 * FIN Accesseurs
	 */
	
	/*
	 * Methodes Public de Plateau
	 */
	public int length (){
		return plateau.length;
	}
	public boolean coupValide (Tuile nouvTuile, Coup coup){
		if(coup.getType().equals(Constantes.Coup.placement)) {
			// Vérifier que la case est sois vide, sois possède une sous version de la tuile qu'on veut poser
			// Et que les 4 cases adjacente ne font pas conflit
			boolean valide = false;

			Point coord = coup.getCoordonnee();
			int x = coord.x;
			int y = coord.y;
			Tuile ancTuile = getTuileAt(x,y);

			if (ancTuile == null) 
				valide = true;
			else {
				if (ancTuile.getPresenceArbres())
					valide = false;
				else {
					valide = nouvTuile.canReplace(ancTuile);
				}
			}

			valide = valide
					&& (getTuileAt(x,y+1) == null || getTuileAt(x,y+1).canConnectTo(nouvTuile, Constantes.Orientation.nord))
					&& (getTuileAt(x,y-1) == null || getTuileAt(x,y-1).canConnectTo(nouvTuile, Constantes.Orientation.sud))
					&& (getTuileAt(x+1,y) == null || getTuileAt(x+1,y).canConnectTo(nouvTuile, Constantes.Orientation.ouest))
					&& (getTuileAt(x-1,y) == null || getTuileAt(x-1,y).canConnectTo(nouvTuile, Constantes.Orientation.est));

			return valide;
		} else 
			return false;
	}
	public String coupSimultaneValide (Tuile nouvTuile, Coup coup) {
		if(coup.getType().equals(Constantes.Coup.placement)) {
			// Vérifier que la case est sois vide, sois possède une sous version de la tuile qu'on veut poser
			// Et que les 4 cases adjacente ne font pas conflit

			Point coord = coup.getCoordonnee();
			int x = coord.x;
			int y = coord.y;
			Tuile ancTuile = getTuileAt(x,y);

			if (ancTuile == null) 
				return null; // Le moteur n'est pas sensé arriver ici : 
			// Si la tuile est vide le coup simultané n'a pas lieu d'être

			if (ancTuile.getPresenceArbres())
				return null;

			if (!nouvTuile.canReplace(ancTuile))
				return null;

			int valide = 0;

			valide += (getTuileAt(x,y+1) == null || getTuileAt(x,y+1).canConnectTo(nouvTuile, Constantes.Orientation.nord))? 0 : 1;
			valide += (getTuileAt(x,y-1) == null || getTuileAt(x,y-1).canConnectTo(nouvTuile, Constantes.Orientation.sud))? 0 : 2;
			valide += (getTuileAt(x+1,y) == null || getTuileAt(x+1,y).canConnectTo(nouvTuile, Constantes.Orientation.ouest))? 0 : 4;
			valide += (getTuileAt(x-1,y) == null || getTuileAt(x-1,y).canConnectTo(nouvTuile, Constantes.Orientation.est))? 0 : 8;

			switch (valide) {
			case 1 : return (getTuileAt(x,y+1).getPresenceArbres()) ? null : Constantes.Orientation.nord ;
			case 2 : return (getTuileAt(x,y-1).getPresenceArbres()) ? null : Constantes.Orientation.sud  ;
			case 4 : return (getTuileAt(x+1,y).getPresenceArbres()) ? null : Constantes.Orientation.ouest;
			case 8 : return (getTuileAt(x-1,y).getPresenceArbres()) ? null : Constantes.Orientation.est  ;
			default : return null;
			}

		} else 
			return null;
	}
	public Tuile getTuileAt (int x, int y){
		return plateau[x][y];
	}
	public Point getTerminalPosition (int numberLigne, int numberTerminal){
		Point p;
		switch (numberLigne) {
		case 1 : 
			if (numberTerminal == 1)
				p = new Point(0,10);
			else
				p = new Point(13,6);
			break;
		case 2 : 
			if (numberTerminal == 1)
				p = new Point(0,6);
			else
				p = new Point(13,2);
			break;
		case 3 : 
			if (numberTerminal == 1)
				p = new Point(0,2);
			else
				p = new Point(13,10);
			break;
		case 4 : 
			if (numberTerminal == 1)
				p = new Point(2,0);
			else
				p = new Point(6,13);
			break;
		case 5 : 
			if (numberTerminal == 1)
				p = new Point(6,0);
			else
				p = new Point(10,13);
			break;
		case 6 : 
			if (numberTerminal == 1)
				p = new Point(10,0);
			else
				p = new Point(2,13);
			break;
		default :
			p = null;
		}
		return p;
	}
	public Point getEscalelPosition (int numberEscale){
		try {
			return new Point(PositionEscale[numberEscale-1][0],PositionEscale[numberEscale-1][1]);
		} catch (Exception e) {
			return null;
		}
	}	
	public int aCoterDUneEscaleNonAssignee (Point p){
		int x = p.x, y = p.y;
		if (x != 0 					&& plateau[x-1][y] != null && plateau[x-1][y].getTypeTuile() == 2 && ((Escale)(plateau[x-1][y])).getStop() == null)
			return ((Escale)(plateau[x-1][y])).getNumeroEscale();
		if (x != plateau.length-1	&& plateau[x+1][y] != null && plateau[x+1][y].getTypeTuile() == 2 && ((Escale)(plateau[x+1][y])).getStop() == null)
			return ((Escale)(plateau[x+1][y])).getNumeroEscale();
		if (y != 0 					&& plateau[x][y-1] != null && plateau[x][y-1].getTypeTuile() == 2 && ((Escale)(plateau[x][y-1])).getStop() == null)
			return ((Escale)(plateau[x][y-1])).getNumeroEscale();
		if (y != plateau.length-1	&& plateau[x][y+1] != null && plateau[x][y+1].getTypeTuile() == 2 && ((Escale)(plateau[x][y+1])).getStop() == null)
			return ((Escale)(plateau[x][y+1])).getNumeroEscale();
			
		return 0;
	}
	public boolean ObjectifComplet (Objectifs objectifs){
		int[] escales = new int[3];
		for (int i = 0; i< 3; i++){
			escales[i] = objectifs.getEscalesCibles()[i];
		}
		return atteindreObjectifDepuisPosition(getTerminalPosition(objectifs.getLigne(),1),Constantes.Orientation.ouest,getTerminalPosition(objectifs.getLigne(),2), escales);

	}

	public Plateau clone () {
		Plateau p = new Plateau();
		for (int i = 0; i < Constantes.Dimensions.dimensionPlateau; i++) {
			for (int j = 0; j < Constantes.Dimensions.dimensionPlateau; j++) {
				Tuile t = getTuileAt(i,j);
				if (t != null)
					p.setTuileAt(i, j, t.clone());
			}
		}
		return p;
	}
	public boolean equals (){
		boolean plateauIdentique = false;
		
		return plateauIdentique;
	}
	public String toString (){
		String chaine_resultat = "";
		
		for (int indexLigne = 1; indexLigne < plateau.length-1; indexLigne++){
			for (int indexColonne = 1; indexColonne < plateau.length-1; indexColonne++){
				if ( plateau[indexLigne][indexColonne] != null ){
					chaine_resultat += plateau[indexLigne][indexColonne].toString();
				}
				else {
					chaine_resultat += "{null}";
				}
				chaine_resultat += " ";
			}
			chaine_resultat += "\n";
		}
		
		return chaine_resultat;
	}
	
	/*
	 * Methodes Private de Plateau
	 */
	private boolean atteindreObjectifDepuisPosition(Point depart, String direction, Point arrive, int[] escale) {

		PriorityQueue<TuileChemin> file = new PriorityQueue<TuileChemin>
		(Constantes.Dimensions.dimensionPlateau*Constantes.Dimensions.dimensionPlateau, new ComparateurChemin());
		Point po = depart;
		TuileChemin tuileCheminCourant = new TuileChemin(po,direction,0,0, null);
		file.add(tuileCheminCourant);
		po = new Point();
		RejectList seens = new RejectList();
		boolean found = false;
		
		boolean plusDEscale;
		plusDEscale = (escale[0] == -1 && escale[1] == -1 && escale[2] == -1);

		do {
			tuileCheminCourant = file.remove();
			Point pCourant = tuileCheminCourant.getPosition();
			Tuile t = plateau[pCourant.x][pCourant.y];

			if (!seens.contain(tuileCheminCourant)) {
				seens.add(tuileCheminCourant);
				
				if (t != null) {
					if (t.getTypeTuile() == 1 && pCourant.equals(depart)) {
						if (t.connectionsExistantes(Constantes.Orientation.est)) {
							po.x = pCourant.x+1; po.y = pCourant.y;
							file.add(new TuileChemin(po,Constantes.Orientation.ouest,tuileCheminCourant.getPriority()+1,0, tuileCheminCourant));
						}
						if (t.connectionsExistantes(Constantes.Orientation.ouest)) {
							po.x = pCourant.x-1; po.y = pCourant.y;
							file.add(new TuileChemin(po,Constantes.Orientation.est,tuileCheminCourant.getPriority()+1,0, tuileCheminCourant));
						}
						if (t.connectionsExistantes(Constantes.Orientation.sud)) {
							po.x = pCourant.x; po.y = pCourant.y+1;
							file.add(new TuileChemin(po,Constantes.Orientation.nord,tuileCheminCourant.getPriority()+1,0, tuileCheminCourant));
						}
						if (t.connectionsExistantes(Constantes.Orientation.nord)) {
							po.x = pCourant.x; po.y = pCourant.y-1;
							file.add(new TuileChemin(po,Constantes.Orientation.sud,tuileCheminCourant.getPriority()+1,0, tuileCheminCourant));
						}
					}
					else {
						ArrayList<String> listeDirectionPossible = t.getDirectionConnectedTo(tuileCheminCourant.getDirection());
						if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.est)) {
							po.x = pCourant.x+1; po.y = pCourant.y;
							if (listeDirectionPossible.contains(Constantes.Orientation.est))
								file.add(new TuileChemin(po,Constantes.Orientation.ouest,tuileCheminCourant.getPriority()+1,0, tuileCheminCourant));
						}
						if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.ouest)) {
							po.x = pCourant.x-1; po.y = pCourant.y;
							if (listeDirectionPossible.contains(Constantes.Orientation.ouest))
								file.add(new TuileChemin(po,Constantes.Orientation.est,tuileCheminCourant.getPriority()+1,0, tuileCheminCourant));
						}
						if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.sud)) {
							po.x = pCourant.x; po.y = pCourant.y+1;
							if (listeDirectionPossible.contains(Constantes.Orientation.sud))
								file.add(new TuileChemin(po,Constantes.Orientation.nord,tuileCheminCourant.getPriority()+1,0, tuileCheminCourant));
						}
						if (!tuileCheminCourant.getDirection().equals(Constantes.Orientation.nord)) {
							po.x = pCourant.x; po.y = pCourant.y-1;
							if (listeDirectionPossible.contains(Constantes.Orientation.nord))
								file.add(new TuileChemin(po,Constantes.Orientation.sud,tuileCheminCourant.getPriority()+1,0, tuileCheminCourant));
						}
					}
					
					if (t.getEscaleLiee() != 0 && !plusDEscale) {
						for (int i = 0; i < 3; i++) {
							if (escale[i] == t.getEscaleLiee()) {
								escale[i] = -1;
								found = atteindreObjectifDepuisPosition(pCourant, tuileCheminCourant.getDirection(), arrive, escale);
								escale[i] = t.getEscaleLiee();
							}
						}
					}
					
					if (pCourant.equals(arrive) && plusDEscale)
						found = true;
				}
				
			}
		} while(!found && !file.isEmpty());

		return found;
	}

}
