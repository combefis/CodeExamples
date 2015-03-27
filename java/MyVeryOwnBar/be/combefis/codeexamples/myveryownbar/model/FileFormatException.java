// FileFormatException.java

package be.combefis.codeexamples.myveryownbar.model;

/**
 * Exception raised when the format of the file to load the bar is invalid
 * 
 * @author Sébastien Combéfis
 * @version March 3, 2015
 */
@SuppressWarnings ("serial")
public final class FileFormatException extends Exception
{
	/**
	 * Creates a new exception
	 * 
	 * @pre "msg" != null
	 * @post An instance of this exception is created, representing
	 *       an exception with the specified "msg"
	 */
	public FileFormatException (String msg)
	{
		super (msg);
	}
}