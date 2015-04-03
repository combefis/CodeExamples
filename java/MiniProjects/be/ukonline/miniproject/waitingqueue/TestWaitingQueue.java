// TestWaitingQueue.java

package be.ukonline.miniproject.waitingqueue;

/**
 * A small class testing the waiting queue
 *
 * @author Sébastien Combéfis
 * @version January 8, 2009
 */
public class TestWaitingQueue
{
	/**
	 * Main method
	 */
	public static void main (String[] args)
	{
		Person[] p = {
			new Person ("John", "Doe"),
			new Person ("Sam", "Watts"),
			new Person ("Chuck", "Norris"),
			new Person ("Jay", "Farrell"),
			new Person ("Tom", "Spitz"),
			new Person ("Bill", "Mainland")
		};
		
		WaitingQueueIF wq = new LinkedWaitingQueue();
		System.out.println ("Initialisation :\n\t" + wq);
		
		wq.add (p[0]);
		wq.add (p[1]);
		System.out.println ("Adding John & Sam :\n\t" + wq);
		
		System.out.println ("Removed : " + wq.remove() + "\n\t" + wq);
		
		wq.add (p[2]);		
		wq.add (p[3]);
		System.out.println ("Adding Chuck & Jay :\n\t" + wq);
		
		wq.requeueFirst();
		System.out.println ("Requeue first :\n\t" + wq);
		
		wq.switchPlace (p[3]);
		System.out.println ("Jay takes Chuck's place :\n\t" + wq);
		
		wq.switchPlace (p[1]);
		System.out.println ("Sam takes Chuck's place :\n\t" + wq);
		
		wq.requeueFirst();
		System.out.println ("Requeue first :\n\t" + wq);
		
		wq.remove (p[3]);
		System.out.println ("Jay removed from the queue :\n\t" + wq);
		
		wq.add (p[5]);		
		wq.add (p[3]);
		wq.add (p[4]);
		System.out.println ("Adding Bill, Jay & Tom :\n\t" + wq);
		
		System.out.println ("Removed #8 : " + wq.remove (8) + "\n\t" + wq);
		
		System.out.println ("Is Bill in queue ? " + wq.isInQueue (p[5]));
		System.out.println ("Is John in queue ? " + wq.isInQueue (p[0]));
		
		System.out.println ("First : " + wq.getFirstPerson());
		System.out.println ("Last : " + wq.getLastPerson());
		
		wq.switchPlace (p[4]);
		System.out.println ("Tom takes Bill's place :\n\t" + wq);
		
		try
		{
			((LinkedWaitingQueue) wq).save ("queue");
			System.out.println ("Saved queue :\n\t" + wq);
			
			WaitingQueueIF wq2 = LinkedWaitingQueue.load ("queue");
			System.out.println ("Loaded queue :\n\t" + wq2);
		}
		catch (Exception exception)
		{
			System.err.println ("Error : " + exception.getMessage());
		}
		
		System.out.println ("Remove all remaining in number order");
		int i = 1;
		while (! wq.isEmpty())
		{
			Person r = wq.remove (i);
			if (r != null)
			{
				System.out.println ("Removed #" + i + " : " + r + "\n\t" + wq);
			}
			i++;
		}
	}
}