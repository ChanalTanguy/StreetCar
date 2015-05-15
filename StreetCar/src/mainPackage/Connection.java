package mainPackage;

import constantesPackages.Constantes;

public class Connection {
	private int pointA, pointB;
	
	public Connection (int a, int b){
		pointA = a;
		pointB = b;
	}
	
	public int getPointA (){
		return pointA;
	}
	
	public int getPointB (){
		return pointB;
	}
	
	public void setPointA (int newPointA){
		pointA = newPointA;
	}
	
	public void setPointB (int newPointB){
		pointB = newPointB;
	}
	
	public void rotationGauche (){
		pointA = (pointA%4 == Constantes.pointGauche) ? Constantes.pointBas : (pointA%4) - 1;
		pointB = (pointB%4 == Constantes.pointGauche) ? Constantes.pointBas : (pointB%4) - 1;
	}
	
	public void rotationDroite (){
		pointA = (pointA%4) + 1;
		pointB = (pointB%4) + 1;
	}
	
	// retourne vrai si connection_appelante.pointA == otherConnection.pointB
	//				ou  connection_appelante.pointB == otherConnection.pointA
	public boolean isConnected (Connection otherConnection){
		boolean resultat = ( pointA == otherConnection.getPointB() 
						  || pointB == otherConnection.getPointA() );
		
		return resultat;
	}
	
	public String toString (){
		String resultat = "";
		resultat = resultat + "( " + pointA + "; " + pointB + ")";
		return resultat;
	}
	
	public boolean equals (Connection autreConnection){
		return ( pointA == autreConnection.getPointA() && pointB == autreConnection.getPointB() );
	}
	
	public Connection clone (){
		return new Connection(pointA, pointB);
	}
}
