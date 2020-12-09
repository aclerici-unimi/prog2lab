import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class SimpleQueue<E> implements Queue<E> {
	private final List<E> q;

	/*
	 * Abstraction Function: AF(q) = Queue{q.get(0), q.get(1), ...,
	 * q.get(q.size()-1)}. If q.isEmpty(), AF(q) is the empty queue.
	 *
	 * Representation Invariant: q is not null.
	 */

	public SimpleQueue() {
		q = new LinkedList<E>();
	}

	/**
	 * Implementation of the representation invariant. Returns true if the
	 * representation respects all its requirements. Used in assertions.
	 * 
	 * @return true if the representation is ok; false otherwise.
	 */
	public boolean repOk() {
		return q != null;
	}

	@Override
	public Iterator<E> iterator() {
		return q.iterator();
	}

	@Override
	public int size() {
		return q.size();
	}

	@Override
	public boolean isEmpty() {
		return q.isEmpty();
	}

	@Override
	public boolean enqueue(E e) {
		return q.add(e);
	}

	@Override
	public E dequeue() {
		if (q.isEmpty())
			throw new NoSuchElementException("can't dequeue, queue is empty");
		E out = q.remove(0); // still not null
		assert repOk();
		return out;
	}

	@Override
	public String toString() {
		String res = "[";
		if (q.size() > 0) {
			Iterator<E> it = q.iterator();
			E el = it.next();
			while (it.hasNext()) {
				res += el + ", ";
				el = it.next();
			}
			res += el;
		}
		return res + "]";
	}
}
