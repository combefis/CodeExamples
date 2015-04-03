// USBMemoryStick.java

package be.ukonline.miniproject.usb;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

/**
 * Class representing a USB memory stick
 *
 * @author Sébastien Combéfis
 * @version March 12, 2009
 */
public final class USBMemoryStick implements StorageDevice
{
	// Instance variables
	private final List<Block> blocks;
	private final int capacity;
	private int used;
	
	/**
	 * Constructor
	 * 
	 * @pre capacity > 0
	 * @post An instance of this is created, representing an USB memory stick
	 *       with specified capacity, and containing nothing
	 */
	public USBMemoryStick (int capacity)
	{
		blocks = new ArrayList<Block>();
		blocks.add (new EmptyBlock (capacity));
		
		this.capacity = capacity;
		used = 0;
	}
	
	public int getCapacity()
	{
		return capacity;
	}
	
	public int getUsed()
	{
		return used;
	}
	
	public boolean isFull()
	{
		return used == capacity;
	}
	
	public void addFile (File f) throws FullException
	{
		if (f.getSize()+ used > capacity)
		{
			throw new FullException ("Stick is full, only remaining " + (capacity - used));
		}
		
		// Search for the first empty block
		Block b = null;
		int index = -1;
		for (int i = 0; i < blocks.size() && index == - 1; i++)
		{
			b = blocks.get (i);
			if (b instanceof EmptyBlock)
			{
				if (b.getSize() >= f.getSize())
				{
					index = i;
				}
			}
		}
		
		// If found, add the file
		if (index != -1)
		{
			blocks.add (index, f);
			blocks.set (index + 1, new EmptyBlock (b.getSize() - f.getSize()));
		}
		else
		{
			// Defragment the stick
			List<Integer> toremove = new ArrayList<Integer>();
			int empty = 0;
			for (int i = 0; i < blocks.size(); i++)
			{
				Block bb = blocks.get (i);
				if (bb instanceof EmptyBlock)
				{
					toremove.add (i);
					empty += bb.getSize();
				}
			}
			for (int i = toremove.size() - 1; i >= 0; i--)
			{
				blocks.remove (toremove.get (i).intValue());
			}
			
			// Add the file
			blocks.add (f);
			if (empty - f.getSize() > 0)
			{
				blocks.add (new EmptyBlock (empty - f.getSize()));
			}
		}
		
		used += f.getSize();
	}

	public File getFile (String name) throws FileNotFoundException
	{
		return (File) blocks.get (getFileIndex (name));
	}
	
	/**
	 * Get the index of a file from the device
	 * 
	 * @pre name != null && ! name.equals ("")
	 * @post The returned value contains the index of a file with specified name if such file exists
	 * @throws FileNotFoundException otherwise
	 */
	private int getFileIndex (String name) throws FileNotFoundException
	{
		for (int i = 0; i < blocks.size(); i++)
		{
			Block b = blocks.get (i);
			if (b instanceof File && ((File) b).getName().equals (name))
			{
				return i;
			}
		}
		throw new FileNotFoundException();
	}
	
	public File remove (String name)  throws FileNotFoundException
	{
		int i = getFileIndex (name);
		File f = (File) blocks.get (i);
		blocks.set (i, new EmptyBlock (f.getSize()));
		used -= f.getSize();
		return f;
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		for (Block b : blocks)
		{
			builder.append (b).append ("\n");
		}
		return builder.toString();
	}
	
	/**
	 * Inner class representing an empty block, that is containing no data
	 *
	 * @author S�bastien Comb�fis
	 * @version 2009-03-12
	 */
	private final static class EmptyBlock implements Block
	{
		// Instance variable
		private final int size;
		
		/**
		 * Constructor
		 * 
		 * @pre size > 0
		 * @post An instance of this is created, representing
		 *       an empty block with the specified size
		 */
		public EmptyBlock (int size)
		{
			this.size = size;
		}
		
		public int getSize()
		{
			return size;
		}
		
		@Override
		public String toString()
		{
			return "Empty Block (" + size + ")";
		}
	}
	
	@SuppressWarnings ("serial")
	public static JComponent createComponent (final USBMemoryStick usb)
	{
		return new JComponent()
		{
			@Override
			protected void paintComponent (Graphics graphics)
			{
				super.paintComponent (graphics);
				
				int width = getWidth();
				int height = getHeight();
				
				int nextpos = 0;
				
				for (int i = 0; i < usb.blocks.size(); i++)
				{
					Block b = usb.blocks.get (i);
					int w = (int) (b.getSize() / (double) usb.capacity * width);
					if (i == usb.blocks.size() - 1)
					{
						w = width;
					}
					
					if (b instanceof File)
					{
						graphics.setColor (new Color (0x99, 0, 0));
						graphics.fillRect (nextpos, 0, w, height);
						
						graphics.setColor (Color.WHITE);
						graphics.drawString (((File) b).getName(), nextpos + 2, (int) (height / 2.0));
					}
					else
					{
						graphics.setColor (new Color (0, 0, 0x99));
						graphics.fillRect (nextpos, 0, w, height);
					}
					graphics.setColor (Color.WHITE);
					graphics.drawString ("" + b.getSize(), nextpos + 2, height - 5);
					graphics.drawRect (nextpos, 0, w, height);
					nextpos += w;
				}
			}
		};
	}
}