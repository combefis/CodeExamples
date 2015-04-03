// Soundex.java

package be.ukonline.miniproject.soundex;

import java.util.Scanner;

/**
 * Test class for the SoundexGenerator
 * 
 * @author Sébastien Combéfis
 * @version December 27, 2008
 */
public class Soundex
{
	/**
	 * Main method
	 */
	public static void main (String[] args)
	{
		Scanner scanner = new Scanner (System.in);
		
		// Welcome message
		System.out.println ("Soundex Generator");
		
		// Choose the language
		int language = 0;
		do
		{
			System.out.println ("Choose language :\n1 - English\n2 - French");
			if (scanner.hasNextInt())
			{
				language = scanner.nextInt();
			}
			else
			{
				scanner.next();
			}
		}
		while (! (language == 1 || language == 2));
		
		// Get the soundex generator
		SoundexGenerator soundex = null;
		switch (language)
		{
			case 1: soundex = SoundexGenerator.englishSoundexGenerator(); break;
			case 2: soundex = SoundexGenerator.frenchSoundexGenerator(); break;
		}
		
		// Main loop
		String line = null;
		do
		{
			System.out.print ("? ");
			line = scanner.next();
			if (! "0".equals (line) && line.length() > 1)
			{
				System.out.println (soundex.computeSoundex (line));
			}
		}
		while (! "0".equals (line));
		
		System.out.println ("Au revoir !");
	}
}