// Beer.java

package be.combefis.codeexamples.myveryownbar.model;

import java.util.Collections;
import java.util.List;

/**
 * Generic beer characterised by a list of beer types,
 * a strength, a barcode and a name
 * 
 * @author Sébastien Combéfis
 * @version March 11, 2015
 */
public final class Beer extends Item
{
	// Instance variables
	private final List<BeerType> types;
	private final double strength;
	
	/**
	 * Creates a beer with a barcode, name, strength and list of beer types
	 * 
	 * @pre "barcode" > 0
	 *      "name" != null && "name" != ""
	 *      "strength" > 0
	 *      "types" != null && "types".size() > 0
	 * @post An instance of this has been created, representing a beer with specified
	 *       "barcode", "name", "strength" and list of beer types "types"
	 */
	public Beer (int barcode, String name, double strength, List<BeerType> types)
	{
		super (barcode, name);
		
		assert strength > 0;
		assert types != null && types.size() > 0;
		
		this.strength = strength;
		this.types = Collections.unmodifiableList (types);
	}
	
	/**
	 * Gets the list of beer types of the beer
	 * 
	 * @pre -
	 * @post The returned value contains the list of beer type of this beer
	 */
	public List<BeerType> getTypes()
	{
		return types;
	}
	
	/**
	 * Gets the strength of the beer
	 * 
	 * @pre -
	 * @post The returned value contains the strength of this beer
	 */
	public double getStrength()
	{
		return strength;
	}
	
	@Override
	public String toString()
	{
		return String.format ("%s (%.2f%%) %s", getName(), strength, types);
	}
}