import java.util.Iterator;

final public class ExtendedSortedIntSet extends SortedIntSet {

	/**
	 * 
	 * @return
	 */
	public Iterator<Integer> reverseElements() {
		return els.bigToSmall();
	}

}
