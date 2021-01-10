/**
 * Abstraction of a Segment Figure.
 *
 * @repInv l is positive.
 */
public abstract class Segment extends Figure {
	protected final int l;

	/**
	 * Constructs a new Segment.
	 * 
	 * @param l
	 *                  length.
	 * @param r
	 *                  row coordinate.
	 * @param c
	 *                  column coordinate.
	 * @throws IllegalArgumentException
	 *                                          if r or c or l is negative.
	 */
	public Segment(int l, int r, int c) {
		super(r, c);
		if (l <= 0)
			throw new IllegalArgumentException();
		this.l = l;
		assert repOk();
	}

	/**
	 * Implementation of the representation invariant. Returns true if the
	 * representation respects all its requirements. Used in assertions.
	 * 
	 * @return true if the representation of this is ok; false otherwise.
	 */
	public boolean repOk() {
		return super.repOk() && l > 0;
	}

}
