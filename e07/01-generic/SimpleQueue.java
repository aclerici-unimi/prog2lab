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
		assert repOk();
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
		boolean retVal = q.add(e);
		assert repOk();
		return retVal;
	}

	@Override
	public E dequeue() {
		if (q.isEmpty())
			throw new NoSuchElementException("can't dequeue, queue is empty");
		E retVal = q.remove(0);
		assert repOk();
		return retVal;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof SimpleQueue))
			return false;
		SimpleQueue<?> other = (SimpleQueue<?>) obj;
		if (q.size() != other.q.size())
			return false;
		for (E el : q)
			if (!other.q.contains(el))
				return false;
		return true;
	}

	@Override
	public int hashCode() {
		int res = 31;
		for (E e : q)
			res = res * 31 + e.hashCode();
		return res;
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
