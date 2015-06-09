package sauvegardePackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import joueurPackage.Joueur;
import joueurPackage.JoueurHumain;
import joueurPackage.JoueurIA;
import joueurPackage.MainJoueur;
import joueurPackage.Objectifs;
import mainPackage.Moteur;
import objectPackage.Pioche;
import objectPackage.Plateau;
import objectPackage.tuilePackage.Connection;
import objectPackage.tuilePackage.Tuile;

public class Chargement {

	int joueur;
	int joueurInactif;
	int nbCartes;
	int numTour;
	int nbPioche;
	int ligne;
	int typeJoueur;
	Boolean j1 = false;
	Boolean j2 = false;
	int[] objectifs = new int[3];
	String[] objectif1;
	String[] objectif2;
	String tuileS;
	String pioche;
	String lignePlateau;
	MainJoueur main = new MainJoueur();
	Pioche pio = new Pioche();
	Plateau plat = new Plateau();
	JoueurHumain joueurH1, joueurH2;
	Joueur joueurIA1, joueurIA2;
	Tuile tuile;
	
	public void charger(Moteur mot, String name) //TODO faire en sorte de lire les escales
	{
		try 
		{		
			if(!name.contains(".txt"))
			{
				name = name+".txt";
				name = "save/"+name;
			}
			FileReader f = new FileReader(new File(name));
			BufferedReader br = new BufferedReader(f);
			
			//Num tour
			numTour = Character.getNumericValue((br.readLine().charAt(0)));
			mot.setNumTour(numTour);
			
			//Joueur actif
			joueur = Character.getNumericValue((br.readLine().charAt(0)));
			mot.setcurrentPlayer(joueur);
			
			//Type du joueur actif
			typeJoueur = Character.getNumericValue((br.readLine().charAt(0)));
			mot.getTabPlayers()[joueur].setType(typeJoueur);
		
			//Ligne joueur actif
			ligne = Character.getNumericValue((br.readLine().charAt(0)));
			
			//Objectifs joueur actif
			objectif1 = br.readLine().split(" ");
			objectifs[0] = Integer.parseInt(objectif1[0]);
			objectifs[1] = Integer.parseInt(objectif1[1]);
			objectifs[2] = Integer.parseInt(objectif1[2]);	
			
			//Nb cartes dans main joueur actif
			nbCartes = Character.getNumericValue((br.readLine().charAt(0)));

			//Cartes dans main joueur actif
			for(int i = 0; i<nbCartes; i++)
			{
				tuileS = br.readLine();			
				main.setTuileAt(i, creerTuile(tuileS));
			}
			
			if(typeJoueur == 0) //joueur humain
			{
				joueurH1 = new JoueurHumain(mot, new Objectifs(ligne, objectifs));
				joueurH1.setMain(main.clone());
			}
			else
			{
				switch(typeJoueur)
				{
				case 1 :
					joueurIA1 = JoueurIA.JoueurIAFacile(mot, new Objectifs(ligne, objectifs));
					break;
				case 2 :
					joueurIA1 = JoueurIA.JoueurIAMoyen(mot, new Objectifs(ligne, objectifs));
					break;
				case 3 :
					joueurIA1 = JoueurIA.JoueurIADifficile(mot, new Objectifs(ligne, objectifs));
					break;
				}

				joueurIA1.setMain(main.clone());
				j1 = true;
			}
			
			//Joueur inactif
			joueurInactif = Character.getNumericValue((br.readLine().charAt(0)));
			
			//Type joueur inactif
			typeJoueur = Character.getNumericValue((br.readLine().charAt(0)));
			
			//Ligne joueur inactif
			ligne = Character.getNumericValue((br.readLine().charAt(0)));
			
			//Objectifs joueur inactif
			objectif2 = br.readLine().split(" ");
			objectifs[0] = Integer.parseInt(objectif2[0]);
			objectifs[1] = Integer.parseInt(objectif2[1]);
			objectifs[2] = Integer.parseInt(objectif2[2]);
			
			//Nb cartes dans main joueur inactif
			nbCartes =  Character.getNumericValue((br.readLine().charAt(0)));

			//Cartes dans main joueur inactif
			for(int i = 0; i<nbCartes; i++)
			{
				tuileS = br.readLine();
				main.setTuileAt(i, creerTuile(tuileS));
			}
			
			if(typeJoueur == 0) //joueur humain
			{
				joueurH2 = new JoueurHumain(mot, new Objectifs(ligne, objectifs));
				joueurH2.setMain(main.clone());
			}
			else
			{
				switch(typeJoueur)
				{
				case 1 :
					joueurIA2 = JoueurIA.JoueurIAFacile(mot, new Objectifs(ligne, objectifs));
					break;
				case 2 :
					joueurIA2 = JoueurIA.JoueurIAMoyen(mot, new Objectifs(ligne, objectifs));
					break;
				case 3 :
					joueurIA2 = JoueurIA.JoueurIADifficile(mot, new Objectifs(ligne, objectifs));
					break;

				}
				joueurIA2.setMain(main.clone());
				j2 = true;
			}
			
			if(!j1)
			{
				if(!j2)
				{
					mot.setPlayers(joueurH1, joueurH2);
				}
				else
				{
					mot.setPlayers(joueurH1, joueurIA2);
				}
			}
			else
			{
				if(!j2)
				{
					mot.setPlayers(joueurIA1, joueurH2);
				}
				else
				{
					mot.setPlayers(joueurIA1, joueurIA2);
				}
			}

			//Pioche
			nbPioche = Integer.parseInt(br.readLine());
			pioche = br.readLine();
			
			
			String[]tabPioche = pioche.split("} ");
			for(int i = 0; i<tabPioche.length; i++)
			{
				pio.add(creerTuile(tabPioche[i]+"}"));
				
			}
			
			mot.setPioche(pio);;

			//Plateau
			for(int i = 0; i<12; i++)
			{
				lignePlateau = br.readLine();
				String[]tabPlat = lignePlateau.split("} ");
				
				for(int j=0; j<tabPlat.length;j++)
				{
					tabPlat[j]+="}";

					if(!(tabPlat[j].equals("{null}")) && !(tabPlat[j].equals("{Nord:[]}"))) 
					{
						plat.setTuileAt(i+1, j+1, creerTuile(tabPlat[j]));
					}
				}
			}
			
			mot.setPlateau(plat);
			//Historique
			
			System.out.println("Fichier chargé");
			br.close();
			f.close();
		}
		catch (IOException e) {e.printStackTrace();}
	}

