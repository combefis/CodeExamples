// AlreadyInQueueException.java

package be.ukonline.miniproject.waitingqueue;

/**
 * Exception for the case when a person is already in the queue
 *
 * @author Sébastien Combéfis
 * @version January 8, 2009
 */
@SuppressWarnings ("serial")
public class AlreadyInQueueException extends RuntimeException
{
	/**
	 * Constructor
	 * 
	 * @pre -
	 * @post An instance of this is created
	 */
	public AlreadyInQueueException()
	{
		super ("The person is already in the queue");
	}
}