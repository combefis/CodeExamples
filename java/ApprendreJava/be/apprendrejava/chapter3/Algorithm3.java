// Algorithm3.java

package be.apprendrejava.chapter3;

public class Algorithm3
{
	public static void main (String[] args)
	{
		int n = 12; // Entrée
		
		// Algorithme
		boolean prime = true;
		for (int i = 2; i < n; i++)
		{
			if (n % i == 0)
			{
				prime = false;
			}
		}
		
		// Sortie
		System.out.println (prime);
	}
}