import java.util.LinkedList;
import java.util.List;

/**
 * Abstraction of a filesytem directory.
 * 
 * @absFun AF = filesystem entry AF(super), directory which has for children the
 *         elements of {@code children}.
 * @repInv children and its elements are not null; there cannot be two children
 *         whose names are equals.
 */
public class Directory extends Entry {
	private final List<Entry> children;

	/**
	 * Constructs a new empty Directory of name {@code name}.
	 * 
	 * @param name
	 *                     name of the new directory.
	 * @throws NullPointerException
	 *                                      if name is null.
	 */
	public Directory(String name) {
		super(name);
		children = new LinkedList<>();
	}

	@Override
	public boolean isDirectory() {
		return true;
	}

	@Override
	public int size() {
		int res = 0;
		for (Entry e : children) {
			res += e.size();
		}
		return res;
	}

	@Override
	public String ls() {
		String res = "";
		for (Entry e : children) {
			res += ">>> " + e.name();
			if (e.isDirectory())
				res += "*";
			else
				res += "(" + e.size() + ")";
			;
			res += "\n";
		}
		return res;
	}

	/**
	 * Returns the {@link Entry} specified by the given relative path, or null if no
	 * such Entry exists.
	 * 
	 * @param path
	 *                     path to parse to find the wanted Entry.
	 * @return found Entry.
	 * @throws NullPointerException
	 *                                          if path is null.
	 * @throws IllegalArgumentException
	 *                                          if path is empty.
	 */
	public Entry fetch(List<String> path) {
		if (path == null)
			throw new NullPointerException();
		if (path.isEmpty())
			throw new IllegalArgumentException();
		String toFind = path.remove(0);
		for (Entry e : children) {
			if (e.name().equals(toFind)) {
				if (path.isEmpty())
					return e;
				else
					return e.isDirectory() ? ((Directory) e).fetch(path) : null;
			}
		}
		return null;
	}

	private boolean contains(String name) {
		for (Entry e : children)
			if (e.name().equals(name))
				return true;
		return false;
	}

	/**
	 * Creates the Directory specified by the given name.
	 * 
	 * @param name
	 *                     name of the new directory.
	 * @throws NullPointerException
	 *                                      if name is null.
	 */
	public void mkdir(String name) {
		if (name == null)
			throw new NullPointerException();
		if (contains(name))
			throw new IllegalArgumentException("an Entry with this name already exists");
		children.add(new Directory(name));
	}

	/**
	 * Creates the File specified by the given name.
	 * 
	 * @param name
	 *                     name of the new File.
	 * @param size
	 *                     size of the new File.
	 * @throws NullPointerException
	 *                                          if name is null.
	 * @throws IllegalArgumentException
	 *                                          if size is non positive.
	 */
	public void mkfile(String name, int size) {
		if (name == null)
			throw new NullPointerException();
		if (size <= 0)
			throw new IllegalArgumentException();
		if (contains(name))
			throw new IllegalArgumentException("an Entry with this name already exists");
		children.add(new File(name, size));
	}

}
