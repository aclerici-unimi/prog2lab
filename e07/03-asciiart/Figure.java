/**
 * Abstraction of a figure drawable on a BitMap.
 *
 * @repInv r and c are non negative.
 */
public abstract class Figure {
	protected final int r;
	protected final int c;

	/**
	 * Constructs a new Figure of coordinates r and c.
	 * 
	 * @param r row coordinate.
	 * @param c column coordinate.
	 *
	 * @throws IllegalArgumentException if r or c is negative.
	 */
	protected Figure(int r, int c) {
		if (r < 0 || c < 0)
			throw new IllegalArgumentException();
		this.r = r;
		this.c = c;
	}

	/**
	 * Implementation of the representation invariant. Returns true if the
	 * representation respects all its requirements. Used in assertions.
	 * 
	 * @return true if the representation of this is ok; false otherwise.
	 */
	public boolean repOk() {
		return r >= 0 && c >= 0;
	}

	/**
	 * Draw this Figure on the given {@link BitMap}.
	 * 
	 * @param b BitMap to draw this Figure on.
	 */
	abstract void drawOn(BitMap b);

}
