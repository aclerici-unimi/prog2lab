import java.util.ArrayList;
import java.util.Collections;

public class IntCodeMainMemory extends BasicMemory implements MainMemory {

	/**
	 * Constructs a memory from the given blob of data. This abstracts the concept
	 * of inserting a phisical memory, or loading a program in memory.
	 * 
	 * @param blob the new memory content.
	 */
	IntCodeMainMemory(Integer[] blob) {
		data = new ArrayList<Integer>();
		Collections.addAll(data, blob);
	}

	/** Constructs a new, empty memory. */
	IntCodeMainMemory() {
		data = new ArrayList<Integer>();
	}

}
