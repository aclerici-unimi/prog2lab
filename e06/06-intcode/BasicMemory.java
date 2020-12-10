import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/** Basic implementation of {@link Memory}. */
public class BasicMemory implements Memory {
	protected List<Integer> data;

	/** Constructs an empty BasicMemory */
	public BasicMemory() {
		data = new ArrayList<Integer>();
	}

	@Override
	public Integer get(int index) {
		if (index >= data.size())
			return 0;
		return data.get(index);
	}

	/**
	 * Trims the capacity of this memory so that it doesn't contain trailing zeros.
	 * This doesn't change the abstraction of this since any cell beyond the
	 * capacity of the memory is equal to zero by {@link Memory} specification.
	 */
	public void trimZeros() {
		ListIterator<Integer> it = data.listIterator(data.size());
		while (it.hasPrevious() && it.previous() == 0)
			it.remove();
	}

	@Override
	public void set(int index, Integer content) {
		if (index >= data.size())
			for (int i = data.size(); i <= index; i++)
				data.add(0);
		data.set(index, content);
		// trimZeros(); // to save space
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

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != this.getClass())
			return false;
		return ((BasicMemory) obj).data.equals(this.data);
	}

	@Override
	public int hashCode() {
		return 31 * data.hashCode();
	}

}
