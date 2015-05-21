package mainPackage;

import java.awt.Dimension;
import java.awt.Point;

import joueurPackage.Coup;
import tuilePackage.*;
import constantesPackages.Constantes;

public class Plateau {
	private Tuile[][] plateau;

	public Plateau (){
		plateau = new Tuile[Constantes.Dimensions.dimensionPlateau][Constantes.Dimensions.dimensionPlateau];
		initialisationTuile();
	}

	public void initialisationTuile() {
		// Mettre toutes les tuiles de bords (qui ne sont pas des terminaux) à la vertical
		for (int i = 0; i<Constantes.Dimensions.dimensionPlateau;i++) {
			if (i == 2 || i == 3 || i == 6 || i == 7 || i == 10 || i == 11 ) {
				setTuileAt(0, i, new Tuile(true));
				setTuileAt(Constantes.Dimensions.dimensionPlateau-1, i, new Tuile(true));
			}
		}
		// à l'horizontal
		for (int i = 1; i<Constantes.Dimensions.dimensionPlateau-1;i++) {
			if (i == 2 || i == 3 || i == 6 || i == 7 || i == 10 || i == 11 ) {
				setTuileAt(i, 0, new Tuile(true));
				setTuileAt(i, Constantes.Dimensions.dimensionPlateau-1, new Tuile(true));
			}
		}

		// Mettre les escales
		setTuileAt( 1,  5, new Escale( 1));
		setTuileAt( 2,  9, new Escale( 2));
		setTuileAt( 4,  2, new Escale( 3));
		setTuileAt( 4,  7, new Escale( 4));
		setTuileAt( 5, 12, new Escale( 5));
		setTuileAt( 6,  4, new Escale( 6));
		setTuileAt( 7,  9, new Escale( 7));
		setTuileAt( 8,  1, new Escale( 8));
		setTuileAt( 9,  6, new Escale( 9));
		setTuileAt( 9, 11, new Escale(10));
		setTuileAt(11,  4, new Escale(11));
		setTuileAt(12,  8, new Escale(12));

		// Mettre les Terminus
		Tuile t;
		// /!\ DEAD ZONE ! Le code suivant est extremement moche, 
		// et sera potentiellement soumis à un REFACTOR OF DOOM
		
		// Terminus du Nord
		for(int i = 0; i<3;i++) {
			// Tuile gauche
			t = new Terminus(i+1,1); t.addConnection(new Connection(Constantes.Orientation.est,Constantes.Orientation.sud));
			setTuileAt(2+4*i, 0, t);
			// Tuile droite
			t = new Terminus(i+1,1); t.addConnection(new Connection(Constantes.Orientation.ouest,Constantes.Orientation.sud));
			setTuileAt(3+4*i, 0, t);
		}

		// Terminus du Sud
		for(int i = 0; i<3;i++) {
			// Tuile gauche
			t = new Terminus(i+1,2); t.addConnection(new Connection(Constantes.Orientation.est,Constantes.Orientation.nord));
			setTuileAt(10-4*i, 13, t);
			// Tuile droite
			t = new Terminus(i+1,2); t.addConnection(new Connection(Constantes.Orientation.ouest,Constantes.Orientation.nord));
			setTuileAt(11-4*i, 13, t);
		}
		
		// Terminus de l'est
		for(int i = 0; i<3;i++) {
			// Tuile haute
			t = new Terminus(i+4,1); t.addConnection(new Connection(Constantes.Orientation.sud,Constantes.Orientation.ouest));
			setTuileAt(0, 2+4*i, t);
			// Tuile basse
			t = new Terminus(i+4,1); t.addConnection(new Connection(Constantes.Orientation.nord,Constantes.Orientation.ouest));
			setTuileAt(0, 3+4*i, t);
		}

		// Terminus de l'ouest
		for(int i = 0; i<3;i++) {
			// Tuile haute
			t = new Terminus(i+4,2); t.addConnection(new Connection(Constantes.Orientation.sud,Constantes.Orientation.est));
			setTuileAt(13, 10-4*i, t);
			// Tuile basse
			t = new Terminus(i+4,2); t.addConnection(new Connection(Constantes.Orientation.nord,Constantes.Orientation.est));
			setTuileAt(13, 11-4*i, t);
		}
	}
	
	public Tuile[][] getPlateau (){
		return plateau;
	}

	public boolean coupValide (Coup coup){
		if(coup.getType().equals(Constantes.Coup.placement)) {
			// Vérifier que la case est sois vide, sois possède une sous version de la tuile qu'on veut poser
			// Et que les 4 cases adjacente ne font pas conflit
			boolean valide = false;

			Point coord = coup.getCoordonnee();
			int x = coord.x;
			int y = coord.y;
			Tuile nouvTuile = getTuileAt(x,y);

			
			
			valide = (getTuileAt(x,y+1) == null || getTuileAt(x,y+1).canConnectTo(nouvTuile, Constantes.Orientation.est))
				  && (getTuileAt(x,y-1) == null || getTuileAt(x,y-1).canConnectTo(nouvTuile, Constantes.Orientation.ouest))
				  && (getTuileAt(x+1,y) == null || getTuileAt(x+1,y).canConnectTo(nouvTuile, Constantes.Orientation.nord))
				  && (getTuileAt(x-1,y) == null || getTuileAt(x-1,y).canConnectTo(nouvTuile, Constantes.Orientation.sud));

			return valide;
		} else {
			return true; // Un coup qui n'a rien a voir avec le plateau est forcément valide au yeux du plateau (Duh)
		}
	}

	public Tuile getTuileAt(int x, int y) {
		return plateau[x][y];
	}

	public void setTuileAt(int x, int y, Tuile t) {
		plateau[x][y] = t;
	}
	
	public Point getTerminalPosition(int numberLigne, int numberTerminal) {
		Point p;
		switch (numberLigne) {
		case 1 : 
			if (numberTerminal == 1)
				p = new Point(2,0);
			else
				p = new Point(10,13);
			break;
		case 2 : 
			if (numberTerminal == 1)
				p = new Point(6,0);
			else
				p = new Point(6,13);
			break;
		case 3 : 
			if (numberTerminal == 1)
				p = new Point(10,0);
			else
				p = new Point(2,13);
			break;
		case 4 : 
			if (numberTerminal == 1)
				p = new Point(13,2);
			else
				p = new Point(0,10);
			break;
		case 5 : 
			if (numberTerminal == 1)
				p = new Point(13,6);
			else
				p = new Point(0,6);
			break;
		case 6 : 
			if (numberTerminal == 1)
				p = new Point(13,10);
			else
				p = new Point(0,2);
			break;
		default :
			p = null;
		}
		return p;
	}
	

	public Plateau clone() {
		Plateau p = new Plateau();
		for (int i = 0; i < Constantes.Dimensions.dimensionPlateau; i++) {
			for (int j = 0; j < Constantes.Dimensions.dimensionPlateau; j++) {
				p.setTuileAt(i, j, getTuileAt(i,j).clone());
			}
		}
		return p;
	}

}
