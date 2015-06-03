package iaPackage;

import java.awt.Point;

public class TuileChemin {

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
	
	public Point getPosition() {
		return new Point(x,y);
	}
	
	public String getDirection() {
		return direction;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public int getHeuristique() {
		return heuristique;
	}
	
	public TuileChemin getPrevious() {
		return previous;
	}
	
	public static int compare(TuileChemin o1, TuileChemin o2) {
		return (o1.getPriority()+o1.getHeuristique())-(o2.getPriority()+o2.getHeuristique());
	}
}
