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
	 * A FAIRE/FINIR <== EST FINI
	 * @param fixedTuile
	 * @return
	 */
	
	// retourne vrai si la tuile_appelante peut etre posee a cote de "fixedTuile"
	// => on verifie alors si l'une des connections de la tuile appelante est connectee a
	//	  l'une des connections de "fixedTuile" est connectee a la tuile_appelante
	public boolean isAdjacent (Tuile fixedTuile, String cote){
		boolean connectionTrouvee = false;
		switch (cote){
		case Constantes.Orientation.nord:
/*			if ( this.connectionsExistantes(Constantes.Orientation.sud) && fixedTuile.connectionsExistantes(Constantes.Orientation.nord) ){
				connectionTrouvee = true;
			}
*/			
			connectionTrouvee = ( this.connectionsExistantes(Constantes.Orientation.sud) && fixedTuile.connectionsExistantes(Constantes.Orientation.nord) );
			
			break;
		case Constantes.Orientation.sud:
/*			if ( this.connectionsExistantes(Constantes.Orientation.nord) && fixedTuile.connectionsExistantes(Constantes.Orientation.sud) ){
				connectionTrouvee = true;
			}
*/			
			connectionTrouvee = ( this.connectionsExistantes(Constantes.Orientation.nord) && fixedTuile.connectionsExistantes(Constantes.Orientation.sud) );
			
			break;
		case Constantes.Orientation.est:
/*			if ( this.connectionsExistantes(Constantes.Orientation.ouest) && fixedTuile.connectionsExistantes(Constantes.Orientation.est) ){
				connectionTrouvee = true;
			}
*/			
			connectionTrouvee = ( this.connectionsExistantes(Constantes.Orientation.ouest) && fixedTuile.connectionsExistantes(Constantes.Orientation.est) );
			
			break;
		case Constantes.Orientation.ouest:
/*			if ( this.connectionsExistantes(Constantes.Orientation.est) && fixedTuile.connectionsExistantes(Constantes.Orientation.ouest) ){
				connectionTrouvee = true;
			}
*/			
			connectionTrouvee = ( this.connectionsExistantes(Constantes.Orientation.est) && fixedTuile.connectionsExistantes(Constantes.Orientation.ouest) );
			
			break;
		default:
			throw new RuntimeException("cote non indique pour adjacence des Tuiles");
		}
		
		
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
		
		resultat = resultat + orientation + "\n";
		resultat = resultat + listeConnections.toString();
		
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
	 * @param autreListe : liste de connections qui sera comparee avec la liste de la tuile appelante
	 * @return true : les 2 listes sont identiques
	 */
	/* /!\ ATTENTION /!\ CORRIGEE MAIS A REVERIFIER
	 * sous quelles conditions 2 tuiles sont elles identiques ???
	 * 		=> sous quelles conditions 2 listes de connections sont identiques ???
	 * 
	 * 
	 */
	private boolean listesIdentiques (ArrayList<Connection> autreListe){
		boolean estContenu = false;
		
		ListIterator<Connection> iterateurConnections = listeConnections.listIterator();
		while ( iterateurConnections.hasNext() && !estContenu ){
			ListIterator<Connection> iterateurAutresConnections = autreListe.listIterator();
			Connection connection1 = iterateurConnections.next();
			while ( iterateurAutresConnections.hasNext() && !estContenu){
				estContenu = estContenu || connection1.equals(iterateurAutresConnections.next());
			}
			if (estContenu){
				System.out.println("connection " + connection1.toString() + " ou une identique se trouve dans la liste donnee en parametre");
				estContenu = false;
			}
			else {
				System.out.println("aucune connection identique a " + connection1.toString() + " dans la liste donnee en parametre");
			}
		}
		
		return estContenu;
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
	
	/**
	 * retourne vrai si la tuile appelante possede au moins une connexion avec l'une de ses extremites
	 * sur le cote indique
	 * retourne faux sinon
	 * @param cote : chaine de caracteres indiquant sur quel cote doit etre chercher l'extremite des connections
	 * @return true : une connection possede une extremite est sur le cote indique, false sinon.
	 */
	private boolean connectionsExistantes (String cote){
		boolean connectionTrouvee = false;
		ListIterator<Connection> iterateurConnections = listeConnections.listIterator();
		
		switch (cote){
		case Constantes.Orientation.nord:
			while ( iterateurConnections.hasNext() && !connectionTrouvee){
				connectionTrouvee = iterateurConnections.next().isConnectedTo(Constantes.Orientation.nord);
			}
			break;
		case Constantes.Orientation.sud:
			while ( iterateurConnections.hasNext() && !connectionTrouvee){
				connectionTrouvee = iterateurConnections.next().isConnectedTo(Constantes.Orientation.sud);
			}
			break;
		case Constantes.Orientation.est:
			while ( iterateurConnections.hasNext() && !connectionTrouvee){
				connectionTrouvee = iterateurConnections.next().isConnectedTo(Constantes.Orientation.est);
			}
			break;
		case Constantes.Orientation.ouest:
			while ( iterateurConnections.hasNext() && !connectionTrouvee){
				connectionTrouvee = iterateurConnections.next().isConnectedTo(Constantes.Orientation.ouest);
			}
			break;
		default:
			throw new RuntimeException("cote non indique pour adjacence des connections");
		}
		
		return connectionTrouvee;
	}
}
