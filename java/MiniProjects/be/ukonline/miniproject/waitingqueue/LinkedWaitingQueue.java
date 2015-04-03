// LinkedWaitingQueue.java

package be.ukonline.miniproject.waitingqueue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * An implementation of a waiting queue based on a
 * simple linked structure
 *
 * @author Sébastien Combéfis
 * @version January 8, 2009
 */
public class LinkedWaitingQueue implements WaitingQueueIF
{
	// Instance variables
	private int nextNumber;
	private WaitingPerson front; 
	private WaitingPerson rear;
	
	/**
	 * Constructor
	 * 
	 * @pre -
	 * @post An instance of this is created
	 *       representing a new waiting queue containing no persons
	 *       The next ticket number is the 1
	 */
	public LinkedWaitingQueue()
	{
		nextNumber = 1;
		front = rear = null;
	}
	
	/*
	 * @see WaitingQueueIF#add(Person)
	 */
	public void add (Person p) throws AlreadyInQueueException
	{
		if (isInQueue (p))
		{
			throw new AlreadyInQueueException();
		}
		
		WaitingPerson wp = new WaitingPerson (p);
		
		// Empty queue
		if (front == null)
		{
			front = wp;
		}
		// Not empty queue
		else
		{
			rear.next = wp;
		}
		rear = wp;
	}

	/*
	 * @see WaitingQueueIF#getFirstPerson()
	 */
	public Person getFirstPerson() throws EmptyQueueException
	{
		if (isEmpty())
		{
			throw new EmptyQueueException();
		}
		return front.person;
	}

	/*
	 * @see WaitingQueueIF#getLastPerson()
	 */
	public Person getLastPerson() throws EmptyQueueException
	{
		if (isEmpty())
		{
			throw new EmptyQueueException();
		}
		return rear.person;
	}
	
	/*
	 * @see WaitingQueueIF#getPersonNumber()
	 */
	public int getPersonNumber()
	{
		int count = 0;
		WaitingPerson current = front;
		
		while (current != null)
		{
			count++;
			current = current.next;
		}
		
		return count;
	}

	/*
	 * @see WaitingQueueIF#isEmpty()
	 */
	public boolean isEmpty()
	{
		return front == null;
	}

	/*
	 * @see WaitingQueueIF#isInQueue(Person)
	 */
	public boolean isInQueue (Person p)
	{
		WaitingPerson current = front;
		
		while (current != null)
		{
			if (current.person.equals (p))
			{
				return true;
			}
			current = current.next;
		}
		
		return false;
	}

	/*
	 * @see WaitingQueueIF#remove()
	 */
	public Person remove() throws EmptyQueueException
	{
		if (isEmpty())
		{
			throw new EmptyQueueException();
		}
		
		Person p = front.person;
		front = front.next;
		
		// If queue becomes empty
		if (front == null)
		{
			rear = null;
		}
		
		return p;
	}

	/*
	 * @see WaitingQueueIF#remove(Person)
	 */
	public Person remove (Person p) throws NotInQueueException
	{
		assert p != null;
		
		// The person to remove is the first
		if (front.person.equals (p))
		{
			return remove();
		}
		
		// Search the person and set current before
		WaitingPerson before = front;
		while (before.next != null && ! before.next.person.equals (p))
		{
			before = before.next;
		}
		
		// Reach the end of the queue
		if (before.next == null)
		{
			throw new NotInQueueException();
		}
		
		// If the removed is the last
		if (before.next == rear)
		{
			rear = before;
		}
		
		before.next = before.next.next;
		return p;
	}

	/*
	 * @see WaitingQueueIF#remove(int)
	 */
	public Person remove (int nb)
	{
		assert nb > 0;
		
		WaitingPerson current = front;
		while (current != null)
		{
			if (current.ticketNumber == nb)
			{
				return remove (current.person);
			}
			current = current.next;
		}
		return null;
	}

	/*
	 * @see WaitingQueueIF#requeueFirst()
	 */
	public void requeueFirst()
	{
		if (! isEmpty())
		{
			int nb = front.ticketNumber;
			Person p = remove();
			add (p);
			rear.ticketNumber = nb;
		}
	}

