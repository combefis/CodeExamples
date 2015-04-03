// EuroCoin.java

package be.ukonline.miniproject.vendingmachine;

import java.math.BigDecimal;

/**
 * Enum representing a monetary euro coin
 *
 * @author Sébastien Combéfis
 * @version January 2, 2009
 */
public enum EuroCoin implements Coin
{
	// Enumeration
	ONECENT		(new BigDecimal ("0.01")),
	TWOCENTS	(new BigDecimal ("0.02")),
	FIVECENTS	(new BigDecimal ("0.05")),
	TENCENTS	(new BigDecimal ("0.1")),
	TWENTYCENTS	(new BigDecimal ("0.2")),
	FIFTYCENTS	(new BigDecimal ("0.5")),
	ONEEURO		(new BigDecimal ("1")),
	TWOEUROS	(new BigDecimal ("2"));
	
	// Instance variables
	private final BigDecimal value;
	
	/**
	 * Constructor
	 * 
	 * @pre v != null, v > 0
	 * @post An instance of this is created representing a coin
	 *       with the specififed value
	 */
	private EuroCoin (BigDecimal v)
	{
		assert v != null && v.compareTo (BigDecimal.ZERO) > 0;
		
		value = v;
	}
	
	/*
	 * @see Coin#getValue()
	 */
	public BigDecimal getValue()
	{
		return value;
	}
	
	@Override
	public String toString()
	{
		return "�" + value;
	}
}