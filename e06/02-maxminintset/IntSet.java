import java.util.Iterator;

public abstract class IntSet {
	protected int sz; // the size

	// constructors

	public IntSet() {
		sz = 0;
	}

	// abstract methods

	public abstract void insert(int x);

	public abstract void remove(int x);

	public abstract Iterator<Integer> elements();

	public abstract boolean repok();

	// methods

	public boolean isIn(int x) {
		Iterator<Integer> g = elements();
		Integer z = x;
		while (g.hasNext())
			if (g.next().equals(z))
				return true;
		return false;
	}

	public int size() {
		return sz;
	}

	// implementations of subset and toString go here

}
