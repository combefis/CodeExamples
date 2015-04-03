// MissingMoneyException.java

package be.ukonline.miniproject.vendingmachine;

import java.math.BigDecimal;

/**
 * Exception for the case where the machine miss money
 *
 * @author Sébastien Combéfis
 * @version January 2, 2009
 */
@SuppressWarnings ("serial")
public class MissingMoneyException extends RuntimeException
{
	// Instance variables
	private final BigDecimal missingMoney;
	
	/**
	 * Constructor
	 * 
	 * @pre value > 0
	 * @post An instance of this is created
	 */
	public MissingMoneyException (BigDecimal value)
	{
		super ("Missing " + value);
		missingMoney = value;
	}
	
	/**
	 * Get the missing money
	 * 
	 * @pre -
	 * @post The returned value contains the missing value
	 */
	public BigDecimal getMissingMoney()
	{
		return missingMoney;
	}
}