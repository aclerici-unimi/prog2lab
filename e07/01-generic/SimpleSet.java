import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class SimpleSet<E> implements Set<E> {
	private final List<E> els;

	/*
	 * Abstraction Function: AF(els) = Queue{els.get(0), els.get(1), ...,
	 * els.get(els.size()-1)}. If els.isEmpty(), AF(els) is the empty set.
	 *
	 * Representation Invariant: els is not null; there are no duplicates in the
	 * set.
	 */

	/** Constructs an empty SimpleSet. */
	public SimpleSet() {
		els = new LinkedList<E>();
		assert repOk();
	}

	/**
	 * Implementation of the representation invariant. Returns true if the
	 * representation respects all its requirements. Used in assertions.
	 * 
	 * @return true if the representation is ok; false otherwise.
	 */
	public boolean repOk() {
		if (els == null)
			return false;
		int i = 0;
		for (E el : els) {
			if (el == null)
				return false;
			ListIterator<E> it = els.listIterator(i + 1);
			while (it.hasNext())
				if (el.equals(it.next()))
					return false;
			i++;
		}
		return true;
	}

	@Override
	public Iterator<E> iterator() {
		return els.iterator();
	}

	@Override
	public int size() {
		return els.size();
	}

	@Override
	public boolean isEmpty() {
		return els.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return els.contains(o);
	}

	@Override
	public E choose() {
		if (els.isEmpty())
			throw new NoSuchElementException("can't choose an element: the set is empty");
		return els.get(0);
	}

	@Override
	public boolean add(E e) {
		if (e == null)
			throw new NullPointerException("can't ad a null element");
		if (!els.contains(e)) {
			els.add(e);
			assert repOk();
			return true;
		}
		return false;
	}

	@Override
	public boolean remove(Object o) {
		boolean retVal = els.remove(o);
		assert repOk();
		return retVal;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof SimpleSet))
			return false;
		SimpleSet<?> other = (SimpleSet<?>) obj;
		if (els.size() != other.els.size())
			return false;
		for (E el : els)
			if (!other.els.contains(el))
				return false;
		return true;
	}

	@Override
	public int hashCode() {
		int res = 31;
		for (E e : els)
			res = res * 31 + e.hashCode();
		return res;
	}

	@Override
	public String toString() {
		String res = "{ ";
		if (els.size() > 0) {
			Iterator<E> it = els.iterator();
			E el = it.next();
			while (it.hasNext()) {
				res += el + ", ";
				el = it.next();
			}
			res += el + " ";
		}
		return res + "}";
	}

}
