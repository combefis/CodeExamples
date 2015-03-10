// Order.java

package be.combefis.codeexamples.myveryownbar.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A list of beers that have been ordered and consumed
 * 
 * @author Sébastien Combéfis
 * @version February 11, 2015
 */
public final class Order
{
	// Instance variables
	private final List<Pair> beers;
	private final Map<Beer,Double> prices;
	
	/**
	 * Creates an empty order
	 * 
	 * @pre -
	 * @post An instance of this is created, representing an empty order
	 */
	public Order (Map<Beer,Double> prices)
	{
		beers = new ArrayList<Pair>();
		this.prices = Collections.unmodifiableMap (prices);
	}
	
	/**
	 * Gets the total price of the order
	 * 
	 * @pre -
	 * @post The returned value contains the total price of this order
	 */
	public double totalPrice()
	{
		double total = 0;
		for (Pair b : beers)
		{
			total += b.quantity * prices.get (b.beer);
		}
		return total;
	}
	
	/**
	 * Adds a beer to the order
	 * 
	 * @pre "beer" != null
	 *      "quantity" > 0
	 * @post The specified "beer" has been added to this order
	 *       with the specified "quantity"
	 */
	public void addBeer (Beer beer, int quantity)
	{
		Pair p = new Pair (beer, quantity);
		int index = beers.indexOf (p);
		if (index == -1)
		{
			beers.add (p);
		}
		else
		{
			beers.get (index).quantity += quantity;
		}
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		for (Pair pair : beers)
		{
			builder.append (pair).append ("\n");
		}
		return builder.toString();
	}
	
	/**
	 * A pair associating a beer with a quantity
	 * 
	 * @author Sébastien Combéfis
	 * @version February 11, 2015
	 */
	private static final class Pair
	{
		// Instance variables
		private final Beer beer;
		private int quantity;
		
		/**
		 * Create a pair with a beer and a quantity
		 * 
		 * @pre beer != null
		 *      quantity > 0
		 * @post An instance of this is created, representing a pair associating
		 *       the specified beer with the specified quantity
		 */
		public Pair (Beer beer, int quantity)
		{
			this.beer = beer;
			this.quantity = quantity;
		}
		
		@Override
		public String toString()
		{
			return String.format ("%d x %s", quantity, beer);
		}
		
		@Override
		public boolean equals (Object o)
		{
			if (o instanceof Pair)
			{
				Pair p = (Pair) o;
				return beer.equals (p.beer);
			}
			return false;
		}
	}
}