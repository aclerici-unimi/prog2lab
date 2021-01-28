import java.util.List;

/**
 * Abstraction of a virtual filesytem.
 * 
 * @absFun AF(root) = filesytem of root {@code root}.
 * @repInv root is not null.
 */
public class Filesystem {
	private final Directory root;

	/**
	 * Constructs a new Filesystem.
	 */
	public Filesystem() {
		root = new Directory("root");
	}

	/**
	 * Returns the root of this filesytem.
	 * 
	 * @return root directory of this.
	 */
	public Directory root() {
		return root;
	}

	/**
	 * Returns the {@link Entry} specified by the given absolute path, or null if no
	 * such Entry exists.
	 * 
	 * @param path
	 *                     path to parse to find the wanted Entry.
	 * @return found Entry or null.
	 */
	public Entry fetch(List<String> path) {
		if (path.isEmpty())
			return root;
		return root.fetch(path);
	}

	/**
	 * Creates the Directory specified by the given absolute path.
	 * 
	 * @param path
	 *                     absolute path to create the directory in.
	 * @throws IllegalArgumentException
	 *                                          if the mother directory cannot be
	 *                                          found.
	 */
	public void mkdir(List<String> path) {
		Entry e = fetch(path.subList(0, path.size() - 1));
		if (e == null || !e.isDirectory())
			throw new IllegalArgumentException("can't find the mother directory");
		((Directory) e).mkdir(path.get(path.size() - 1));
	}

	/**
	 * Creates the File specified by the given absolute path.
	 * 
	 * @param path
	 *                     absolute path to create the File in.
	 * @param size
	 *                     size of the new File.
	 * @throws IllegalArgumentException
	 *                                          if the mother directory cannot be
	 *                                          found.
	 */
	public void mkfile(List<String> path, int size) {
		Entry e = fetch(path.subList(0, path.size() - 1));
		if (e == null || !e.isDirectory())
			throw new IllegalArgumentException("can't find the mother directory");
		((Directory) e).mkfile(path.get(path.size() - 1), size);
	}

	/**
	 * List the contents of the {@link Directory} specified by the given absolute
	 * path.
	 * 
	 * @param path
	 *                     absolute path to list the contents of.
	 * @return list view of the specified path.
	 */
	public String ls(List<String> path) {
		return fetch(path).ls();
	}

	/**
	 * Returns the size of the Entry specified by the given absolute path.
	 * 
	 * @param path
	 *                     absolute path to list the contents of.
	 * @return the size.
	 */
	public int size(List<String> path) {
		return fetch(path).size();
	}

}
