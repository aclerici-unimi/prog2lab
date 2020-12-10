import java.util.NoSuchElementException;

public interface Queue<E> extends Iterable<E> {

	/**
	 * Returns the number of elements in this queue.
	 *
	 * @return the size.
	 */
	int size();

	/**
	 * Returns true if this map contains no key-value mappings.
	 * 
	 * @return true if the map is empty, false otherwise.
	 */
	boolean isEmpty();

	/**
	 * Inserts the specified element at the tail of this queue. Returns true if the
	 * operation was successful.
	 * 
	 * @param e new element
	 * @return true if the operation was successful, false otherwise.
	 */
	boolean enqueue(E e);

	/**
	 * Removes and returns the element at the head of this queue.
	 * 
	 * @return the removed element.
	 * @throws NoSuchElementException if the queue is empty.
	 */
	E dequeue();

}
