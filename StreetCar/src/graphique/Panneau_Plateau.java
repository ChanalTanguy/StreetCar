package graphique;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

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
	private int casePlateauSelectionnee = -1;
	private int tuileMainSelectionnee = -1;
	private int coordXSelection = -1;
	private int coordYSelection = -1;
	private boolean piocheSelectionnee;
	/*
	 * FIN Details Visuels
	 */
	
	/*
	 * Attributs d'Images
	 */
	private BufferedImage fond, plateau, pioche;
	/*
	 * FIN Images
	 */
	
	/*
	 * Attributs d'Entiers Fixes
	 */
	private int depart = 100;
	private int tailleCase; // = 50;
	private int tailleCaseMain; // = 70;
	private int ecart = 150;
	private int petitEcartMain = 30;
	private int mainDuHaut = 20; // = depart - 10 - tailleCaseMain;
	private int mainDuBas = 820; // = 820;
	private int decalageMain = 2*depart;
	private int departPlateau;
	int largeurPossible;
	int hauteurPossible;
	
	/*
	 * FIN Entiers Fixes
	 */
	
	/*
	 * ACCESSEURS
	 */
	public int getMainSelectionnee (){
		return mainSelectionnee;
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
	public String getNotifications (){
		return notifications;
	}
	public int getDecalageMain (){
		return decalageMain;
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
	public void setCaseSelectionnee (int caseSelected){
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
	/*
	 * FIN ACCESSEURS
	 */
	
	public Panneau_Plateau (Color newCouleur, Moteur referenceMoteur){
		super(newCouleur);
		mot = referenceMoteur;
		notifications = "tests d'ecriture";
		initialiserImages();
		
		addMouseListener( new Ecouteur_Plateau(this, referenceMoteur) );
	}
	
	public void paintComponent (Graphics g){
		crayon = (Graphics2D) g;
		
		largeur = getSize().width;
		hauteur = getSize().height;
		
		System.out.println("largeur/hauteur : " + largeur + " " + hauteur);
		
		largeurPossible = largeur - (largeur/7 + 20) - depart;
		hauteurPossible = hauteur - 2*depart;
		
//		System.out.println("possibles : " + largeurPossible + " " + hauteurPossible);
		
		if (largeurPossible < hauteurPossible) {
			tailleCase = largeurPossible/mot.getPlateau().length();
			departPlateau = largeurPossible/2;
		}
		else {
			tailleCase = hauteurPossible/mot.getPlateau().length();
			departPlateau = hauteurPossible/2;
		}
		tailleCaseMain = tailleCase + tailleCase/2;

//		System.out.println("tailles : " + tailleCase + " " + tailleCaseMain);
		
		mainDuHaut = depart - 10 - tailleCaseMain;
		mainDuBas = depart + mot.getPlateau().length() * tailleCase + 10;
		
		
//		nettoyage(crayon);
		
		crayon.setColor(couleur);
		crayon.fillRect(0, 0, largeur, hauteur);
		
		dessinerFond(crayon);
		
		dessinerPlateau(crayon);
		dessinerQuadrillage(crayon);
		
		dessinerContenuPlateau(crayon, mot.getPlateau());
		
		ecrireNotifications(crayon);
		dessinerPioche(crayon);
		
		dessinerMain1(crayon, mot.getTabPlayers()[0].getMain());
		dessinerMain2(crayon, mot.getTabPlayers()[1].getMain());
		
		if ( mainSelectionnee != -1 ){
			dessinerMainSelectionner(crayon);
		}
		if ( casePlateauSelectionnee != -1 ){
			System.out.println("une case est selectionnee");
			dessinerCaseSelectionnee(crayon);
		}
		if ( piocheSelectionnee ){
			dessinerPiocheSelectionnee(crayon);
		}
/*		
		if (contoursSurlignes){
			crayon.setColor(Color.red);
			crayon.drawRect(0, 0, largeur-1, hauteur-1);
		}
*/
	}
	
	/*
	 * Methodes Private de Panneau_Plateau
	 */
	// Methode de clean
	private void nettoyage(Graphics2D crayon) {
		crayon.setColor(Color.white);
		crayon.fillRect(0, 0, largeur, hauteur);
	}
	
	// 6 Methodes de dessin principales
	private void dessinerFond (Graphics2D crayon) {
		crayon.drawImage(fond, 0, 0, largeur, hauteur, this);
	}
	private void dessinerPlateau (Graphics2D crayon) {
/*
//		int dimPlateau = depart + (mot.getPlateau().length()-2) * tailleCase;
		int dimPlateau = mot.getPlateau().length() * tailleCase;
		
//		crayon.drawImage(plateau, depart, depart, dimPlateau, dimPlateau, this);
		int positionDepartY = depart + hauteurPossible/2 - departPlateau;
		crayon.drawImage(plateau, depart, positionDepartY, dimPlateau, dimPlateau, this);
*/		
		int dimPlateau = mot.getPlateau().length() * tailleCase;
		int dimensionHaute = hauteur - 2*depart;
		crayon.drawImage(plateau, depart, depart, dimPlateau, dimPlateau, this);
		
		
	}
	private void dessinerQuadrillage (Graphics2D crayon){
		crayon.setColor(Color.black);
		for (int numeroLigne = 0; numeroLigne < mot.getPlateau().length()+1; numeroLigne++){
			crayon.drawLine( depart, depart + numeroLigne*tailleCase, depart + mot.getPlateau().length()*tailleCase, depart + numeroLigne*tailleCase );
			crayon.drawLine( depart + numeroLigne*tailleCase, depart, depart + numeroLigne*tailleCase, depart + mot.getPlateau().length()*tailleCase );
		}
	}
	private void dessinerPioche (Graphics2D crayon) {
		crayon.drawImage(pioche, 6*largeur/7, hauteur/2-tailleCase, 2*tailleCase, 2*tailleCase, this);
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
				if (plateau.getTuileAt(ligne, colonne) != null){
					dessinerTuile(crayon, plateau.getTuileAt(ligne, colonne), ligne-1, colonne);
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
			dessinerRotation(crayon, tuileAt.getImage(), angle, (depart + tailleCase) + coordX*tailleCase + 1, depart + coordY*tailleCase + 1, tailleCase-1);
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
	
	// 3 Methodes de dessin de details visuels
	private void dessinerMainSelectionner(Graphics2D crayon) {
		System.out.println("tuileMainSelectionnee : " + tuileMainSelectionnee);
		
		int coordX = 2*depart + (tuileMainSelectionnee) * (tailleCaseMain + petitEcartMain);
		casePlateauSelectionnee = -1;
		crayon.setColor(Color.white);
		switch (mainSelectionnee){
		case 1 :
			crayon.drawRect( coordX, mainDuBas, tailleCaseMain, tailleCaseMain);
			break;
		case 2 :
			crayon.drawRect( coordX, mainDuHaut, tailleCaseMain, tailleCaseMain);
		}
	}
	private void dessinerCaseSelectionnee(Graphics2D crayon) {
		mainSelectionnee = -1;
		crayon.setColor(Color.white);
		crayon.drawRect(coordXSelection*tailleCase + depart, coordYSelection*tailleCase + depart, tailleCase, tailleCase);
	}
	private void dessinerPiocheSelectionnee(Graphics2D crayon) {
		int coordX = 6*largeur/7 - 1;
		int coordY = hauteur/2-tailleCase - 1;
		int dimension = 2*tailleCase + 1;
		crayon.setColor(Color.white);
		if (mot.getPioche().isEmpty()){
			crayon.fillRect(coordX, coordY, dimension, dimension);
		}
		else {
			crayon.drawRect(coordX, coordY, dimension, dimension);
			piocheSelectionnee = false;
		}
	}
	
	/*
	 * Methode Private d'iniialisation d'image
	 */
	protected void initialiserImages (){
		fond = Constantes.Images.initBackground("tramOui.png");
		plateau = Constantes.Images.initBackground("plateau.png");
		pioche = Constantes.Images.initBackground("pioche.png");
	}

}
