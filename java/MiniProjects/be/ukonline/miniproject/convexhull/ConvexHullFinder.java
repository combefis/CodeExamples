// ConvexHullFinder.java

package be.ukonline.miniproject.convexhull;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Class representing a frame for finding a convex hull
 * 
 * @author Sébastien Combéfis
 * @version December 22, 2008
 */
public class ConvexHullFinder
{
	// Instance variables
	private final ConvexHullPane chpane;
	
	/**
	 * Constructor
	 * 
	 * @pre -
	 * @post A frame is created and made visible allowing the user
	 *       to enter points and to compute the convex-hull
	 *       of these points in the plane
	 */
	public ConvexHullFinder()
	{
		// Building the frame
		JFrame frame = new JFrame ("Convex Hull Finder 0.1");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		
		// Build the components
		chpane = new ConvexHullPane();
		JButton compute = new JButton ("Compute");
		compute.addActionListener (new ComputeListener());
		
		// Add components to the frame
		Container container = frame.getContentPane();
		container.add (chpane, BorderLayout.CENTER);
		container.add (compute, BorderLayout.SOUTH);
		
		// Shows the frame
		frame.setSize (new Dimension (400, 300));
		frame.setVisible (true);
	}
	
	/**
	 * Inner class for the action of computing the convex hull
	 */
	private class ComputeListener implements ActionListener
	{
		// The convex hull of entered points is computed and shown on the frame
		public void actionPerformed (ActionEvent event)
		{
			List<Point> points = chpane.getPoints();
			List<DirectedLine> res = new ArrayList<DirectedLine>();
			List<DirectedLine> totest = getAllDirectedLines (points);
			
			// For each directed line (p,q)
			for (DirectedLine dl : totest)
			{
				boolean valid = true;
				for (int i = 0; i < points.size() && valid; i++)
				{
					Point p = points.get (i);
					if (! p.equals (dl.getOrigin()) && ! p.equals (dl.getDestination()))
					{
						if (p.liesOnLeft (dl))
						{
							valid = false;
							break;
						}
					}
				}
				
				// If all points on the left, add the directed line
				if (valid)
				{
					res.add (dl);
				}
			}
			
			chpane.setLines (res);
			
			for (DirectedLine line : res)
			{
				System.out.println (line);
			}
		}
		
		/**
		 * Build the list of lines to test for the convex-hull algorithm
		 * 
		 * @pre points != null
		 * @post The returned value contains the list of all directed lines
		 *       (p, q) with p != q, for all p,q in points
		 */
		private List<DirectedLine> getAllDirectedLines (List<Point> points)
		{
			assert points != null;
			
			List<DirectedLine> list = new ArrayList<DirectedLine>();
			for (Point p : points)
			{
				for (Point q : points)
				{
					if (! p.equals (q))
					{
						list.add (new DirectedLine (p, q));
					}
				}
			}
			return list;
		}
	}
	
	/**
	 * Main method
	 */
	public static void main (String[] args)
	{
		new ConvexHullFinder();
	}
}