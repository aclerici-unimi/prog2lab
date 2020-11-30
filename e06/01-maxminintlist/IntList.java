import java.util.Iterator;

/**
 * OVERVIEW: IntLists are immutable lists of Objects. A typical IntList is a
 * sequence [x1, ..., xn].
 */
public abstract class IntList {

	// This specification was originally contained in Barbara Liskov and
	// John Guttag's "Program Development in Java: Abstraction, Specification, and
	// Object-Oriented Design", released by Addison-Wesley Professional in 2000.

	/**
	 * EFFECTS: If this is empty throws EmptyException else returns first element of
	 * this.
	 */
	public abstract Object first() throws EmptyException;

	/**
	 * EFFECTS: If this is empty throws EmptyException else returns the list
	 * containing all but the first element of this, in the original order.
	 */
	public abstract IntList rest() throws EmptyException;

	/**
	 * EFFECTS: Returns a generator that will produce the elements of this, each
	 * exactly once, in their order in this.
	 */
	public abstract Iterator<Integer> elements();

	/**
	 * EFFECTS: Adds x to the beginning of this.
	 */
	public abstract IntList addEl(Object x);

	/**
	 * EFFECTS: Returns a count of the number of elements of this.
	 */
	public abstract int size();

	public abstract boolean repOK();

	public String toString() {
		String res = "[";
		Iterator<Integer> it = elements();
		if (size() > 0) {
			Integer n = it.next();
			while (it.hasNext()) {
				res += n + ", ";
				n = it.next();
			}
			res += n;
		}
		return res + "]";
	}

	public boolean equals(Object o) {
		try {
			return equals((IntList) o);
		} catch (ClassCastException e) {
			return false;
		}
	}

	public boolean equals(IntList o) {
		Iterator<Integer> it = elements(), ito = o.elements();
		while (it.hasNext() && ito.hasNext()) {
			Integer i = it.next();
			Integer io = ito.next();
			if (!i.equals(io))
				return false;
		}
		if (it.hasNext() || ito.hasNext())
			return false;
		return true;
	}

}
