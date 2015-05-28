package iaPackage;

import java.util.Comparator;

class ComparateurChemin implements Comparator<TuileChemin>{

	@Override
	public int compare(TuileChemin o1, TuileChemin o2) {
		return (o1.priority+o1.heuristique)-(o2.priority+o2.heuristique);
	}
	
}
