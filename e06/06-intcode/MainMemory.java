import java.util.LinkedList;
import java.util.List;

/**
 * Abstraction of the concept of main {@link Memory}. A main memory can be
 * fetched in mass, that is, each cell in an index interval is fetched
 * alltogether.
 */
public interface MainMemory extends Memory {

	/**
	 * Returns a list containing the content of the cells in the given index
	 * interval.
	 * 
	 * @param startIndex index of the first cell.
	 * @param endIndex   index of the first cell not to be included in the list.
	 * @return the list of the cells' contents.
	 */
	default List<Integer> blobGet(int startIndex, int endIndex) {
		List<Integer> res = new LinkedList<Integer>();
		for (int i = startIndex; i < endIndex; i++) {
			res.add(get(i));
		}
		return res;
	}

}
