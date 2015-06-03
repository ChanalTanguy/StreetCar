package iaPackage;

class ListeTuilePossible {
	
	TuileChemin[] tableau;
	int taille;
	
	ListeTuilePossible() {
		tableau = new TuileChemin[500];
		taille = 0;
	}
	
	void add(TuileChemin t) {
		tableau[taille++] = t;
	}
	
	TuileChemin remove() {
		int bestOne = 0;
		for (int i = 1; i<taille; i++) {
			if (TuileChemin.compare(tableau[bestOne], tableau[i]) > 0) {
				bestOne = i;
			}
		}
		TuileChemin tuileEnleve = tableau[bestOne];
		tableau[bestOne] = tableau[(taille--)-1];
		return tuileEnleve;
	}
}
