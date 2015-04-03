// EmptyQueueException.java

package be.ukonline.miniproject.waitingqueue;

/**
 * Exception for the case when the queue is empty
 *
 * @author Sébastien Combéfis
 * @version January 8, 2009
 */
@SuppressWarnings ("serial")
public class EmptyQueueException extends RuntimeException
{
	/**
	 * Constructor
	 * 
	 * @pre -
	 * @post An instance of this is created
	 */
	public EmptyQueueException()
	{
		super ("The queue is empty");
	}
}