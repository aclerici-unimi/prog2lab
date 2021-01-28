/**
 * Abstraction of a filesytem file.
 * 
 * @absFun AF = filesystem entry AF(super), file of size {@code size}.
 * @repInv size is positive.
 */
public class File extends Entry {
	private int size;

	/**
	 * Constructs a new file of name {@code name} and size {@code size}.
	 * 
	 * @param name
	 *                     the name of the new file.
	 * @param size
	 *                     the size of the new file.
	 * @throws NullPointerException
	 *                                          if name is null.
	 * @throws IllegalArgumentException
	 *                                          if size is non positive.
	 */
	public File(String name, int size) {
		super(name);
		if (size <= 0)
			throw new IllegalArgumentException();
		this.size = size;
	}

	@Override
	public boolean isDirectory() {
		return false;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public String ls() {
		return ">>> " + name() + "(" + size() + ")";
	}

}
