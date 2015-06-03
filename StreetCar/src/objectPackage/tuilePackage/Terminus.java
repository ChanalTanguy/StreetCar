package objectPackage.tuilePackage;


public class Terminus extends Tuile{
	private int numeroLigne;
	private int numeroTerminus;
	
	public Terminus (){
		super(true);
	}
	
	public Terminus (int newLigne, int newTerminus){
		super(true);
		setNumeroLigne(newLigne);
		setNumeroTerminus(newTerminus);
		setType(1);
	}

	public int getNumeroLigne() {
		return numeroLigne;
	}

	public void setNumeroLigne(int numeroLigne) {
		this.numeroLigne = numeroLigne;
	}

	public int getNumeroTerminus() {
		return numeroTerminus;
	}

	public void setNumeroTerminus(int numeroTerminus) {
		this.numeroTerminus = numeroTerminus;
	}
	

}
