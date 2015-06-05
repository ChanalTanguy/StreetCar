package graphique;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import joueurPackage.Coup;
import joueurPackage.MainJoueur;
import mainPackage.Moteur;
import objectPackage.Plateau;
import objectPackage.tuilePackage.Tuile;
import constantesPackages.Constantes;

public class Panneau_Plateau extends Pan_Abstract{
	/*
	 * Attribut Principal
	 */
	private Moteur mot;
	private String notifications;
	/*
	 * FIN Attributs Principaux
	 */
	
	/*
	 * Attributs pour Details Visuels
	 */
	private int mainSelectionnee = -1;
	private boolean casePlateauSelectionnee = false;
	private int tuileMainSelectionnee = -1;
	private int coordXSelection = -1;
	private int coordYSelection = -1;
	private boolean piocheSelectionnee;
	
	private Point[] coupsJoues;
	private Point[] coupsPrecedents;
	private Point[] coupsHistorique;
	private Coup coupSimultaneEnAction;
	private int numeroTuileCoupSimultane;
	private Tuile tuilePourCoupSimultane;
	private Plateau plateauAComparer;
	/*
	 * FIN Details Visuels
	 */
	
	/*
	 * Attributs d'Images
	 */
	private BufferedImage fond, plateau, pioche, piocheMain, rotate;
	private BufferedImage surbrillance, surbrillanceVerte, surbrillanceCyan, surbrillanceViolet, surbrillanceRouge, surbrillanceJaune;
	private BufferedImage imageCoupSimultane;
	/*
	 * FIN Images
	 */
	
	/*
	 * Attributs d'Entiers de Positionnement
	 */
	private int depart = 100;
	private int tailleCase;
	private int tailleCaseMain;
	private int ecart = 150;
	private int coordXTuile;
	private int petitEcartMain = 30;
	private int mainDuHaut = 20;
	private int mainDuBas = 820;
	private int decalageMain;
	private int positionXPioche;
	private int positionYPioche;
	private int dimensionPioche;
	public boolean piocher = false;
	/*
	 * FIN Entiers Fixes
	 */
	
	/*
	 * ACCESSEURS
	 */
	public Moteur getMoteur (){
		return mot;
	}
	public String getNotifications (){
		return notifications;
	}

	public int getMainSelectionnee (){
		return mainSelectionnee;
	}
	public int getCoordXTuile()
	{
		return coordXTuile;
	}
	public int getTuileMainSelectionnee (){
		return tuileMainSelectionnee;
	}
	public int getDepart (){
		return depart;
	}
	public int getTailleCase (){
		return tailleCase;
	}
	public int getEcart (){
		return ecart;
	}
	public int getPetitEcartMain (){
		return petitEcartMain;
	}
	public int getMainDuHaut (){
		return mainDuHaut;
	}
	public int getMaintDuBas (){
		return mainDuBas;
	}
	public int getTailleCaseMain (){
		return tailleCaseMain;
	}
	public int getDecalageMain (){
		return decalageMain;
	}
	public int getPositionXPioche (){
		return positionXPioche;
	}
	public int getPositionYPioche (){
		return positionYPioche;
	}
	public int getDimensionPioche (){
		return dimensionPioche;
	}
	public int getNumeroTuileCoupSimultane (){
		return numeroTuileCoupSimultane;
	}
	public Tuile getTuilePourCoupSimultane (){
		return tuilePourCoupSimultane;
	}
	public Coup getCoupSimultaneActif (){
		return coupSimultaneEnAction;
	}
	
