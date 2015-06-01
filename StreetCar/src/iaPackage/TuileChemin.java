package iaPackage;

import java.awt.Point;

class TuileChemin {

	private Point position;
	private String direction;
	private int priority;
	private int heuristique;
	private TuileChemin previous;
	
	public TuileChemin(Point po, String d, int pri, int h, TuileChemin pre) {
		position = po;
		direction = d;
		priority = pri;
		heuristique = h;
		previous = pre;
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
	
	TuileChemin getPrevious() {
		return previous;
	}
}
