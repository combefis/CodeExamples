// Rectangle.java

package be.apprendrejava.chapter5;

public class Rectangle
{
	// Variables d'instance
	private Coordinate coord;       // Coordonnée coin inférieur gauche
	private double width, height;   // Largeur et hauteur

	// Constructeur
	public Rectangle (double x, double y, double w, double h)
	{
		width = w;
		height = h;
		coord = new Coordinate (x, y);
	}
	
	// Méthodes
	public Rectangle copy (double f)
	{
		return new Rectangle (coord.getX(), coord.getY(), f * width, f * height);
	}
	
	private boolean isBetween (double value, double min, double max)
	{
		return min <= value && value <= max;
	}

	public boolean contains (Coordinate c)
	{
		return isBetween (c.getX(), coord.getX(), coord.getX() + width)
			&& isBetween (c.getY(), coord.getY(), coord.getY() + height);
	}

	public boolean contains (Rectangle rect)
	{
		return isBetween (rect.coord.getX(), coord.getX(), coord.getX() + width + rect.width)
			&& isBetween (rect.coord.getY(), coord.getY(), coord.getY() + height + rect.height);
	}
	
	public static void main (String[] args)
	{
		Rectangle r1 = new Rectangle (0, 0, 5, 10);
		Rectangle r2 = new Rectangle (-2, -3, 2, 7);
		Rectangle r3 = r1.copy (1.5);
		
		System.out.println (r1.contains (new Coordinate (1, 1)));
		System.out.println (r2.contains (r1));
		System.out.println (r3.contains (r1));
	}
}