// ConvexHullPane.java

package be.ukonline.miniproject.convexhull;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

/**
 * Class for drawing a set of points and its convex-hull
 * 
 * @author Sébastien Combéfis
 * @version December 22, 2008
 */
@SuppressWarnings ("serial")
public class ConvexHullPane extends JComponent
{
	// Constants
	private static final int POINT_WIDTH = 4;
	
	// Instance variables
	private final List<Point> points;
	private final List<DirectedLine> lines;

	/**
	 * Constructor
	 * 
	 * @pre -
	 * @post An instance of this is created representing a pane
	 *       where the user can click to add points that are drawn
	 *       and able to draw a list of lines
	 */
	public ConvexHullPane()
	{
		points = new ArrayList<Point>();
		lines = new ArrayList<DirectedLine>();
		
		addMouseListener (new ClickListener());
	}
	
	/**
	 * Get the points drawn by the user on this pane
	 * 
	 * @pre -
	 * @post The returned value contains the list of Point entered by the user on this pane
	 */
	public List<Point> getPoints()
	{
		List<Point> list = new ArrayList<Point>();
		list.addAll (points);
		return list;
	}
	
	/**
	 * Set the lines to draw
	 * 
	 * @pre lines != null
	 * @post The lines drawn by this pane is set to the specified list of lines
	 *       and the component is repainted
	 */
	public void setLines (List<DirectedLine> lines)
	{
		assert lines != null;
		
		this.lines.clear();
		this.lines.addAll (lines);
		repaint();
	}
	
	// Draw the points entered by the user and the lines set by setLines
	@Override
	protected void paintComponent (Graphics g)
	{
		super.paintComponent (g);
		
		// Background
		g.setColor (Color.WHITE);
		g.fillRect (0, 0, getWidth(), getHeight());
		
		// Lines
		g.setColor (Color.RED);
		for (DirectedLine dl : lines)
		{
			Point orig = dl.getOrigin();
			Point dest = dl.getDestination();
			g.drawLine (orig.getX(), orig.getY(), dest.getX(), dest.getY());
		}
		
		// Points
		g.setColor (Color.BLACK);
		final int SHIFT = POINT_WIDTH / 2;
		for (Point p : points)
		{
			g.fillOval (p.getX() - SHIFT, p.getY() - SHIFT, POINT_WIDTH, POINT_WIDTH);
		}
	}
	
	/**
	 * Inner class for the action of clicking on this pane
	 */
	private class ClickListener extends MouseAdapter
	{
		// A point corresponding to the click coordinate is added to the
		// list of of points to be drawn by this component
		@Override
		public void mouseClicked (MouseEvent e)
		{
			points.add (new Point (e.getX(), e.getY()));
			repaint();
		}
	}
}