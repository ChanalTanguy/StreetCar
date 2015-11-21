package graphique;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class Panneau_Notifications extends Pan_Abstract{
	JTextArea message;
	
	
	public Panneau_Notifications (Color newCouleur){
		super(newCouleur);
		message = new JTextArea ("Notifications");
		message.setEditable(false);
		add(message);
	}
	
	public void paintComponent (Graphics g){
		crayon = (Graphics2D) g;
		largeur = getSize().width;
		hauteur = getSize().height;
		
		crayon.setColor(Color.white);
		crayon.fillRect(0, 0, largeur, hauteur);
		
		crayon.setColor(couleur);
		crayon.fillRect(0, 0, largeur, hauteur);
		
		crayon.setColor(Color.black);
		crayon.drawString(message.getText(), 20, hauteur/2);
		
//		System.out.println("message : " + message.getText());
		
	}
	
	public void updateMessage (String newNotif){
		message.setText(newNotif);
		repaint();
	}
	
	protected void initialiserImages(){};
}
