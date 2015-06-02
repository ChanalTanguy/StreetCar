package sauvegardePackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import joueurPackage.MainJoueur;
import mainPackage.Moteur;
import objectPackage.Pioche;
import objectPackage.tuilePackage.Tuile;

public class Chargement {

	int joueur;
	int nbCartes;
	int numTour;
	String tuileS;
	String pioche;
	String lignePlateau;
	MainJoueur main;
	
	void charger(Moteur mot, String name)
	{
		try 
		{
			FileReader f = new FileReader(new File(name));
			BufferedReader br = new BufferedReader(f);
			
			//Joueur actif
			joueur = Character.getNumericValue((br.readLine().charAt(0)));
			System.out.println("Joueur " + joueur);
			
			//Num tour
			numTour = Character.getNumericValue((br.readLine().charAt(0)));
			System.out.println("Tour " + numTour);
			
			//Nb cartes dans main joueur actif
			nbCartes = Character.getNumericValue((br.readLine().charAt(0)));
			System.out.println("NbCartes actif " + nbCartes);
			
			//Cartes dans main joueur actif
			for(int i = 0; i<nbCartes; i++)
			{
				tuileS = br.readLine();
				System.out.println("Tuile " + i + " " + tuileS);
			}

			//Nb cartes dans main joueur inactif
			nbCartes =  Character.getNumericValue((br.readLine().charAt(0)));
			System.out.println("NbCartes inactif " + nbCartes);

			//Cartes dans main joueur inactif
			for(int i = 0; i<nbCartes; i++)
			{
				tuileS = br.readLine();
				System.out.println("Tuile " + i + " " + tuileS);
			}

			//Pioche à spliter en fonction du nombres de cartes dedans
			pioche = br.readLine();
			System.out.println(pioche);
			
			for(int i = 0; i<12; i++)
			{
				lignePlateau = br.readLine();
				System.out.println(lignePlateau);			
			}

			System.out.println("Fichier chargé");
			f.close();
		}
		catch (IOException e) {e.printStackTrace();}
	}
}
