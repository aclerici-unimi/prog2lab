import java.util.List;
import java.util.ArrayList;

/**
 * Abstract type based on the mathematical concept of set of integers. Elements
 * cannot appear more than once in a set. The class provides constructors to
 * make empty sets and to add, count, pick and remove elements.
 */
public class IntSet {
	private final List<Integer> elements;

	/*
	 * Abstraction Function: AF(elements) = Set{elements.get(0), elements.get(1),
	 * ..., elements.get(elements.size()-1)} (in no particular order). If
	 * elements.isEmpty(), AF(elements) is the empty set.
	 *
	 * Representation Invariant: elements is non null; there are no duplicates among
	 * elements' elements.
	 */

	/**
	 * Constructs an empty IntSet.
	 */
	public IntSet() {
		this.elements = new ArrayList<>();
		assert repOk();
	}

	/**
	 * Implementation of the representation invariant. Returns true if the
	 * representation respects all its requirements. Used in assertions.
	 * 
	 * @return true if the representation is ok; false otherwise.
	 */
	public boolean repOk() {
		if (elements == null)
			return false;
		for (int i = 0; i < elements.size(); i++)
			if (elements.lastIndexOf(elements.get(i)) != i)
				return false;
		return true;
	}

	/**
	 * Inserts the element x in this. If x is already in the set, does nothing.
	 *
	 * @param x integer to be inserted.
	 */
	public void insert(int x) {
		if (!this.contains(x))
			this.elements.add(x);
		assert repOk();
	}

	/**
	 * Remove the element x from this. If x is already in the set, does nothing.
	 *
	 * @param x element to be removed.
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
	 * Checks if x is in this.
	 * 
	 * @param x element to check if it is in this.
	 * @return true if x is in this, false otherwise.
	 */
	public boolean contains(int x) {
		return this.elements.contains(x);
	}

	/**
	 * Returns the cardinality of this.
	 *
	 * @return cardinality of this.
	 */
	public int size() {
		return this.elements.size();
	}

	/**
	 * Returns an element of this. The element is picked with no particular
	 * criteria.
	 * 
	 * @return elements of this.
	 * @throws EmptySetException if this is empty.
	 */
	public int choose() throws EmptySetException {
		if (this.size() == 0)
			throw new EmptySetException("Can't extract element: empty set");
		return this.elements.get(this.elements.size() - 1);
	}

	/**
	 * Returns a string identifying this set and its elements. It uses the
	 * mathematical format: {elem1, elem2, ...}
	 *
	 * @return a string identifying this set.
	 */
	@Override
	public String toString() {
		String res = "{";
		if (this.size() > 0) {
			int i;
			for (i = 0; i < this.elements.size() - 1; i++)
				res += this.elements.get(i) + ", ";
			res += this.elements.get(i);
		}
		return res + "}";
	}
}
