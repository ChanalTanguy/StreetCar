package mainPackage;

import graphique.Panneau_Historique;
import graphique.Panneau_Plateau;
import historiqPackage.Configuration;
import historiqPackage.Historique;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Random;

import joueurPackage.Coup;
import joueurPackage.Joueur;
import joueurPackage.JoueurHumain;
import joueurPackage.JoueurIA;
import joueurPackage.MainJoueur;
import joueurPackage.Objectifs;
import objectPackage.Pioche;
import objectPackage.Plateau;
import constantesPackages.Constantes;

public class Moteur {
	private Joueur[] tabPlayers;
	private int currentPlayer;
	private Plateau plateauDeJeu;
	private int nbActions;
	private Pioche pioche;
	private Panneau_Plateau panneau_Jeu;
	private Panneau_Historique panneau_Historique;
	private Coup coupSimultane;
	
	private int numeroDeTour;
	private Historique historiqueDeTours;
	
	private Coup[] coupsPrecedents;
	
	/*
	 * Constructeur
	 */
	public Moteur(Plateau referencePlateau) {
		Objectifs[] obj = new Objectifs[2];
//		obj = tirerObjectif();
		initBidon(obj);
		
		System.out.println("Objectif joueur 1 : " + obj[0].toString());
		System.out.println("Objectif joueur 2 : " + obj[1].toString());
		
		
		tabPlayers = new Joueur[2];
		tabPlayers[0] = new JoueurHumain(this,obj[0]);
		
//		tabPlayers[1] = new JoueurHumain(this,obj[1]);
		tabPlayers[1] = new JoueurIA(this, obj[1]);
		
		currentPlayer = 0;
		numeroDeTour = 0;
		plateauDeJeu = referencePlateau;
		nbActions = 4;
		
		pioche = new Pioche();
		pioche.initialisation();
		pioche.shuffle();
		
		historiqueDeTours = new Historique();
		coupsPrecedents = new Coup[Coup.nbMaxPlacements];
		
		Configuration configurationInitiale = new Configuration (tabPlayers, currentPlayer, plateauDeJeu, pioche, numeroDeTour++, coupsPrecedents);
		historiqueDeTours.add(configurationInitiale);
		historiqueDeTours.get(0).setHistorique(historiqueDeTours);

	}
	
	/*
	 * FIN Constructeur
	 */
	

	/*
	 * Constructeur 2
	 */
	public void setPlayers(Joueur j1, Joueur j2) {
		tabPlayers[0] = j1.clone();
		tabPlayers[1] = j2.clone();
	}
	/*
	 * FIN Constructeur 2
	 */
	
	/* 
	 * Accesseurs
	 */
	public int getcurrentPlayer (){
		return currentPlayer;
	}
	public Joueur[] getTabPlayers (){
		return tabPlayers;
	}
	public Plateau getPlateau (){
		return plateauDeJeu;
	}
	public int getNbActions (){
		return nbActions;
	}
	public Pioche getPioche (){
		return pioche;
	}

	public int getNumTour(){
		return numeroDeTour;
	}
	public Historique getHistorique (){
		return historiqueDeTours;
	}
	public Panneau_Historique getPanneau_Historique (){
		return panneau_Historique;
	}
	
	public void setPanneau_Jeu (Panneau_Plateau referencePanJeu){
		panneau_Jeu = referencePanJeu;
		panneau_Jeu.setNotifications(Constantes.Message.auTourDe(currentPlayer));
	}
	public void setPanneau_Historique (Panneau_Historique referencePanHistorique){
		panneau_Historique = referencePanHistorique;
	}
	
	public void setcurrentPlayer (int referenceCurrentPlayer){
		currentPlayer = referenceCurrentPlayer;
	}
	public void setMainPlayers (MainJoueur referenceMainJoueur, int player){
		tabPlayers[player].setMain(referenceMainJoueur);
	}

