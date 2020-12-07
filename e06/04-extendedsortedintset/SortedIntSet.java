import java.util.Iterator;

/**
 * OVERVIEW: A sorted int set is an int set whose elements are accessible in
 * sorted order
 */
public class SortedIntSet extends IntSet {
	protected ExtendedOrderedIntList els;

	/*
	 * the abstraction function is: AF(c) = c.els[1],..., c.els[c.sz]
	 *
	 * the rep invariant is: c.els != null && c.sz = c.els.size
	 */

	/**
	 * EFFECTS: Makes this be the empty sorted set.
	 */
	public SortedIntSet() {
		els = new ExtendedOrderedIntList();
	}

	@Override
	public boolean repOk() {
		return els != null && sz == els.size();
	}

	/*
	 * EFFECTS: If this is empty throws EmptyException else returns the largest
	 * element of this.
	 */
	public int max() throws EmptyException {
		if (els.size() == 0)
			throw new EmptyException();
		Iterator<Integer> it = els.bigToSmall();
		return it.next();
	}

	/**
	 * EFFECTS: Returns a generator that will produce all elements of this, each
	 * exactly once, in ascending order.<br>
	 *
	 * REQUIRES: this not be modified while the generator is in use.
	 */
	@Override
	public Iterator<Integer> elements() {
		return els.smallToBig();
	}

	@Override
	public boolean isIn(int x) {
		return els.contains(x);
	}

	@Override
	public boolean subset(IntSet s) {
		if (s instanceof SortedIntSet)
			return subset((SortedIntSet) s);
		Iterator<Integer> it = this.els.smallToBig();
		while (it.hasNext()) {
			if (!s.isIn(it.next()))
				return false;
		}
		return true;
	}

	/**
	 * Returns true if this is a subset of s. That is, if each element of this is
	 * also contained in s.
	 * 
	 * @param s second set.
	 * @return true if this is a subset of s, false otherwise.
	 */
	public boolean subset(SortedIntSet s) {
		Iterator<Integer> it = this.els.smallToBig();
		while (it.hasNext()) {
			if (!s.els.contains(it.next()))
				return false;
		}
		return true;
	}

	@Override
	public void insert(int x) {
		if (!els.contains(x)) {
			els.add(x);
			sz++;
		}
	}

	@Override
	public void remove(int x) {
		if (els.remove(x))
			sz--;
	}

}
