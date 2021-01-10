/**
 * Abstraction of a Vertical Segment Figure.
 *
 * @absFun vertical segment of coordinates r and c and length l.
 * @repInv see Segment.
 */
public class VSegment extends Segment {

	/**
	 * Constructs a new vertical segment of coordinates r and c and length l.
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
	public VSegment(int l, int r, int c) {
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
		if (c <= b.c() && r + l <= b.c())
			for (int i = r; i < r + l; i++) {
				b.on(i, c);
			}
	}

}
