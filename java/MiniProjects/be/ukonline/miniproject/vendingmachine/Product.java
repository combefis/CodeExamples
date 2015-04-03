// Product.java

package be.ukonline.miniproject.vendingmachine;

import java.math.BigDecimal;

/**
 * Class representing a product
 *
 * @author Sébastien Combéfis
 * @version January 2, 2009
 */
public class Product
{
	// Instance variables
	private final BigDecimal price;
	private final String name;
	
	/**
	 * Constructor
	 * 
	 * @pre name != null, name != ""
	 *      price != null, price > 0
	 * @post An instance of this is created with the specified name and price
	 */
	public Product (String name, BigDecimal price)
	{
		assert name != null && ! "".equals (name);
		assert price != null && price.compareTo (BigDecimal.ZERO) > 0;
		
		this.name = name;
		this.price = price;
	}
	
	/**
	 * Get the name of this product
	 * 
	 * @pre -
	 * @post The returned value contains the name of this product
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Get the price of this product
	 * 
	 * @pre -
	 * @post The returned value contains the price of this product
	 */
	public BigDecimal getPrice()
	{
		return price;
	}
	
	@Override
	public String toString()
	{
		return name + " (" + price + ")";
	}
	
	@Override
	public boolean equals (Object o)
	{
		if (o instanceof Product)
		{
			Product p = (Product) o;
			return name.equals (p.name) && price.equals (p.price);
		}
		return false;
	}
}