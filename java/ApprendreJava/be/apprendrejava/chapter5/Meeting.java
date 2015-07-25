// Meeting.java

package be.apprendrejava.chapter5;

public class Meeting
{
	public String object;
	public Hour start, end;
	
	public Meeting (String str, Hour s, Hour e)
	{
		object = str;
		start = s;
		end = e;
	}

	public int getDuration()
	{
		return (end.getHour() * 60 + end.getMinutes()) - (start.getHour() * 60 + start.getMinutes());
	}
}