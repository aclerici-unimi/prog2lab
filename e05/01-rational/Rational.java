/**
 * Implementation of the concept of rational number. {@code Rational}s are
 * immutable objects which can be summed, multiplied and divided in couples,
 * each operation returning a new {@code Rational}.
 */
public class Rational implements Cloneable {
	private final int num;
	private final int den;

	/*
	 * Abstraction Function: A(num, den) = num/den rational number.
	 *
	 * Representation Invariant: den!=0
	 *
	 * Abstraction Invariant: den!=0
	 */

	/**
	 * Constructs the rational n/d.
	 * 
	 * @param n numerator
	 * @param d denominator
	 * @throws IllegalArgumentException if {@code d<0}.
	 */
	public Rational(int n, int d) {
		if (d == 0)
			throw new IllegalArgumentException("denominator can't be 0.");
		num = n;
		den = d;
	}

	/**
	 * Returns the numerator of this.
	 * 
	 * @return numerator
	 */
	public int num() {
		return num;
	}

	/**
	 * Returns the denominator of this.
	 * 
	 * @return denominator
	 */
	public int den() {
		return den;
	}

	/**
	 * Reduces this Rational in an irreducible one.
	 * 
	 * @return this reduced
	 */
	public Rational reduce() {

	}

	/**
	 * Returns the sum of this and q.
	 * 
	 * @param q addend
	 * @return the sum
	 */
	public Rational add(Rational q) {

	}

	/**
	 * Returns the product of this and q.
	 * 
	 * @param q factor
	 * @return the product
	 */
	public Rational mult(Rational q) {

	}

	/**
	 * Returns the reciprocal of this.
	 * 
	 * @return reciprocal of this
	 * @throws ArithmeticException if den is zero
	 */
	public Rational reciprocal() {
		if (num == 0)
			throw new ArithmeticException("illegal rational: can't divide by zero.");
		return new Rational(den, num);
	}

	/**
	 * Returns the quotient of this and q.
	 * 
	 * @param q divisor
	 * @return the quotient
	 * @throws ArithmeticException if q is zero
	 */
	public Rational div(Rational q) {

	}

	/**
	 * Compares the specified object with this Rational for equality. Two
	 * {@code Rational}s a/b and c/d are defined to be equal if {@code ad=bc}.
	 *
	 * @param o the object to be compared with this.
	 * @return true if this and o are equals.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Rational))
			return false;
		Rational other = (Rational) obj;
		return this.num * other.den == this.den * other.num;
	}

	/**
	 * Returns the hash code value for this Rational.
	 *
	 * @return the hash code.
	 */
	@Override
	public int hashCode() {
		return 31 * num + den;
	}

}
