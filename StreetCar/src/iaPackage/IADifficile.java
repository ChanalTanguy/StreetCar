package iaPackage;

import joueurPackage.Coup;
import mainPackage.*;

public class IADifficile implements InterfaceIA {
	
	Moteur moteur;
	
	public IADifficile(Moteur moteur) {
		this.moteur = moteur;
	}

	public Coup getCoup() {
		
		Plateau plateau = (Plateau) moteur.getPlateau().clone();
		
		// * Générer le graphe (ou pas selon comment on s'y prend)
		// ** Trouver le chemin le plus rapide pour rejoindre ses terminus
		// *** Placer une tuile au hasard sur le chemin (avec Rotation potentiel)
		// ** Deviner les objectifs de l'adversaire
		// *** Calculer un chemin potentiel qu'il pourrait prendre
		// **** Lui faire chier
		return null;
	}

}
