package joueurPackage;

public class Objectifs {
	/*
	 * Attributs Principaux
	 */
	int ligneAEtablir;
	int[] escalesCibles;
	/*
	 * FIN Attributs Principaux
	 */
	
	private int nbEscalesMax = 3;
	private int aucuneEscale = -1;
	private int aucuneLigne = -1;
	
	/*
	 * Constructeurs
	 */
	public Objectifs (){
		ligneAEtablir = aucuneLigne;
		escalesCibles = new int[nbEscalesMax];
		initialisationEscales();
	}
	public Objectifs (int numeroLigne){
		ligneAEtablir = numeroLigne;
		escalesCibles = new int[nbEscalesMax];
		initialisationEscales();
	}
	public Objectifs (int numeroLigne, int[] escalesVisees){
		ligneAEtablir = numeroLigne;
		escalesCibles = new int[escalesVisees.length];
		initialisationEscales(escalesVisees);
	}
	/*
	 * FIN Constructeurs
	 */
	
	/*
	 * Accesseurs
	 */
	public int getLigne (){
		return ligneAEtablir;
	}
	public int[] getEscalesCibles (){
		return escalesCibles;
	}
	
	public void setLigne (int newLigne){
		ligneAEtablir = newLigne;
	}
	/*
	 * FIN Accesseurs
	 */
	
	/*
	 * Methodes Public de Objectifs
	 */
	public void ajouterEscales (int newEscale){
		int indexAjout = 0;
		while ( indexAjout < escalesCibles.length && escalesCibles[indexAjout] != aucuneEscale){
			indexAjout++;
		}
		if ( indexAjout < escalesCibles.length ){
			escalesCibles[indexAjout] = newEscale;
		}
	}
	public Objectifs clone (){
		return new Objectifs (ligneAEtablir, escalesCibles);
	}
	public String toString (){
		String chaine_resultat = "";
		chaine_resultat += ligneAEtablir + "\n";
		for (int numeroEscale = 0; numeroEscale < escalesCibles.length; numeroEscale++){
			chaine_resultat += escalesCibles[numeroEscale] + " ";
		}
		return chaine_resultat;
	}
	
	/*
	 * FIN Methodes Public
	 */
	
	/*
	 * Methodes Private de Objectifs
	 */
	private void initialisationEscales (){
		for (int numeroEscale = 0; numeroEscale < escalesCibles.length; numeroEscale++){
			escalesCibles[numeroEscale] = aucuneEscale;
		}
	}
	private void initialisationEscales (int[] escalesVisees){
		for (int numeroEscale = 0; numeroEscale < escalesCibles.length; numeroEscale++){
			escalesCibles[numeroEscale] = escalesVisees[numeroEscale];
		}
	}

}
