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
	int typeJoueur;
	Pioche pio;
	String[] date;
	String dateFinale = null;
	Tuile[][] plat;
	MainJoueur mainJactif, mainJinact;
	String name, objectif1, objectif2;
	Date dateCourante;
	
	public Sauvegarder(Moteur moteur)
	{
		mot = moteur;
		dateCourante = new Date();
		date = dateCourante.toString().split(":");
		dateFinale = date[0];
		for(int i = 1; i<date.length; i++)
		{
			dateFinale += "_";
			dateFinale += date[i];		
		}
		date = dateFinale.toString().split(" ");
		dateFinale = date[0];
		for(int i = 1; i<date.length; i++)
		{
			dateFinale += "_";
			dateFinale += date[i];		
		}
		name = "save/Sauvegarde_du_" + dateFinale + ".txt";
		save(this);
	}
	
	Sauvegarder(Moteur moteur, String nom)
	{
		mot = moteur;
		name = nom + ".txt";
		save(this);
	}
	
	private void save(Sauvegarder s) //TODO faire les escales
	{
		joueur = mot.getcurrentPlayer();
		objectif1 = mot.getTabPlayers()[joueur].getObjectifs().toString();
		objectif2 =  mot.getTabPlayers()[1-joueur].getObjectifs().toString();
		mainJactif = mot.getTabPlayers()[joueur].getMain();
		mainJinact =  mot.getTabPlayers()[1-joueur].getMain();
		pio = mot.getPioche();
		plat = mot.getPlateau().getPlateau();
		numTour = mot.getNumTour();
		//TODO Prendre en compte historique
		
		try 
		{
			FileWriter f = new FileWriter(new File(name));
			
			//Tour
			f.write(new String(""+numTour));
			f.write(System.getProperty("line.separator"));
			
			//Joueur actif
			f.write(new String(""+joueur));
			f.write(System.getProperty("line.separator"));
			
			//Type joueur actif
			typeJoueur = mot.getTabPlayers()[joueur].getType();
			f.write(new String("")+typeJoueur);
			f.write(System.getProperty("line.separator"));
			
			//Objectif joueur actif
			f.write("" +objectif1); 
			f.write(System.getProperty("line.separator"));
			
			//Main joueur actif
			f.write(mainJactif.toString());
			
			//Joueur inactif
			f.write(new String(""+(1-joueur)));	
			f.write(System.getProperty("line.separator"));
			
			//Type joueur inactif
			typeJoueur = mot.getTabPlayers()[1-joueur].getType();
			f.write(new String("")+typeJoueur);
			f.write(System.getProperty("line.separator"));
			
			//Objectif joueur inactif
			f.write("" +objectif2); 
			f.write(System.getProperty("line.separator"));
			
			//Main joueur inactif
			f.write(mainJinact.toString());
			
			//Pioche
			f.write(pio.toString());
			f.write(System.getProperty("line.separator"));		
			
			//Plateau
			for(int i=1; i<13; i++)
			{
				for(int j=1; j<13; j++)
				{
					if(plat[i][j] == null) f.write("{null} ");
					else 
					{
						if(plat[i][j].getEscaleLiee() == 0) //Si une tuile n'est pas liée
						{
							f.write(plat[i][j].toString() + " ");
						}
						else
						{
							f.write(plat[i][j].getEscaleLiee()+plat[i][j].toString() +  " ");
						}
					}
				}
				f.write(System.getProperty("line.separator"));
			}
			
			//Historique
			//f.write(mot.getHistorique().toString());
			
			
			System.out.println("Fichier enregistré");
			f.close();
		}
		catch (IOException e) {e.printStackTrace();}
	
		
	}
	
	
}
