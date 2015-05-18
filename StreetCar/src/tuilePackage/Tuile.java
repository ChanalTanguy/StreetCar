package tuilePackage;

import java.util.ArrayList;
import java.util.ListIterator;

import mainPackage.ActionsToken;
import constantesPackages.Constantes;

public class Tuile implements ActionsToken{
	private boolean immuable;
	private ArrayList<Connection> listeConnections;
	
	/*
	 * 2 Constructeurs
	 */
	public Tuile (){
		immuable = false;
		listeConnections = new ArrayList<Connection>();
	}
	
	public Tuile (boolean presenceArbres){
		immuable = presenceArbres;
		listeConnections = new ArrayList<Connection>();
	}
	
	/*
	 * les 4 Accesseurs
	 */
	public boolean getPresenceArbres (){
		return immuable;
	}
	
	public ArrayList<Connection> getListeConnections (){
		return listeConnections;
	}
	
	public void setPresenceArbres (boolean newPresence){
		immuable = newPresence;
	}
	
	public void setListeConnections (ArrayList<Connection> newListe){
	/*		effectuer un test si la liste de l'objet appelant est vide ?
	 * =>	if ( listeConnections.isEmpty() ){
	 * 			listeConnections.clear();
	 * 		}
	 */
		if ( listeConnections.addAll(newListe) ){
			System.out.println("update de la liste ok");
		}
		else {
			System.out.println("update refuse/annule");
		}
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
			
			break;
		case Constantes.Rotation.rotationGauche:
			
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
	
	private boolean listesIdentiques (ArrayList<Connection> autreListe){
		boolean resultat;
		
		resultat = (listeConnections.size() == autreListe.size()) ? listeConnections.containsAll(autreListe) : false;
		
		return resultat;
	}

	
}
