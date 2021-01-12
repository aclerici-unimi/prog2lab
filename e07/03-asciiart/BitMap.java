import java.util.Arrays;

/**
 * BitMap abstraction used by ASCIIArt code components.
 *
 * @absFun bitmap matrix of dimensions m.length, m[0].length which has an
 *         activated pixel in every position x, y where m[x][y] is true.
 * @repInv m!=null and its dimensions are at least 1x1 and at most 1000x1000.
 */
public class BitMap {
	private final boolean m[][];

	/**
	 * Constructs a new BitMap of dimensions b, h. The dimensions cannot exceed
	 * 1000.
	 * 
	 * @param b
	 *                  base.
	 * @param h
	 *                  height.
	 * @throws IllegalArgumentException
	 *                                          if {@code b>1000} or {@code h>1000}.
	 */
	public BitMap(int b, int h) {
		if (b > 1000 || h > 1000 || b <= 0 || h <= 0)
			throw new IllegalArgumentException("invalid dimensions for the BitMap");
		m = new boolean[b][h];
		assert repOk();
	}

	/**
	 * Implementation of the representation invariant. Returns true if the
	 * representation respects all its requirements. Used in assertions.
	 * 
	 * @return true if the representation of this is ok; false otherwise.
	 */
	public boolean repOk() {
		return m != null && m.length > 0 && m[0].length > 0;
	}

	/** Clears every pixel of this BitMap. */
	public void clear() {
		for (int i = 0; i < m.length; i++)
			for (int j = 0; j < m[i].length; j++)
				m[i][j] = false;
		assert repOk();
	}

	/** Inverts every pixel of this BitMap. */
	public void invert() {
		for (int i = 0; i < m.length; i++)
			for (int j = 0; j < m[i].length; j++)
				m[i][j] = !m[i][j];
		assert repOk();
	}

	/**
	 * Sets the state of the pixel of coordinates r, c to on.
	 * 
	 * @param r
	 *                  row coordinate
	 * @param c
	 *                  column coordinate
	 *
	 * @throws IllegalArgumentException
	 *                                          if r or c is an invalid coordinate.
	 */
	public void on(int r, int c) {
		if (r < 0 || c < 0 || r >= m.length || c > m[0].length)
			throw new IllegalArgumentException();
		m[r][c] = true;
		assert repOk();
	}

	/**
	 * Sets the state of the pixel of coordinates r, c to on.
	 * 
	 * @param r
	 *                  row coordinate
	 * @param c
	 *                  column coordinate
	 *
	 * @throws IllegalArgumentException
	 *                                          if r or c is an invalid coordinate.
	 */
	public void off(int r, int c) {
		if (r < 0 || c < 0 || r >= m.length || c > m[0].length)
			throw new IllegalArgumentException();
		m[r][c] = false;
		assert repOk();
	}

	/**
	 * Returns the number of rows in this BitMap.
	 * 
	 * @return number of rows of this.
	 */
	public int r() {
		if (m == null)
			throw new NullPointerException();
		return m.length;
	}

	/**
	 * Returns the number of columns in this BitMap.
	 * 
	 * @return number of columns of this.
	 */
	public int c() {
		if (m == null)
			throw new NullPointerException();
		if (m.length == 0)
			return 0;
		return m[0].length;
	}

	@Override
	public String toString() {
		String res = "";
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[0].length; j++)
				if (m[i][j])
					res += "*";
				else
					res += ".";
			res += "\n";
		}
		return res.trim();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof BitMap))
			return false;
		BitMap other = (BitMap) obj;
		if (!Arrays.deepEquals(m, other.m))
			return false;
		return true;
	}

}
