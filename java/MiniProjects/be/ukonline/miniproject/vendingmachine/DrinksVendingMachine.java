// DrinksVendingMachine.java

package be.ukonline.miniproject.vendingmachine;

import java.math.BigDecimal;

/**
 * A generic vending machine
 *
 * @author Sébastien Combéfis
 * @version January 3, 2009
 */
public class DrinksVendingMachine extends GenericVendingMachine
{
	private static final Product[] soldProducts = {new Product ("Coca-Cola", new BigDecimal ("0.6")),
												new Product ("Fanta", new BigDecimal ("0.6")),
												new Product ("Canada Dry", new BigDecimal ("0.7")),
												new Product ("Nestea", new BigDecimal ("0.7")),
												new Product ("Schweppes Agrum", new BigDecimal ("0.8"))};
	private static final Coin[] acceptedCoins = EuroCoin.values();
	
	/**
	 * Constructor
	 * 
	 * @pre -
	 * @post An instance of this is created
	 */
	public DrinksVendingMachine()
	{
		super (soldProducts, acceptedCoins);
	}
}