	public void setNbActions (int referenceNbActions){
		nbActions = referenceNbActions;
	}
	public void setPioche (Pioche referencePioche){
		pioche = referencePioche;
	}
	public void setNumTour(int referenceNumTour)
	{
		numeroDeTour = referenceNumTour;
	}
	public void setPlateau(Plateau referencePlateau) {
		plateauDeJeu = referencePlateau;
	}
	/*
	 * FIN Accesseurs
	 */

	
	/*
	 * Methodes Public du Moteur
	 */
	public void start (){
		tabPlayers[currentPlayer].attendCoup();
	}
	public void stop () {
		tabPlayers[currentPlayer].stopPlayer();
	}
	/**
	 * Vérifie si un coup est valide puis l'execute si c'est le cas. 
	 * Change le joueur si besoin puis permet au joueur courant de jouer.
	 * @param coupChoisi
	 */
	public void jouerCoup (Coup coupChoisi) {
		
		String msg = "";
		String r;
		if (coupValide(coupChoisi)) {
			if ( coupSimultane != null ){
				coupSimultane = null;
				panneau_Jeu.setCoupSimultaneEnAction(null);
				panneau_Jeu.effacerCoupsJoues();
			}
			if (coupChoisi.getType().equals(Coup.placement)) {
				tabPlayers[currentPlayer].jouerTuileSurPlateau(coupChoisi.getTuile(), coupChoisi.getCoordonnee().x, coupChoisi.getCoordonnee().y, plateauDeJeu);
				nbActions--;
				
				panneau_Jeu.ajouterCoup(coupChoisi);
				ajouterCoup(coupChoisi);
				
				coupSimultane = null;
				
			} else if (coupChoisi.getType().equals(Coup.vol)) {
				tabPlayers[currentPlayer].volerTuile(coupChoisi.getTuile(), tabPlayers[(currentPlayer+1)%2]);
				nbActions--;
				coupSimultane = null;
			} else if (coupChoisi.getType().equals(Coup.pioche)) {
				if (!pioche.isEmpty())
					tabPlayers[currentPlayer].piocher(pioche);
				nbActions = 0;
				coupSimultane = null;
			} else {
				System.out.println("Erreur : Cas non géré"); // Cas sensé inateignable
			}
			
			if (nbActions == 0) {
				System.out.println("\n ===== Clic Pioche ===== ");
				
				panneau_Jeu.setPiocheSelectionnee(true);
				panneau_Jeu.repaint();
				
				if ( plateauDeJeu.ObjectifComplet(tabPlayers[currentPlayer].getObjectifs()) ){
					System.out.println("objectif rempli");
					tabPlayers[currentPlayer].setVoyagePossible(true);
				}
				else {
					System.out.println("objectif incomplet");
				}
				
				System.out.println("tour " + numeroDeTour + " valide");
				System.out.println("joueur courant de ce tour : " + currentPlayer);
				
				// Mise a Jour de l'historique de tours
				// creation de la configuration a ajouter
				currentPlayer = (currentPlayer+1)%2;
				
				Configuration config_a_Ajouter = new Configuration (tabPlayers, currentPlayer, plateauDeJeu, pioche, numeroDeTour, historiqueDeTours, coupsPrecedents);
				
				nbActions = 4;
				
				// s'il existe deja au moins un tour dans le "futur", on compare les coups actuels joues et ceux qui auraient ete joues
				if ( numeroDeTour < historiqueDeTours.size() ){
					System.out.println(" ===== Tour charge au prealable ===== ");

					effacerHistorique(numeroDeTour);
				}
				historiqueDeTours.ajouter(config_a_Ajouter);
				panneau_Historique.setNumeroTourCourant(numeroDeTour);
				numeroDeTour++;
				
				System.out.println("prochain tour : " + numeroDeTour);
				System.out.println("joueur courant de ce tour : " + currentPlayer);
				System.out.println("prochaine historique.size du tour : " + historiqueDeTours.size());
				
				panneau_Jeu.afficherCoupsPrecedents();
				panneau_Jeu.effacerCoupsJoues();
				viderCoupsPrecedents();
				panneau_Jeu.setCoupSimultaneEnAction(null);
				panneau_Jeu.setPiocheSelectionnee(false);
				
				panneau_Historique.repaint();
				System.out.println(" ===== FIN Clic Pioche ===== \n");
			}
			if (nbActions > 2)
				msg = Constantes.Message.auTourDe(currentPlayer);
			else 
				msg = Constantes.Message.finDeTour(currentPlayer);
		}
		else if ((r = plateauDeJeu.coupSimultaneValide(tabPlayers[currentPlayer].getMain().getTuileAt(coupChoisi.getTuile()), coupChoisi)) != null && nbActions == 4)
		{
			if (coupSimultane == null) {
				coupSimultane = coupChoisi;
				msg = "Coup simultanée possible";
				
				panneau_Jeu.ajouterCoup(coupSimultane);
				panneau_Jeu.setTuilePourCoupSimultane(tabPlayers[currentPlayer].getMain().getTuileAt(coupSimultane.getTuile()));
				panneau_Jeu.setCoupSimultaneEnAction(coupSimultane);
				
			}
			else if (coupSimultane.getTuile() == coupChoisi.getTuile()) {
				// Erreur même tuile selectionné pour la mettre au même endroit
				msg = Constantes.Message.poseImpossible;
				coupSimultane = null;
			}
			else {
				boolean coupSimultaneValide;
				Point p1 = coupSimultane.getCoordonnee();
				Point p2 = coupChoisi.getCoordonnee();
				
				switch (r) {
					case Constantes.Orientation.nord :  coupSimultaneValide = (p1.x == p2.x && p1.y == p2.y+1); break;
					case Constantes.Orientation.sud :   coupSimultaneValide = (p1.x == p2.x && p1.y == p2.y-1); break;
					case Constantes.Orientation.est :	coupSimultaneValide = (p1.x == p2.x-1 && p1.y == p2.y); break;
					case Constantes.Orientation.ouest : coupSimultaneValide = (p1.x == p2.x+1 && p1.y == p2.y); break;
					default : coupSimultaneValide = false;
				}
						
				if ( coupSimultaneValide ) {
					tabPlayers[currentPlayer].jouerTuileSurPlateau(coupChoisi.getTuile(), coupChoisi.getCoordonnee().x, coupChoisi.getCoordonnee().y, plateauDeJeu);
					tabPlayers[currentPlayer].jouerTuileSurPlateau(coupSimultane.getTuile(), coupSimultane.getCoordonnee().x, coupSimultane.getCoordonnee().y, plateauDeJeu);
					nbActions -= 2;
					
					// les 2 coups sont valides, on les considere alors comme 2 coups actifs 
					// => on supprime le coup simultane enregistre dans le panneau_plateau
					panneau_Jeu.setCoupSimultaneEnAction(null);
					// => on ajoute le coup courant dans le tableau de coups precedents
					ajouterCoup(coupChoisi);
					// => on ajoute aussi le coup simultane precedemment joue
					ajouterCoup(coupSimultane);
					// => on ajouter les 2 coups joues dans le panneau de jeu pour les afficher en coup Actif
					panneau_Jeu.ajouterCoup(coupChoisi);
					panneau_Jeu.ajouterCoup(coupSimultane);
					
					msg = Constantes.Message.finDeTour(currentPlayer);
				} else {
					// Erreur même tuile selectionné pour la mettre au même endroit
					msg = Constantes.Message.poseImpossible;
				}
				coupSimultane = null;
			}
		}
		else {
			if ( coupSimultane != null ){
				panneau_Jeu.effacerCoupsJoues();
				coupSimultane = null;
				panneau_Jeu.setCoupSimultaneEnAction(null);
			}
			if (nbActions < 3)
				msg = Constantes.Message.finDeTour(currentPlayer);
			else if (coupChoisi.getType().equals(Coup.placement))
				msg = Constantes.Message.poseImpossible;
			else if (coupChoisi.getType().equals(Coup.vol))
				msg = Constantes.Message.volImpossible;
			else if (coupChoisi.getType().equals(Coup.pioche))
				msg = Constantes.Message.piocheImpossible;
			else {
				msg = Constantes.Message.tramImpossible; // N'EST PAS SENSE ARRIVER !
			}
			System.out.println("Mauvais coup");
			
		}
		panneau_Jeu.setNotifications(msg);
		panneau_Jeu.repaint();
		
		tabPlayers[currentPlayer].attendCoup();

	}
	
