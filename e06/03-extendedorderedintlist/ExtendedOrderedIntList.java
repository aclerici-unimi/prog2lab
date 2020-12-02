import java.util.Iterator;
import java.util.Stack;

/**
 * Data structure containing a mutable ordered list of integers, extended with
 * the possibility of iterating from the greatest to the least int.
 */
public class ExtendedOrderedIntList extends OrderedIntList {

	private Stack<Integer> stackify() {
		Stack<Integer> res = new Stack<Integer>();
		Iterator<Integer> it = smallToBig();
		while (it.hasNext())
			res.push(it.next());
		return res;
	}

	/**
	 * Returns an iterator that will produce the elements of this ordered from
	 * smallest to largest. This implementation is memory inefficient since it
	 * stores a copy of the data.
	 * 
	 * @return the iterator.
	 */
	public Iterator<Integer> bigToSmall() {
		return new Iterator<Integer>() {
			private Stack<Integer> elements = stackify();

			@Override
			public boolean hasNext() {
				return !elements.isEmpty();
			}

			@Override
			public Integer next() {
				return elements.pop();
			}
		};
	}

}
