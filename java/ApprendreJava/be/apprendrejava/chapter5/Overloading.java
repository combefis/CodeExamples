// Overloading.java

package be.apprendrejava.chapter5;

public class Overloading
{
	public int test (int a, int b)
	{
		System.out.print ("I ");
		return a + b;
	}

	public String test (long a, char b)
	{
		System.out.print ("S ");
		return "" + a + b;
	}

	public double test (byte a, int b)
	{
		System.out.print ("D ");
		return a + b;
	}

	public char test (long a, short b)
	{
		System.out.print ("C ");
		return (char) (a + b);
	}
	
	public static void main (String[] args)
	{
		byte b = 12;
		short s = 43;
		int i = 90;
		long l = 123;

		Overloading o = new Overloading();
		System.out.println (o.test (b, i));
		System.out.println (o.test (s, i));
		System.out.println (o.test (l, i));
		System.out.println (o.test (i, s));
	}
}