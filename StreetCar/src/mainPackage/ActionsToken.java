package mainPackage;

public interface ActionsToken {
	
	// permet d'appliquer une rotation a l'objet appelant
	void rotation(String sensRotation);
	
	// permet d'effectuer un swap entre un token et l'appelant
	/*
	 * /!\ il faudra preciser si la methode retourne un objet ou non
	 * /!\		=> si oui, lequel ?
	 * /!\		=> sinon, comment efectuer le swap ?
	 */
	
	void swap (Object token);
}
