// Hour.java

package be.apprendrejava.chapter5;

public class Hour
{
	private int minutes;

	public Hour (int h, int m)
	{
		minutes = h * 60 + m;
	}
	
	public int getHour()
	{
		return minutes / 60;
	}
	
	public int getMinutes()
	{
		return minutes % 60;
	}
}