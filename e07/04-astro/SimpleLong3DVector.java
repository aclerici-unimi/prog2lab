/**
 * Simple implementation of the {@link ThreeDVector} interface with Long
 * components.
 */
public class SimpleLong3DVector implements ThreeDVector<Long> {
	long x;
	long y;
	long z;

	/**
	 * Constructs a SimpleInt3DVector with the given components.
	 * 
	 * @param x x component.
	 * @param y y component.
	 * @param z z component.
	 */
	public SimpleLong3DVector(long x, long y, long z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public Long x() {
		return x;
	}

	@Override
	public Long y() {
		return y;
	}

	@Override
	public Long z() {
		return z;
	}

	@Override
	public ThreeDVector<Long> plus(ThreeDVector<Long> other) {
		return new SimpleLong3DVector(this.x + other.x(), this.y + other.y(), this.z + other.z());
	}

	@Override
	public Long norm() {
		return Math.abs(x) + Math.abs(y) + Math.abs(z);
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}

}
