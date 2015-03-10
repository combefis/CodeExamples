// Item.java

package be.combefis.codeexamples.myveryownbar.model;

/**
 * Generic item characterised by a barcode, a name and a price
 * 
 * @author Sébastien Combéfis
 * @version February 11, 2015
 */
public class Item
{
	// Instance variables
	private final int barcode;
	private final String name;
	
	/**
	 * Creates an item with the specified barcode, name and price
	 * 
	 * @pre "barcode" > 0
	 *      "name" != null && "name" != ""
	 * @post An instance of this is created, representing an item with the
	 *       specified "barcode" and "name"
	 */
	public Item (int barcode, String name)
	{
		assert barcode > 0;
		assert name != null && ! "".equals (name);
		
		this.barcode = barcode;
		this.name = name;
	}
	
	/**
	 * Gets the barcode of the item
	 * 
	 * @pre -
	 * @post The returned value contains the barcode of this item
	 */
	public final int getBarcode()
	{
		return barcode;
	}
	
	/**
	 * Gets the name of the item
	 * 
	 * @pre -
	 * @post The returned value contains the name of this item
	 */
	public final String getName()
	{
		return name;
	}
	
	@Override
	public boolean equals (Object o)
	{
		if (o instanceof Item)
		{
			Item item = (Item) o;
			return barcode == item.barcode;
		}
		return false;
	}
}