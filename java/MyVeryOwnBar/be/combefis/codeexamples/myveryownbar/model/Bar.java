// Bar.java

package be.combefis.codeexamples.myveryownbar.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * A bar that proposes a list of beers to sale
 * 
 * @author Sébastien Combéfis
 * @version February 11, 2015
 */
public final class Bar
{
	// Instance variables
	private final String name;
	private final Map<Beer,Double> beers;
	
	/**
	 * Create a bar with the speficied name
	 * 
	 * @pre path != null && path != ""
	 * @post 
	 */
	public Bar (String path) throws IOException, FileFormatException
	{
		assert path != null && ! "".equals (path);
		
		this.beers = new Hashtable<Beer,Double>();
		name = loadBar (path);
	}
	
	private String loadBar (String path) throws IOException, FileFormatException
	{
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader (new FileReader (path));
			
			String name = reader.readLine();
			if (name == null)
			{
				throw new FileFormatException ("The first line of the file must contain the name of the bar.");
			}
			
			String line;
			while ((line = reader.readLine()) != null)
			{
				String[] tokens = line.split (";");
				if (tokens.length != 5)
				{
					throw new FileFormatException ("Missing elements in line: " + line);
				}
				
				try
				{
					List<BeerType> beertypes = new ArrayList<BeerType>();
					String[] types = tokens[3].split ("/");
					for (String type : types)
					{
						beertypes.add (Enum.valueOf (BeerType.class, type));
					}
					
					Beer beer = new Beer (Integer.parseInt (tokens[0]), tokens[1], Double.parseDouble (tokens[2]), beertypes);
					beers.put (beer, Double.parseDouble (tokens[4]));
				}
				catch (NumberFormatException exception)
				{
					throw new FileFormatException ("Barcode, strength or price is invalid in line: " + line);
				}
			}
			
			return name;
		}
		finally
		{
			reader.close();
		}
	}
	
	/**
	 * Gets the name of the bar
	 * 
	 * @pre -
	 * @post The returned value contains the name of this bar
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Gets the list of proposed beers
	 * 
	 * @pre -
	 * @post The returned value contains the list of the beers
	 *       proposed by this bar
	 */
	public Map<Beer,Double> getProposedBeers()
	{
		return Collections.unmodifiableMap (beers);
	}
	
	/**
	 * Add a new beer to the bar
	 * 
	 * @pre "beer" != null
	 *      "price" > 0
	 * @post The specified beer has been added to the list of beers
	 *       proposed by this bar
	 * @throws AlreadyPresentBeerException if the specified beer was
	 *         already proposed by this bar
	 */
	public void addBeer (Beer beer, double price) throws AlreadyPresentBeerException
	{
		assert beer != null;
		assert price > 0;
		
		if (beers.containsKey (beer))
		{
			throw new AlreadyPresentBeerException();
		}
		
		beers.put (beer, price);
	}
	
	/**
	 * Makes an order of beers
	 * 
	 * @pre beers != null && beers.size() > 0
	 * @post The returned value contains an order consisting of
	 *       the specified "beers"
	 */
	public Order makeOrder (List<Beer> beers)
	{
		Order order = new Order (this.beers);
		for (Beer beer : beers)
		{
			order.addBeer (beer, 1);
		}
		return order;
		
	}
}