	//Permet de créer une tuile à partie d'une chaine de caractère de la forme {Orientation:[(Connection;Connection), (...;...)]}
	private Tuile creerTuile(String tuileS2) {
		int linked = 0;
		int val;
		Tuile t = new Tuile();
		String[] orient;
		String orientation;
		String[] connect;
		String[] connections;
		Connection con;
		
		//Escale liée
		val = Character.getNumericValue(tuileS2.charAt(1));
		if(val >= 0 && val <=2) //Cela veut dire que l'on a un nombre à deux chiffres
		{
			val += Character.getNumericValue(tuileS2.charAt(0))*10;
			linked = val;
			tuileS2 = tuileS2.substring(2, tuileS2.length());
		}
		
		val = Character.getNumericValue(tuileS2.charAt(0));
		if(val >= 1 && val <=9)
		{
			linked = val;
			tuileS2 = tuileS2.substring(1, tuileS2.length());
		}
		
		
		//Orientation générale
		orient = tuileS2.split(":");
		orientation = orient[0].substring(1);
		t.setOrientation(orientation);
		
		//Connections
		connect = orient[1].split(","); //Ce split est là pour les multi-connections
		
		for(int i = 0; i<connect.length; i++)
		{
			connections = connect[i].substring(2,connect[i].length()-1).replace(")", "").replace("]", "").split(";");
			con = new Connection(connections[0], connections[1]);
			t.getListeConnections().add(con);
		}
		
		t.rechercheImage();
		t.setEscaleLiee(linked);
	
		return t;
	}

}
