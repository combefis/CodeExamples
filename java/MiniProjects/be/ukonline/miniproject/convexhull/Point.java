// Point.java

package be.ukonline.miniproject.convexhull;

/**
 * Class representing a point on a screen
 * A point has integer x and y coordinate
 * 
 * @author Sébastien Combéfis
 * @version December 22, 2008
 */
public class Point
{
	// Instance variables
	private final int x, y;
	
	/**
	 * Constructor
	 * 
	 * @pre -
	 * @post An instance of this is created representing the point (x, y)
	 */
	public Point (int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Get the x-coordinate of this point
	 * 
	 * @pre -
	 * @post The returned value contains the x-coordinate of this point
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * Get the y-coordinate of this point
	 * 
	 * @pre -
	 * @post The returned value contains the y-coordinate of this point
	 */
	public int getY()
	{
		return y;
	}
	
	/**
	 * Test whether this point lies on the left of a line
	 * 
	 * @pre line != null
	 * @post The returned value contains true if this point lies on the left
	 *       of (or on) the specified directed line and false otherwise
	 */
	public boolean liesOnLeft (DirectedLine line)
	{
		Point p = line.getOrigin();
		Point q = line.getDestination();
		
		int det = (q.x * y + p.x * q.y + p.y * x) - (q.x * p.y + x * q.y + y * p.x);
		return det > 0;
	}
	
	/**
	 * Test whether this point lies on the right of a line
	 * 
	 * @pre line != null
	 * @post The returned value contains true if this point lies on the right
	 *       of (or on) the specified directed line and false otherwise
	 */
	public boolean liesOnRight (DirectedLine line)
	{
		return ! liesOnLeft (line);
	}
	
	@Override
	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}
	
	@Override
	public boolean equals (Object o)
	{
		if (o instanceof Point)
		{
			Point p = (Point) o;
			return x == p.x && y == p.y;
		}
		return false;
	}
}