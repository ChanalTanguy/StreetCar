package iaPackage;

import java.awt.Point;

class TuileChemin {

	private int x;
	private int y;
	private String direction;
	private int priority;
	private int heuristique;
	private TuileChemin previous;
	
	public TuileChemin(Point po, String d, int pri, int h, TuileChemin pre) {
		x = po.x;
		y = po.y;
		direction = d;
		priority = pri;
		heuristique = h;
		previous = pre;
	}
	
	Point getPosition() {
		return new Point(x,y);
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
	
	static int compare(TuileChemin o1, TuileChemin o2) {
		return (o1.getPriority()+o1.getHeuristique())-(o2.getPriority()+o2.getHeuristique());
	}
}
