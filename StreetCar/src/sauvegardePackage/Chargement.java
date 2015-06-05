package sauvegardePackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import constantesPackages.Constantes;
import joueurPackage.MainJoueur;
import mainPackage.Moteur;
import objectPackage.Pioche;
import objectPackage.Plateau;
import objectPackage.tuilePackage.Connection;
import objectPackage.tuilePackage.Tuile;

public class Chargement {

	int joueur;
	int nbCartes;
	int numTour;
	int nbPioche;
	String tuileS;
	String pioche;
	String lignePlateau;
	MainJoueur main = new MainJoueur();
	Pioche pio = new Pioche();
	Plateau plat = new Plateau();
	Tuile tuile;
	
	public void charger(Moteur mot, String name)
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
			
			//Joueur actif
			joueur = Character.getNumericValue((br.readLine().charAt(0)));
			mot.setcurrentPlayer(joueur);
			
			//Num tour
			numTour = Character.getNumericValue((br.readLine().charAt(0)));
			mot.setNumTour(numTour);
			
			//Nb cartes dans main joueur actif
			nbCartes = Character.getNumericValue((br.readLine().charAt(0)));

			//Cartes dans main joueur actif
			for(int i = 0; i<nbCartes; i++)
			{
				tuileS = br.readLine();			
				main.setTuileAt(i, creerTuile(tuileS));
				mot.setMainPlayers(main.clone(), joueur);		
			}

			//Nb cartes dans main joueur inactif
			nbCartes =  Character.getNumericValue((br.readLine().charAt(0)));

			//Cartes dans main joueur inactif
			for(int i = 0; i<nbCartes; i++)
			{
				tuileS = br.readLine();
				tuile = creerTuile(tuileS);
				main.setTuileAt(i, creerTuile(tuileS));
				mot.setMainPlayers(main.clone(), 1-joueur);
			}

			nbPioche = Integer.parseInt(br.readLine());
			
			pioche = br.readLine();
			
			String[]tabPioche = pioche.split("} ");
			for(int i = 0; i<tabPioche.length; i++)
			{
				pio.add(creerTuile(tabPioche[i]+"}"));
			}
			
			for(int i = 0; i<12; i++)
			{
				lignePlateau = br.readLine();
				//System.out.println(lignePlateau);
				
				String[]tabPlat = lignePlateau.split("} ");
				for(int k = 0; k<tabPlat.length;k++)
				{
					//System.out.println(tabPlat[k]+"}");
				}
				
				for(int j=0; j<tabPlat.length;j++)
				{
					tabPlat[j]+="}";
					if(!(tabPlat[j].equals("{null}")) && !(tabPlat[j].equals("{Nord:[]}"))) 
					{
						//System.out.println(tabPlat[j]);
						//System.out.println(creerTuile(tabPlat[j]).toString());
						plat.setTuileAt(i+1, j+1, creerTuile(tabPlat[j]));
					}
				}
			}
			
			mot.setPlateau(plat);
			//System.out.println(mot.getTabPlayers()[0].getMain());  
			//Fonction de test d'affichage de plateau
			/*
			System.out.println(mot.getcurrentPlayer());
			System.out.println(mot.getTabPlayers()[mot.getcurrentPlayer()].getMain().toString());
			System.out.println(1-mot.getcurrentPlayer());
			System.out.println(mot.getTabPlayers()[1-mot.getcurrentPlayer()].getMain().toString());
			System.out.println(mot.getPioche().toString());
			
			for(int i = 1; i<13;i++)
			{
				for(int j=1; j<13; j++)
				{
					System.out.print(mot.getPlateau().getPlateau()[i][j]+ " ");
				}
				System.out.println("");
			}*/
		
				
			System.out.println("Fichier chargé");
			f.close();
		}
		catch (IOException e) {e.printStackTrace();}
	}

	//Permet de créer une tuile à partie d'une chaine de caractère de la forme {Orientation:[(Connection;Connection), (...;...)]}
	private Tuile creerTuile(String tuileS2) {
		Tuile t = new Tuile();
		String[] orient;
		String orientation;
		String[] connect;
		String[] connections;
		Connection con;
		
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
		
		return t;
	}

}
