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
	private boolean objectifsVisibleJ1 = false;
	private boolean objectifsVisibleJ2 = false;
	private int mainSelectionnee = -1;
	private boolean casePlateauSelectionnee = false;
	private int tuileMainSelectionnee = -1;
	private int coordXSelection = -1;
	private int coordYSelection = -1;
	private boolean piocheSelectionnee;
	
	private Coup[] coupsJoues;
	private Coup[] coupsPrecedents;
	private Coup[] coupsHistorique;
	private Coup coupSimultaneEnAction;
	private Tuile tuilePourCoupSimultane;
	private Plateau plateauAComparer;
	/*
	 * FIN Details Visuels
	 */
	
	/*
	 * Attributs d'Images
	 */
	private BufferedImage fond,cadreNotif, plateau, pioche, piocheMain, rotate, stop, carteObjectif1, carteObjectif2, carteObjectifDos;
	private BufferedImage escale1J1, escale2J1, escale1J2, escale2J2, tramJ1, tramJ2;
	private BufferedImage surbrillance, surbrillanceVerte, surbrillanceCyan, surbrillanceViolet, surbrillanceRouge, surbrillanceJaune;
	private BufferedImage opacitePasse, opaciteFutur;
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
	private int positionXObjJ1;
	private int positionYObjJ1;
	private int positionXObjJ2;
	private int positionYObjJ2;
	private int dimensionPioche;
	public boolean piocher = false;
	/*
	 * FIN Entiers Fixes
	 */
	
	/*
	 * ACCESSEURS
	 */
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
	public boolean getVisObjJ1 (){
		return objectifsVisibleJ1;
	}
	public boolean getVisObjJ2 (){
		return objectifsVisibleJ2;
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
	public int getPositionXObjJ1 (){
		return positionXObjJ1;
	}
	public int getPositionYObjJ1 (){
		return positionYObjJ1;
	}
	public int getPositionXObjJ2 (){
		return positionXObjJ2;
	}
	public int getPositionYObjJ2 (){
		return positionYObjJ2;
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
	public Moteur getMoteur (){
		return mot;
	}
	public String getNotifications (){
		return notifications;
	}
	public Coup getCoupSimultaneActif (){
		return coupSimultaneEnAction;
	}
	
	public void setMainSelectionnee (int numeroMain) {
		mainSelectionnee = numeroMain;
	}
	
	public void setVisObjJ1 (boolean newbool) {
		objectifsVisibleJ1 = newbool;
	}
	
	public void setVisObjJ2 (boolean newbool) {
		objectifsVisibleJ2 = newbool;
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
	public void setCoupsPrecedents (Coup[] referenceCoupsPrecedents){
		for (int numeroCoup = 0; numeroCoup < referenceCoupsPrecedents.length; numeroCoup++){
			if ( referenceCoupsPrecedents[numeroCoup] != null ){
				coupsPrecedents[numeroCoup] = referenceCoupsPrecedents[numeroCoup].clone();
			}
			else {
				coupsPrecedents[numeroCoup] = referenceCoupsPrecedents[numeroCoup];
			}
		}
	}
	public void setCoupSimultaneEnAction (Coup referenceCoup){
		coupSimultaneEnAction = referenceCoup;
	}
	public void setTuilePourCoupSimultane (Tuile referenceTuilePourCoupSimultane){
		tuilePourCoupSimultane = referenceTuilePourCoupSimultane.clone();
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
		coupsJoues = new Coup[Coup.nbMaxPlacements];
		coupsPrecedents = new Coup[Coup.nbMaxPlacements];
		coupsHistorique = new Coup[Coup.nbMaxPlacements];
		
		Ecouteur_Plateau ecouteur = new Ecouteur_Plateau(this, referenceMoteur);
		addMouseListener(ecouteur);
		addMouseMotionListener (ecouteur);
	}
	/*
	 * FIN Constructeur
	 */
	
	
	/*
	 * Methode paintComponent de Panneau_Plateau
	 */
	public void paintComponent (Graphics g){
//		System.out.println("\n ===== Paint Panneau_Plateau ===== ");
		crayon = (Graphics2D) g;
		
		largeur = getSize().width;
		hauteur = getSize().height;
		
		int largeurPossible = largeur - (largeur/7 + 20) - depart;
		int hauteurPossible = hauteur - 2*depart;
		
		if (largeurPossible < hauteurPossible) {
			tailleCase = largeurPossible/mot.getPlateau().length();
		}
		else {
			tailleCase = hauteurPossible/mot.getPlateau().length();
		}
		
		decalageMain = depart + 2*tailleCase;

		tailleCaseMain = tailleCase + tailleCase/2;
		petitEcartMain = tailleCaseMain/2;
		
		mainDuHaut = depart - 10 - tailleCaseMain;
		mainDuBas = depart + mot.getPlateau().length() * tailleCase + 10;
		
		positionXObjJ1 = depart+((tailleCase*145)/10);
		positionYObjJ1 = depart+(tailleCase*13);
		
		positionXObjJ2 = depart+((tailleCase*145)/10);
		positionYObjJ2 = (depart-tailleCase);
		
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
		
		//dessin du cadre de notification
		dessinerCadreNotification();
		
		// La notification pour Joueur
		ecrireNotifications(crayon);
		
		//objectifs des joueurs
		dessinerObjectifJ1();
		dessinerObjectifJ2();
		
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
		if ( piocheSelectionnee ){
			dessinerPiocheSelectionnee(crayon);
		}
		
		dessinerCoupsHistorique(crayon);

//		System.out.println("\n ===== FIN Paint Panneau_Plateau ===== ");
	}
	
	
	/*
	 * Methodes Public de Panneau_Plateau 
	 */
	public void ajouterCoup (Coup coupChoisi){
		int numeroCoup = 0;
		while ( numeroCoup < coupsJoues.length && coupsJoues[numeroCoup] != null ){
			numeroCoup++;
		}
		if ( numeroCoup < coupsJoues.length ){
			coupsJoues[numeroCoup] = coupChoisi.clone();
		}
	}
	public void effacerCoupsJoues (){
		for (int numeroCoup = 0; numeroCoup < coupsJoues.length; numeroCoup++){
			coupsJoues[numeroCoup] = null;
		}
	}
	public void afficherCoupsPrecedents (){
		for (int numeroCoup = 0; numeroCoup < coupsPrecedents.length; numeroCoup++){
//			if ( coupsJoues[numeroCoup] != null ){
				coupsPrecedents[numeroCoup] = coupsJoues[numeroCoup].clone();
//			}
//			else {
//				coupsPrecedents[numeroCoup] = coupsJoues[numeroCoup];
//			}
		}
	}
	public void chargerCoupsHistoriques (Coup[] referenceCoupsHistorique){
		for (int numeroCoup = 0; numeroCoup < referenceCoupsHistorique.length; numeroCoup++){
			if ( referenceCoupsHistorique[numeroCoup] != null ){
				coupsHistorique[numeroCoup] = referenceCoupsHistorique[numeroCoup].clone();
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
	
	//dessiner les objectifs, cach�s ou non, du joueur1
	private void dessinerObjectifJ1()
	{
		if(objectifsVisibleJ1)
		{
			crayon.drawImage(carteObjectif1, positionXObjJ1, positionYObjJ1, (tailleCase*30)/10, (tailleCase*25)/10, this);
			crayon.drawImage(escale1J1, positionXObjJ1+(tailleCase*30)/10-(3*tailleCase)+tailleCase/4+1, positionYObjJ1+tailleCase-5, tailleCase, tailleCase,this);
			crayon.drawImage(escale2J1, positionXObjJ1+(tailleCase*30)/10-tailleCase-tailleCase/4-1, positionYObjJ1+tailleCase-5, tailleCase, tailleCase,this);
		} 
		else
		{
			crayon.drawImage(carteObjectifDos, positionXObjJ1, positionYObjJ1, (tailleCase*30)/10, (tailleCase*25)/10, this);
		}
	}
	
	//dessine les objectifs, cach�s ou non, du joueur2
	private void dessinerObjectifJ2()
	{
		if(objectifsVisibleJ2)
		{
			crayon.drawImage(carteObjectif2, positionXObjJ2, positionYObjJ2, (tailleCase*30)/10, (tailleCase*25)/10, this);
			crayon.drawImage(escale1J2, positionXObjJ1+(tailleCase*30)/10-(3*tailleCase)+tailleCase/4+1, positionYObjJ2+tailleCase-5, tailleCase, tailleCase,this);
			crayon.drawImage(escale2J2, positionXObjJ1+(tailleCase*30)/10-tailleCase-tailleCase/4-1, positionYObjJ2+tailleCase-5, tailleCase, tailleCase,this);
		} 
		else
		{
			crayon.drawImage(carteObjectifDos, positionXObjJ2, positionYObjJ2, (tailleCase*30)/10, (tailleCase*25)/10, this);
		}
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
				Point positionDeComparaison = new Point(ligne, colonne);
				if ( coupSimultaneEnAction != null && positionDeComparaison.equals(coupSimultaneEnAction.getCoordonnee()) ){
					dessinerTuile(crayon, tuilePourCoupSimultane, ligne, colonne);
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
			if (tuileAt.getEscaleLiee()!=0)
			{
				crayon.drawImage(stop,  depart + (tailleCase*coordX), depart + (tailleCase*coordY), tailleCase/3, tailleCase/3, this);
			}
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
	private void dessinerCadreNotification()
	{
		crayon.drawImage(cadreNotif, depart+(tailleCase*15), depart+(tailleCase*2), tailleCase*5, tailleCase*4, this);
	}
	//a remplacer pas un texte de notification avec une image
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
			dessinerSurlignageActif(crayon, coupsJoues[numeroCoup].getCoordonnee());
			numeroCoup++;
		}
	}
	private void dessinerCoupsPrecedents (Graphics2D crayon){
		int numeroCoup = 0;
		while ( numeroCoup < coupsPrecedents.length && coupsPrecedents[numeroCoup] != null ){
			dessinerSurlignagePrecedent(crayon, coupsPrecedents[numeroCoup].getCoordonnee());
			numeroCoup++;
		}
	}
	private void dessinerCoupsHistorique (Graphics2D crayon){
		int numeroCoup = 0;
		while ( numeroCoup < coupsHistorique.length && coupsHistorique[numeroCoup] != null ){
			dessinerSurlignageHistorique(crayon, coupsHistorique[numeroCoup].getCoordonnee());
			numeroCoup++;
		}
	}
	private void dessinerSurlignageActif (Graphics2D crayon, Point caseASurligner){
		crayon.drawImage(surbrillanceVerte, depart + caseASurligner.x*tailleCase - tailleCase/4+5 , depart + caseASurligner.y*tailleCase - tailleCase/4+5, tailleCase-4+tailleCase/2-5, tailleCase-4+tailleCase/2-5, this);
	}
	private void dessinerSurlignagePrecedent (Graphics2D crayon, Point caseASurligner){
		crayon.drawImage(surbrillanceCyan,depart + caseASurligner.x*tailleCase - tailleCase/4+5 , depart + caseASurligner.y*tailleCase - tailleCase/4+5, tailleCase-4+tailleCase/2-5, tailleCase-4+tailleCase/2-5, this);
	}
	private void dessinerSurlignageHistorique (Graphics2D crayon, Point caseASurligner){
		crayon.drawImage(surbrillanceJaune, depart + caseASurligner.x*tailleCase - tailleCase/4+5 , depart + caseASurligner.y*tailleCase - tailleCase/4+5, tailleCase-4+tailleCase/2-5, tailleCase-4+tailleCase/2-5, this);
	}
	private void dessinerCoupSimultane (Graphics2D crayon){
		int coordXPlateau = coupSimultaneEnAction.getCoordonnee().x;
		int coordYPlateau = coupSimultaneEnAction.getCoordonnee().y;
		crayon.drawImage(surbrillanceViolet, depart + coordXPlateau*tailleCase - tailleCase/4+5, depart + coordYPlateau*tailleCase - tailleCase/4+5, tailleCase-4+tailleCase/2-5, tailleCase-4+tailleCase/2-5, this);
		
		int coordXMain = decalageMain + coupSimultaneEnAction.getTuile() * (tailleCaseMain + petitEcartMain);
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
				if ( plateauCourant.getTuileAt(ligne, colonne) != null && plateauAComparer.getTuileAt(ligne, colonne) != null){
					if ( plateauCourant.getTuileAt(ligne, colonne).equals(plateauAComparer.getTuileAt(ligne, colonne)) ){
						dessinerTuile(crayon, plateauCourant.getTuileAt(ligne, colonne), ligne, colonne);
					}
					else {
						dessinerTuile(crayon, plateauAComparer.getTuileAt(ligne, colonne), ligne, colonne);
					}
				}
				else if ( plateauCourant.getTuileAt(ligne, colonne) != null && plateauAComparer.getTuileAt(ligne, colonne) == null ){
					dessinerTuile(crayon, plateauCourant.getTuileAt(ligne, colonne), ligne, colonne);
				}
				else if ( plateauCourant.getTuileAt(ligne, colonne) == null && plateauAComparer.getTuileAt(ligne, colonne) != null ){
					dessinerTuile(crayon, plateauAComparer.getTuileAt(ligne, colonne), ligne, colonne);
				}
				if ( plateauCourant.getTuileAt(ligne,  colonne) != null && plateauAComparer.getTuileAt(ligne, colonne) == null ){
					dessinerTuileMasquageAbsence(crayon, ligne, colonne);
				}
				else if ( plateauCourant.getTuileAt(ligne, colonne) == null && plateauAComparer.getTuileAt(ligne, colonne) != null ){
					dessinerTuileMasquageAnticipation(crayon, ligne, colonne);
				}
			}
		}
	}
	private void dessinerTuileMasquageAbsence (Graphics2D crayon, int coordX, int coordY){
		crayon.drawImage(opacitePasse, depart + coordX*tailleCase +1, depart + coordY*tailleCase +1, tailleCase-1, tailleCase-1, this);
	}
	private void dessinerTuileMasquageAnticipation (Graphics2D crayon, int coordX, int coordY){
		crayon.drawImage(opaciteFutur, depart + coordX*tailleCase +1, depart + coordY*tailleCase +1, tailleCase-1, tailleCase-1, this);
	}
	
	// Methodes de verification
	private boolean coupSimultaneActive (){
		return coupSimultaneEnAction != null;
	}
	

	/*
	 * Methode Private d'iniialisation d'image
	 */
	protected void initialiserImages (){
		//initialisation des cartes de lignes selon la lignes tiré
		switch(mot.getTabPlayers()[0].getObjectifs().getLigne())
		{
			case 1: 
					carteObjectif1 = Constantes.Images.initCarte("carte1.png");
					tramJ1 = Constantes.Images.initCarte("tram1.png");
					break;
			case 2: 
					carteObjectif1 = Constantes.Images.initCarte("carte2.png");
					tramJ1 = Constantes.Images.initCarte("tram2.png");
					break;
			case 3: 
					carteObjectif1 = Constantes.Images.initCarte("carte3.png");
					tramJ1 = Constantes.Images.initCarte("tram3.png");
					break;
			case 4: 
					carteObjectif1 = Constantes.Images.initCarte("carte4.png");
					tramJ1 = Constantes.Images.initCarte("tram4.png");
					break;
			case 5: 
					carteObjectif1 = Constantes.Images.initCarte("carte5.png");
					tramJ1 = Constantes.Images.initCarte("tram5.png");
					break;
			case 6: 
					carteObjectif1 = Constantes.Images.initCarte("carte6.png");
					tramJ1 = Constantes.Images.initCarte("tram6.png");
					break;
		}
		switch(mot.getTabPlayers()[1].getObjectifs().getLigne())
		{
			case 1: 
					carteObjectif2 = Constantes.Images.initCarte("carte1.png");
					tramJ2 = Constantes.Images.initCarte("tram1.png");
					break;
			case 2: 
					carteObjectif2 = Constantes.Images.initCarte("carte2.png");
					tramJ2 = Constantes.Images.initCarte("tram2.png");
					break;
			case 3: 
					carteObjectif2 = Constantes.Images.initCarte("carte3.png");
					tramJ2 = Constantes.Images.initCarte("tram3.png");
					break;
			case 4: 
					carteObjectif2 = Constantes.Images.initCarte("carte4.png");
					tramJ2 = Constantes.Images.initCarte("tram4.png");
					break;
			case 5: 
					carteObjectif2 = Constantes.Images.initCarte("carte5.png");
					tramJ2 = Constantes.Images.initCarte("tram5.png");
					break;
			case 6: 
					carteObjectif2 = Constantes.Images.initCarte("carte6.png");
					tramJ2 = Constantes.Images.initCarte("tram6.png");
					break;
		}
		//initialisation des escales selon les escales tiré
		switch(mot.getTabPlayers()[0].getObjectifs().getEscalesCibles()[0])
		{
			case 1:
					escale1J1 = Constantes.Images.initCarte("A.jpg");
					break;
			case 2:
					escale1J1 = Constantes.Images.initCarte("B.jpg");
					break;
			case 3:
					escale1J1 = Constantes.Images.initCarte("C.jpg");
					break;
			case 4:
					escale1J1 = Constantes.Images.initCarte("D.jpg");
					break;
			case 5:
					escale1J1 = Constantes.Images.initCarte("E.jpg");
					break;
			case 6:
					escale1J1 = Constantes.Images.initCarte("F.jpg");
					break;
			case 7:
					escale1J1 = Constantes.Images.initCarte("G.jpg");
					break;
			case 8:
					escale1J1 = Constantes.Images.initCarte("H.jpg");
					break;
			case 9:
					escale1J1 = Constantes.Images.initCarte("I.jpg");
					break;
			case 10:
					escale1J1 = Constantes.Images.initCarte("J.jpg");
					break;
			case 11:
					escale1J1 = Constantes.Images.initCarte("K.jpg");
					break;
			case 12:
					escale1J1 = Constantes.Images.initCarte("L.jpg");
					break;
		}
		
		switch(mot.getTabPlayers()[0].getObjectifs().getEscalesCibles()[1])
		{
			case 1:
					escale2J1 = Constantes.Images.initCarte("A.jpg");
					break;
			case 2:
					escale2J1 = Constantes.Images.initCarte("B.jpg");
					break;
			case 3:
					escale2J1 = Constantes.Images.initCarte("C.jpg");
					break;
			case 4:
					escale2J1 = Constantes.Images.initCarte("D.jpg");
					break;
			case 5:
					escale2J1 = Constantes.Images.initCarte("E.jpg");
					break;
			case 6:
					escale2J1 = Constantes.Images.initCarte("F.jpg");
					break;
			case 7:
					escale2J1 = Constantes.Images.initCarte("G.jpg");
					break;
			case 8:
					escale2J1 = Constantes.Images.initCarte("H.jpg");
					break;
			case 9:
					escale2J1 = Constantes.Images.initCarte("I.jpg");
					break;
			case 10:
					escale2J1 = Constantes.Images.initCarte("J.jpg");
					break;
			case 11:
					escale2J1 = Constantes.Images.initCarte("K.jpg");
					break;
			case 12:
					escale2J1 = Constantes.Images.initCarte("L.jpg");
					break;
		}
		
		switch(mot.getTabPlayers()[1].getObjectifs().getEscalesCibles()[0])
		{
			case 1:
					escale1J2 = Constantes.Images.initCarte("A.jpg");
					break;
			case 2:
					escale1J2 = Constantes.Images.initCarte("B.jpg");
					break;
			case 3:
					escale1J2 = Constantes.Images.initCarte("C.jpg");
					break;
			case 4:
					escale1J2 = Constantes.Images.initCarte("D.jpg");
					break;
			case 5:
					escale1J2 = Constantes.Images.initCarte("E.jpg");
					break;
			case 6:
					escale1J2 = Constantes.Images.initCarte("F.jpg");
					break;
			case 7:
					escale1J2 = Constantes.Images.initCarte("G.jpg");
					break;
			case 8:
					escale1J2 = Constantes.Images.initCarte("H.jpg");
					break;
			case 9:
					escale1J2 = Constantes.Images.initCarte("I.jpg");
					break;
			case 10:
					escale1J2 = Constantes.Images.initCarte("J.jpg");
					break;
			case 11:
					escale1J2 = Constantes.Images.initCarte("K.jpg");
					break;
			case 12:
					escale1J2 = Constantes.Images.initCarte("L.jpg");
					break;
		}
		
		switch(mot.getTabPlayers()[1].getObjectifs().getEscalesCibles()[1])
		{
			case 1:
					escale2J2 = Constantes.Images.initCarte("A.jpg");
					break;
			case 2:
					escale2J2 = Constantes.Images.initCarte("B.jpg");
					break;
			case 3:
					escale2J2 = Constantes.Images.initCarte("C.jpg");
					break;
			case 4:
					escale2J2 = Constantes.Images.initCarte("D.jpg");
					break;
			case 5:
					escale2J2 = Constantes.Images.initCarte("E.jpg");
					break;
			case 6:
					escale2J2 = Constantes.Images.initCarte("F.jpg");
					break;
			case 7:
					escale2J2 = Constantes.Images.initCarte("G.jpg");
					break;
			case 8:
					escale2J2 = Constantes.Images.initCarte("H.jpg");
					break;
			case 9:
					escale2J2 = Constantes.Images.initCarte("I.jpg");
					break;
			case 10:
					escale2J2 = Constantes.Images.initCarte("J.jpg");
					break;
			case 11:
					escale2J2 = Constantes.Images.initCarte("K.jpg");
					break;
			case 12:
					escale2J2 = Constantes.Images.initCarte("L.jpg");
					break;
		}
		carteObjectifDos = Constantes.Images.initCarte("carteDos.png");
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
		opacitePasse = Constantes.Images.initBackground("opaciteGris.png");
		opaciteFutur = Constantes.Images.initBackground("opaciteFutur.png");
		stop = Constantes.Images.initTuile("stop.png");
		cadreNotif = Constantes.Images.initBackground("cadreNotif.png");
		
	}

}
