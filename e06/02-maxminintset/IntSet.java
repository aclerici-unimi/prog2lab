import java.util.ArrayList;
import java.util.Iterator;

/**
 * OVERVIEW: IntSets are mutable, unbounded sets of integers. A typical IntSet
 * is {x1..., xn}.
 */
public class IntSet {
	private final ArrayList<Integer> elements;
	protected int sz;

	/**
	 * EFFECTS: Initializes this to be empty.
	 */
	public IntSet() {
		this.elements = new ArrayList<>();
		assert repOk();
	}

	public boolean repOk() {
		if (elements == null)
			return false;
		int i = 0, stop = elements.size() / 2;
		for (Integer el : elements) {
			if (elements.lastIndexOf(el) != i)
				return false;
			if (i >= stop)
				break;
			i++;
		}
		return true;
	}

	/**
	 * MODIFIES: this
	 *
	 * EFFECTS: Adds x to the elements of this.
	 */
	public void insert(int x) {
		if (!elements.contains(x))
			this.elements.add(x);
	}

	/**
	 * MODIFIES: this
	 *
	 * EFFECTS: Removes x from this.
	 */
	public void remove(int x) {
		int index = this.elements.indexOf(x);
		if (index != -1) {
			int lastIndex = this.elements.size() - 1;
			this.elements.set(index, this.elements.get(lastIndex));
			this.elements.remove(lastIndex);
		}
		assert repOk();
	}

	/**
	 * EFFECTS: If x is in this returns true else returns false.
	 */
	public boolean isIn(int x) {
		return elements.contains(x);
	}

	/**
	 * EFFECTS: Returns the cardinality of this.
	 */
	public int size() {
		return elements.size();
	}

	/**
	 * EFFECTS: Returns a generator that produces all elements of this (as
	 * Integers), each exactly once, in arbitrary order.
	 *
	 * REQUIRES: this not be modified while the generator is in use.
	 */
	public Iterator<Integer> elements() {
		return elements.iterator();
	}

	/**
	 * EFFECTS: Returns true if this is a subset of s else returns false.
	 */
	public boolean subset(IntSet s) {
		for (Integer el : s.elements)
			if (!this.elements.contains(el))
				return false;
		return true;
	}

}
