// Person.java

package be.apprendrejava.chapter5;

import java.util.Date;

public class Person
{
	private final String name;
	private final Date birthday;
	
	public Person (String n, Date b)
	{
		name = n;
		birthday = new Date (b.getYear(), b.getMonth(), b.getDay());
	}
	
	public Date getBirthday()
	{
		return new Date (birthday.getYear(), birthday.getMonth(), birthday.getDay());
	}
}