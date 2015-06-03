package objectPackage;

import java.awt.Point;

import joueurPackage.Coup;
import objectPackage.tuilePackage.Connection;
import objectPackage.tuilePackage.Escale;
import objectPackage.tuilePackage.Terminus;
import objectPackage.tuilePackage.Tuile;
import constantesPackages.Constantes;

public class Plateau {
	private Tuile[][] plateau;

	private static final int[][] PositionEscale = {  { 1, 5},
												     { 2, 9},
												     { 4, 2},
												     { 4, 7},
												     { 5,12},
												     { 6, 4},
												     { 7, 9},
												     { 8, 1},
												     { 9, 6},
												     { 9,11},
												     {11, 4},
												     {12, 8}
	};
	
	public Plateau (){
		plateau = new Tuile[Constantes.Dimensions.dimensionPlateau][Constantes.Dimensions.dimensionPlateau];
		initialisationTuile();
	}

	public void initialisationTuile() {
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
	
	public Tuile[][] getPlateau (){
		return plateau;
	}

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
	
	public String coupSimultaneValide(Tuile nouvTuile, Coup coup) {
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

	public Tuile getTuileAt(int x, int y) {
		return plateau[x][y];
	}

	public void setTuileAt(int x, int y, Tuile t) {
		plateau[x][y] = t;
		Tuile tAdjacente;
		for (int i = 0; i < 4; i++) {
			
			tAdjacente = null;
			switch (i) {
			case 0 : if (x != Constantes.Dimensions.dimensionPlateau-1) tAdjacente = plateau[x+1][y]; break;
			case 1 : if (x != 0) 										tAdjacente = plateau[x-1][y]; break;
			case 2 : if (y != Constantes.Dimensions.dimensionPlateau-1) tAdjacente = plateau[x][y+1]; break;
			default : if (y != 0) 										tAdjacente = plateau[x][y-1]; break;
			}
			if (tAdjacente != null && tAdjacente.getTypeTuile() == 2 && t.getTypeTuile() != 3) {
				Escale e = (Escale) tAdjacente;
				if (e.getStop() == null) {
					e.setStop(new Point(x,y));
					t.setEscale(e.getNumeroEscale());
				}
			}
			
		}
	}
	
	public Point getTerminalPosition(int numberLigne, int numberTerminal) {
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
	
	public Point getEscalelPosition(int numberEscale) {
		
		try {
			return new Point(PositionEscale[numberEscale-1][0],PositionEscale[numberEscale-1][1]);
		} catch (Exception e) {
			return null;
		}
	}

	public Plateau clone() {
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

}
