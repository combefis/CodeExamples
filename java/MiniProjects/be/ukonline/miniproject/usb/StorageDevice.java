// StorageDevice.java

package be.ukonline.miniproject.usb;

/**
 * Interface representing a storage device
 *
 * @author Sébastien Combéfis
 * @version March 12, 2009
 */
public interface StorageDevice
{
	/**
	 * Get the capacity of the device
	 * 
	 * @pre -
	 * @post The returned value contains the total capacity of this device
	 */
	public int getCapacity();
	
	/**
	 * Get the used space on the device
	 * 
	 * @pre -
	 * @post The returned value contains the space used on this device
	 */
	public int getUsed();
	
	/**
	 * Test whether the device is full
	 * 
	 * @pre -
	 * @post The returned value contains true if this device is true
	 *       and false otherwise
	 */
	public boolean isFull();
	
	/**
	 * Add a file to the device
	 * 
	 * @pre f != null
	 * @post The file is added to the storage device if there is enough place
	 * @throws FullException otherwise
	 */
	public void addFile (File f) throws FullException;
	
	/**
	 * Get a file from the device
	 * 
	 * @pre name != null && ! name.equals ("")
	 * @post The returned value contains a file with specified name if such file exists
	 * @throws FileNotFoundException otherwise
	 */
	public File getFile (String name) throws FileNotFoundException;
	
	/**
	 * Remove a file from the device
	 * 
	 * @pre name != null && ! name.equals ("")
	 * @post The returned value contains a file with specified name if such file exists
	 *       and this file is removed from the device
	 * @throws FileNotFoundException otherwise
	 */
	public File remove (String name)  throws FileNotFoundException;
}