	public void setMainSelectionnee (int numeroMain) {
		mainSelectionnee = numeroMain;
	}
	public void setTuileSelectionnee (int numeroTuile) {
		tuileMainSelectionnee = numeroTuile;
	}
	public void setPiocheSelectionnee (boolean piocheClic){
		piocheSelectionnee = piocheClic;
	}
	public void setCaseSelectionnee (boolean caseSelected){
		casePlateauSelectionnee = caseSelected;
	}
	public void setCoordXSelection (int newCoordX){
		coordXSelection = newCoordX;
	}
	public void setCoordYSelection (int newCoordY){
		coordYSelection = newCoordY;
	}
	public void setNotifications (String newNotif){
		notifications = newNotif;
	}
	public void setCoupsPrecedents (Point[] referenceCoupsPrecedents){
		for (int numeroCoup = 0; numeroCoup < referenceCoupsPrecedents.length; numeroCoup++){
			if ( referenceCoupsPrecedents[numeroCoup] != null ){
				coupsPrecedents[numeroCoup] = (Point) referenceCoupsPrecedents[numeroCoup].clone();
			}
			else {
				coupsPrecedents[numeroCoup] = referenceCoupsPrecedents[numeroCoup];
			}
		}
/*		int numeroCoup = 0;
		while ( numeroCoup < referenceCoupsPrecedents.length ){
			coupsPrecedents[numeroCoup] = (Point) referenceCoupsPrecedents[numeroCoup];
			numeroCoup++;
		}
*/	}
	public void setCoupSimultaneEnAction (Coup referenceCoup){
		coupSimultaneEnAction = referenceCoup;
	}
	public void setImageCoupSimultane (BufferedImage imageTuile){
		imageCoupSimultane = imageTuile;
	}
	public void setNumeroTuileCoupSimultane (int newNumeroTuile){
		numeroTuileCoupSimultane = newNumeroTuile;
	}
	public void setTuilePourCoupSimultane (Tuile referenceTuileCoupSimultane){
		tuilePourCoupSimultane = referenceTuileCoupSimultane.clone();
	}
	
	/*
	 * FIN ACCESSEURS
	 */
	
