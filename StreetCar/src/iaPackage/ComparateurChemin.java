package iaPackage;

import java.util.Comparator;

class ComparateurChemin implements Comparator<TuileChemin>{

	@Override
	public int compare(TuileChemin o1, TuileChemin o2) {
		return (o1.getPriority()+o1.getHeuristique())-(o2.getPriority()+o2.getHeuristique());
	}
	
}
