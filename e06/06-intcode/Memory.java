/**
 * Abstraction of the concept of memory. A memory has cells which can be set or
 * fetched (get). This abstraction has possibly infinite cells. Every new cell
 * contains a 0.
 */
public interface Memory {

	/**
	 * Sets the cell at index {@code index} to the value {@code content}.
	 * 
	 * @param index   the cell index.
	 * @param content the new content.
	 */
	void set(int index, Integer content);

	/**
	 * Returns the content at the cell at {@code index}.
	 * 
	 * @param index the index.
	 * @return the content of the cell.
	 */
	Integer get(int index);

}