	// Methodes de Kévin
	public void annulerTour_V2 (){
		System.out.println("\n ===== Annulation du tour V2 ===== ");
		
		System.out.println("numero du tour actuel : " + numeroDeTour);
		System.out.println("joueur courant : " + currentPlayer);
		Configuration configARetablir;
		if ( numeroDeTour > 0 ){
			configARetablir = historiqueDeTours.get(numeroDeTour - 1);
		}
		else {
			configARetablir = historiqueDeTours.get(0);
		}
		plateauDeJeu = configARetablir.getPlateauDuTour().clone();
		for (int indexJoueur = 0; indexJoueur < tabPlayers.length; indexJoueur++){
			tabPlayers[indexJoueur] = configARetablir.getJoueurAt(indexJoueur).clone();
		}
		viderCoupsPrecedents();
		nbActions = 4;
		panneau_Jeu.repaint();
		tabPlayers[currentPlayer].attendCoup();
		
		System.out.println(" ===== FIN Annulation du tour V2 ===== \n");
	}
	public void chargerTour_v2 (int numeroTourACharger){
		System.out.println("\n ===== Chargement Tour V2 ===== ");
		int numeroReelTour = numeroTourACharger + historiqueDeTours.getNbConfigsPrecedentes();
		System.out.println("tour " + numeroReelTour + " a charger");
		
		Configuration configACharger = historiqueDeTours.get(numeroReelTour);
		viderCoupsPrecedents();
		System.out.println("config.numTour : " + configACharger.getNumeroTour());
		System.out.println("config.joueurCourant : " + configACharger.getJoueurCourant());
		System.out.println("config.nbconfig : " + configACharger.getHistorique().size());
		System.out.println("historique.size actuel : " + historiqueDeTours.size());
		
		viderCoupsPrecedents();
		panneau_Jeu.effacerCoupsJoues();
		
		nbActions = 4;
		plateauDeJeu = configACharger.getPlateauDuTour().clone();
		pioche = configACharger.getPiocheDuTour().clone();
		for (int indexPlayer = 0; indexPlayer < tabPlayers.length; indexPlayer++){
			tabPlayers[indexPlayer] = configACharger.getJoueurAt(indexPlayer).clone();
		}
		currentPlayer = configACharger.getJoueurCourant();
		numeroDeTour = configACharger.getNumeroTour() + 1;
		panneau_Historique.setNumeroTourCourant(numeroDeTour-1);
		panneau_Jeu.setCoupsPrecedents(configACharger.getCoupsPrecedents());
		panneau_Jeu.setNotifications(Constantes.Message.auTourDe(currentPlayer));
	
		// Mise a jour graphique du Panneau_Plateau et du Panneau_Historique
		panneau_Jeu.repaint();
		panneau_Historique.repaint();
		
		// Attente du tour du joueur courant de la configuration chargee
		tabPlayers[currentPlayer].attendCoup();
		
		
		
		System.out.println(" ===== FIN Chargement Tour V2 ===== \n");
	}

