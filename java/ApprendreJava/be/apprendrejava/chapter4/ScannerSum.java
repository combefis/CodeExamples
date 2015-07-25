// ScannerSum.java

package be.apprendrejava.chapter4;

import java.util.Scanner;

public class ScannerSum
{
	public static void main (String[] args)
	{
		Scanner scanner = new Scanner (System.in);
		int sum = 0;
		while (true)
		{
			if (scanner.hasNextInt())
			{
				sum += scanner.nextInt();
			}
			else if ("bye".equals (scanner.next()))
			{
				System.out.printf ("Somme = %d\n", sum);
				
				scanner.close();
				System.exit (0);
			}
		}
	}
}