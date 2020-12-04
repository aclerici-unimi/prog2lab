import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

final public class SmallIntSet extends IntSet {
	final private List<Integer> elements;

	/*
	 * Abstraction Function: A(elements) = elements, in no particular order.
	 *
	 * Representation Invariant: sz=elements.size(); elements contains no
	 * duplicates, elements is not null.
	 */

	public SmallIntSet() {
		elements = new LinkedList<Integer>();
		assert repOk();
	}

	@Override
	public boolean repOk() {
		if (elements == null || sz != elements.size())
			return false;
		int i = 0;
		for (Integer el : elements) {
			if (i != elements.lastIndexOf(el))
				return false;
			i++;
		}
		return true;
	}

	@Override
	public boolean isIn(int x) {
		return elements.contains(x);
	}

	@Override
	public void insert(int x) {
		if (!elements.contains(x)) {
			elements.add(x);
			sz++;
		}
		assert repOk();
	}

	@Override
	public void remove(int x) {
		if (elements.contains(x)) {
			elements.remove(x);
			sz--;
		}
		assert repOk();
	}

	@Override
	public Iterator<Integer> elements() {
		return elements.iterator();
	}

	@Override
	public boolean subset(IntSet s) {
		if (s instanceof SmallIntSet)
			return subset((SmallIntSet) s);
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
	public boolean subset(SmallIntSet s) {
		for (Integer el : this.elements)
			if (!s.elements.contains(el))
				return false;
		return true;
	}

}