	/*	
	public void annulerTour (){
		System.out.println("\n ===== Annulation du tour ===== ");
		System.out.println("numeroDeTour : " + numeroDeTour);
//		if ( numeroDeTour > 0 ){
			Configuration dernierTour = historiqueDeTours.get(numeroDeTour-1);
			
			System.out.println("numero du tour recupere : " + dernierTour.getNumeroTour());
			
			for (int numeroPlayer = 0; numeroPlayer < tabPlayers.length; numeroPlayer++){
				tabPlayers[numeroPlayer] = dernierTour.getJoueurAt(numeroPlayer).clone();
			}
			plateauDeJeu = dernierTour.getPlateauDuTour().clone();
			nbActions = 4;
			panneau_Jeu.repaint();
			tabPlayers[currentPlayer].attendCoup();
//		}
		System.out.println(" ===== FIN Annulation du tour ===== \n");
	}
*/
/*
	public void chargerTour (int numeroTourACharger){
		System.out.println("\n ===== Chargement d'un Tour ===== ");
		
		int indexTour = numeroTourACharger + historiqueDeTours.getNbConfigsPrecedentes();
		System.out.println("numTourActif : " + indexTour);
		
		if ( indexTour < historiqueDeTours.size()-1 ){
			System.out.println("chargement d'un tour anterieur");
		}
		else {
			System.out.println("chargement du dernier tour joue");
		}
			
		Configuration configACharger = historiqueDeTours.get(indexTour);
		
		plateauDeJeu = configACharger.getPlateauDuTour().clone();
		pioche = configACharger.getPiocheDuTour().clone();
		
		tabPlayers = new Joueur[configACharger.getNombreJoueurs()];
		for (int numeroJoueur = 0; numeroJoueur < tabPlayers.length; numeroJoueur++){
			tabPlayers[numeroJoueur] = configACharger.getJoueurAt(numeroJoueur).clone();
		}
		
		currentPlayer = configACharger.getJoueurCourant();
		
		numeroDeTour = configACharger.getNumeroTour();
		
		System.out.println("joueur courant charge : " + currentPlayer);
		
		panneau_Jeu.setCoupsPrecedents(configACharger.getCoupsPrecedents());
		viderCoupsPrecedents();
		panneau_Jeu.setNotifications(Constantes.Message.auTourDe(currentPlayer));
		
		// Mise a jour graphique du Panneau_Plateau et du Panneau_Historique
		panneau_Jeu.repaint();
		panneau_Historique.repaint();
		
		// Attente du tour du joueur courant de la configuration chargee
		tabPlayers[currentPlayer].attendCoup();

		System.out.println(" ===== FIN Chargement d'un Tour ===== \n");
	}
*/	
	public void montrerCoupsJoues (int numeroTourAMontrer){
		int numTourActif = numeroTourAMontrer + historiqueDeTours.getNbConfigsPrecedentes();
		Configuration configARecuperer = historiqueDeTours.get(numTourActif);
		
		panneau_Jeu.comparateurDePlateau(configARecuperer.getPlateauDuTour());
		panneau_Jeu.chargerCoupsHistoriques(configARecuperer.getCoupsPrecedents());
	}
	public void effacerCoupsJoues (){
		panneau_Jeu.comparateurDePlateau(null);
		panneau_Jeu.effacerCoupsHistoriques();
	}
	
