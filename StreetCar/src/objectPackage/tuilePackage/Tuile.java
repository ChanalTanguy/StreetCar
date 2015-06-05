 package objectPackage.tuilePackage;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.ListIterator;

import constantesPackages.Constantes;

public class Tuile {
	private boolean immuable;
	private String orientation;
	private ArrayList<Connection> listeConnections;
	private BufferedImage imageTuile;
	private int typeTuile; // 0 : classique, 1 : terminus, 2 : Escale, 3 : Bord
	private int escaleLiee;
	
	/*
	 * 2 Constructeurs
	 */
	public Tuile (){
		immuable = false;
		orientation = Constantes.Orientation.nord;
		listeConnections = new ArrayList<Connection>();
		typeTuile = 0;
		escaleLiee = 0;
	}
	public Tuile (boolean presenceArbres){
		immuable = presenceArbres;
		orientation = Constantes.Orientation.nord;
		listeConnections = new ArrayList<Connection>();
		typeTuile = 0;
		escaleLiee = 0;
	}
	
	/*
	 * 12 Constructeur de tuile fixes
	 */
	public static Tuile newLigneDroite() {
		Tuile t = new Tuile(false);
		t.addConnection(new Connection(Constantes.Orientation.nord, Constantes.Orientation.sud));
		t.setImage(Constantes.Images.initTuile("ligneDroite.jpg"));
		return t;
	}
	public static Tuile newVirage() {
		Tuile t = new Tuile(false);
		t.addConnection(new Connection(Constantes.Orientation.est, Constantes.Orientation.nord));
		t.setImage(Constantes.Images.initTuile("virage.jpg"));
		return t;
	}
	public static Tuile newBifurcationDroite() {
		Tuile t = new Tuile(false);
		t.addConnection(new Connection(Constantes.Orientation.nord, Constantes.Orientation.sud));
		t.addConnection(new Connection(Constantes.Orientation.ouest, Constantes.Orientation.nord));
		t.setImage(Constantes.Images.initTuile("bifurcationDroite.jpg"));
		return t;
	}
	public static Tuile newBifurcationGauche() {
		Tuile t = new Tuile(false);
		t.addConnection(new Connection(Constantes.Orientation.nord, Constantes.Orientation.sud));
		t.addConnection(new Connection(Constantes.Orientation.est, Constantes.Orientation.nord));
		t.setImage(Constantes.Images.initTuile("bifurcationGauche.jpg"));
		return t;
	}
	public static Tuile newSeparation() {
		Tuile t = new Tuile(false);
		t.addConnection(new Connection(Constantes.Orientation.ouest, Constantes.Orientation.nord));
		t.addConnection(new Connection(Constantes.Orientation.est, Constantes.Orientation.nord));
		t.setImage(Constantes.Images.initTuile("separation.jpg"));
		return t;
	}
	public static Tuile newDoubleVirage() {
		Tuile t = new Tuile(false);
		t.addConnection(new Connection(Constantes.Orientation.ouest, Constantes.Orientation.sud));
		t.addConnection(new Connection(Constantes.Orientation.est, Constantes.Orientation.nord));
		t.setImage(Constantes.Images.initTuile("doubleVirage.jpg"));
		return t;
	}
	public static Tuile newDoubleBifurcation() {
		Tuile t = new Tuile(true);
		t.addConnection(new Connection(Constantes.Orientation.ouest, Constantes.Orientation.sud));
		t.addConnection(new Connection(Constantes.Orientation.est, Constantes.Orientation.sud));
		t.addConnection(new Connection(Constantes.Orientation.nord, Constantes.Orientation.sud));
		t.setImage(Constantes.Images.initTuile("doubleBifurcation.jpg"));
		return t;
	}
	public static Tuile newCroisement() {
		Tuile t = new Tuile(true);
		t.addConnection(new Connection(Constantes.Orientation.ouest, Constantes.Orientation.est));
		t.addConnection(new Connection(Constantes.Orientation.nord, Constantes.Orientation.sud));
		t.setImage(Constantes.Images.initTuile("croisement.jpg"));
		return t;
	}
	public static Tuile newBifurcationsSeparesGauche() {
		Tuile t = new Tuile(true);
		t.addConnection(new Connection(Constantes.Orientation.ouest, Constantes.Orientation.sud));
		t.addConnection(new Connection(Constantes.Orientation.est, Constantes.Orientation.nord));
		t.addConnection(new Connection(Constantes.Orientation.nord, Constantes.Orientation.sud));
		t.setImage(Constantes.Images.initTuile("bifurcationSepareGauche.jpg"));
		return t;
	}
	public static Tuile newBifurcationsSeparesDroite() {
		Tuile t = new Tuile(true);
		t.addConnection(new Connection(Constantes.Orientation.est, Constantes.Orientation.sud));
		t.addConnection(new Connection(Constantes.Orientation.ouest, Constantes.Orientation.nord));
		t.addConnection(new Connection(Constantes.Orientation.nord, Constantes.Orientation.sud));
		t.setImage(Constantes.Images.initTuile("bifurcationSepareDroite.jpg"));
		return t;
	}
	public static Tuile newQuadrupleVirages() {
		Tuile t = new Tuile(true);
		t.addConnection(new Connection(Constantes.Orientation.est, Constantes.Orientation.sud));
		t.addConnection(new Connection(Constantes.Orientation.ouest, Constantes.Orientation.nord));
		t.addConnection(new Connection(Constantes.Orientation.ouest, Constantes.Orientation.sud));
		t.addConnection(new Connection(Constantes.Orientation.est, Constantes.Orientation.nord));
		t.setImage(Constantes.Images.initTuile("quadrupleVirage.jpg"));
		return t;
	}
	public static Tuile newBifurcationsEmbrassees() {
		Tuile t = new Tuile(true);
		t.addConnection(new Connection(Constantes.Orientation.est, Constantes.Orientation.nord));
		t.addConnection(new Connection(Constantes.Orientation.ouest, Constantes.Orientation.est));
		t.addConnection(new Connection(Constantes.Orientation.ouest, Constantes.Orientation.nord));
		t.setImage(Constantes.Images.initTuile("bifurcationsEmbrassees.jpg"));
		return t;
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
	public BufferedImage getImage (){
		return imageTuile;
	}
	public int getTypeTuile (){
		return typeTuile;
	}
	public int getEscaleLiee() {
		return escaleLiee;
	}
	
	public void setImage (BufferedImage newImage){
		imageTuile = newImage;
	}
	public void setPresenceArbres (boolean newPresence){
		immuable = newPresence;
	}
	public void setListeConnections (ArrayList<Connection> newListe){
		listeConnections = new ArrayList<Connection>();
		for (Connection connec : newListe) {
			listeConnections.add(connec.clone());
		}
	}
	public void setOrientation (String newOrientation){
		orientation = new String(newOrientation);
	}
	public void setType(int newTypeTuile) {
		typeTuile = newTypeTuile;
	}
	public void setEscaleLiee(int numeroEscale) {
		escaleLiee = numeroEscale;
	}
	
	/*
	 * Methodes Public de Tuile
	 */
	public void addConnection (Connection nouvelleConnection){
		listeConnections.add(nouvelleConnection);
	}
	public boolean canConnectTo (Tuile nouvTuile, String cote){
		boolean connectionTrouvee = false;
		switch (cote) {
		case Constantes.Orientation.nord:
			connectionTrouvee = ( this.connectionsExistantes(Constantes.Orientation.nord) && nouvTuile.connectionsExistantes(Constantes.Orientation.sud) )
						     || ( !this.connectionsExistantes(Constantes.Orientation.nord) && !nouvTuile.connectionsExistantes(Constantes.Orientation.sud) );
			break;
		case Constantes.Orientation.sud:
			connectionTrouvee = ( this.connectionsExistantes(Constantes.Orientation.sud) && nouvTuile.connectionsExistantes(Constantes.Orientation.nord) )
							 || ( !this.connectionsExistantes(Constantes.Orientation.sud) && !nouvTuile.connectionsExistantes(Constantes.Orientation.nord) );
			break;
		case Constantes.Orientation.est:
			connectionTrouvee = ( this.connectionsExistantes(Constantes.Orientation.est) && nouvTuile.connectionsExistantes(Constantes.Orientation.ouest) )	
							 || ( !this.connectionsExistantes(Constantes.Orientation.est) && !nouvTuile.connectionsExistantes(Constantes.Orientation.ouest) );
			break;
		case Constantes.Orientation.ouest:
			connectionTrouvee = ( this.connectionsExistantes(Constantes.Orientation.ouest) && nouvTuile.connectionsExistantes(Constantes.Orientation.est) )
							 || ( !this.connectionsExistantes(Constantes.Orientation.ouest) && !nouvTuile.connectionsExistantes(Constantes.Orientation.est) );
			break;
		default:
			throw new RuntimeException("cote non indique pour adjacence des Tuiles");
		}
		return connectionTrouvee;
	}
	public void rotation() {
		effectuerRotationDroite();
	}
	@SuppressWarnings("unchecked")
	public boolean canReplace(Tuile ancTuile) {
		boolean yesItCan = true;
		ArrayList<Connection> listeConnections = (ArrayList<Connection>) this.listeConnections.clone();
		
		for (Connection connec1 : ancTuile.getListeConnections()) {
			boolean thisOneIsHere = false;
			ListIterator<Connection> i = (ListIterator<Connection>) listeConnections.listIterator();
			Connection connec2;
			while ((i.hasNext()) && !thisOneIsHere) {
				if ((connec2 = i.next()).equals(connec1)) {
					thisOneIsHere = true;
					listeConnections.remove(connec2);
				}
			}
			yesItCan &= thisOneIsHere;
		}
		yesItCan &= !listeConnections.isEmpty();
		return yesItCan;
	}
	public ArrayList<String> getDirectionConnectedTo(String direction) {
		ArrayList<String> listDirection = new ArrayList<String>();
		
		for (Connection cconnec : listeConnections) {
			if (cconnec.getPointA().equals(direction))
				listDirection.add(cconnec.getPointB());
			if (cconnec.getPointB().equals(direction))
				listDirection.add(cconnec.getPointA());
		}
		
		return listDirection;
	}
	/**
	 * retourne vrai si la tuile appelante possede au moins une connexion avec l'une de ses extremites
	 * sur le cote indique
	 * retourne faux sinon
	 * @param cote : chaine de caracteres indiquant sur quel cote doit etre chercher l'extremite des connections
	 * @return true : une connection possede une extremite est sur le cote indique, false sinon.
	 */
	public boolean connectionsExistantes (String cote){
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
	
	/**
	 * Methode pour cloner la BufferedImage de la tuile appelante
	 * @param
	 * @return
	 */
	public BufferedImage deepCopy() {
		BufferedImage imageACopier = this.getImage();
		ColorModel cm = imageACopier.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = imageACopier.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	/**
	 * Affecte une image a une tuile creee depuis la sauvegarde d'une partie
	 */
	public void rechercheImage (){
		String orientationDeBase = this.getOrientation();
		int nombreDeRotationsAvant = 0, nombreDeRotationsApres = 0;
		switch (orientationDeBase){
		case Constantes.Orientation.est:
			nombreDeRotationsAvant = 3;
			nombreDeRotationsApres = 1;
			break;
		case Constantes.Orientation.sud:
			nombreDeRotationsAvant = 2;
			nombreDeRotationsApres = 2;
			break;
		case Constantes.Orientation.ouest:
			nombreDeRotationsAvant = 1;
			nombreDeRotationsApres = 3;
			break;
		}
		for (int compteurRotation = 0; compteurRotation < nombreDeRotationsAvant; compteurRotation++){
			this.rotation();
		}
		Tuile tuileDeComparaison;
		if ( this.equals( (tuileDeComparaison = Tuile.newLigneDroite()) ) ){
			this.setPresenceArbres(tuileDeComparaison.getPresenceArbres());
			this.setOrientation(tuileDeComparaison.getOrientation());
			this.setListeConnections(tuileDeComparaison.getListeConnections());
			this.setEscaleLiee(tuileDeComparaison.getEscaleLiee());
			this.setType(tuileDeComparaison.getTypeTuile());
			this.setImage(tuileDeComparaison.deepCopy());
		}
		else if ( this.equals( (tuileDeComparaison = Tuile.newVirage()) ) ){
			this.setPresenceArbres(tuileDeComparaison.getPresenceArbres());
			this.setOrientation(tuileDeComparaison.getOrientation());
			this.setListeConnections(tuileDeComparaison.getListeConnections());
			this.setEscaleLiee(tuileDeComparaison.getEscaleLiee());
			this.setType(tuileDeComparaison.getTypeTuile());
			this.setImage(tuileDeComparaison.deepCopy());
		}
		else if ( this.equals( (tuileDeComparaison = Tuile.newBifurcationDroite()) ) ){
			this.setPresenceArbres(tuileDeComparaison.getPresenceArbres());
			this.setOrientation(tuileDeComparaison.getOrientation());
			this.setListeConnections(tuileDeComparaison.getListeConnections());
			this.setEscaleLiee(tuileDeComparaison.getEscaleLiee());
			this.setType(tuileDeComparaison.getTypeTuile());
			this.setImage(tuileDeComparaison.deepCopy());
		}
		else if ( this.equals( (tuileDeComparaison = newBifurcationGauche()) ) ){
			this.setPresenceArbres(tuileDeComparaison.getPresenceArbres());
			this.setOrientation(tuileDeComparaison.getOrientation());
			this.setListeConnections(tuileDeComparaison.getListeConnections());
			this.setEscaleLiee(tuileDeComparaison.getEscaleLiee());
			this.setType(tuileDeComparaison.getTypeTuile());
			this.setImage(tuileDeComparaison.deepCopy());
		}
		else if ( this.equals( (tuileDeComparaison = newSeparation()) ) ){
			this.setPresenceArbres(tuileDeComparaison.getPresenceArbres());
			this.setOrientation(tuileDeComparaison.getOrientation());
			this.setListeConnections(tuileDeComparaison.getListeConnections());
			this.setEscaleLiee(tuileDeComparaison.getEscaleLiee());
			this.setType(tuileDeComparaison.getTypeTuile());
			this.setImage(tuileDeComparaison.deepCopy());
		}
		else if ( this.equals( (tuileDeComparaison = newDoubleVirage()) ) ){
			this.setPresenceArbres(tuileDeComparaison.getPresenceArbres());
			this.setOrientation(tuileDeComparaison.getOrientation());
			this.setListeConnections(tuileDeComparaison.getListeConnections());
			this.setEscaleLiee(tuileDeComparaison.getEscaleLiee());
			this.setType(tuileDeComparaison.getTypeTuile());
			this.setImage(tuileDeComparaison.deepCopy());
		}
		else if ( this.equals( (tuileDeComparaison = newDoubleBifurcation()) ) ){
			this.setPresenceArbres(tuileDeComparaison.getPresenceArbres());
			this.setOrientation(tuileDeComparaison.getOrientation());
			this.setListeConnections(tuileDeComparaison.getListeConnections());
			this.setEscaleLiee(tuileDeComparaison.getEscaleLiee());
			this.setType(tuileDeComparaison.getTypeTuile());
			this.setImage(tuileDeComparaison.deepCopy());
		}
		else if ( this.equals( (tuileDeComparaison = newCroisement()) ) ){
			this.setPresenceArbres(tuileDeComparaison.getPresenceArbres());
			this.setOrientation(tuileDeComparaison.getOrientation());
			this.setListeConnections(tuileDeComparaison.getListeConnections());
			this.setEscaleLiee(tuileDeComparaison.getEscaleLiee());
			this.setType(tuileDeComparaison.getTypeTuile());
			this.setImage(tuileDeComparaison.deepCopy());
		}
		else if ( this.equals( (tuileDeComparaison = newBifurcationsSeparesGauche()) ) ){
			this.setPresenceArbres(tuileDeComparaison.getPresenceArbres());
			this.setOrientation(tuileDeComparaison.getOrientation());
			this.setListeConnections(tuileDeComparaison.getListeConnections());
			this.setEscaleLiee(tuileDeComparaison.getEscaleLiee());
			this.setType(tuileDeComparaison.getTypeTuile());
			this.setImage(tuileDeComparaison.deepCopy());
		}
		else if ( this.equals( (tuileDeComparaison = newBifurcationsSeparesDroite()) ) ){
			this.setPresenceArbres(tuileDeComparaison.getPresenceArbres());
			this.setOrientation(tuileDeComparaison.getOrientation());
			this.setListeConnections(tuileDeComparaison.getListeConnections());
			this.setEscaleLiee(tuileDeComparaison.getEscaleLiee());
			this.setType(tuileDeComparaison.getTypeTuile());
			this.setImage(tuileDeComparaison.deepCopy());
		}
		else if ( this.equals( (tuileDeComparaison = newQuadrupleVirages()) ) ){
			this.setPresenceArbres(tuileDeComparaison.getPresenceArbres());
			this.setOrientation(tuileDeComparaison.getOrientation());
			this.setListeConnections(tuileDeComparaison.getListeConnections());
			this.setEscaleLiee(tuileDeComparaison.getEscaleLiee());
			this.setType(tuileDeComparaison.getTypeTuile());
			this.setImage(tuileDeComparaison.deepCopy());
		}
		else if ( this.equals( (tuileDeComparaison = newBifurcationsEmbrassees()) ) ){
			this.setPresenceArbres(tuileDeComparaison.getPresenceArbres());
			this.setOrientation(tuileDeComparaison.getOrientation());
			this.setListeConnections(tuileDeComparaison.getListeConnections());
			this.setEscaleLiee(tuileDeComparaison.getEscaleLiee());
			this.setType(tuileDeComparaison.getTypeTuile());
			this.setImage(tuileDeComparaison.deepCopy());
		}
		
		for (int compteurRotation = 0; compteurRotation < nombreDeRotationsApres; compteurRotation++){
			this.rotation();
		}
	}
	
	public String toString (){
		String resultat = "{";
		resultat = resultat + orientation + ":";
		resultat = resultat + listeConnections.toString()+ "}";
		return resultat;
	}
	public boolean equals (Tuile autreTuile){
		boolean resultat = false;
		
		resultat = ( immuable == autreTuile.getPresenceArbres() );
		resultat = resultat && listesIdentiques(autreTuile.getListeConnections());
		
		return resultat;
	}
	public Tuile clone (){
		Tuile newTuile = new Tuile(immuable);
		
		newTuile.setListeConnections(listeConnections);
		newTuile.setImage(imageTuile);
		newTuile.setOrientation(orientation);
		newTuile.setType(typeTuile);
		newTuile.setEscaleLiee(escaleLiee);
		
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
	private boolean listesIdentiques (ArrayList<Connection> autreListe){
		
		boolean listeIdentique = true;
		boolean estContenu = false;
		
		// La vérification dans les deux sens est necessaire, car une connexion qui est dans la liste 2
		// Mais pas dans la liste 1 ne sera pas prise en compte sinon
		
		// Vérifiez que chaque de la liste 1 est présente dans l'autre liste
		ListIterator<Connection> iterateurConnections = listeConnections.listIterator();
		while ( iterateurConnections.hasNext() && listeIdentique){
			estContenu = false;
			ListIterator<Connection> iterateurAutresConnections = autreListe.listIterator();
			Connection connection1 = iterateurConnections.next();
			while ( iterateurAutresConnections.hasNext() && !estContenu){
				Connection connection2 = iterateurAutresConnections.next();
				estContenu = connection1.equals(connection2);
			}
			listeIdentique = estContenu;
		}
		
		// Vérifiez que chaque connexion de la liste 2 est présente dans l'autre liste
		iterateurConnections = autreListe.listIterator();
		while ( iterateurConnections.hasNext() && listeIdentique){
			estContenu = false;
			ListIterator<Connection> iterateurAutresConnections = listeConnections.listIterator();
			Connection connection1 = iterateurConnections.next();
			while ( iterateurAutresConnections.hasNext() && !estContenu){
				Connection connection2 = iterateurAutresConnections.next();
				estContenu = connection1.equals(connection2);
			}
			listeIdentique = estContenu;
		}
		
		return listeIdentique;
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
	 * tourne toutes les connections de la tuile appelante d'un cran dans le sens horaire 
	 */
	private void rotationDroiteConnections (){
		ListIterator<Connection> iterateurConnections = listeConnections.listIterator();
		while ( iterateurConnections.hasNext() ){
			iterateurConnections.next().rotationDroite();
		}
	}
	
}
