import java.util.Iterator;

/**
 * OVERVIEW: MaxIntSet is a subtype of IntSet with an addit ional // method,
 * max, to determine the maximum element of the set.
 */
public class MaxIntSet extends IntSet {
	private int biggest;

	private int findMax() {
		Iterator<Integer> it = elements();
		int max = it.next();
		int el;
		while (it.hasNext()) {
			el = it.next();
			if (el > max)
				max = el;
		}
		return max;
	}

	@Override
	public boolean repOk() {
		if (!super.repOk())
			return false;
		if (size() == 0)
			return true;
		return biggest == findMax();
	}

	@Override
	public void insert(int x) {
		if (size() == 0 || x > biggest)
			biggest = x;
		super.insert(x);
	}

	@Override
	public void remove(int x) {
		super.remove(x);
		if (size() == 0 || x < biggest)
			biggest = findMax();
		return;
	}

	/**
	 * EFFECTS: If this is empty throws EmptyException else returns the largest
	 * element of this.
	 */
	public int max() throws EmptyException {
		if (size() == 0)
			throw new EmptyException();
		return biggest;
	}

}
