// VariableScope.java

package be.apprendrejava.chapter3;

public class VariableScope
{
	public static void main (String[] args)
	{
		// aucune
		int a = 12;
		// a
		{
			// a
			int b = a * 100;
			// a et b
		}
		int b = 12;
		// a et b
		{
			// a et b
			int c = a + 10;
			// a, b et c
		}
		// a et b
	}
}