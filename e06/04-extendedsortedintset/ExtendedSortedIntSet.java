import java.util.Iterator;

final public class ExtendedSortedIntSet extends SortedIntSet {

	/**
	 * Returns a generator that will produce all elements of this, each exactly
	 * once, in ascending order.
	 * 
	 * @return the iterator.
	 */
	public Iterator<Integer> reverseElements() {
		return els.bigToSmall();
	}

}
