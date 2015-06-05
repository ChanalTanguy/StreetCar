package mainPackage;

import graphique.Panneau_Historique;
import graphique.Panneau_Plateau;
import historiqPackage.Configuration;
import historiqPackage.Historique;

import java.awt.Point;

import joueurPackage.Coup;
import joueurPackage.Joueur;
import joueurPackage.JoueurHumain;
import joueurPackage.JoueurIA;
import joueurPackage.MainJoueur;
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
	
	private int numeroDeTour = 0;
	private Historique historiqueDeTours;
	
	private Point[] coupsPrecedents;
	private Point[] coupsFuturs;
	
	/*
	 * Constructeur
	 */
	public Moteur(Plateau referencePlateau) {
		tabPlayers = new Joueur[2];
		tabPlayers[0] = new JoueurHumain(this,1);
		tabPlayers[0].setLigne(1);
		tabPlayers[1] = new JoueurIA(this,4);
		tabPlayers[1].setLigne(4);
		
		currentPlayer = 0;
		plateauDeJeu = referencePlateau;
		nbActions = 4;
		
		pioche = new Pioche();
		pioche.initialisation();
		pioche.shuffle();
		
		historiqueDeTours = new Historique();
		coupsPrecedents = new Point[Constantes.Coup.nbMaxPlacements];
		coupsFuturs = new Point[Constantes.Coup.nbMaxPlacements];
		
		Configuration configurationInitiale = new Configuration (tabPlayers, currentPlayer, plateauDeJeu, pioche, numeroDeTour++, historiqueDeTours, coupsPrecedents);
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
		tabPlayers[0] = j1;
		tabPlayers[0].setLigne(1);
		tabPlayers[1] = j2;
		tabPlayers[1].setLigne(4);
		currentPlayer = 0;
		
		historiqueDeTours.clear();
		Configuration configurationInitiale = new Configuration (tabPlayers, currentPlayer, plateauDeJeu, pioche, numeroDeTour++, historiqueDeTours, coupsPrecedents);
		historiqueDeTours.add(configurationInitiale);
		historiqueDeTours.get(0).setHistorique(historiqueDeTours);
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
			if (coupChoisi.getType().equals(Constantes.Coup.placement)) {
				tabPlayers[currentPlayer].jouerTuileSurPlateau(coupChoisi.getTuile(), coupChoisi.getCoordonnee().x, coupChoisi.getCoordonnee().y, plateauDeJeu);
				nbActions--;
				
				panneau_Jeu.ajouterCoup(coupChoisi.getCoordonnee());
				ajouterCoup(coupChoisi.getCoordonnee());
				
				coupSimultane = null;
				
			} else if (coupChoisi.getType().equals(Constantes.Coup.vol)) {
				tabPlayers[currentPlayer].volerTuile(coupChoisi.getTuile(), tabPlayers[(currentPlayer+1)%2]);
				nbActions--;
				coupSimultane = null;
			} else if (coupChoisi.getType().equals(Constantes.Coup.pioche)) {
				if (!pioche.isEmpty())
					tabPlayers[currentPlayer].piocher(pioche);
				nbActions = 0;
				coupSimultane = null;
			} else {
				System.out.println("Erreur : Cas non géré"); // Cas sensé inateignable
			}
			
			if (nbActions == 0) {
				System.out.println("\n ===== Jouer Coup Pioche ===== ");
				
				currentPlayer = (currentPlayer+1)%2;
				nbActions = 4;
				
				// Mise a Jour de l'historique de tours
				// creation de la configuration a ajouter
				Configuration config_a_Ajouter = new Configuration (tabPlayers, currentPlayer, plateauDeJeu, pioche, numeroDeTour++, historiqueDeTours, coupsPrecedents);
				
				// s'il existe deja au moins un tour dans le "futur", on compare les coups actuels joues et ceux qui auraient ete joues
				if ( numeroDeTour < historiqueDeTours.size()-1 ){
					Configuration configFuturAnticipe = historiqueDeTours.get(numeroDeTour);
					int numeroCoup = 0;
					
					while ( numeroCoup < configFuturAnticipe.getCoupsPrecedents().length ){
						if ( configFuturAnticipe.getCoupsPrecedents()[numeroCoup] != null ){
							coupsFuturs[numeroCoup] = (Point) configFuturAnticipe.getCoupsPrecedents()[numeroCoup].clone();
						}
						else {
							coupsFuturs[numeroCoup] = (Point) configFuturAnticipe.getCoupsPrecedents()[numeroCoup];
						}
						numeroCoup++;
					}
					
					for (int index = 0; index < coupsFuturs.length; index++){
						System.out.print("APRES coupsFuturs[" + index + "] : ");
						try{
							System.out.println(coupsFuturs[index].toString());
						} catch (Exception e){
							System.out.println("null");
						}
					}
					
					for (int index = 0; index < coupsPrecedents.length; index++){
						System.out.print("APRES coupsPrecedents[" + index + "] : ");
						try{
							System.out.println(coupsPrecedents[index].toString());
						} catch (Exception e){
							System.out.println("null");
						}
					}
					
					System.out.println(" ===== Tour charge au prealable ===== ");
					System.out.println("numeroDeTour : " + numeroDeTour);
					
					if ( !tableauDeCoupsVide(coupsFuturs) ){
						if ( coupsJoues_AnticipesIdentiques(coupsPrecedents, coupsFuturs) ){
							System.out.println("les memes coups seront joues");
						}
						else {
							System.out.println("des coups differents seront joues");
							effacerHistorique(numeroDeTour);
							historiqueDeTours.ajouter(config_a_Ajouter);
						}
					}
					else {
						System.out.println("tour courant joue");
					}
				}
				else {
					historiqueDeTours.ajouter(config_a_Ajouter);
					System.out.println("tour joue normalement");
				}
				
				
				
				
				historiqueDeTours.last().setHistorique(historiqueDeTours);
				
				panneau_Jeu.afficherCoupsPrecedents();
				panneau_Jeu.effacerCoupsJoues();
				effacerCoupsPrecedents();
				panneau_Jeu.setCoupSimultaneEnAction(null);
				
				panneau_Historique.repaint();

				System.out.println(" ===== FIN Jouer Coup Pioche ===== ");
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
				
				panneau_Jeu.ajouterCoup(coupSimultane.getCoordonnee());
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
					ajouterCoup(coupChoisi.getCoordonnee());
					// => on ajoute aussi le coup simultane precedemment joue
					ajouterCoup(coupSimultane.getCoordonnee());
					// => on ajouter les 2 coups joues dans le panneau de jeu pour les afficher en coup Actif
					panneau_Jeu.ajouterCoup(coupChoisi.getCoordonnee());
					panneau_Jeu.ajouterCoup(coupSimultane.getCoordonnee());
					
					msg = Constantes.Message.finDeTour(currentPlayer);
				} else {
					// Erreur même tuile selectionné pour la mettre au même endroit
					msg = Constantes.Message.poseImpossible;
				}
				coupSimultane = null;
			}
		}
		else {
			if (nbActions < 3)
				msg = Constantes.Message.finDeTour(currentPlayer);
			else if (coupChoisi.getType().equals(Constantes.Coup.placement))
				msg = Constantes.Message.poseImpossible;
			else if (coupChoisi.getType().equals(Constantes.Coup.vol))
				msg = Constantes.Message.volImpossible;
			else if (coupChoisi.getType().equals(Constantes.Coup.pioche))
				msg = Constantes.Message.piocheImpossible;
			else {
				msg = Constantes.Message.tramImpossible; // N'EST PAS SENSE ARRIVER !
			}
			System.out.println("Mauvais coup");
			coupSimultane = null;
		}
		panneau_Jeu.setNotifications(msg);
		panneau_Jeu.repaint();
		
		tabPlayers[currentPlayer].attendCoup();

	}

	// Methodes de Kévin
	public void annulerTour (){
		Configuration dernierTour = historiqueDeTours.last();

		for (int numeroPlayer = 0; numeroPlayer < tabPlayers.length; numeroPlayer++){
			tabPlayers[numeroPlayer] = dernierTour.getJoueurAt(numeroPlayer).clone();
		}
		plateauDeJeu = dernierTour.getPlateauDuTour().clone();
		nbActions = 4;
		panneau_Jeu.repaint();
		tabPlayers[currentPlayer].attendCoup();
	}
	public void chargerTour (int numeroTourACharger){
//		int numTourActif = numeroTourACharger + historiqueDeTours.getNbConfigsPrecedentes();
		
		numeroDeTour = numeroTourACharger + historiqueDeTours.getNbConfigsPrecedentes();;
		
		if ( numeroDeTour < historiqueDeTours.size()-1 ){
			System.out.println("\n ===== Chargement d'un Tour ===== ");
			System.out.println("numTourActif : " + numeroDeTour);
			System.out.println("historique.size : " + historiqueDeTours.size());
/*			
			for (int index = 0; index < coupsFuturs.length; index++){
				System.out.print("AVANT coupsFuturs[" + index + "] : ");
				try{
					System.out.println(coupsFuturs[index].toString());
				} catch (Exception e){
					System.out.println("null");
				}
			}
*/			
			
			Configuration configACharger = historiqueDeTours.get(numeroDeTour);
			Configuration configFuturAnticipe = historiqueDeTours.get(numeroDeTour+1);
			
			plateauDeJeu = configACharger.getPlateauDuTour().clone();
			pioche = configACharger.getPiocheDuTour().clone();
			
			tabPlayers = new Joueur[configACharger.getNombreJoueurs()];
			for (int numeroJoueur = 0; numeroJoueur < tabPlayers.length; numeroJoueur++){
				tabPlayers[numeroJoueur] = configACharger.getJoueurAt(numeroJoueur).clone();
			}
			
			currentPlayer = configACharger.getJoueurCourant();
//			historiqueDeTours = configACharger.getHistorique().clone();
			
			int numeroCoup = 0;
			while ( numeroCoup < coupsPrecedents.length ){
				if ( configACharger.getCoupsPrecedents()[numeroCoup] != null ){
					coupsFuturs[numeroCoup] = (Point) configFuturAnticipe.getCoupsPrecedents()[numeroCoup].clone();
				}
				else {
					coupsFuturs[numeroCoup] = (Point) configFuturAnticipe.getCoupsPrecedents()[numeroCoup];
				}
				numeroCoup++;
			}
			
			panneau_Jeu.setCoupsPrecedents(configACharger.getCoupsPrecedents());
			effacerCoupsPrecedents();
			panneau_Jeu.setNotifications(Constantes.Message.auTourDe(currentPlayer));
			
			// Mise a jour graphique du Panneau_Plateau et du Panneau_Historique
			panneau_Jeu.repaint();
			panneau_Historique.repaint();
			
			// Attente du tour du joueur courant de la configuration chargee
			tabPlayers[currentPlayer].attendCoup();
/*			
			for (int index = 0; index < coupsFuturs.length; index++){
				System.out.print("APRES coupsFuturs[" + index + "] : ");
				try{
					System.out.println(coupsFuturs[index].toString());
				} catch (Exception e){
					System.out.println("null");
				}
			}
*/			

			System.out.println("\t tour actif : " + numeroDeTour);
			
			System.out.println(" ===== FIN Chargement d'un Tour ===== \n");
		}
		else {
			System.out.println("le tour a charger est le dernier tour joué");
		}
	}
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
			if (c.getType().equals(Constantes.Coup.placement)) {
					// Vérifie si le joueur prend une tuile existante et vérifie si le placement est valide
				return (tabPlayers[currentPlayer].getMain().getTuileAt(c.getTuile()) != null) && 
						(plateauDeJeu.coupValide(tabPlayers[currentPlayer].getMain().getTuileAt(c.getTuile()),c));
			} else
				return false;
		}
		else {
			if (c.getType().equals(Constantes.Coup.vol)) {
				// true si l'autre joueur est en phase 2, que la carte volé est différente de null
				// et que la main du joueur n'est pas pleine (2 remplacements ont été fait)
				// false sinon
				return ((!tabPlayers[(currentPlayer+1)%2].getMain().isFull()) && (tabPlayers[(currentPlayer+1)%2].getPhase() == 2)
					&&	 tabPlayers[(currentPlayer+1)%2].getMain().getTuileAt(c.getTuile()) != null);
			} else if (c.getType().equals(Constantes.Coup.pioche)) {
				return true; // Rien d'autre à vérifier (Quand la main est pleine, piocher mettra fin au tour)
			} else
				return false;
		}
	}
	private void ajouterCoup (Point coupJoue){
		int numeroCoup = 0;
		while ( numeroCoup < coupsPrecedents.length && coupsPrecedents[numeroCoup] != null ){
			numeroCoup++;
		}
		if ( numeroCoup < coupsPrecedents.length ){
			coupsPrecedents[numeroCoup] = coupJoue;
		}
	}
	private void effacerCoupsPrecedents (){
		for (int numeroCoup = 0; numeroCoup < coupsPrecedents.length; numeroCoup++){
			coupsPrecedents[numeroCoup] = null;
		}
	}
	private boolean coupsJoues_AnticipesIdentiques (Point[] prochainsCoups, Point[] coupsEnregistres){
/*		System.out.println("\n ===== Comparaison CoupsJoues_Anticipes ===== ");
		
		for (int index = 0; index < prochainsCoups.length; index++){
			System.out.print("prochainsCoups[" + index + "] : ");
			try{
				System.out.println(prochainsCoups[index].toString());
			} catch (Exception e){
				System.out.println("null");
			}
		}
		for (int index = 0; index < coupsEnregistres.length; index++){
			System.out.print("coupsEnregistres[" + index + "] : ");
			try {
				System.out.println(coupsEnregistres[index].toString());
			} catch (Exception e){
				System.out.println("null");
			}
		}
*/		
		boolean coupsIdentiques = true;
		
		int numeroProchainCoup = 0;
		while ( numeroProchainCoup < prochainsCoups.length && coupsIdentiques ){
			int numeroCoupEnregistre = 0;
			boolean recherche = false;
			while ( numeroCoupEnregistre < coupsEnregistres.length && !recherche ){
				recherche = prochainsCoups[numeroProchainCoup].equals(coupsEnregistres[numeroCoupEnregistre]);
				numeroCoupEnregistre++;
			}
			coupsIdentiques = coupsIdentiques && recherche;
			numeroProchainCoup++;
		}
		

//		System.out.println("\n ===== FIN Comparaison CoupsJoues_Anticipes ===== ");
		
		return coupsIdentiques;
	}
	
	private void effacerHistorique (int numeroDeTour){
		
	}
	
	private boolean tableauDeCoupsVide (Point[] tab){
		boolean estVide = true;
		
		for (int index = 0; index < tab.length; index++){
			estVide = estVide && tab[index] == null;
		}
		
		return estVide;
	}
	
	
}
