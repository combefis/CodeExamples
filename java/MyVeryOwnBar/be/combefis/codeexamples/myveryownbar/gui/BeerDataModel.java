// AbstractTableModel.java

package be.combefis.codeexamples.myveryownbar.gui;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import be.combefis.codeexamples.myveryownbar.model.AlreadyPresentBeerException;
import be.combefis.codeexamples.myveryownbar.model.Bar;
import be.combefis.codeexamples.myveryownbar.model.Beer;

/**
 * Data model for a list of beers
 * 
 * @author Sébastien Combéfis
 * @version March 27, 2015
 */
public final class BeerDataModel extends AbstractTableModel
{
	// Constants
	private static final String[] COLUMNS_NAME = {"Name", "Price", "Stock"};
	
	// Instance variables
	private final List<BeerInfo> beers;
	
	/**
	 * Creates a data model representing a list of beers
	 * 
	 * @pre "bar" != null
	 * @post An instance of this is created, representing a
	 *       list of beers with associated price corresponding
	 *       to the specified "bar"
	 */
	public BeerDataModel (Bar bar)
	{
		beers = new ArrayList<BeerInfo>();
		
		for (Beer beer : bar.getProposedBeers().keySet())
		{
			beers.add (new BeerInfo	(beer, bar.getPrice (beer), 0));
		}
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
		
		beers.add (new BeerInfo (beer, price, 0));
	}
	
	@Override
	public int getColumnCount()
	{
		return 3;
	}

	@Override
	public String getColumnName (int column)
	{
		return COLUMNS_NAME[column];
	}

	@Override
	public int getRowCount()
	{
		return beers.size();
	}

	@Override
	public Object getValueAt (int row, int column)
	{
		BeerInfo beerinfo = beers.get (row);
		switch (column)
		{
			case 0: return beerinfo.beer.getName();
			case 1: return beerinfo.price;
			case 2: return beerinfo.stock;
		}
		
		return null;
	}
	
	/**
	 * Information about a beer
	 * - Price
	 * - Quantity in stock
	 * 
	 * @author Sébastien Combéfis
	 * @version April 2, 2015
	 */
	private static class BeerInfo
	{
		// Instance variables
		private final Beer beer;
		private final double price;
		private final int stock;
		
		/**
		 * Creates a new set of information about a beer
		 * 
		 * @pre "beer" != null
		 *      "price" > 0
		 *      "stock" >= 0
		 * @post An instance of this is created, representing a set of information
		 *       about the specified "beer", that is, its "price" and the quantity
		 *       available in "stock"
		 */
		public BeerInfo (Beer beer, double price, int stock)
		{
			this.beer = beer;
			this.price = price;
			this.stock = stock;
		}
	}
}