	/*
	 * Methodes Private du Moteur
	 */
	/**
	 * Vérifie si le coup c peut être jouer dans la configuration actuelle
	 * @param c
	 * @return
	 */
	private boolean coupValide (Coup c) {
		if (nbActions > 2) {
			if (c.getType().equals(Coup.placement)) {
					// Vérifie si le joueur prend une tuile existante et vérifie si le placement est valide
				return (tabPlayers[currentPlayer].getMain().getTuileAt(c.getTuile()) != null) && 
						(plateauDeJeu.coupValide(tabPlayers[currentPlayer].getMain().getTuileAt(c.getTuile()),c));
			} else
				return false;
		}
		else {
			if (c.getType().equals(Coup.vol)) {
				// true si l'autre joueur est en phase 2, que la carte volé est différente de null
				// et que la main du joueur n'est pas pleine (2 remplacements ont été fait)
				// false sinon
				return ((!tabPlayers[(currentPlayer+1)%2].getMain().isFull()) && (tabPlayers[(currentPlayer+1)%2].getPhase() == 2)
					&&	 tabPlayers[(currentPlayer+1)%2].getMain().getTuileAt(c.getTuile()) != null);
			} else if (c.getType().equals(Coup.pioche)) {
				return true; // Rien d'autre à vérifier (Quand la main est pleine, piocher mettra fin au tour)
			} else
				return false;
		}
	}
	private void ajouterCoup (Coup coupJoue){
		int numeroCoup = 0;
		while ( numeroCoup < coupsPrecedents.length && coupsPrecedents[numeroCoup] != null ){
			numeroCoup++;
		}
		if ( numeroCoup < coupsPrecedents.length ){
			coupsPrecedents[numeroCoup] = coupJoue.clone();
		}
	}
	private void viderCoupsPrecedents (){
		for (int numeroCoup = 0; numeroCoup < coupsPrecedents.length; numeroCoup++){
			coupsPrecedents[numeroCoup] = null;
		}
	}
	
