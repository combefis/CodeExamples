// TestUSBMemoryStick.java

package be.ukonline.miniproject.usb;

/**
 * Test class for USBMemoryStick
 *
 * @author Sébastien Combéfis
 * @version December 12, 2009
 */
public class TestUSBMemoryStick
{
	public static void main (String[] args)
	{
		System.out.println (">>> creation");
		USBMemoryStick usb = new USBMemoryStick (1024);
		System.out.println (usb);
		
		System.out.println (">>> adding some files");
		usb.addFile (new File ("movie.mpg", createData (524)));
		usb.addFile (new File ("picture.png", createData (50)));
		usb.addFile (new File ("document.pdf", createData (150)));
		System.out.println (usb);
		
		System.out.println (">>> remove an existing file");
		try
		{
			usb.remove ("picture.png");
			System.out.println (usb);
		}
		catch (FileNotFoundException exception)
		{
			System.out.println ("'picture.png' not found on the USB stick");
		}
		
		System.out.println (">>> removing non existing file");
		try
		{
			usb.remove ("nonExistingFile");
			System.out.println (usb);
		}
		catch (FileNotFoundException exception)
		{
			System.out.println ("'nonExistingFile' not found on the USB stick\n");
		}
		
		System.out.println (">>> adding two more files");
		usb.addFile (new File ("picture.jpg", createData (70)));
		usb.addFile (new File ("picture.gif", createData (45)));
		System.out.println (usb);
		
		System.out.println (">>> adding a file to make the USB stick full");
		usb.addFile (new File ("sound.mp3", createData (usb.getCapacity() - usb.getUsed())));
		System.out.println (usb);
		
		System.out.println (">>> adding a file to a full USB stick");
		try
		{
			usb.addFile (new File ("afile", createData (100)));
			System.out.println (usb);
		}
		catch (FullException exception)
		{
			System.out.println ("USB stick is full");
		}
	}
	
	private static String createData (int size)
	{
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < size; i++)
		{
			builder.append (' ');
		}
		return builder.toString();
	}
}