import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class BasicMemory implements Memory {
	protected List<Integer> data;

	public BasicMemory() {
		data = new ArrayList<Integer>();
	}

	@Override
	public Integer get(int index) {
		if (index >= data.size())
			return 0;
		return data.get(index);
	}

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

}
