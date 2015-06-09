package historiqPackage;

import java.util.ArrayList;

public class Historique extends ArrayList<Configuration>{
	private int nombreConfigsPrecedentes, nombreConfigsSuivantes;
	private int nombreMaxOnglets;
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
	
	public void setNbConfigsPrecedentes (int newValue){
		nombreConfigsPrecedentes = newValue;
	}
	public void setNbConfigsSuivantes (int newValue){
		nombreConfigsSuivantes = newValue;
	}
	public void setNbMaxOnglets (int newValue){
		nombreMaxOnglets = newValue;
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
	
	public void defilementVersHaut (){
		if ( nombreConfigsPrecedentes > 0){
			nombreConfigsPrecedentes--;
			nombreConfigsSuivantes++;
		}
	}
	public void defilementVersBas (){
		if ( nombreConfigsSuivantes > 0 ){
			nombreConfigsPrecedentes++;
			nombreConfigsSuivantes--;
		}
	}
	
	public boolean ajouter (Configuration config){
		boolean ajoutValide = add(config);
		if (ajoutValide){
			if ( this.size() > nombreMaxOnglets ) {
				nombreConfigsPrecedentes++;
			}
		}
		return ajoutValide;
	}
	public boolean retirer (Configuration config){
		boolean retraitValide = remove(config);
		if (retraitValide){
//			if ( this.size() > nombreMaxOnglets ){
			if ( nombreConfigsSuivantes > 0 ){
				nombreConfigsSuivantes--;
			}
			if ( nombreConfigsPrecedentes > 0 ){
				nombreConfigsPrecedentes--;
			}
		}
		return retraitValide;
	}
	
	public String toString (){
		String chaine_resultat = "";
		for (int numeroConfig = 0; numeroConfig < this.size(); numeroConfig++){
			chaine_resultat += this.get(0).toString();
		}
		return chaine_resultat;
	}
	public Historique clone (){
		Historique renvoi = new Historique();
		for (int numeroConfig = 0; numeroConfig < this.size(); numeroConfig++){
			renvoi.add(this.get(numeroConfig));
		}
		renvoi.nombreConfigsPrecedentes = this.nombreConfigsPrecedentes;
		renvoi.nombreConfigsSuivantes = this.nombreConfigsSuivantes;

//		renvoi.nombreMaxOnglets = this.nombreMaxOnglets;
		
		return renvoi;
	}
}
