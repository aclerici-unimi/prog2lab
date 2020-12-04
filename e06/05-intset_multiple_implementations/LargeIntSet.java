import java.util.HashSet;
import java.util.Iterator;

final public class LargeIntSet extends IntSet {
	private final HashSet<Integer> elements;

	public LargeIntSet() {
		elements = new HashSet<Integer>();
	}

	/*
	 * Abstraction Function: A(elements) = elements.
	 *
	 * Representation Invariant: sz=elements.size(); elements is not null.
	 */

	@Override
	public boolean repOk() {
		return elements != null && sz == elements.size();
	}

	@Override
	public void insert(int x) {
		if (elements.add(x))
			sz++;
		assert repOk();
	}

	@Override
	public void remove(int x) {
		if (elements.remove(x))
			sz--;
		assert repOk();
	}

	@Override
	public Iterator<Integer> elements() {
		return elements.iterator();
	}

	@Override
	public boolean isIn(int x) {
		return elements.contains(x);
	}

	@Override
	public boolean subset(IntSet s) {
		if (s instanceof LargeIntSet)
			return subset((LargeIntSet) s);
		for (Integer el : this.elements)
			if (!s.isIn(el))
				return false;
		return true;
	}

	/**
	 * Returns true if this is a subset of s. That is, if each element of this is
	 * also contained in s.
	 * 
	 * @param s second set.
	 * @return true if this is a subset of s, false otherwise.
	 */
	public boolean subset(LargeIntSet s) {
		for (Integer el : this.elements)
			if (!s.elements.contains(el))
				return false;
		return true;
	}

}
