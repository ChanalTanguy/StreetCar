package sauvegardePackage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import joueurPackage.MainJoueur;
import mainPackage.Moteur;
import objectPackage.Pioche;
import objectPackage.tuilePackage.Tuile;

public class Sauvegarder {

	Moteur mot;
	int joueur;
	int numTour;
	Pioche pio;
	Tuile[][] plat;
	MainJoueur mainJactif, mainJinact;
	String name;
	Date dateCourante;
	
	Sauvegarder(Moteur moteur)
	{
		mot = moteur;
		dateCourante = new Date();
		name = "Sauvegarde du " + dateCourante + ".txt";
		save(this);
	}
	
	Sauvegarder(Moteur moteur, String nom)
	{
		mot = moteur;
		name = nom + ".txt";
		save(this);
	}
	
	private void save(Sauvegarder s)
	{
		joueur = mot.getcurrentPlayer();
		mainJactif = mot.getTabPlayers()[joueur].getMain();
		mainJinact =  mot.getTabPlayers()[1-joueur].getMain();
		pio = mot.getPioche();
		plat = mot.getPlateau().getPlateau();
		numTour = mot.getNumTour();
		//TODO Prendre en compte l'historique ?
		//TODO Prendre en compte les objectifs de chacun joueur.objectif
		
		try 
		{
			FileWriter f = new FileWriter(new File(name));
			f.write(new String(""+joueur));
			f.write(System.getProperty("line.separator"));
			f.write(new String(""+numTour));
			f.write(System.getProperty("line.separator"));
			
			f.write(mainJactif.toString());
			f.write("{Nord:[(Est;Sud), (Ouest;Nord), (Ouest;Sud), (Est;Nord)]}");
			f.write(System.getProperty("line.separator"));
			f.write(mainJinact.toString());
					
			f.write(pio.toString());
			f.write(System.getProperty("line.separator"));		
			
			for(int i=0; i<12; i++)
			{
				for(int j=0; j<12; j++)
				{
					if(plat[i][j] == null) f.write("{null} ");
					else f.write(plat[i][j].toString() + " ");
				}
				f.write(System.getProperty("line.separator"));
			}
			
			System.out.println("Fichier enregistré");
			f.close();
		}
		catch (IOException e) {e.printStackTrace();}
	
		
	}
	
	
}
