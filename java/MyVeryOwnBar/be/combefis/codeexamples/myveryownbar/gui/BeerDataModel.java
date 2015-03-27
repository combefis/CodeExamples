// AbstractTableModel.java

package be.combefis.codeexamples.myveryownbar.gui;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import be.combefis.codeexamples.myveryownbar.model.AlreadyPresentBeerException;
import be.combefis.codeexamples.myveryownbar.model.Beer;

/**
 * Data model for a list of beers
 * 
 * @author Sébastien Combéfis
 * @version March 27, 2015
 */
public final class BeerDataModel extends AbstractTableModel
{
	// Instance variables
	private final List<Beer> beers;
	private final Map<Beer,Double> prices;
	
	/**
	 * Creates a data model representing a list of beers
	 * 
	 * @pre -
	 * @post An instance of this is created, representing an empty
	 *       list of beers with associated price
	 */
	public BeerDataModel()
	{
		beers = new ArrayList<Beer>();
		prices = new Hashtable<Beer,Double>();
	}
	
	/**
	 * Adds a beer to the model
	 * 
	 * @pre "beer" != null
	 *      "price" > 0
	 * @post The specified "beer" has been added to this data model, with the specified "price"
	 * @throws AlreadyPresentBeerException if the specified "beer" has already been added to this data model
	 */
	public void addBeer (Beer beer, double price) throws AlreadyPresentBeerException
	{
		if (beers.contains (beer))
		{
			throw new AlreadyPresentBeerException();
		}
		
		beers.add (beer);
		prices.put (beer, price);
	}
	
	@Override
	public int getColumnCount()
	{
		return 2;
	}

	@Override
	public int getRowCount()
	{
		return beers.size();
	}

	@Override
	public Object getValueAt (int line, int column)
	{
		Beer beer = beers.get (line);
		return column == 0 ? beer.getName() : prices.get (beer);
	}
}