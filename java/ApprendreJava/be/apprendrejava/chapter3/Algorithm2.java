// Algorithm2.java

package be.apprendrejava.chapter3;

public class Algorithm2
{
	public static void main (String[] args)
	{
		// Entrées
		int n = 6;
		
		// Algorithme
		int sum = 1;
		for (int i = 1; i <= n; i++)
		{
			sum += i;
		}
		
		// Sortie
		System.out.println (sum);
	}
}