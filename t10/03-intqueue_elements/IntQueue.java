import java.util.Arrays;
import java.util.Iterator;

/**
 * Implementation of the queue data structure with integer data and limited
 * capacity. IntQueue objects are mutable.
 */
public class IntQueue implements Cloneable {
	private final int[] elements;
	private int head, tail;

	/*
	 * Abstraction Function: AF(elements, head, tail) = Queue{elements[head],
	 * elements[(head+1)%n], ..., elements[(tail-1+n)%n]} except when head=-1, in
	 * which case AF(elements, head, tail) = Queue{}.
	 *
	 * Representation Invariant: Head and tail are non negative numbers, except when
	 * the queue is empty, in which case head = -1 and tail = 0; elements is non
	 * null; head and tail are less then elements' size.
	 */

	/**
	 * Constructs an empty {@code IntQueue} with a maximum capacity of {@code n}.
	 * Requires {@code n} to be positive.
	 * 
	 * @param n maximum capacity of this.
	 * @throws IllegalArgumentException if n≤0
	 */
	public IntQueue(int n) {
		if (n <= 0)
			throw new IllegalArgumentException("Can't construct a queue of capacity " + n);
		this.elements = new int[n]; // non null
		this.head = -1; // empty queue: permitted case
		this.tail = 0;
		assert repOk();
	}

	/**
	 * Implementation of the representation invariant. Returns true if the
	 * representation respects all its requirements. Used in assertions.
	 * 
	 * @return true if the representation is ok; false otherwise.
	 */
	public boolean repOk() {
		return elements != null && head >= -1 && tail >= 0 && head < elements.length && tail < elements.length
				&& (head != -1 || tail == 0);
	}

	/**
	 * Returns true if this queue is at its maximum capacity.
	 *
	 * @return true if the queue reached its capacity.
	 */
	public boolean isFull() {
		// observer
		return tail == head;
	}

	/**
	 * Returns true if this queue has no elements.
	 *
	 * @return true if the queue is empty.
	 */
	public boolean isEmpty() {
		// observer
		return head == -1; // (&& tail == 0)
	}

	/**
	 * Adds n to this queue. The queue must not have reached its maximum capacity.
	 * Modifies this.
	 * 
	 * @param n element to add.
	 * @throws FullQueueException if the queue is full.
	 */
	public void enqueue(int n) {
		if (isFull())
			throw new FullQueueException(
					"can't enqueue, queue has reached maximum capacity of " + elements.length);
		if (isEmpty())
			head = 0; // non negative
		elements[tail] = n;
		tail = (tail + 1) % elements.length; // non negative
		assert repOk();
	}

	/**
	 * Removes and returns the head element from this queue. The queue must not be
	 * empty. Modifies this.
	 * 
	 * @return the removed element.
	 * @throws EmptyQueueException if the queue is empty.
	 */
	public int dequeue() {
		if (isEmpty())
			throw new EmptyQueueException("can't dequeue, queue is empty");
		int out = elements[head];
		head = (head + 1) % elements.length; // non negative
		if (head == tail) {
			head = -1; // empty queue: permitted case
			tail = 0;
		}
		assert repOk();
		return out;
	}

	/**
	 * Returns this queue's size.
	 *
	 * @return this' size.
	 */
	public int size() {
		// observer
		if (isEmpty())
			return 0;
		if (tail > head)
			return tail - head;
		else
			return elements.length - (head - tail);
	}

	/**
	 * Returns an iterator over the elements contained in this Queue from the head
	 * to the tail.
	 * 
	 * @return the iterator
	 */
	public Iterator<Integer> elements() {
		return new Iterator<Integer>() {
			private int offset = 0;

			@Override
			public boolean hasNext() {
				return offset < size();
			}

			@Override
			public Integer next() {
				return elements[(head + offset++) % elements.length];
			}

		};
	}

	/**
	 * Returns a string identifying this IntQueue.
	 *
	 * @return the string.
	 */
	@Override
	public String toString() {
		String res = "IntQueue : [";
		if (!isEmpty()) {
			int i;
			for (i = 0; i < size() - 1; i++)
				res += elements[(head + i) % elements.length] + ", ";
			res += elements[(head + i) % elements.length];
		}
		return res + "]";
	}

	/**
	 * Compares the specified object with this IntQueue for equality. Two
	 * {@code IntQueue}s are defined to be equal if they contain equal {@code int}s,
	 * in the same place relative to the head of the queue.
	 *
	 * @param o the object to be compared with this.
	 * @return true if this and o are equals.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof IntQueue))
			return false;
		IntQueue other = (IntQueue) o;
		if (this.size() != other.size())
			return false;
		for (int i = 0; i < this.size() - 1; i++)
			if (this.elements[(this.head + i) % this.elements.length] != other.elements[(other.head + i)
					% other.elements.length])
				return false;
		return true;
	}

	/**
	 * Returns the hash code value for this IntQueue.
	 *
	 * @return the hash code.
	 */
	@Override
	public int hashCode() {
		int res = 0;
		for (int i = 0; i < size(); i++)
			res += 31 * elements[(head + i) % elements.length];
		return res;
	}

	/**
	 * Returns a shallow copy of this IntQueue instance.
	 *
	 * @return a clone of this.
	 */
	@Override
	public IntQueue clone() {
		IntQueue res = new IntQueue(elements.length);
		for (int i = 0; i < size(); i++)
			res.enqueue(elements[(head + i) % elements.length]);
		return res;

	}

}
