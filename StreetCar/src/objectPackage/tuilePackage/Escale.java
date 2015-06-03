package objectPackage.tuilePackage;

import java.awt.Point;


public class Escale extends Tuile{
	int numeroEscale;
	Point stop;
	
	public Escale (){
		super(true);
	}
	
	public Escale (int newNumero){
		super(true);
		numeroEscale = newNumero;
		typeTuile = 2;
	}
	
	public void setStop(Point p) {
		stop = p;
	}
	
}
