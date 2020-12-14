// use this command to build documentation: javadoc -d docs -tag repInv:class:"Representation Invariant" -tag absFun:class:"Abstraction Function" *.java

/**
 * Simple implementation of the {@link ThreeDVector} interface with Long
 * components.
 * 
 * @absFun AF(this) = Three dimensional vector of x=this.x, y=this.y, z=this.z.
 * @repInv true.
 */
public class SimpleLong3DVector implements ThreeDVector<Long> {
	private final long x;
	private final long y;
	private final long z;

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
		assert repOk();
	}

	/**
	 * Implementation of the representation invariant. Returns true if the
	 * representation respects all its requirements. Used in assertions.
	 * 
	 * @return true if the representation of this is ok; false otherwise.
	 */
	public boolean repOk() {
		return true;
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
		if (other == null)
			throw new NullPointerException("can't add a null vector");
		SimpleLong3DVector res = new SimpleLong3DVector(this.x + other.x(), this.y + other.y(),
				this.z + other.z());
		assert res.repOk();
		return res;
	}

	@Override
	public Long norm() {
		return Math.abs(x) + Math.abs(y) + Math.abs(z);
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ThreeDVector<?>))
			return false;
		ThreeDVector<?> other = (ThreeDVector<?>) obj;
		return this.x().equals(other.x()) && this.y().equals(other.y()) && this.z().equals(other.z());
	}

}
