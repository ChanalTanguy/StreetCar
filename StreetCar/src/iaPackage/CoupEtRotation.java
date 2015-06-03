package iaPackage;

import joueurPackage.Coup;

class CoupEtRotation {

	private Coup coup;
	private int nbRotation;
	private int cout;
	
	CoupEtRotation(Coup c, int n) {
		coup = c;
		nbRotation = n;
	}
	
	Coup getCoup() {
		return coup;
	}
	
	int getNbRotation() {
		return nbRotation;
	}
	
	int getCout() {
		return cout;
	}
	
	void setCout(int c) {
		cout = c;
	}
	
}
