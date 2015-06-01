package historiqPackage;

import java.util.ArrayList;

public class Historique extends ArrayList<Configuration>{
	int nombreConfigsPrecedentes, nombreConfigsSuivantes;
	
	public Historique (){
		super();
		nombreConfigsPrecedentes = 0;
		nombreConfigsSuivantes = 0;
	}
	
	public Configuration first (){
		return this.get(0);
	}
	public Configuration last (){
		return this.get(this.size()-1);
	}
	
}
