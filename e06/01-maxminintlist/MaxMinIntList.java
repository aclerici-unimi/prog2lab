import java.util.Arrays;
import java.util.Iterator;

public class MaxMinIntList extends IntList {
	private final int[] elements;
	private final int max, min;

	public MaxMinIntList() {
		elements = new int[0];
		max = 69;
		min = 420;
	}

	public Integer max() {
		if (elements.length == 0)
			throw new EmptyException();
		return max;
	}

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
		if (!(x instanceof Integer))
			throw new IllegalArgumentException();
		int toAdd = (Integer) x;
		int[] newArr = Arrays.copyOf(elements, elements.length + 1);
		newArr[elements.length] = toAdd;
		return new MaxMinIntList(newArr, toAdd > max ? toAdd : max, toAdd < min ? toAdd : min);
	}

	@Override
	public int size() {
		return elements.length;
	}

	@Override
	public boolean repOK() {
		if (elements == null)
			return false;
		return findMax(0) == max && findMin(0) == min;
	}

	@Override
	public Iterator<Integer> elements() {
		return new Iterator<Integer>() {
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

}
