/**
 * Implementation of the queue data structure with integer data and limited
 * capacity. IntQueue objects are mutable.
 */
public class IntQueue {
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
		this.elements = new int[n];
		this.head = -1;
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
		return tail == head;
	}

	/**
	 * Returns true if this queue has no elements.
	 *
	 * @return true if the queue is empty.
	 */
	public boolean isEmpty() {
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
			head = 0;
		elements[tail] = n;
		tail = (tail + 1) % elements.length;
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
		head = (head + 1) % elements.length;
		if (head == tail) {
			head = -1;
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
		if (isEmpty())
			return 0;
		if (tail > head)
			return tail - head;
		else
			return elements.length - (head - tail);
	}

	/**
	 * Returns a string identifying this queue.
	 *
	 * @return a string identifying this queue.
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

}