	/*
	 * @see WaitingQueueIF#switchPlace(Person)
	 */
	public void switchPlace (Person p) throws NotInQueueException
	{
		assert p != null;
		
		int size = getPersonNumber();
		
		// The person to switch is the first person of the queue
		if (size >= 1 && front.person.equals (p))
		{
			return;
		}
		// The person to switch is the second person of the queue
		else if (size >= 2 && front.next.person.equals (p))
		{
			WaitingPerson current = front.next;
			front.next = current.next;
			current.next = front;
			front = current;
			return;
		}
		// The person to switch is between the third and last place in the queue
		else if (size >= 3)
		{
			WaitingPerson current = front;
			while (current.next.next != null && ! current.next.next.person.equals (p))
			{
				current = current.next;
			}
			
			if (current != null)
			{
				WaitingPerson b = current.next;
				current.next = b.next;
				b.next = current.next.next;
				current.next.next = b;
				
				if (b.next == null)
				{
					rear = b;
				}
				return;
			}
		}
		
		throw new NotInQueueException();
	}
	
	/**
	 * Save the queue in a file
	 * 
	 * @pre file != null
	 * @post This queue is saved in the specified file
	 *       If the file already exists, its content is cleaned
	 *       If the file does not exists, it's created
	 *       The first line contains the next ticket number
	 *       The next lines contains the persons in the queue in the following format :
	 *          TicketNumber;Firstname;Lastname
	 *       The first person is the one that is in the front of the queue
	 * @throws IOException if an error occured while writing to the file
	 */
	public void save (String file) throws IOException
	{
		assert file != null;
		
		PrintWriter writer = new PrintWriter (new BufferedWriter (new FileWriter (file)));
		writer.println (nextNumber);
		WaitingPerson current = front;
		while (current != null)
		{
			writer.println (current.ticketNumber + ";" + current.person.getFirstname() + ";" + current.person.getLastname());
			current = current.next;
		}
		
		// Checking errors
		if (writer.checkError())
		{
			writer.close();
			throw new IOException();
		}
			
		writer.close();
	}
	
	/**
	 * Load a queue from a file
	 * 
	 * @pre file != null
	 * @post If the file exists and and is correctly formatted (see save)
	 *       The returned value contains the queue contained in the file
	 * @throws IOException if an error occured while reading the file
	 * @throws FileNotFoundException if the specified file was not found
	 * @throws ParseException if there are errors in the formatting
	 */
	public static WaitingQueueIF load (String file) throws IOException, FileNotFoundException, ParseException
	{
		assert file != null;
		
		LinkedWaitingQueue wq = new LinkedWaitingQueue();
		BufferedReader reader = new BufferedReader (new FileReader (file));
		
		// Read the first line : next queue number
		String line = reader.readLine();
		if (line == null)
		{
			reader.close();
			throw new ParseException ("First line is missing", 0);
		}
		
		int nextnumber;
		try
		{
			nextnumber = Integer.parseInt (line);
			if (nextnumber < 0)
			{
				reader.close();
				throw new ParseException ("First line must be positive", 0);
			}
		}
		catch (NumberFormatException exception)
		{
			throw new ParseException ("First line must be an integer", 0);
		}
		
		// Read the content of the file : waiting persons
		int linenb = 1;
		while ((line = reader.readLine()) != null)
		{
			String[] tokens = line.split (";");
			if (tokens.length != 3)
			{
				reader.close();
				throw new ParseException ("Line " + linenb + " does not contains the three required elements", 0);
			}
			
			int ticketnumber;
			try
			{
				ticketnumber = Integer.parseInt (tokens[0]);
				if (ticketnumber < 0)
				{
					throw new ParseException ("Line " + linenb + "'s queue number must be positive", 0);
				}
			}
			catch (NumberFormatException exception)
			{
				reader.close();
				throw new ParseException ("Line " + linenb + "'s queue number must be an integer", 0);
			}
			
			wq.nextNumber = ticketnumber;
			wq.add (new Person (tokens[1], tokens[2]));
			
			linenb++;
		}
		
		wq.nextNumber = nextnumber;
		
		reader.close();
		return wq;
	}
	
	@Override
	public String toString()
	{
		ArrayList<String> list = new ArrayList<String>();
		WaitingPerson current = front;
		while (current != null)
		{
			list.add (current.toString());
			current = current.next;
		}
		Collections.reverse (list);
		
		return "Rear " + list.toString().replaceAll (",", " =>") + " Front";
	}
	
	/**
	 * Inner class representing a node of the list
	 */
	private class WaitingPerson
	{
		// Instance variables
		public WaitingPerson next;
		public int ticketNumber;
		public Person person;
		
		/**
		 * Constructor
		 * 
		 * @pre p != null
		 * @post An instance of this is created representing a node
		 *       of the list for the specified person, with the next
		 *       ticket number as personal number
		 */
		public WaitingPerson (Person p)
		{
			person = p;
			ticketNumber = nextNumber++;
		}
		
		@Override
		public String toString()
		{
			return ticketNumber + ":" + person;
		}
	}
}