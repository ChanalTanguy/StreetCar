package iaPackage;

import java.awt.Point;

class TuileChemin {

	private Point position;
	private String direction;
	private int priority;
	private int heuristique;
	
	public TuileChemin(Point po, String d, int pr, int h) {
		position = po;
		direction = d;
		priority = pr;
		heuristique = h;
	}
	
	Point getPosition() {
		return position;
	}
	
	String getDirection() {
		return direction;
	}
	
	int getPriority() {
		return priority;
	}
	
	int getHeuristique() {
		return heuristique;
	}
}
