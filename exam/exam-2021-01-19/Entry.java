/**
 * Abstraction of a filesystem entry.
 * 
 * @absFun AF(name) = filesystem entry of name {@code name}. It is a directory
 *         if directory()=true, file otherwise.
 * @repInv name is not null.
 */
public abstract class Entry {
	private final String name;

	/**
	 * Constructs a new Entry of name {@code name}.
	 * 
	 * @param name
	 *                     name of the new Entry.
	 * @throws NullPointerException
	 *                                      if name is null.
	 */
	public Entry(String name) {
		if (name == null)
			throw new NullPointerException();
		this.name = name;
	}

	/**
	 * Returns true if this Entry is a directory, false otherwise.
	 * 
	 * @return true if this Entry is a directory.
	 */
	public abstract boolean isDirectory();

	/**
	 * Returns the name of this Entry.
	 * 
	 * @return the name of this Entry.
	 */
	public String name() {
		return name;
	}

	/**
	 * Returns the size of this Entry.
	 * 
	 * @return size of this.
	 */
	abstract public int size();

	/**
	 * Returns a String containing the list of Entries in this Entry if it is a
	 * Directory, or this File if it is so. Directories will end with * (es. dir*)
	 * and files will contain their size in parenthesis (es. file(10)).
	 * 
	 * @return a String containing the list of children of this.
	 */
	abstract public String ls();

}