	/*
	 * Constructeur
	 */
	public Panneau_Plateau (Color newCouleur, Moteur referenceMoteur){
		super(newCouleur);
		mot = referenceMoteur;
		notifications = "tests d'ecriture";
		initialiserImages();
		coupsJoues = new Point[Constantes.Coup.nbMaxPlacements];
		coupsPrecedents = new Point[Constantes.Coup.nbMaxPlacements];
		coupsHistorique = new Point[Constantes.Coup.nbMaxPlacements];
		numeroTuileCoupSimultane = -1;
		
		Ecouteur_Plateau ecouteur = new Ecouteur_Plateau(this, referenceMoteur);
		addMouseListener(ecouteur);
//		addMouseMotionListener (ecouteur);
	}
	/*
	 * FIN Constructeur
	 */
	
	
	/*
	 * Methode paintComponent de Panneau_Plateau
	 */
	public void paintComponent (Graphics g){
//		System.out.println("\n vvvvv PaintComponent Plateau vvvvv \n");
		crayon = (Graphics2D) g;
		
		largeur = getSize().width;
		hauteur = getSize().height;
		
		int largeurPossible = largeur - (largeur/7 + 20) - depart;
		int hauteurPossible = hauteur - 2*depart;
		
		if (largeurPossible < hauteurPossible) {
			tailleCase = largeurPossible / mot.getPlateau().length();
		}
		else {
			tailleCase = hauteurPossible / mot.getPlateau().length();
		}
		
		decalageMain = depart + 2*tailleCase;

		tailleCaseMain = tailleCase + tailleCase/2;
		petitEcartMain = tailleCaseMain/2;
		
		mainDuHaut = depart - 10 - tailleCaseMain;
		mainDuBas = depart + mot.getPlateau().length() * tailleCase + 10;
		
		positionXPioche = 6*largeur/7;
		positionYPioche = hauteur/2-tailleCase;
		dimensionPioche = 2*tailleCase;
		
		crayon.setColor(couleur);
		crayon.fillRect(0, 0, largeur, hauteur);
		
		// Le fond
		dessinerFond(crayon);
		
		// L'image du plateau + le quadrillage
		dessinerPlateau(crayon);
		dessinerQuadrillage(crayon);
		
		if ( plateauAComparer != null ){
			dessinerComparaison(crayon, mot.getPlateau(), plateauAComparer);
		}
		else {
			// Le contenu de plateau de jeu
			dessinerContenuPlateau(crayon, mot.getPlateau());
			
			dessinerCoupsPrecedents(crayon);
			if ( !coupSimultaneActive() ){
				dessinerCoupsJoues(crayon);
			}
			else {
				dessinerCoupSimultane(crayon);
			}
		}
		
		// La notification pour Joueur
		ecrireNotifications(crayon);
		
		// La pioche
		dessinerPioche(crayon);
		
		// Les 2 mains
		dessinerMain1(crayon, mot.getTabPlayers()[0].getMain());
		dessinerMain2(crayon, mot.getTabPlayers()[1].getMain());
		
		// Surbrillance de la tuile selectionnee dans une main s'il y en a une
		if ( mainSelectionnee != -1 ){
			dessinerMainSelectionnee(crayon);
		}
		// Surbrillance de la case selectionnee dans le plateau s'il y en a une
		if ( casePlateauSelectionnee ){
			dessinerCaseSelectionnee(crayon);
		}
		// Surbrillance de la pioche si elle est selectionnee
//		System.out.println("piocheSelectionnee : " + piocheSelectionnee);
		if ( piocheSelectionnee ){
			dessinerPiocheSelectionnee(crayon);
		}
		
		
		
		
		
		dessinerCoupsHistorique(crayon);
		
//		System.out.println("\n ^^^^^ FIN PaintComponent Plateau ^^^^^ ");
	}
	
	
	/*
	 * Methodes Public de Panneau_Plateau 
	 */
	public void ajouterCoup (Point coupChoisi){
		int numeroCoup = 0;
		while ( numeroCoup < coupsJoues.length && coupsJoues[numeroCoup] != null ){
			numeroCoup++;
		}
		if ( numeroCoup < coupsJoues.length ){
			coupsJoues[numeroCoup] = (Point) coupChoisi.clone();
		}
	}
	public void effacerCoupsJoues (){
		for (int numeroCoup = 0; numeroCoup < coupsJoues.length; numeroCoup++){
			coupsJoues[numeroCoup] = null;
		}
	}
	public void afficherCoupsPrecedents (){
		for (int numeroCoup = 0; numeroCoup < coupsPrecedents.length; numeroCoup++){
			if ( coupsJoues[numeroCoup] != null ){
				coupsPrecedents[numeroCoup] = (Point) coupsJoues[numeroCoup].clone();
			}
			else {
				coupsPrecedents[numeroCoup] = coupsJoues[numeroCoup];
			}
		}
	}
	public void chargerCoupsHistoriques (Point[] referenceCoupsHistorique){
		for (int numeroCoup = 0; numeroCoup < referenceCoupsHistorique.length; numeroCoup++){
			if ( referenceCoupsHistorique[numeroCoup] != null ){
				coupsHistorique[numeroCoup] = (Point)referenceCoupsHistorique[numeroCoup].clone();
			}
			else {
				coupsHistorique[numeroCoup] = referenceCoupsHistorique[numeroCoup];
			}
		}
		repaint();
	}
	public void effacerCoupsHistoriques (){
		for (int numeroCoup = 0; numeroCoup < coupsHistorique.length; numeroCoup++){
			coupsHistorique[numeroCoup] = null;
		}
		repaint();
	}
	public void comparateurDePlateau (Plateau referencePlateauAComparer){
		plateauAComparer = referencePlateauAComparer;
	}
	
