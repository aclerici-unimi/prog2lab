import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Data structure containing a mutable ordered list of integers.
 */
public class OrderedIntList {
	// Most of this specification was originally contained in Barbara Liskov and
	// John Guttag's "Program Development in Java: Abstraction, Specification, and
	// Object-Oriented Design", released by Addison-Wesley Professional in 2000. The
	// implementation is mostly an upgrade of the Liskov's version by professor
	// Massimo Santini. My additions will be specified.
	private boolean isEmpty;
	private int value;
	private OrderedIntList left, right;

	/*
	 * Abstraction Function: AF(this) = if isEmpty then empty list, otherwise
	 * AF(left) + [value] + AF(right)
	 *
	 * Representation Invariant: isEmpty or {left and right are not null and their
	 * IR is true and !left.isEmpty => left.greatest < val and !right.isEmpty => val
	 * < right.least}
	 */

	public OrderedIntList() {
		this.isEmpty = true;
		assert repOk();
	}

	private int greatest() {
		// my implementation
		if (left.isEmpty)
			if (right.isEmpty)
				return value;
			else {
				int r = right.greatest();
				return r > value ? r : value;
			}
		else if (right.isEmpty) {
			int l = left.greatest();
			return l > value ? l : value;
		} else {
			int l = left.greatest(), r = right.greatest();
			int lr = l > r ? l : r;
			return lr > value ? lr : value;
		}
	}

	private int least() {
		// my implementation
		if (left.isEmpty)
			if (right.isEmpty)
				return value;
			else {
				int r = right.least();
				return r < value ? r : value;
			}
		else if (right.isEmpty) {
			int l = left.least();
			return l < value ? l : value;
		} else {
			int l = left.least(), r = right.least();
			int lr = l < r ? l : r;
			return lr < value ? lr : value;
		}
	}

	/**
	 * Implementation of the representation invariant. Returns true if the
	 * representation respects all its requirements. Used in assertions.
	 * 
	 * @return true if the representation is ok; false otherwise.
	 */
	public boolean repOk() {
		// my implementation
		if (isEmpty)
			return true;
		System.out.println("\nlist: " + this);
		System.out.println("elem: " + value);
		if (!left.isEmpty)
			System.out.println("left greatest:\t" + left.greatest());
		if (!right.isEmpty)
			System.out.println("right least:\t" + right.least());
		if (left == null || right == null || !left.repOk() || !right.repOk()
				|| (!left.isEmpty && left.greatest() >= value)
				|| (!right.isEmpty && right.least() <= value))
			return false;
		return true;
	}

	public int size() {
		return isEmpty ? 0 : 1 + left.size() + right.size();
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public boolean contains(int value) {
		if (isEmpty)
			return false;
		if (value == this.value)
			return true;
		if (value < this.value)
			return left.contains(value);
		else
			return right.contains(value);
	}

	public void add(int value) {
		if (value == this.value)
			throw new IllegalArgumentException("Duplicate value");
		if (isEmpty) {
			this.value = value;
			isEmpty = false;
			left = new OrderedIntList();
			right = new OrderedIntList();
		} else if (value < this.value)
			left.add(value);
		else
			right.add(value);
		assert repOk();
	}

	private int min() {
		if (isEmpty)
			throw new NoSuchElementException();
		if (left.isEmpty)
			return value;
		return left.min();
	}

	public boolean remove(int value) {
		if (isEmpty)
			return false;
		if (value == this.value) {
			if (left.isEmpty && right.isEmpty) {
				isEmpty = true;
				left = right = null;
			} else if (left.isEmpty) {
				this.value = right.value;
				left = right.left;
				right = right.right;
			} else if (right.isEmpty) {
				this.value = left.value;
				right = left.right;
				left = left.left;
			} else {
				int min = right.min();
				this.value = min;
				right.remove(min);
			}
			assert repOk();
			return true;
		}
		if (value < this.value)
			return left.remove(value);
		else
			return right.remove(value);
	}

	public Iterator<Integer> smallToBig() {
		return new Iterator<>() {
			private boolean used = false;
			private Integer current = null;
			private final Iterator<Integer> leftIterator = isEmpty ? null : left.smallToBig();
			private final Iterator<Integer> rightIterator = isEmpty ? null : right.smallToBig();

			@Override
			public boolean hasNext() {
				if (isEmpty)
					return false;
				if (current != null)
					return true;
				if (leftIterator.hasNext()) {
					current = leftIterator.next();
					return true;
				}
				if (used == false) {
					current = value;
					used = true;
					return true;
				}
				if (rightIterator.hasNext()) {
					current = rightIterator.next();
					return true;
				}
				return false;
			}

			@Override
			public Integer next() {
				if (!hasNext())
					throw new NoSuchElementException();
				Integer ret = current;
				current = null;
				return ret;
			}
		};
	}

	public Iterator<Integer> bigToSmall() {
		return new Iterator<>() {
			private boolean used = false;
			private Integer current = null;
			private final Iterator<Integer> leftIterator = isEmpty ? null : left.bigToSmall();
			private final Iterator<Integer> rightIterator = isEmpty ? null : right.bigToSmall();

			@Override
			public boolean hasNext() {
				if (isEmpty)
					return false;
				if (current != null)
					return true;
				if (rightIterator.hasNext()) {
					current = rightIterator.next();
					return true;
				}
				if (used == false) {
					current = value;
					used = true;
					return true;
				}
				if (leftIterator.hasNext()) {
					current = leftIterator.next();
					return true;
				}
				return false;
			}

			@Override
			public Integer next() {
				if (!hasNext())
					throw new NoSuchElementException();
				Integer ret = current;
				current = null;
				return ret;
			}
		};
	}

	/**
	 * Returns a string identifying this OrderedIntList.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		// my implementation
		return isEmpty ? "" : (left.toString() + " " + value + " " + right.toString()).trim();
	}

}
