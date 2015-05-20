package iaPackage;

import mainPackage.Moteur;
import joueurPackage.Coup;

public interface InterfaceIA {
	public Coup getCoup(Moteur moteur);
}
