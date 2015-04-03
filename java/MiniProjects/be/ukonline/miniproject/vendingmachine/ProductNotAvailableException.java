// ProductNotAvailableException.java

package be.ukonline.miniproject.vendingmachine;

/**
 * Exception for the case where no more product is available
 *
 * @author Sébastien Combéfis
 * @version January 2, 2009
 */
@SuppressWarnings ("serial")
public class ProductNotAvailableException extends RuntimeException
{
	/**
	 * Constructor
	 * 
	 * @pre -
	 * @post An instance of this is created
	 */
	public ProductNotAvailableException()
	{
		super ("Product not available");
	}
}