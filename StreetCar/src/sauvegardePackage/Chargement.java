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
	String tuileS;
	MainJoueur main;
	
	void charger(Moteur mot, String name)
	{
		try 
		{
			FileReader f = new FileReader(new File(name));
			BufferedReader br = new BufferedReader(f);
			
			//Joueur actif
			joueur = Character.getNumericValue((f.read()));
			System.out.println("Joueur " + joueur);
			f.skip(1);
			
			//Nb cartes dans main joueur actif
			nbCartes = Character.getNumericValue(f.read());
			System.out.println("NbCartes actif " + nbCartes);
			f.read();
			
			//Cartes dans main joueur actif
			for(int i = 0; i<nbCartes; i++)
			{
				tuileS = br.readLine();
				System.out.println("Tuile " + i + " " + tuileS);
			}
			f.read();
			
			//Nb cartes dans main joueur inactif
			nbCartes = f.read();
			System.out.println("NbCartes inactif " + nbCartes);
			f.read();
			
			//Cartes dans main joueur inactif
			for(int i = 0; i<nbCartes; i++)
			{
				tuileS = br.readLine();
				System.out.println("Tuile " + i + " " + tuileS);
			}
			f.read();

			//Pioche
			/*
			f.write("Pioche :");
			f.write(System.getProperty("line.separator"));
			f.write(pio.toString());
			f.write(System.getProperty("line.separator"));
			
			f.write(System.getProperty("line.separator"));
			f.write("Plateau :");
			f.write(System.getProperty("line.separator"));
			
			for(int i=0; i<12; i++)
			{
				for(int j=0; j<12; j++)
				{
					if(plat[i][j] == null) f.write("null ");
					else f.write(plat[i][j].toString() + " ");
				}
				f.write(System.getProperty("line.separator"));
			}
			*/
			System.out.println("Fichier chargé");
			f.close();
		}
		catch (IOException e) {e.printStackTrace();}
	}
}
