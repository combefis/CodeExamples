// City.java

package be.apprendrejava.chapter5;

public class City
{
	// Variables d'instance
	private Citizen mayor;    // Bourgmestre de la ville
	private String name;      // Nom de la ville
	
	// Constructeurs
	public City (String s)
	{
		name = s;
	}
	
	// Méthodes
	public void setMayor (Citizen c)
	{
		mayor = c;
	}
}

class Citizen{}