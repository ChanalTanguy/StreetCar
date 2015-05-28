package iaPackage;

import java.awt.Point;

class TuileChemin {

	Point position;
	String direction;
	int priority;
	public int heuristique;
	
	public TuileChemin(Point po, String d, int pr, int h) {
		position = po;
		direction = d;
		priority = pr;
		heuristique = h;
	}
	
}
