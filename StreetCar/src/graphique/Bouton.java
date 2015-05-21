package graphique;

import javax.swing.JButton;

public class Bouton extends JButton{

	public Bouton (String name, Panneau referenceNotif){
		super(name);
		addMouseListener(new GestionSouris(this, referenceNotif));
	}
}
