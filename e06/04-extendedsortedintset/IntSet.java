import java.util.Iterator;

public abstract class IntSet {
	protected int sz;

	/**
	 * EFFECTS: Initializes this to be empty.
	 */
	public IntSet() {
		sz = 0;
	}

	public abstract boolean repOk();

	/**
	 * MODIFIES: this
	 *
	 * EFFECTS: Adds x to the elements of this.
	 */
	public abstract void insert(int x);

	/**
	 * MODIFIES: this
	 *
	 * EFFECTS: Removes x from this.
	 */
	public abstract void remove(int x);

	/**
	 * EFFECTS: Returns a generator that produces all elements of this (as
	 * Integers), each exactly once, in arbitrary order.
	 *
	 * REQUIRES: this not be modified while the generator is in use.
	 */
	public abstract Iterator<Integer> elements();

	/**
	 * EFFECTS: If x is in this returns true else returns false.
	 */
	public boolean isIn(int x) {
		Iterator<Integer> g = elements();
		Integer z = x;
		while (g.hasNext())
			if (g.next().equals(z))
				return true;
		return false;
	}

	/**
	 * EFFECTS: Returns the cardinality of this.
	 */
	public int size() {
		return sz;
	}

	// implementations of subset and toString go here

	/**
	 * EFFECTS: Returns true if this is a subset of s else returns false.
	 */
	public boolean subset(IntSet s) {
		Iterator<Integer> it = s.elements();
		while (it.hasNext()) {
			if (!this.isIn(it.next()))
				return false;
		}
		return true;
	}

	public String toString() {
		String res = "";
		Iterator<Integer> it = elements();
		if (size() > 0) {
			Object n = it.next();
			while (it.hasNext()) {
				res += n + ", ";
				n = it.next();
			}
			res += n;
		}
		return res;
	}

}
