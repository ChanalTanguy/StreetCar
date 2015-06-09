package iaPackage;

import joueurPackage.Coup;
import joueurPackage.JoueurIA;

public interface InterfaceIA {
	public Coup getCoup();
	
	public void setJoueur(JoueurIA j);
}
