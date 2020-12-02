import java.util.Iterator;

final public class MaxMinIntSet extends MaxIntSet {
	private int smallest = 0;

	private int findMin() {
		Iterator<Integer> it = elements();
		int min = it.next();
		int el;
		while (it.hasNext()) {
			el = it.next();
			if (el < min)
				min = el;
		}
		return min;
	}

	@Override
	public boolean repOk() {
		if (!super.repOk())
			return false;
		if (size() == 0)
			return true;
		return smallest == findMin();
	}

	@Override
	public void insert(int x) {
		if (size() == 0 || x < smallest)
			smallest = x;
		super.insert(x);
	}

	@Override
	public void remove(int x) {
		super.remove(x);
		if (size() == 0 || x < smallest)
			smallest = findMin();
		return;
	}

	/**
	 * EFFECTS: If this is empty throws EmptyException else returns the smallest
	 * elements of this.
	 */
	public int min() throws EmptyException {
		if (size() == 0)
			throw new EmptyException();
		return smallest;
	}

}
