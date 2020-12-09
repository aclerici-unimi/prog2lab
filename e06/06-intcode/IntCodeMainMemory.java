import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IntCodeMainMemory extends BasicMemory implements MainMemory {

	/**
	 * Constructs a memory from the given blob of data. This abstracts the concept
	 * of inserting a phisical memory, or loading a program in memory.
	 * 
	 * @param blob the new memory content.
	 */
	IntCodeMainMemory(List<Integer> blob) {
		data = new ArrayList<Integer>(blob);
	}

	/** Constructs a new, empty memory. */
	IntCodeMainMemory() {
		data = new ArrayList<Integer>();
	}

	@Override
	public String toString() {
		String res = "Memory : [";
		if (data.size() > 0) {
			Iterator<Integer> it = data.iterator();
			Integer cell = it.next();
			while (it.hasNext()) {
				res += cell + ",";
				cell = it.next();
			}
			res += cell;
		}
		return res + "]";
	}

}
