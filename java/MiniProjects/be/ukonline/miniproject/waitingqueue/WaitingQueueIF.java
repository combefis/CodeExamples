// WaitingQueueIF.java

package be.ukonline.miniproject.waitingqueue;

/**
 * Interface representing a waiting queue
 *
 * @author Sébastien Combéfis
 * @version January 8, 2009
 */
public interface WaitingQueueIF
{
	/**
	 * Get the number of person waiting
	 * 
	 * @pre -
	 * @post The returned value contains the number of persons
	 *       that are waiting in the queue
	 */
	public int getPersonNumber();
	
	/**
	 * Test whether the queue is empty
	 * 
	 * @pre -
	 * @post The returned value contains true if the queue is empty
	 *       and false otherwise
	 */
	public boolean isEmpty();
	
	/**
	 * Test whether a person is in the queue
	 * 
	 * @pre -
	 * @post The returned value contains true if the specified person
	 *       is in the queue or false otherwise
	 */
	public boolean isInQueue (Person p);
	
	/**
	 * Get the first person of the queue
	 * 
	 * @pre -
	 * @post The returned value contains the person waiting
	 *       in the front of the queue
	 * @throws EmptyQueueException if the queue is empty
	 */
	public Person getFirstPerson() throws EmptyQueueException;
	
	/**
	 * Get the last person of the queue
	 * 
	 * @pre -
	 * @post The returned value contains the person waiting
	 *       at the rear of the queue
	 * @throws EmptyQueueException if the queue is empty
	 */
	public Person getLastPerson() throws EmptyQueueException;
	
	/**
	 * Add a person to the queue
	 * 
	 * @pre p != null
	 * @post The specified person is added to the rear of the queue
	 * @throws AlreadyInQueueException if p is already in the queue
	 */
	public void add (Person p) throws AlreadyInQueueException;

	/**
	 * Remove the first person from the queue
	 * 
	 * @pre -
	 * @post The returned value contains the person in the front of the queue
	 *       This person is removed from the queue
	 * @throws EmptyQueueException if the queue is empty 
	 */
	public Person remove() throws EmptyQueueException;
	
	/**
	 * Remove a person from the queue
	 * 
	 * @pre p != null
	 * @post The returned value contains p
	 *       p is removed from the queue
	 * @throws NotInQueueException if p is not in the queue
	 */
	public Person remove (Person p) throws NotInQueueException;
	
	/**
	 * Remove a person with specified ticket number from the queue
	 * 
	 * @pre nb > 0
	 * @post The returned value contains the person with the specified
	 *       ticket number and is removed from the queue
	 *       or null if no one has this number in the queue
	 */
	public Person remove (int nb);
	
	/**
	 * Requeue the first person
	 * 
	 * @pre -
	 * @post The first person at the front of the queue is
	 *       requeued at the rear of the queue if the
	 *       queue is not empty, otherwise the queue is unchanged
	 */
	public void requeueFirst();
	
	/**
	 * Switch two person of place
	 * 
	 * @pre p != null
	 * @post If there is someone in front of p, this person and
	 *       p switch their respective place in the queue,
	 *       otherwise the queue is unchanged
	 * @throws NotInQueueException if p is not in the queue
	 */
	public void switchPlace (Person p) throws NotInQueueException;
}