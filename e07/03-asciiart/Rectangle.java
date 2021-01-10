/**
 * Abstraction of a Rectangle Figure.
 *
 * @absFun Rectangle of height h and length l.
 * @repInv l and h are positive.
 */
public class Rectangle extends Figure {
	private final int h, l;

	/**
	 * Constructs a new Rectangle of height h, length l and coordinates r and c.
	 * 
	 * @param h
	 *                  height.
	 * @param l
	 *                  length.
	 * @param r
	 *                  row coordinate.
	 * @param c
	 *                  column coordinate.
	 */
	public Rectangle(int h, int l, int r, int c) {
		super(r, c);
		this.h = h;
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
		return super.repOk() && l > 0 && h > 0;
	}

	@Override
	void drawOn(BitMap b) {
		Figure s = new HSegment(l, r, c);
		s.drawOn(b);
		s = new HSegment(l, r + h - 1, c);
		s.drawOn(b);
		s = new VSegment(h, r, c);
		s.drawOn(b);
		s = new VSegment(h, r, c + l - 1);
		s.drawOn(b);
	}

}
