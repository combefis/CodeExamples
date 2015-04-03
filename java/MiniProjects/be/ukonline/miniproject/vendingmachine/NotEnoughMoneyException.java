// NotEnoughMoneyException.java

package be.ukonline.miniproject.vendingmachine;

/**
 * Exception for the case where not enough money was inserted into the machine
 *
 * @author Sébastien Combéfis
 * @version January 2, 2009
 */
@SuppressWarnings ("serial")
public class NotEnoughMoneyException extends Exception
{
	/**
	 * Constructor
	 * 
	 * @pre -
	 * @post An instance of this is created
	 */
	public NotEnoughMoneyException()
	{
		super ("Not enough money to give back change");
	}
}