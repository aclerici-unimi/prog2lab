import java.util.ArrayList;
import java.util.Iterator;

public class MaxMinIntSet extends IntSet {
	private final ArrayList<Integer> elements;
	private int max = 0, min = 0;

	public MaxMinIntSet() {
		elements = new ArrayList<Integer>();
		max = 69;
		min = 420;
	}

	/*
	 * Abstraction Function: AF() = elements (in no particular order), with max =
	 * max and min = min;
	 *
	 * Representation Invariant: elements is non null; elements has no duplicates;
	 * elements.size=0 or (max is the maximum of the elements in elements, min is
	 * the minimum of the elements in elements).
	 */

	public int max() {
		return max;
	}

	public int min() {
		return min;
	}

	@Override
	public void insert(int x) {
		if (elements.contains(x))
			return;
		sz++;
		if (!elements.isEmpty()) {
			if (x > max)
				max = x;
			if (x < min)
				min = x;
		} else {
			max = x;
			min = x;
		}
		elements.add(x);
	}

	private int findMax() {
		Iterator<Integer> it = elements.iterator();
		int res = it.next();
		while (it.hasNext()) {
			int n = it.next();
			if (n > res)
				res = n;
		}
		return res;
	}

	private int findMin() {
		Iterator<Integer> it = elements.iterator();
		int res = it.next();
		while (it.hasNext()) {
			int n = it.next();
			if (n < res)
				res = n;
		}
		return res;
	}

	@Override
	public void remove(int x) {
		if (elements.size() == 0)
			throw new EmptyException();
		sz--;
		if (!(elements.size() == 1)) {
			if (x == max)
				max = findMax();
			if (x == min)
				max = findMin();
		}
		elements.remove(x);
	}

	@Override
	public Iterator<Integer> elements() {
		return elements.iterator();
	}

	@Override
	public boolean repok() {
		if (elements == null)
			return false;
		int size = elements.size();
		int i = 0;
		for (int n : elements) {
			if (i >= size / 2)
				break;
			if (n != elements.lastIndexOf(n))
				break;
			i++;
		}
		return size == 0 || findMax() == max && findMin() == min;
	}

}
