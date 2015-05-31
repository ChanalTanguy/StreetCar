package iaPackage;

import java.util.ArrayList;

class RejectList extends ArrayList<TuileChemin> {
	
	boolean contain(TuileChemin tuile) {
		for (TuileChemin t : this) {
			if (t.getDirection().equals(tuile.getDirection()) && t.getPosition().equals(tuile.getPosition()))
				return true;
		}
		return false;
	}

}
