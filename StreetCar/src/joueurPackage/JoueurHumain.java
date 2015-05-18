package joueurPackage;

public class JoueurHumain implements Joueur {

	public EcouteurPlateau ecouteurPlateau;
	
	public JoueurHumain(Moteur m) {
		ecouteurPlateau = new EcouteurPlateau(m);
	}
	
	public void attendCoup() {
		ecouteurPlateau.enable();
	}

}
