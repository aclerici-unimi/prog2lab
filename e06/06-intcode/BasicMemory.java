import java.util.List;

public class BasicMemory implements Memory {
	protected List<Integer> data;

	@Override
	public Integer get(int index) {
		if (index >= data.size())
			return 0;
		return data.get(index);
	}

	@Override
	public void set(int index, Integer content) {
		if (index >= data.size())
			for (int i = data.size(); i < index; i++)
				data.add(0);
		data.set(index, content);
	}

}
