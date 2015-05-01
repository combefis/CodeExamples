// Bar.java

package be.combefis.codeexamples.myveryownbar.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * A bar that proposes a list of beers to sale
 * 
 * @author Sébastien Combéfis
 * @version May 1, 2015
 */
public final class Bar
{
	// Instance variables
	private final String name;
	private final Map<Beer,BeerInfo> beers;
	private final Connection connection;
	
	/**
	 * Create a bar with the speficied name
	 * 
	 * @pre name != null, name != ""
	 * @post An instance of this has been created, representing a new empty bar if the beers.sqlite
	 *       does not exist, or initialised with the content of beers.sqlite otherwise, with the
	 *       specified "name"
	 * @throws SQLException if an SQL error occurred
	 */
	public Bar (String name, String path) throws SQLException, IOException, FileFormatException
	{
		this.beers = new Hashtable<Beer,BeerInfo>();
		this.name = name;
		
		this.connection = DriverManager.getConnection ("jdbc:sqlite:beers.sqlite");
		if (! new File ("beers.sqlite").exists())
		{
			initialiseDatabase();
		}
		readDatabase();
		loadBar (path);
	}
	
	/**
	 * Initialise the database
	 */
	private void initialiseDatabase() throws SQLException
	{
		Statement statement = connection.createStatement();
		
		statement.executeUpdate ("CREATE TABLE beers (barcode INTEGER PRIMARY KEY NOT NULL, name VARCHAR NOT NULL, strength FLOAT NOT NULL, price FLOAT NOT NULL, quantity INTEGER NOT NULL DEFAULT 0)");
		statement.executeUpdate ("CREATE TABLE categories (id_cat INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR NOT NULL UNIQUE )");
		statement.executeUpdate ("CREATE TABLE beercat (barcode INTEGER NOT NULL REFERENCES beers(barcode), id_cat INTEGER NOT NULL REFERENCES categories(id_cat), PRIMARY KEY (barcode, id_cat))");
	}
	
	/**
	 * Load beers into the program from the database
	 */
	private void readDatabase() throws SQLException
	{
		Statement statement = connection.createStatement();
		ResultSet beers = statement.executeQuery ("SELECT * FROM beers");
		while (beers.next())
		{
			List<BeerType> beertypes = new ArrayList<BeerType>();
			ResultSet categories = statement.executeQuery ("SELECT C.name FROM categories C, beercat B WHERE C.id_cat=B.id_cat AND B.barcode=" + beers.getInt	(1));
			while (categories.next())
			{
				beertypes.add (Enum.valueOf (BeerType.class, categories.getString (1)));
			}
			categories.close();
			
			Beer beer = new Beer (beers.getInt (1), beers.getString (2), beers.getDouble (3), beertypes);
			this.beers.put (beer, new BeerInfo (beers.getDouble (4), beers.getInt (5)));
		}
		beers.close();
	}
	
	/**
	 * Load beers into the program and the database from the specified file
	 */
	private void loadBar (String path) throws SQLException, IOException, FileFormatException
	{
		Statement statement = connection.createStatement();
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader (new FileReader (path));
			
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
					int barcode = Integer.parseInt (tokens[0]);
					ResultSet results = statement.executeQuery (String.format ("SELECT COUNT(barcode) FROM beers WHERE barcode=%d", barcode));
					results.next();
					if (results.getInt (1) == 0)
					{
						List<BeerType> beertypes = new ArrayList<BeerType>();
						String[] types = tokens[3].split ("/");
						for (String type : types)
						{
							beertypes.add (Enum.valueOf (BeerType.class, type));
							
							results = statement.executeQuery (String.format ("SELECT COUNT(*) FROM categories WHERE name='%s'", type));
							results.next();
							if (results.getInt (1) == 0)
							{
								statement.executeUpdate (String.format ("INSERT INTO categories (name) VALUES ('%s')", type));
							}
						}
						
						Beer beer = new Beer (barcode, tokens[1], Double.parseDouble (tokens[2]), beertypes);
						this.beers.put (beer, new BeerInfo (Double.parseDouble (tokens[4])));
						
						statement.executeUpdate (String.format (Locale.ENGLISH, "INSERT INTO beers (barcode, name, strength, price) VALUES (%d, '%s', %f, %f)", beer.getBarcode(), beer.getName(), beer.getStrength(), this.beers.get (beer).price));
						for (String type : types)
						{
							statement.executeUpdate (String.format ("INSERT INTO beercat VALUES (%d, (SELECT id_cat FROM categories WHERE name='%s' LIMIT 1))", barcode, type));
						}
					}
					results.close();
				}
				catch (NumberFormatException exception)
				{
					throw new FileFormatException ("Barcode, strength or price is invalid in line: " + line);
				}
			}
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
		Map<Beer,Double> beers = new HashMap<Beer, Double>();
		for (Beer beer : this.beers.keySet())
		{
			beers.put (beer, this.beers.get (beer).price);
		}
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
		
		beers.put (beer, new BeerInfo (price));
	}
	
	/**
	 * Gets the price of a beer
	 * 
	 * @pre "beer" != null
	 * @post The returned value contains the price of "beer"
	 * @throws NotPresentBeerException if the "beer" is not proposed by this bar
	 */
	public double getPrice (Beer beer)
	{
		if (beers.get (beer) == null)
		{
			throw new NotPresentBeerException();
		}
		
		return beers.get (beer).price;
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
		Order order = new Order (getProposedBeers());
		for (Beer beer : beers)
		{
			order.addBeer (beer, 1);
		}
		return order;
	}
	
	/**
	 * Information about a beer
	 * 
	 * @author Sébastien Combéfis
	 * @version May 1, 2015
	 */
	private static class BeerInfo
	{
		// Instance variables
		private final double price;
		private int quantity;
		
		/**
		 * Creates informations about a beer
		 * 
		 * @pre price > 0
		 *      quantity > 0
		 * @post An instance of this is created, representing information about a beer,
		 *       with specified "price" and "quantity"
		 */
		public BeerInfo (double price, int quantity)
		{
			this.price = price;
			this.quantity = quantity;
		}
		
		/**
		 * Creates informations about a beer
		 * 
		 * @pre price > 0
		 * @post An instance of this is created, representing information about a beer,
		 *       with specified "price" and with quantity set to 0
		 */
		public BeerInfo (double price)
		{
			this (price, 0);
		}
	}
}