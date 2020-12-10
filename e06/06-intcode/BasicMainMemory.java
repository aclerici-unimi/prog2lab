import java.util.ArrayList;
import java.util.List;

public class BasicMainMemory extends BasicMemory implements MainMemory {

	/**
	 * Constructs a memory from the given blob of data. This abstracts the concept
	 * of inserting a phisical memory, or loading a program in memory.
	 * 
	 * @param blob the new memory content.
	 */
	BasicMainMemory(List<Integer> blob) {
		data = new ArrayList<Integer>(blob);
	}

}
