// Coordinate.java

package be.apprendrejava.chapter5;

public class Coordinate
{
	// Variables d'instance
	private double x, y;     // Abscisse et ordonnée

	// Constructeurs
	public Coordinate (double a, double b)
	{
		x = a;
		y = b;
	}

	// Méthodes accesseurs
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
}