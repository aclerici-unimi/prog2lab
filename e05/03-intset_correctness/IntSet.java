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
	 *
	 * Abstraction Invariant: there are no duplicates among elements' elements.
	 * 
	 */

	/**
	 * Constructs an empty IntSet.
	 */
	public IntSet() {
		this.elements = new ArrayList<>(); // provides a non null value for elements. There are no elements,
							// thus no duplicates.
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
		if (!this.contains(x)) // observer
			this.elements.add(x); // happens only if the element was not in the set, thus there will be
						// still no duplicates after this operation.
		assert repOk();
	}

	/**
	 * Remove the element x from this. If x is already in the set, does nothing.
	 *
	 * @param x element to be removed.
	 */
	public void remove(int x) {
		int index = this.elements.indexOf(x); // observer
		if (index != -1) {
			int lastIndex = this.elements.size() - 1;
			this.elements.set(index, this.elements.get(lastIndex));
			this.elements.remove(lastIndex);
		} // can't invalidate the RI (doesn't risk to add duplicates)
		assert repOk();
	}

	/**
	 * Checks if x is in this.
	 * 
	 * @param x element to check if it is in this.
	 * @return true if x is in this, false otherwise.
	 */
	public boolean contains(int x) {
		return this.elements.contains(x); // observer
	}

	/**
	 * Returns the cardinality of this.
	 *
	 * @return cardinality of this.
	 */
	public int size() {
		return this.elements.size(); // observer
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
		return this.elements.get(this.elements.size() - 1); // observer
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

	/**
	 * Compares the specified object with this set for equality. Two sets are
	 * defined to be equal if they contain the same elements, with no matter for
	 * their order.
	 *
	 * @param o the object to be compared with this.
	 * @return true if this and o are equals.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof IntSet))
			return false;
		IntSet other = (IntSet) o;
		if (other.size() != this.size())
			return false;
		for (int element : this.elements) {
			if (!other.contains(element))
				return false;
		}
		return true;
	}

	/**
	 * Returns the hash code value for this list.
	 *
	 * @return the hash code.
	 */
	@Override
	public int hashCode() {
		int res = 0;
		for (Integer element : this.elements) {
			res += element.hashCode();
		}
		return res;
	}

	/**
	 * Returns a shallow copy of this IntSet instance.
	 *
	 * @return a clone of this
	 */
	@Override
	public IntSet clone() {
		IntSet res = new IntSet();
		for (int el : this.elements)
			res.insert(el); // previously proved
		return res;
	}

}
