package tuilePackage;

import constantesPackages.Constantes;

public class Connection {
	private String pointA, pointB;
	
	public Connection (String a, String b){
		pointA = a;
		pointB = b;
	}
	
	public String getPointA (){
		return pointA;
	}
	
	public String getPointB (){
		return pointB;
	}
	
	public void setPointA (String newPointA){
		pointA = newPointA;
	}
	
	public void setPointB (String newPointB){
		pointB = newPointB;
	}
	
	public void rotationGauche (){
		switch (pointA){
		case Constantes.Orientation.nord:
			pointA = Constantes.Orientation.ouest;
			break;
		case Constantes.Orientation.est:
			pointA = Constantes.Orientation.nord;
			break;
		case Constantes.Orientation.sud:
			pointA = Constantes.Orientation.est;
			break;
		case Constantes.Orientation.ouest:
			pointA = Constantes.Orientation.sud;
			break;
		default:
			throw new RuntimeException("probleme de rotation vers la gauche");
		}
		switch (pointB){
		case Constantes.Orientation.nord:
			pointB = Constantes.Orientation.ouest;
			break;
		case Constantes.Orientation.est:
			pointB = Constantes.Orientation.nord;
			break;
		case Constantes.Orientation.sud:
			pointB = Constantes.Orientation.est;
			break;
		case Constantes.Orientation.ouest:
			pointB = Constantes.Orientation.sud;
			break;
		default:
			throw new RuntimeException("probleme de rotation vers la gauche");
		}
	}
	
	public void rotationDroite (){
		switch (pointA){
		case Constantes.Orientation.nord:
			pointA = Constantes.Orientation.est;
			break;
		case Constantes.Orientation.est:
			pointA = Constantes.Orientation.sud;
			break;
		case Constantes.Orientation.sud:
			pointA = Constantes.Orientation.ouest;
			break;
		case Constantes.Orientation.ouest:
			pointA = Constantes.Orientation.nord;
			break;
		default:
			throw new RuntimeException("probleme de rotation vers la gauche");
		}
		switch (pointB){
		case Constantes.Orientation.nord:
			pointB = Constantes.Orientation.est;
			break;
		case Constantes.Orientation.est:
			pointB = Constantes.Orientation.sud;
			break;
		case Constantes.Orientation.sud:
			pointB = Constantes.Orientation.ouest;
			break;
		case Constantes.Orientation.ouest:
			pointB = Constantes.Orientation.nord;
			break;
		default:
			throw new RuntimeException("probleme de rotation vers la gauche");
		}
	}
	
	// retourne vrai si connection_appelante.pointA == otherConnection.pointB
	//				ou  connection_appelante.pointB == otherConnection.pointA
	public boolean isConnectedTo (Connection otherConnection){
		boolean resultat = ( pointA == otherConnection.getPointA() 
						  || pointA == otherConnection.getPointB()
						  || pointB == otherConnection.getPointA() 
						  || pointB == otherConnection.getPointB() );
		
		return resultat;
	}
	
	/**
	 * retourne vrai si l'une des extremites de la connection est sur le cote indique
	 * @param cote : chaine de caracteres indiquant quel cote devra toucher la connection
	 * @return vrai : si le pointA ou le pointB sont sur le cote; faux si aucun des 2 ne s'y trouve.
	 */
	public boolean isConnectedTo (String cote){
		boolean extremiteTrouvee;
		
		extremiteTrouvee = ( pointA.equals(cote) || pointB.equals(cote) );
		
		return extremiteTrouvee;
	}
	
	public String toString (){
		String resultat = "";
		resultat = resultat + "( " + pointA + "; " + pointB + ")";
		return resultat;
	}
	
	public boolean equals (Connection autreConnection){
		return ( pointA.equals(autreConnection.getPointA()) && pointB.equals(autreConnection.getPointB()) || 
				 pointA.equals(autreConnection.getPointB()) && pointB.equals(autreConnection.getPointA()) );
	}
	
	public Connection clone (){
		return new Connection(pointA, pointB);
	}
}
