public interface Set<E> extends Iterable<E> {

	/**
	 * Returns the number of elements in this set (its cardinality).
	 * 
	 * @return the size.
	 */
	int size();

	/**
	 * Returns true if this set contains no elements.
	 * 
	 * @return true if this is an empty set, false otherwise.
	 */
	boolean isEmpty();

	/**
	 * Returns true if this set contains the specified element.
	 * 
	 * @param o element whose presence is to be tested.
	 * @return true if this contains o, false otherwise.
	 */
	boolean contains(Object o);

	/**
	 * Returns an element from this set.
	 * 
	 * @return the element.
	 * @throws NoSuchElementException if the set is empty.
	 */
	E choose();

	/**
	 * Adds the specified element to this set if it is not already present.
	 * 
	 * @param e element to be added in this.
	 * @return true if this set did not already contain the specified element, false
	 *         otherwise.
	 */
	boolean add(E e);

	/**
	 * Removes the specified element from this set if it is present.
	 * 
	 * @param o element to be removed.
	 * @return true if this set contained the specified element, false otherwise.
	 */
	boolean remove(Object o);

}
