import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * OVERVIEW: An ordered list is a mutable ordered list of integers. A typical
 * list is a sequence [x1, ..., xn] where {@codex i < xj if i < j}.
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

	/**
	 * EFFECTS: Initializes this to be an empty ordered list.
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
		if (left == null || right == null || !left.repOk() || !right.repOk()
				|| (!left.isEmpty && left.greatest() >= value)
				|| (!right.isEmpty && right.least() <= value))
			return false;
		return true;
	}

	/**
	 * EFFECTS: If el is in this, throws DuplicateException; otherwise, adds el to
	 * this.
	 *
	 * MODIFIES: this
	 */
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

	/**
	 * MODIFIES: this
	 *
	 * EFFECTS: If el is not in this, throws NotFoundException; otherwise, removes
	 * el from this.
	 */
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

	/**
	 * EFFECTS: If el is in this returns true else returns false. Originally called
	 * isIn.
	 */
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

	/**
	 * EFFECTS: Returns true if this is empty else returns false.
	 */
	public boolean isEmpty() {
		return isEmpty;
	}

	/**
	 * EFFECTS: If this is empty, throws EmptyException; otherwise, returns the
	 * smallest element of this. Originally called least.
	 */
	public int min() {
		if (isEmpty)
			throw new NoSuchElementException();
		if (left.isEmpty)
			return value;
		return left.min();
	}

	/**
	 * EFFECTS: Returns a generator that will produce the elements of this (as
	 * Integers), each exactly once, in order from smallest to largest.
	 *
	 * REQUIRES: this must not be modified while the generator is in use.
	 */
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

	/**
	 * Returns this list's size.
	 * 
	 * @return the size.
	 */
	public int size() {
		return isEmpty ? 0 : 1 + left.size() + right.size();
	}

	/**
	 * Returns an iterator that will produce the elements of this ordered from
	 * smallest to largest.
	 * 
	 * @return the iterator.
	 */
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
