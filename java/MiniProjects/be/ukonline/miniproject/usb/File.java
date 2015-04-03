// File.java

package be.ukonline.miniproject.usb;

/**
 * Class representing a file
 *
 * @author Sébastien Combéfis
 * @version March 12, 2009
 */
public final class File implements Block
{
	// Instance variables
	private final String name, data;
	
	/**
	 * Constructor
	 * 
	 * @pre name, data != null
	 *      ! name.equals ("")
	 * @post An instance of this is created representing a file
	 *       with specified name and data
	 *       the size of this file is the number of characters of data
	 */
	public File (String name, String data)
	{
		this.name = name;
		this.data = data;
	}
	
	public int getSize()
	{
		return data.length();
	}
	
	/**
	 * Get the name of the file
	 * 
	 * @pre -
	 * @post The returned value contains the name of this file
	 */
	public String getName()
	{
		return name;
	}
	
	@Override
	public String toString()
	{
		return name + " (" + getSize() + ")";
	}
}