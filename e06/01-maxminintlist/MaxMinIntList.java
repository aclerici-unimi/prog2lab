import java.util.Arrays;
import java.util.Iterator;

/**
 * Partial implementation of IntList that works only for Integer objects.
 * Precisely, it stores them as {@code int}s.
 */
public class MaxMinIntList extends IntList {
	private final int[] elements;
	private final int max, min;

	public MaxMinIntList() {
		elements = new int[0];
		max = 69;
		min = 420;
	}

	/*
	 * Abstraction Function: AF() = elements, with max = max and min = min;
	 *
	 * Representation Invariant: elements is non null; elements.length=0 or (max is
	 * the maximum of the elements in elements, min is the minimum of the elements
	 * in elements).
	 */

	/**
	 * Returns the maximum of this MaxMinIntList.
	 * 
	 * @return the maximum.
	 * @throws EmptyException if the list is empty.
	 */
	public Integer max() {
		if (elements.length == 0)
			throw new EmptyException();
		return max;
	}

	/**
	 * Returns the minimum of this MaxMinIntList.
	 * 
	 * @return the minimum.
	 * @throws EmptyException if the list is empty.
	 */
	public Integer min() {
		if (elements.length == 0)
			throw new EmptyException();
		return min;
	}

	@Override
	public Integer first() throws EmptyException {
		return elements[0];
	}

	private MaxMinIntList(int[] elements, int max, int min) {
		this.elements = elements;
		this.max = max;
		this.min = min;
	}

	private int findMax(int start) {
		int res = elements[start];
		for (int i = start + 1; i < elements.length; i++)
			if (elements[i] > res)
				res = elements[i];
		return res;
	}

	private int findMin(int start) {
		int res = elements[start];
		for (int i = start + 1; i < elements.length; i++)
			if (elements[i] < res)
				res = elements[i];
		return res;
	}

	@Override
	public IntList rest() throws EmptyException {
		if (elements.length == 0)
			throw new EmptyException();
		int newMax = 69, newMin = 420;
		if (elements.length > 1) {
			if (first() == max)
				newMax = findMax(1);
			else
				newMax = max;
			if (first() == min)
				newMin = findMin(1);
			else
				newMin = min;
		}
		return new MaxMinIntList(Arrays.copyOfRange(elements, 1, elements.length), newMax, newMin);
	}

	@Override
	public IntList addEl(Object x) {
		if (x instanceof Integer)
			return addEl((Integer) x);
		throw new UnsupportedOperationException();
	}

	/**
	 * EFFECTS: Adds x to the beginning of this.
	 */
	public IntList addEl(Integer x) {
		int[] newArr = Arrays.copyOf(elements, elements.length + 1);
		newArr[elements.length] = x;
		if (elements.length == 0)
			return new MaxMinIntList(newArr, x, x);
		return new MaxMinIntList(newArr, x > max ? x : max, x < min ? x : min);
	}

	@Override
	public int size() {
		return elements.length;
	}

	@Override
	public boolean repOK() {
		if (elements == null)
			return false;
		return elements.length == 0 || findMax(0) == max && findMin(0) == min;
	}

	@Override
	public Iterator<Object> elements() {
		return new Iterator<Object>() {
			private int nextIndex = 0;

			@Override
			public boolean hasNext() {
				return nextIndex < elements.length;
			}

			@Override
			public Integer next() {
				return elements[nextIndex++];
			}
		};
	}

	@Override
	public String toString() {
		String res = "";
		for (int i = 0; i < elements.length - 1; i++) {
			res += elements[i] + ", ";
		}
		res += elements[elements.length - 1];
		return res;
	}

}
