// FullException.java

package be.ukonline.miniproject.usb;

/**
 * Exception throwned when trying to add a file to a full storage device
 *
 * @author Sébastien Combéfis
 * @version March 12, 2009
 */
@SuppressWarnings ("serial")
public final class FullException extends RuntimeException
{
	/**
	 * Constructor
	 * 
	 * @pre -
	 * @post An instance of this is created
	 */
	public FullException (String msg)
	{
		super (msg);
	}
}