package historiqPackage;

import java.util.ArrayList;

public class Historique extends ArrayList<Configuration>{
	int nombreConfigsPrecedentes, nombreConfigsSuivantes;
	
	/*
	 * Constructeur
	 */
	public Historique (){
		super();
		nombreConfigsPrecedentes = 0;
		nombreConfigsSuivantes = 0;
	}
	/*
	 * FIN Constructeur
	 */
	
	/*
	 * Accesseurs
	 */
	public int getNbConfigsPrecedentes (){
		return nombreConfigsPrecedentes;
	}
	public int getNbConfigsSuivantes (){
		return nombreConfigsSuivantes;
	}
	/*
	 * FIN Accesseurs
	 */
	
	/*
	 * Methodes Public de Historique
	 */
	public Configuration first (){
		return this.get(0);
	}
	public Configuration last (){
		return this.get(this.size()-1);
	}
	
	public void incrPrecedentes (){
		nombreConfigsPrecedentes++;
	}
	public void incrSuivantes (){
		nombreConfigsSuivantes++;
	}
	public void decrPrecedentes (){
		nombreConfigsPrecedentes--;
	}
	public void decrSuivantes (){
		nombreConfigsSuivantes--;
	}
	
	public String toString (){
		String chaine_resultat = "";
		for (int numeroConfig = 0; numeroConfig < this.size(); numeroConfig++){
			chaine_resultat += this.get(0).toString() + "\n";
		}
		return chaine_resultat;
	}
}
