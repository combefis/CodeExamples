// USBSimulator.java

package be.ukonline.miniproject.usb;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Test for the JComponent showing USBMemoryStick
 *
 * @author Sébastien Combéfis
 * @version March 13, 2009
 */
public class USBSimulator
{
	public static void main (String[] args)
	{
		final USBMemoryStick usb = new USBMemoryStick (1024);		
		final JFrame frame = new JFrame ("USB Memory Stick Simulator");
		final JComponent usbComponent = USBMemoryStick.createComponent (usb);
		frame.getContentPane().add (usbComponent, BorderLayout.CENTER);
		
		final JTextField name = new JTextField ("File name");
		final JTextField size = new JTextField ("File size");
		JButton add = new JButton ("add");
		add.addActionListener (new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				try
				{
					int s = Integer.parseInt (size.getText());
					if (s >= 0)
					{
						usb.addFile (new File (name.getText(), createData (s)));
						usbComponent.repaint();
						return;
					}
				}
				catch (NumberFormatException exception){}
				catch (FullException exception)
				{
					JOptionPane.showMessageDialog (frame, "Stick is Full", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				JOptionPane.showMessageDialog (frame, "File Parameters Not Valid", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		final JTextField removename = new JTextField ("File index");
		JButton remove = new JButton ("remove");
		remove.addActionListener (new ActionListener()
		{
			public void actionPerformed (ActionEvent event)
			{
				try
				{
					usb.remove (removename.getText());
					usbComponent.repaint();
				}
				catch (FileNotFoundException exception)
				{
					JOptionPane.showMessageDialog (frame, "File Not Found", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JPanel controlbox = new JPanel();
		controlbox.add (new JLabel ("Name"));
		controlbox.add (name);
		controlbox.add (new JLabel ("Size"));
		controlbox.add (size);
		controlbox.add (add);
		controlbox.add (new JLabel ("Index"));
		controlbox.add (removename);
		controlbox.add (remove);
		
		frame.getContentPane().add (controlbox, BorderLayout.SOUTH);
		
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.setSize (new Dimension (400, 150));
		frame.setVisible (true);
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