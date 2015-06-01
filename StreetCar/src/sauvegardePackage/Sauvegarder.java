package sauvegardePackage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import javax.imageio.stream.FileImageOutputStream;

import joueurPackage.MainJoueur;
import mainPackage.Moteur;
import objectPackage.Pioche;
import objectPackage.tuilePackage.Tuile;

public class Sauvegarder {

	Moteur mot;
	int joueur;
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
	}
	
	Sauvegarder(Moteur moteur, String nom)
	{
		mot = moteur;
		name = nom + ".txt";
	}
	
	void save(Sauvegarder s)
	{
		joueur = mot.getcurrentPlayer();
		mainJactif = mot.getTabPlayers()[joueur].getMain();
		mainJinact =  mot.getTabPlayers()[1-joueur].getMain();
		pio = mot.getPioche();
		plat = mot.getPlateau().getPlateau();
		//TODO Prendre en compte l'historique ?
		//TODO Prendre en compte les objectifs de chacun
		
		try 
		{
			FileWriter f = new FileWriter(new File(name));

			f.write("Joueur actif : " + joueur);
			f.write(System.getProperty("line.separator"));
			
			f.write("Main joueur actif : " + mainJactif.toString());
			f.write(System.getProperty("line.separator"));
			
			f.write("Main joueur inactif : " + mainJinact.toString());
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
			
			System.out.println("Fichier enregistré");
			f.close();
		}
		catch (IOException e) {e.printStackTrace();}
	
		
	}
	
	
}
