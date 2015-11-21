package iaPackage;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class RejectList extends ArrayList<TuileChemin> {
	
	public boolean contain(TuileChemin tuile) {
		for (TuileChemin t : this) {
			if (t.getDirection().equals(tuile.getDirection()) && t.getPosition().equals(tuile.getPosition()))
				return true;
		}
		return false;
	}

}
