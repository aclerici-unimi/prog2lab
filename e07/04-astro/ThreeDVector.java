/**
 * Abstraction of a three-dimensional vector of integers. {@code ThreeDVector}s
 * are immutable objects over ad adequate numeric set.
 */
public interface ThreeDVector<T extends Number> {

	/**
	 * Returns the x component of this ThreeDVector.
	 * 
	 * @return x of this.
	 */
	T x();

	/**
	 * Returns the y component of this ThreeDVector.
	 * 
	 * @return y of this.
	 */
	T y();

	/**
	 * Returns the z component of this ThreeDVector.
	 * 
	 * @return z of this.
	 */
	T z();

	/**
	 * Returns the sum of this and v. The operation is optional.
	 * 
	 * @param other the second addend.
	 * @return the sum.
	 * @throws NullPointerException if other is null.
	 */
	ThreeDVector<T> plus(ThreeDVector<T> other);

	/**
	 * Returns the taxicab norm of this.
	 * 
	 * @return the norm.
	 */
	T norm();

}
