// SoundexGenerator.java

package be.ukonline.miniproject.soundex;

import java.text.Normalizer;
import java.text.Normalizer.Form;

/**
 * Class representing a generator of soundex code
 *
 * @author Sébastien Combéfis
 * @version December 27, 2008
 */
public class SoundexGenerator
{
	// Constants
	private static final SoundexGenerator ENGLISH;
	static
	{
		char[] toremove = {'A', 'E', 'H', 'I', 'O', 'U', 'W', 'Y'};
		char[] map = {'A', '1', '2', '3', 'E', '1', '2', 'H', 'I', '2', '2', '4', '5', '5', 'O', '1', '2', '6', '2', '3', 'U', '1', 'W', '2', 'Y', '2'};
		ENGLISH = new SoundexGenerator (toremove, map);
	}
	
	private static final SoundexGenerator FRENCH;
	static
	{
		char[] toremove = {'A', 'E', 'H', 'I', 'O', 'U', 'W', 'Y'};
		char[] map = {'A', '1', '2', '3', 'E', '9', '7', 'H', 'I', '7', '2', '4', '5', '5', 'O', '1', '2', '6', '8', '3', 'U', '9', 'W', '8', 'Y', '8'};
		FRENCH = new SoundexGenerator (toremove, map);
	}
	
	// Instance variables
	private final char[] toremove;
	private final char[] map;
	
	/**
	 * Constructor
	 * 
	 * @pre r != null
	 *      m != null
	 * @post An instance of this is created
	 *       representing a soundex generator with the specified parameters :
	 *       - r are the characters to remove
	 *       - m is the map array
	 */
	private SoundexGenerator (char[] r, char[] m)
	{
		assert r != null && m != null;
		
		toremove = r;
		map = m;
	}
	
	/**
	 * Get a soundex generator for the english language
	 * 
	 * @pre -
	 * @post The returned value contains a soundex generator
	 *       for the english language
	 */
	public static SoundexGenerator englishSoundexGenerator()
	{
		return ENGLISH;
	}
	
	/**
	 * Get a soundex generator for the french language
	 * 
	 * @pre -
	 * @post The returned value contains a soundex generator
	 *       for the french language
	 */
	public static SoundexGenerator frenchSoundexGenerator()
	{
		return FRENCH;
	}
	
	/**
	 * Compute the soundex of a word
	 * 
	 * @pre s != null
	 *      s.length() > 1
	 * @post The returned value contains the soundex code of the specified word
	 */
	public String computeSoundex (String s)
	{
		assert s != null;
		assert s.length() > 1;
		
		// To upper case and remove spaces
		s = s.toUpperCase();
		s = filter (s, ' ');
		
		// Phase 0 - Remove accents
		String normalized = Normalizer.normalize (s, Form.NFD);
		normalized = normalized.replaceAll ("\\p{InCombiningDiacriticalmarks}+", "");
		normalized = normalized.substring (1);
				
		// Phase 1 - Mapping
		StringBuilder mapped = new StringBuilder (normalized.charAt (0));
		for (char c : normalized.toCharArray())
		{
			mapped.append (mapLetter (c, map));
		}
		
		// Phase 2 - Remove repetitions
		String cleaned = removeRepetitions (mapped.toString());
		
		// Phase 3 - Build the return value
		String ret = s.substring (0, 1) + filter (cleaned, toremove);
		if (ret.length() > 4)
		{
			ret = ret.substring (0, 4);
		}
		else
		{
			ret = fill (ret, '0', 4);
		}
		
		return ret;
	}
	
	/**
	 * Clean a string by replacing all successive repetition of characters by one character
	 * 
	 * @pre s != null
	 * @post The returned value contains a String corresponding to s
	 *       whom all successive repetition of a character is replaced by
	 *       a single occurence of the repeated character
	 * @example s = "eeRRr838888..p"
	 *          returned value = "eRr838.p"
	 */
	private static String removeRepetitions (String s)
	{
		assert s != null;
		
		StringBuilder buff = new StringBuilder();
		char last = '\0';
		for (int i = 0; i < s.length(); i++)
		{
			char c = s.charAt (i);
			if (c != last)
			{
				buff.append (c);
				last = c;
			}
		}
		return buff.toString();
	}
	
	/**
	 * Get a letter corresponding to another with respect to a mapping
	 * 
	 * @pre 'A' <= c <= 'Z'
	 *      map != null && map.length = 26
	 * @post The returned value contains the character corresponding to
	 *       c according to the specified map considering that we have
	 *       the following mapping :
	 *       'A' => map[0], 'B' => map[1], ..., 'Z' => map[25]
	 * @example c = 'E', map = {'Z', 'A', 'B', ..., 'Y'}
	 *          returned value = 'D' 
	 */
	private static char mapLetter (char c, char[] map)
	{
		assert 'A' <= c && c <= 'Z';
		assert map != null && map.length == 26;
		
		return map[c - 'A'];
	}
	
	/**
	 * Fill a string with a character to make it a certain length
	 * 
	 * @pre s != null
	 *      len >= s.length()
	 * @post The returned value contains a String corresponding to
	 *       s filled to the right with occurences of the character c
	 *       so that the returned string's length is len
	 * @example s = "Oups", c = '!', len = 10
	 *          returned value = "Oups!!!!!!"
	 */
	private static String fill (String s, char c, int len)
	{
		assert s != null;
		assert len >= s.length();
		
		StringBuilder buff = new StringBuilder (s);
		for (int i = s.length(); i < len; i++)
		{
			buff.append (c);
		}
		return buff.toString();
	}
	
	/**
	 * Remove all the occurence of a list of characters from a string
	 * 
	 * @pre s != null
	 *      tab != null
	 * @post The returned value contains a String corresponding
	 *       to s whom all the occurences of all the characters that are
	 *       in the array tab are removed
	 * @example s = "Hello World !", tab = {'a', 'e', 'i', 'o', 'u'}
	 *          returned value = "Hll Wrld !"
	 */
	private static String filter (String s, char[] tab)
	{
		assert s != null && tab != null;
		
		for (int i = 0; i < tab.length; i++)
		{
			s = filter (s, tab[i]);
		}
		return s;
	}
	
	/**
	 * Remove all the occurence of a character from a string
	 * 
	 * @pre s != null
	 * @post The returned value contains a String corresponding
	 *       to s whom all the occurences of the character a
	 *       are removed
	 * @example s = "WOW, I got it !", a = 'o'
	 *          returned value = "WOW, I gt it !"
	 */
	private static String filter (String s, char a)
	{
		assert s != null;
		
		StringBuilder buff = new StringBuilder();
		for (int i = 0; i < s.length(); i++)
		{
			char c = s.charAt (i);
			if (c != a)
			{
				buff.append (c);
			}
		}
		return buff.toString();
	}
}