package objectPackage.tuilePackage;

import java.awt.Point;
import java.util.ArrayList;


public class Escale extends Tuile{
	private int numeroEscale;
	private Point stop;
	
	public Escale (){
		super(true);
	}
	
	public Escale (int newNumero){
		super(true);
		setNumeroEscale(newNumero);
		setType(2);
	}

	public int getNumeroEscale() {
		return numeroEscale;
	}

	public void setNumeroEscale(int numeroEscale) {
		this.numeroEscale = numeroEscale;
	}
	
	public Point getStop() {
		return stop;
	}
	
	public void setStop(Point p) {
		stop = p;
	}
	
	public Escale clone() {
		Escale e = new Escale();
		Tuile t = super.clone();
		e.setListeConnections((ArrayList<Connection>) t.getListeConnections().clone());
		e.setPresenceArbres(t.getPresenceArbres());
		e.setOrientation(t.getOrientation());
		e.setEscaleLiee(0);
		e.setImage(null);
		e.setType(2);
		e.numeroEscale = numeroEscale;
		if (stop != null) e.stop = (Point) stop.clone();
		return e;
	}
	
}
