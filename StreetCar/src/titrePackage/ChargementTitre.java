package titrePackage;

import java.io.File;

public class ChargementTitre {

	String reper = "save";
	File f;
	ChargementTitre()
	{
		f = new File(reper);
	}
	ChargementTitre(String repertoire)
	{
		f = new File(repertoire);
	}
	
	public String[] listerRepertoire(){

		String [] listeFichiers;
			
		listeFichiers=f.list();
		for(int i = 0; i<listeFichiers.length; i++)
		{
			if(listeFichiers[i].endsWith(".txt")) listeFichiers[i] = listeFichiers[i].substring(0, listeFichiers[i].length()-4);
			else listeFichiers[i] = null;
		}
		
		return listeFichiers;
	}
}
