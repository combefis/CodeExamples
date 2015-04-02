// BarGUI.java

package be.combefis.codeexamples.myveryownbar.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTable;

import be.combefis.codeexamples.myveryownbar.model.Bar;

/**
 * A GUI to manage a bar
 * 
 * @author Sébastien Combéfis
 * @version April 2, 2015
 */
public final class BarGUI
{
	// Instance variables
	private final Bar bar;
	
	/**
	 * Creates a GUI to manage a bar
	 * 
	 * @pre "bar" != null
	 * @post An instance of this is created, building and showing a GUI to manage
	 *       the specified bar and makes it possible to make orders
	 */
	public BarGUI (Bar bar)
	{
		this.bar = bar;
		
		JFrame frame = new JFrame (bar.getName());
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		
		frame.add (new JTable (new BeerDataModel (bar)), BorderLayout.WEST);
		
		frame.setSize (800, 600);
		frame.setVisible (true);
	}
}