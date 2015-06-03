package titrePackage;

import java.io.File;
import java.util.Vector;

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

		String[] listeFichiers;
			
		listeFichiers=f.list();
		
		trier(listeFichiers);
		
		
		for(int i = 0; i<listeFichiers.length; i++)
		{
			if(listeFichiers[i].endsWith(".txt")) listeFichiers[i] = listeFichiers[i].substring(0, listeFichiers[i].length()-4);
			else listeFichiers[i] = null;
		}
		
		return listeFichiers;
	}
	private void trier(String[] listeFichiers) {
		String x;
		int j;
		
		for(int i = 0; i<listeFichiers.length;i++)
		{
			x = listeFichiers[i];
			for(j = i; j>0 && listeFichiers[j-1].compareTo(x)>0;j--)
			{
				listeFichiers[j] = listeFichiers[j-1];
			}
			listeFichiers[j] = x;
		}
		
	}
}
