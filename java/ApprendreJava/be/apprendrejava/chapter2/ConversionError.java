// ConversionError.java

package be.apprendrejava.chapter2;

public class ConversionError
{
	public static void main (String[] args)
	{
		int dollars;
		float money = 25.0F;
		dollars = money;           // Conversion de float vers int
		System.out.println (dollars);
	}
}