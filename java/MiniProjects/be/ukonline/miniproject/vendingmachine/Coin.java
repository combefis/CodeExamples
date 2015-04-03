// Coin.java

package be.ukonline.miniproject.vendingmachine;

import java.math.BigDecimal;

/**
 * Interface representing a monetary coin
 *
 * @author Sébastien Combéfis
 * @version January 2, 2009
 */
public interface Coin
{
	/**
	 * Get the value of this coin
	 * 
	 * @pre -
	 * @post The returned value contains the value of this coin
	 */
	public BigDecimal getValue();
}