	/*
	 * Methodes Private de Panneau_Plateau
	 */
	// Methodes de dessin principales
	private void dessinerFond (Graphics2D crayon) {
		crayon.drawImage(fond, 0, 0, largeur, hauteur, this);
	}
	private void dessinerPlateau (Graphics2D crayon) {
		int dimPlateau = mot.getPlateau().length() * tailleCase;
		crayon.drawImage(plateau, depart, depart, dimPlateau, dimPlateau, this);
	}
	private void dessinerQuadrillage (Graphics2D crayon){
		crayon.setColor(Color.black);
		for (int numeroLigne = 1; numeroLigne < mot.getPlateau().length(); numeroLigne++){
			// ligne horizontale
			crayon.drawLine( depart + tailleCase, depart + numeroLigne*tailleCase, depart + (mot.getPlateau().length()-1)*tailleCase, depart + numeroLigne*tailleCase );
			// ligne verticale
			crayon.drawLine( depart + numeroLigne*tailleCase, depart + tailleCase, depart + numeroLigne*tailleCase, depart + (mot.getPlateau().length()-1)*tailleCase );
		}
	}
	private void dessinerPioche (Graphics2D crayon) {
		if(mot.getNbActions()<=2) crayon.drawImage(piocheMain, positionXPioche, positionYPioche, dimensionPioche, dimensionPioche, this);
		else crayon.drawImage(pioche, positionXPioche, positionYPioche, dimensionPioche, dimensionPioche, this);
	}
	private void dessinerMain1 (Graphics2D crayon, MainJoueur main) {
		for (int numeroTuile = 0; numeroTuile < main.length(); numeroTuile++){
			Tuile tui = main.getTuileAt(numeroTuile);
			if (tui != null){
				dessinerTuileMain(crayon, tui, numeroTuile, mainDuBas);
			}
		}
	}
	private void dessinerMain2 (Graphics2D crayon, MainJoueur main) {
		for (int numeroTuile = 0; numeroTuile < main.length(); numeroTuile++){
			Tuile tui = main.getTuileAt(numeroTuile);
			if (tui != null){
				dessinerTuileMain(crayon, tui, numeroTuile, mainDuHaut);
			}
		}
	}
	private void dessinerContenuPlateau (Graphics2D crayon, Plateau plateau){
		for (int ligne = 1; ligne < plateau.length()-1; ligne++){
			for (int colonne = 1; colonne < plateau.length()-1; colonne++){
				Point positionDeComparaison = new Point(colonne, ligne);
				if ( coupSimultaneEnAction != null && positionDeComparaison.equals(coupSimultaneEnAction.getCoordonnee()) ){
//					dessinerTuile(crayon, )
					crayon.drawImage(imageCoupSimultane, depart + colonne*tailleCase, depart + ligne*tailleCase, tailleCase-1, tailleCase-1, this);
				}
				else {
					if (plateau.getTuileAt(ligne, colonne) != null){
						dessinerTuile(crayon, plateau.getTuileAt(ligne, colonne), ligne, colonne);
					}
				}
			}
		}
	}
	private void dessinerTuile (Graphics2D crayon, Tuile tuileAt, int coordX, int coordY){
		if (tuileAt.getImage() != null){
			int angle;
			switch(tuileAt.getOrientation()){
				case(Constantes.Orientation.nord) :
					angle = 0;
					break;
				case(Constantes.Orientation.sud) :
					angle = 180;
					break;
				case(Constantes.Orientation.est) :
					angle = 90;
					break;
				case(Constantes.Orientation.ouest) :
					angle = 270;
					break;
				default :
					angle = 0;
					break;
			}
			dessinerRotation(crayon, tuileAt.getImage(), angle, depart + coordX*tailleCase + 1, depart + coordY*tailleCase + 1, tailleCase-1);
		}
	}
	private void dessinerTuileMain (Graphics2D crayon, Tuile tuileAt, int numeroTuile, int coordY){
		int angle;
		switch(tuileAt.getOrientation()){
			case(Constantes.Orientation.nord) :
				angle = 0;
				break;
			case(Constantes.Orientation.sud) :
				angle = 180;
				break;
			case(Constantes.Orientation.est) :
				angle = 90;
				break;
			case(Constantes.Orientation.ouest) :
				angle = 270;
				break;
			default :
				angle = 0;
				break;
		}
		dessinerRotation(crayon, tuileAt.getImage(), angle, decalageMain + numeroTuile * (tailleCaseMain + petitEcartMain), coordY, tailleCaseMain);
		
	}
	private void dessinerRotation (Graphics2D crayon, BufferedImage image, int angle, int coordX, int coordY, int dimension){
		double rotationRequired = Math.toRadians(angle);
		double locationX = image.getWidth() / 2;
		double locationY = image.getHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		
		crayon.drawImage(op.filter(image,  null), coordX, coordY, dimension, dimension, this);
	}
	private void ecrireNotifications (Graphics2D crayon){
		crayon.setColor(Color.white);
		crayon.drawString(notifications, 6*largeur/7-20, hauteur/4);
	}
	
	
	// Methodes de dessin de details visuels
	private void dessinerMainSelectionnee (Graphics2D crayon) {
		coordXTuile = decalageMain + (tuileMainSelectionnee) * (tailleCaseMain + petitEcartMain);
		casePlateauSelectionnee = false;
		crayon.setColor(Color.white);
		switch (mainSelectionnee){
		case 1 :
			//crayon.drawRect( coordXTuile, mainDuBas, tailleCaseMain, tailleCaseMain);
			crayon.drawImage(surbrillance, coordXTuile-tailleCase/2, mainDuBas-tailleCase/2, tailleCaseMain+tailleCase-1, tailleCaseMain+tailleCase-1, this);
			if(mot.getcurrentPlayer() == 0) crayon.drawImage(rotate, coordXTuile-tailleCaseMain/3+10, mainDuBas-tailleCaseMain/3+10, tailleCaseMain/2, tailleCaseMain/2, this);
			break;
		case 2 :
			//crayon.drawRect( coordXTuile, mainDuHaut, tailleCaseMain, tailleCaseMain);
			crayon.drawImage(surbrillance, coordXTuile-tailleCase/2, mainDuHaut-tailleCase/2, tailleCaseMain+tailleCase-1, tailleCaseMain+tailleCase-1, this);
			if(mot.getcurrentPlayer() == 1) crayon.drawImage(rotate, coordXTuile-tailleCaseMain/3+10, mainDuHaut-tailleCaseMain/3+10, tailleCaseMain/2, tailleCaseMain/2, this);
		}
	}
	private void dessinerCaseSelectionnee (Graphics2D crayon) {
		mainSelectionnee = -1;
		//crayon.setColor(Color.white);
		//crayon.drawRect(coordXSelection*tailleCase + depart, coordYSelection*tailleCase + depart, tailleCase, tailleCase);
		crayon.drawImage(surbrillance,coordXSelection*tailleCase + depart -tailleCase/4, coordYSelection*tailleCase + depart-tailleCase/4, tailleCase+tailleCase/2, tailleCase+tailleCase/2, this);
	}
	private void dessinerPiocheSelectionnee (Graphics2D crayon) {
		int coordX = 6*largeur/7 - 1;
		int coordY = hauteur/2-tailleCase - 1;
		int dimension = 2*tailleCase + 1;
		crayon.setColor(Color.white);
		if (mot.getPioche().isEmpty()){
			crayon.fillRect(coordX, coordY, dimension, dimension);
		}
		else {
			//crayon.drawRect(coordX, coordY, dimension, dimension);
			crayon.drawImage(surbrillance, coordX-dimension/6, coordY+dimension/6, dimension+1, dimension+1,this);
			dessinerPioche(crayon);
			piocheSelectionnee = false;
		}
	}
	private void dessinerCoupsJoues (Graphics2D crayon){
		int numeroCoup = 0;
		while ( numeroCoup < coupsJoues.length && coupsJoues[numeroCoup] != null ){
			dessinerSurlignageActif(crayon, coupsJoues[numeroCoup]);
			numeroCoup++;
		}
	}
	private void dessinerCoupsPrecedents (Graphics2D crayon){
		int numeroCoup = 0;
		while ( numeroCoup < coupsPrecedents.length && coupsPrecedents[numeroCoup] != null ){
			dessinerSurlignagePrecedent(crayon, coupsPrecedents[numeroCoup]);
			numeroCoup++;
		}
	}
	private void dessinerCoupsHistorique (Graphics2D crayon){
		int numeroCoup = 0;
		while ( numeroCoup < coupsHistorique.length && coupsHistorique[numeroCoup] != null ){
			dessinerSurlignageHistorique(crayon, coupsHistorique[numeroCoup]);
			numeroCoup++;
		}
	}
	private void dessinerSurlignageActif (Graphics2D crayon, Point caseASurligner){
		//crayon.setColor(Color.green);
		//crayon.drawRect(depart + caseASurligner.x*tailleCase + 2 , depart + caseASurligner.y*tailleCase + 2, tailleCase-4, tailleCase-4);
		crayon.drawImage(surbrillanceVerte, depart + caseASurligner.x*tailleCase - tailleCase/4+5 , depart + caseASurligner.y*tailleCase - tailleCase/4+5, tailleCase-4+tailleCase/2-5, tailleCase-4+tailleCase/2-5, this);
	}
	private void dessinerSurlignagePrecedent (Graphics2D crayon, Point caseASurligner){
		//crayon.setColor(Color.cyan);
		//crayon.drawRect(depart + caseASurligner.x*tailleCase + 2 , depart + caseASurligner.y*tailleCase + 2, tailleCase-4, tailleCase-4);
		crayon.drawImage(surbrillanceCyan,depart + caseASurligner.x*tailleCase - tailleCase/4+5 , depart + caseASurligner.y*tailleCase - tailleCase/4+5, tailleCase-4+tailleCase/2-5, tailleCase-4+tailleCase/2-5, this);
	}
	private void dessinerSurlignageHistorique (Graphics2D crayon, Point caseASurligner){
		crayon.drawImage(surbrillanceJaune, depart + caseASurligner.x*tailleCase - tailleCase/4+5 , depart + caseASurligner.y*tailleCase - tailleCase/4+5, tailleCase-4+tailleCase/2-5, tailleCase-4+tailleCase/2-5, this);
	}
	private void dessinerCoupSimultane (Graphics2D crayon){
		int coordXPlateau = coupSimultaneEnAction.getCoordonnee().x;
		int coordYPlateau = coupSimultaneEnAction.getCoordonnee().y;
		crayon.drawImage(surbrillanceViolet, depart + coordXPlateau*tailleCase - tailleCase/4+5, depart + coordYPlateau*tailleCase - tailleCase/4+5, tailleCase-4+tailleCase/2-5, tailleCase-4+tailleCase/2-5, this);
		
		int coordXMain = decalageMain + numeroTuileCoupSimultane * (tailleCaseMain + petitEcartMain);
		switch (mot.getcurrentPlayer()){
		case 0:
			crayon.drawImage(surbrillanceViolet, coordXMain-tailleCase/2, mainDuBas-tailleCase/2, tailleCaseMain+tailleCase-1, tailleCaseMain+tailleCase-1, this);
			break;
		case 1:
			crayon.drawImage(surbrillanceViolet, coordXMain-tailleCase/2, mainDuHaut-tailleCase/2, tailleCaseMain+tailleCase-1, tailleCaseMain+tailleCase-1, this);
			break;
		}
	}
	private void dessinerComparaison (Graphics2D crayon, Plateau plateauCourant, Plateau plateauAComparer){
		for (int ligne = 1; ligne < plateauCourant.length()-1; ligne++){
			for (int colonne = 1; colonne < plateauCourant.length()-1; colonne++){				
				if ( plateauCourant.getTuileAt(ligne, colonne) != null){
					dessinerTuile(crayon, plateauCourant.getTuileAt(ligne, colonne), ligne, colonne);
				}
				if ( plateauCourant.getTuileAt(ligne,  colonne) != null && plateauAComparer.getTuileAt(ligne, colonne) == null ){
					dessinerTuileGrisee(crayon, ligne, colonne);
				}
			}
		}
	}
	private void dessinerTuileGrisee (Graphics2D crayon, int coordX, int coordY){
//		crayon.setColor(Color.red);
//		crayon.drawRect(depart + coordX*tailleCase + 2, depart + coordY*tailleCase + 2, tailleCase-4, tailleCase-4);
		crayon.drawImage(surbrillanceRouge, depart+coordX*tailleCase - tailleCase/4 + 5 + tailleCase/5, depart + coordY*tailleCase - tailleCase/4 + 5 + tailleCase/5, tailleCase-4+tailleCase/2-5 - 2*tailleCase/5 , tailleCase-4+tailleCase/2-5  - 2*tailleCase/5, this);
	}
	
	// Methodes "generales" de verification
	private boolean coupSimultaneActive (){
		return coupSimultaneEnAction != null;
	}
	

	/*
	 * Methode Private d'iniialisation d'image
	 */
	protected void initialiserImages (){
		fond = Constantes.Images.initBackground("tramOui.png");
		plateau = Constantes.Images.initBackground("plateau.png");
		pioche = Constantes.Images.initBackground("pioche.png");
		piocheMain = Constantes.Images.initBackground("piocheMain.png");
		rotate = Constantes.Images.initBouton("tourner.png");
		surbrillance = Constantes.Images.initBackground("surbrillance.png");
		surbrillanceVerte = Constantes.Images.initBackground("surbrillanceVerte.png");
		surbrillanceCyan = Constantes.Images.initBackground("surbrillanceCyan.png");
		surbrillanceViolet = Constantes.Images.initBackground("surbrillanceViolet.png");
		surbrillanceRouge = Constantes.Images.initBackground("surbrillanceRouge.png");
		surbrillanceJaune = Constantes.Images.initBackground("surbrillanceJaune.png");
	}

}
