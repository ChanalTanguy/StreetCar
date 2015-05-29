package tuilePackage;


public class Terminus extends Tuile{
	int numeroLigne;
	int numeroTerminus;
	
	public Terminus (){
		super(true);
	}
	
	public Terminus (int newLigne, int newTerminus){
		super(true);
		numeroLigne = newLigne;
		numeroTerminus = newTerminus;
		typeTuile = 1;
	}
	

}
