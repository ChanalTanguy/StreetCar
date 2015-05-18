package tuilePackage;

import java.util.ArrayList;
import java.util.ListIterator;

import mainPackage.ActionsToken;
import constantesPackages.Constantes;

public class Tuile implements ActionsToken{
	private boolean immuable;
	private String orientation;
	private ArrayList<Connection> listeConnections;
	
	/*
	 * 2 Constructeurs
	 */
	public Tuile (){
		immuable = false;
		orientation = Constantes.Orientation.nord;
		listeConnections = new ArrayList<Connection>();
	}
	
	public Tuile (boolean presenceArbres){
		immuable = presenceArbres;
		orientation = Constantes.Orientation.nord;
		listeConnections = new ArrayList<Connection>();
	}
	
	/*
	 * les 6 Accesseurs
	 */
	public boolean getPresenceArbres (){
		return immuable;
	}
	
	public ArrayList<Connection> getListeConnections (){
		return listeConnections;
	}
	
	public String getOrientation (){
		return orientation;
	}
	
	public void setPresenceArbres (boolean newPresence){
		immuable = newPresence;
	}
	
	public void setListeConnections (ArrayList<Connection> newListe){
		if ( listeConnections.isEmpty() ){
			listeConnections.clear();
		}
		if ( listeConnections.addAll(newListe) ){
			System.out.println("update de la liste ok");
		}
		else {
			System.out.println("update refuse/annule");
		}
	}
	
	public void setOrientation (String newOrientation){
		orientation = new String(newOrientation);
	}
	
	/*
	 * Methodes de l'objet
	 */
	
	public void addConnection (Connection nouvelleConnection){
		if ( listeConnections.add(nouvelleConnection) ){
			System.out.println("ajout ok");
		}
		else { System.out.println("ajout annule"); }
	}
	
	/**
	 * A FAIRE/FINIR
	 * @param fixedTuile
	 * @return
	 */
	
	// retourne vrai si la tuile_appelante peut etre posee a cote de "fixedTuile"
	// => on verifie alors si l'une des connections de la tuile appelante est connectee a
	//	  l'une des connections de "fixedTuile" est connectee a la tuile_appelante
	public boolean isAdjacent (Tuile fixedTuile){
		boolean connectionTrouvee = false;
		
		
		
		return connectionTrouvee;
	}
	
	public void rotation(String sensRotation) {
		switch (sensRotation){
		case Constantes.Rotation.rotationDroite:
			effectuerRotationDroite();
			break;
		case Constantes.Rotation.rotationGauche:
			effectuerRotationGauche();
			break;
		default:
			break;
		}
	}
	
	/* methode utile ??? <=> on lui fournit un objet (ici une tuile)
	 * 						et on remplace l'appelant par cet objet
	 * 
	 * /!\ l'operation inverse est possible /!\
	 * 
	 */
	public void swap (Object token){
		Tuile tampon = (Tuile) token;
		this.setPresenceArbres(tampon.getPresenceArbres());
		this.setListeConnections(tampon.getListeConnections());
	}
	
	public String toString (){
		String resultat = "";
		
		return resultat;
	}
	
	public boolean equals (Tuile autreTuile){
		boolean resultat = false;
		
		resultat = ( immuable == autreTuile.getPresenceArbres() && listesIdentiques(autreTuile.getListeConnections()) );
		
		return resultat;
	}
	
	public Tuile clone (){
		Tuile newTuile = new Tuile(immuable);
		
		newTuile.setListeConnections(listeConnections);
		
		return newTuile;
	}
	
	
	/*
	 * Methodes privees de la classe
	 */
	
	/**
	 * retourne vrai si la liste appelante et celle en parametre sont identiques
	 */
	private boolean listesIdentiques (ArrayList<Connection> autreListe){
		boolean resultat;
		
		resultat = (listeConnections.size() == autreListe.size()) ? listeConnections.containsAll(autreListe) : false;
		
		return resultat;
	}
	
	/**
	 * modifie l'orientation de la tuile appelante d'un cran dans le sens horaire
	 */
	private void effectuerRotationDroite (){
		switch (orientation){
		case Constantes.Orientation.nord:
			orientation = Constantes.Orientation.est;
			break;
		case Constantes.Orientation.sud:
			orientation = Constantes.Orientation.ouest;
			break;
		case Constantes.Orientation.est:
			orientation = Constantes.Orientation.sud;
			break;
		case Constantes.Orientation.ouest:
			orientation = Constantes.Orientation.nord;
			break;
		default:
			break;
		}
		rotationDroiteConnections();
	}
	
	/**
	 * modifie l'orientation de la tuile appelante d'un cran dans le sens anti-horaire
	 */
	private void effectuerRotationGauche (){
		switch (orientation){
		case Constantes.Orientation.nord:
			orientation = Constantes.Orientation.ouest;
			break;
		case Constantes.Orientation.sud:
			orientation = Constantes.Orientation.est;
			break;
		case Constantes.Orientation.est:
			orientation = Constantes.Orientation.nord;
			break;
		case Constantes.Orientation.ouest:
			orientation = Constantes.Orientation.sud;
			break;
		default:
			break;
		}
		rotationGaucheConnections();
	}
	
	/**
	 * tourne toutes les connections de la tuile appelante d'un cran dans le sens horaire 
	 */
	private void rotationDroiteConnections (){
		ListIterator<Connection> iterateurConnections = listeConnections.listIterator();
		while ( iterateurConnections.hasNext() ){
			iterateurConnections.next().rotationDroite();
		}
	}
	
	/**
	 * tourne toutes les connections de la tuile appelante d'un cran dans le sens anti-horaire
	 */
	private void rotationGaucheConnections (){
		ListIterator<Connection> iterateurConnections = listeConnections.listIterator();
		while ( iterateurConnections.hasNext() ){
			iterateurConnections.next().rotationGauche();
		}
	}
	
}
