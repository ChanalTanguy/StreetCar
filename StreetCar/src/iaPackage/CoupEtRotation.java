package iaPackage;

import joueurPackage.Coup;

class CoupEtRotation {

	Coup coup;
	int nbRotation;
	
	CoupEtRotation(Coup c, int n) {
		coup = c;
		nbRotation = n;
	}
	
	Coup getCoup() {
		return coup;
	}
	
	int nbRotation() {
		return nbRotation;
	}
	
}
