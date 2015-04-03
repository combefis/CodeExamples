// DirectedLine.java

package be.ukonline.miniproject.convexhull;

/**
 * Class representing a directed line
 * 
 * @author Sébastien Combéfis
 * @version December 22, 2008
 */
public class DirectedLine
{
	// Instance variables
	private final Point origin, destination;
	
	/**
	 * Constructor
	 * 
	 * @pre src, dst != null
	 * @post An instance of this is created representing
	 *       a directed line with specified origin and destination
	 */
	public DirectedLine (Point orig, Point dest)
	{
		assert orig != null && dest != null;
		
		origin = orig;
		destination = dest;
	}
	
	/**
	 * Get the origin of this directed line
	 * 
	 * @pre -
	 * @post The returned value contains the origin of this directed line
	 */
	public Point getOrigin()
	{
		return origin;
	}
	
	/**
	 * Get the destination of this directed line
	 * 
	 * @pre -
	 * @post The returned value contains the destination of this directed line
	 */
	public Point getDestination()
	{
		return destination;
	}
	
	@Override
	public String toString()
	{
		return "From " + origin + " to " + destination;
	}
	
	@Override
	public boolean equals (Object o)
	{
		if (o instanceof DirectedLine)
		{
			DirectedLine dl = (DirectedLine) o;
			return origin.equals (dl.origin) && destination.equals (dl.destination);
		}
		return false;
	}
}