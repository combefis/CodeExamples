// MyVeryOwnBar.java

package be.combefis.codeexamples.myveryownbar;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.SwingUtilities;

import be.combefis.codeexamples.myveryownbar.gui.BarGUI;
import be.combefis.codeexamples.myveryownbar.model.Bar;
import be.combefis.codeexamples.myveryownbar.model.Beer;
import be.combefis.codeexamples.myveryownbar.model.FileFormatException;
import be.combefis.codeexamples.myveryownbar.model.Order;

/**
 * Main class
 * 
 * @author Sébastien Combéfis
 * @version March 10, 2015
 */
public final class MyVeryOwnBar
{
	public static void main (String[] args)
	{
		try
		{
			final Bar adhoc = new Bar ("The Best Bar in the World", "beers.txt");
			
			System.out.println (adhoc.getName());
			List<Beer> beers = new ArrayList<Beer> (adhoc.getProposedBeers().keySet());
			for (Beer beer : beers)
			{
				System.out.println (beer);
			}
			System.out.println();
			
			Order order = adhoc.makeOrder (Arrays.asList (beers.get (0), beers.get (1), beers.get (0), beers.get (2)));
			System.out.println (order);
			System.out.println (order.totalPrice());
			
			SwingUtilities.invokeLater (new Runnable()
			{
				public void run()
				{
					new BarGUI (adhoc);
				}
			});
			
		}
		catch (SQLException exception)
		{
			System.err.println ("Error with the database.");
			exception.printStackTrace();
		}
		catch (IOException exception)
		{
			System.err.println ("Error while reading the file.");
		}
		catch (FileFormatException exception)
		{
			System.err.println ("Invalid file: " + exception.getMessage());
		}
	}
}