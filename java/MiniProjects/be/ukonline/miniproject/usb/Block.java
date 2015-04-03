// Block.java

package be.ukonline.miniproject.usb;

/**
 * Interface representing a block
 *
 * @author Sébastien Comb2fis
 * @version March 12, 2009
 */
public interface Block
{
	/**
	 * Get the size of the block
	 * 
	 * @pre -
	 * @post The returned value contains the size of this block
	 */
	public int getSize();
}