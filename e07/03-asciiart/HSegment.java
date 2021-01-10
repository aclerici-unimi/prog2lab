/**
 * Abstraction of a Horizontal Segment Figure.
 *
 * @absFun horizontal segment of coordinates r and c and length l.
 * @repInv see Segment.
 */
public class HSegment extends Segment {

	/**
	 * Constructs a new horizontal segment of coordinates r and c and length l.
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
	public HSegment(int l, int r, int c) {
		super(l, r, c);
		assert repOk();
	}

	/**
	 * Implementation of the representation invariant. Returns true if the
	 * representation respects all its requirements. Used in assertions.
	 * 
	 * @return true if the representation of this is ok; false otherwise.
	 */
	public boolean repOk() {
		return super.repOk();
	}

	@Override
	void drawOn(BitMap b) {
		if (r <= b.r() && c + l <= b.c())
			for (int j = c; j < c + l; j++)
				b.on(r, j);
	}

}
