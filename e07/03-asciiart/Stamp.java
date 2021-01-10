/**
 * Abstraction of a Stamp Figure.
 *
 * @absFun Stamp with activated pixels relative to the positions where m is
 *         true.
 * @repInv m is not null and its dimensions are positive.
 */
public class Stamp extends Figure {
	private final boolean[][] m;

	/**
	 * Constructs a new Stamp of coordinates r and c and values values.
	 * 
	 * @param r
	 *                       row coordinate.
	 * @param c
	 *                       column coordinate.
	 * @param values
	 *                       array which the stamp will use to activate pixels
	 *                       relative to non zero positions.
	 */
	public Stamp(int r, int c, int[][] values) {
		super(r, c);
		m = new boolean[values.length][values[0].length];
		for (int i = 0; i < values.length; i++)
			for (int j = 0; j < values[0].length; j++)
				if (values[i][j] != 0)
					m[i][j] = true;
		assert repOk();
	}

	/**
	 * Implementation of the representation invariant. Returns true if the
	 * representation respects all its requirements. Used in assertions.
	 * 
	 * @return true if the representation of this is ok; false otherwise.
	 */
	public boolean repOk() {
		return super.repOk() && m != null && m.length > 0 && m[0].length > 0;
	}

	@Override
	void drawOn(BitMap b) {
		for (int i = 0; i < m.length; i++)
			for (int j = 0; j < m[0].length; j++)
				if (m[i][j] && r + i <= b.r() && c + j <= b.c())
					b.on(r + i, c + j);
	}
}