	private void effacerHistorique (int numeroTourDepartSuppression){
		System.out.println("\n ===== Effacer Historique =====");
		System.out.println("\ttour de depart : " + numeroTourDepartSuppression);
		System.out.println("\tavant taille de l'historique : " + historiqueDeTours.size());
		
		int compteur = historiqueDeTours.size() - numeroTourDepartSuppression;
		while ( compteur > 0 ){
			historiqueDeTours.retirer(historiqueDeTours.get(numeroTourDepartSuppression));
			compteur--;
		}
		
		panneau_Historique.repaint();
		
		System.out.println("\tapres taille de l'historique : " + historiqueDeTours.size());
		System.out.println(" ===== FIN Effacer Historique ===== \n");
	}
	
	private Objectifs[] tirerObjectif() {
		Random r = new Random();
		int num ;
		char[] val = new char[2];
		String current;
		Objectifs[] obj = new Objectifs[2];
		
		obj[0] = new Objectifs();
		obj[1] = new Objectifs();
		
		//Tirage ligne joueur 1
		obj[0].setLigne((r.nextInt(6))+1);
		
		//Tirage ligne joueur 2
		num = (r.nextInt(6))+1;
		while(num == obj[0].getLigne())	num = (r.nextInt(6)+1);
		obj[1].setLigne(num);
		
		//Lecture des objectifs
		try 
		{
			FileReader f = new FileReader(new File("ressources/cartes.txt"));
			BufferedReader br = new BufferedReader(f);
			current = br.readLine();
			while(Character.getNumericValue((current.charAt(0))) != 6)
			{
				if(Character.getNumericValue((current.charAt(0))) == obj[0].getLigne())
				{
					num = (r.nextInt(6)+1);
					for(int i = 1; i<num; i++)
					{
						br.readLine();
					}
					current = br.readLine();
					val[0] = current.charAt(0);
					val[1] = current.charAt(1);

					obj[0].ajouterEscales(valeurEscale(val[0]));
					obj[0].ajouterEscales(valeurEscale(val[1]));		
	
				}
				
				if(Character.getNumericValue((current.charAt(0))) == obj[1].getLigne())
				{
					num = (r.nextInt(6)+1);
					for(int i = 1; i<num; i++)
					{
						br.readLine();
					}
					current = br.readLine();
					val[0] = current.charAt(0);
					val[1] = current.charAt(1);
					
					obj[1].ajouterEscales(valeurEscale(val[0]));
					obj[1].ajouterEscales(valeurEscale(val[1]));
				}
				current = br.readLine();
			}
		} 	
		catch (Exception e) { e.printStackTrace(); }
		
		return obj;
	}
	private int valeurEscale(char c) {
		int val;
		
		switch(c)
		{
			case 'A':
				val = 1;
				break;
			case 'B':
				val = 2;
				break;
			case 'C':
				val = 3;
				break;
			case 'D':
				val = 4;
				break;
			case 'E':
				val = 5;
				break;
			case 'F':
				val = 6;
				break;
			case 'G':
				val = 7;
				break;
			case 'H':
				val = 8;
				break;
			case 'I':
				val = 9;
				break;
			case 'J':
				val = 10;
				break;
			case 'K':
				val = 11;
				break;
			case 'L':
				val = 12;
				break;
			case 'M':
				val = 13;
				break;
			default :
				val = 0;
				break;
		}
		return val;
	}

	private void initBidon (Objectifs[] tabObjectifs){
		for (int indexObjectif = 0; indexObjectif < tabObjectifs.length; indexObjectif++){
			tabObjectifs[indexObjectif] = new Objectifs();
		}
		tabObjectifs[0].setLigne(4);
		tabObjectifs[0].ajouterEscales(valeurEscale('G'));
		tabObjectifs[0].ajouterEscales(valeurEscale('A'));
		
		tabObjectifs[1].setLigne(6);
		tabObjectifs[1].ajouterEscales(valeurEscale('D'));
		tabObjectifs[1].ajouterEscales(valeurEscale('H'));
	}
	
}
