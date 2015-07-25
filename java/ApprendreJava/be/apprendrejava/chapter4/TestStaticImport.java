// TestStaticImport.java

package be.apprendrejava.chapter4;

import static java.lang.Math.abs;

public class TestStaticImport
{
	public static void main (String[] args)
	{
		double val = -5.0;
		double absval = abs (val);
		System.out.println (absval);
	}
}