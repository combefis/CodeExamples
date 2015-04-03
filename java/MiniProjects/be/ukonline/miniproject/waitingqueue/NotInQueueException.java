// NotInQueueException.java

package be.ukonline.miniproject.waitingqueue;

/**
 * Exception for the case when a person is not in the queue
 *
 * @author Sébastien Combéfis
 * @version January 8, 2009
 */
@SuppressWarnings ("serial")
public class NotInQueueException extends RuntimeException
{
	/**
	 * Constructor
	 * 
	 * @pre -
	 * @post An instance of this is created
	 */
	public NotInQueueException()
	{
		super ("The person is not in the queue");
	}
}