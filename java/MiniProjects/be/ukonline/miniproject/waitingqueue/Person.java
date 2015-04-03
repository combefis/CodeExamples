// Person.java

package be.ukonline.miniproject.waitingqueue;

/**
 * Class representing a person
 *
 * @author Sébastien Combéfis
 * @version January 8, 2009
 */
public class Person implements Comparable<Person>
{
	// Instance variables
	private final String firstname;
	private final String lastname;
	
	/**
	 * Constructor
	 * 
	 * @pre first, last != null
	 *      first, last != ""
	 * @post An instance of this is created representing a person
	 *       with the specified first and lastname
	 */
	public Person (String first, String last)
	{
		assert first != null && ! "".equals (first);
		assert last != null && ! "".equals (last);
		
		firstname = first;
		lastname = last;
	}
	
	/**
	 * Get the firstname
	 * 
	 * @pre -
	 * @post The returned value contains the firstname of this person
	 */
	public String getFirstname()
	{
		return firstname;
	}
	
	/**
	 * Get the lastname
	 * 
	 * @pre -
	 * @post The returned value contains the lastname of this person
	 */
	public String getLastname()
	{
		return lastname;
	}
	
	@Override
	public String toString()
	{
		return firstname + " " + lastname;
	}

	@Override
	public boolean equals (Object o)
	{
		if (o instanceof Person)
		{
			Person p = (Person) o;
			return firstname.equals (p.firstname) && lastname.equals (p.lastname);
		}
		return false;
	}
	
	/**
	 * Compares two persons
	 * The comparison is made with the lastname
	 */
	public int compareTo (Person p)
	{
		return lastname.compareTo (p.lastname);
	}
}