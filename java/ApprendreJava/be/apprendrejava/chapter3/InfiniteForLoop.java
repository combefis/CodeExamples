// InfiniteForLoop.java

package be.apprendrejava.chapter3;

public class InfiniteForLoop
{
	public static void main (String[] args)
	{
		int i = 0;
		for ( ; ; System.out.print (i))
		{
			i = i + 1;
		}
		System.out.println ("Ceci ne sera JAMAIS affiché");
	}
}