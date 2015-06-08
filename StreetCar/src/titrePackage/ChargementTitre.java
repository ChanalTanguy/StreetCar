package titrePackage;

import java.io.File;
import java.util.Vector;

public class ChargementTitre {

	String reper = "save";
	File f;
	public ChargementTitre()
	{
		f = new File(reper);
	}
	ChargementTitre(String repertoire)
	{
		f = new File(repertoire);
	}
	
	public String[] listerRepertoire(){

		String[] listeTxt;
		File[] listeFiles;
			
		listeTxt = f.list();
		listeFiles = f.listFiles();
		
		trier(listeTxt, listeFiles);
		
		for(int i = 0; i<listeTxt.length; i++)
		{
			if(listeTxt[i].endsWith(".txt")) listeTxt[i] = listeTxt[i].substring(0, listeTxt[i].length()-4);
			else listeTxt[i] = null;
		}
		
		if(listeTxt.length>=10)
		{
			for(int i = 10; i<listeTxt.length; i++)
			{
				listeFiles[i].delete();
			}
		}
		
		return listeTxt;
	}
	private void trier(String[] listeTxt, File[] listeFiles) {
		String x;
		File f;
		int j;
		
		for(int i = 0; i<listeTxt.length;i++)
		{
			x = listeTxt[i];
			f = listeFiles[i];
			for(j = i; j>0 && listeTxt[j-1].compareTo(x)<0;j--)
			{
				listeTxt[j] = listeTxt[j-1];
				listeFiles[j] = listeFiles[j-1];
			}
			listeTxt[j] = x;
			listeFiles[j] = f;
		}
		
	}
}
