import java.util.Arrays;
import java.util.Iterator;

/**
 * Abstract type based on the mathematical concept of single variable, integer
 * polynomial. Polynomials are immutable objects which can be added and
 * multiplied together. The class includes constructors to make the zero
 * polynomial, which degree is conventionally assumed to be -1, and monomials.
 * Every other polynomial has to be obtained by some operation of existing
 * polynomials. Other then zero, the degree of a polynomial is the degree of the
 * term of highest degree with a non-zero coefficient.
 */
public class Poly {
	private final int[] coeff;

	/*
	 * Abstraction Function: AF(coeff) = Polynomial:
	 * coeff[coeff.length-1]x^(coeff.length-1) + ... + coeff[1]x + coeff[0] or
	 * AF(coeff) = 0 if coeff.length = 0.
	 *
	 * Representation Invariant: coeff is not null; the last element can't be zero.
	 */

	/**
	 * Constructs a zero polynomial.
	 */
	public Poly() {
		this.coeff = new int[0]; // not null reference, there's no last element (so not 0)
	}

	/**
	 * Constructs a monomial (polynomial). If c=0 or n=-1, returns the zero
	 * polynomial. Otherwise, degree n has to be non negative.
	 * 
	 * @param c coefficient
	 * @param n exponent
	 * @throws NegativeExponentException if n<span>&#60;</span>0 and c!=0 and n!=-1
	 */
	public Poly(int c, int n) {
		if (n == -1 || c == 0) {
			this.coeff = new int[0]; // not null reference
			return;
		}
		if (n < 0)
			throw new NegativeExponentException("can't create monomial: negative degree");
		this.coeff = new int[n + 1]; // not null reference (has zero at the end)
		this.coeff[n] = c; // the zero at the end is no more (c!=0 as previously checked)
		assert repOk();
	}

	/**
	 * Implementation of the representation invariant. Returns true if the
	 * representation respects all its requirements. Used in assertions.
	 * 
	 * @return true if the representation is ok; false otherwise.
	 */
	public boolean repOk() {
		return coeff != null && coeff[coeff.length - 1] != 0;
	}

	/**
	 * Returns the sum of this and q.
	 *
	 * @param q addend (other than this)
	 * @return this+q
	 */
	public Poly add(Poly q) {
		// RI preserved: no modification to this or q
		Poly large, small;
		if (this.coeff.length > q.coeff.length) {
			large = this;
			small = q;
		} else {
			large = q;
			small = this;
		}
		int newDeg = large.coeff.length - 1;
		if (this.coeff.length == q.coeff.length)
			for (; newDeg >= 0; newDeg--) // loop A
				if (small.coeff[newDeg] + large.coeff[newDeg] != 0)
					break;
		Poly res = new Poly(newDeg + 1); // last coeff (coeff[newDeg]) can be zero if and only if newDeg>=0
		int i;
		for (i = 0; i < small.coeff.length && i <= newDeg; i++) // loop B
			res.coeff[i] = small.coeff[i] + large.coeff[i];
		for (int j = i; j <= newDeg; j++) // loop C
			res.coeff[j] = large.coeff[j];
		// If newDeg==-1 then the rep is ok. In any other case, loop B is entered.
		// If loop B exits for the loss of the second condition (i<=newDeg), then
		// coeff[newDeg] has been set to a value X and loop C doesn't iterate (because
		// j=i>newDeg).
		// If loop B exits for the loss of the first condition, then loop C
		// iterates until coeff[newDeg] has been set to a value X (because it is its
		// only condition).
		//
		// As for the value X, it depends directly on newDeg.
		// If this and q are of different degrees then newDeg==large.coeff.length-1, and
		// loop C determines that X!=0 since large.coeff[newDeg] is != for large's RI
		// (loop B will necessarily exit because of the loss of the first condition and
		// loop C will necessarily iterate).
		// If this and q are of the same degree, then newDeg is the biggest degree where
		// small.coeff[newDeg]+large.coeff[newDeg]!=0, as proven by loop A: in this case
		// loop B determines X to be !=0 (loop C is certain not to be executed since
		// loop B exits for the loss of the second condition).
		assert res.repOk();
		return res;
	}

	/**
	 * Returns the algebric sum of this and -q.
	 *
	 * @param q subtrahend
	 * @return this-q
	 */
	public Poly sub(Poly q) {
		return this.add(q.minus()); // both called methods are RI-friendly
	}

	/**
	 * Returns the product of this and -q.
	 *
	 * @param q factor (other than this)
	 * @return this*q
	 */
	public Poly mul(Poly q) {
		if (q.coeff.length == -1 || this.coeff.length == -1)
			return new Poly(); // RI-ok
		Poly r = new Poly(this.coeff.length + q.coeff.length - 1); // not null by previous exclusion
		for (int i = 0; i < this.coeff.length; i++)
			for (int j = 0; j <= q.coeff.length - 1; j++)
				r.coeff[i + j] += this.coeff[i] * q.coeff[j];
		// The coefficient of the maximum degree is obtained by multiplying the two
		// coefficients of the maximum degree, which are non zero by hypotesis. By the
		// Zero-product Property, their product is non zero.
		assert r.repOk();
		return r;
	}

	private Poly(int n) {
		this.coeff = new int[n]; // not repOk, used in class methods
	}

	/**
	 * Returns the opposite of this.
	 *
	 * @return -this
	 */
	public Poly minus() {
		Poly res = new Poly(this.coeff.length);
		for (int n = 0; n < this.coeff.length; n++)
			res.coeff[n] = -this.coeff[n]; // the coefficient of the maximum degree is not zero, so it is
							// its opposite
		assert res.repOk();
		return res;
	}

	/**
	 * Returns the degree of this.
	 *
	 * @return degree of this
	 */
	public int degree() {
		// observer
		return this.coeff.length - 1;
	}

	/**
	 * Returns the coefficient of the term of dth degree.
	 * 
	 * @param d degree
	 * @return coefficient
	 */
	public int coeff(int d) {
		// observer
		return this.coeff[d];
	}

	/**
	 * Returns an iterator over the degrees of non-zero coefficient contained in
	 * this Poly.
	 * 
	 * @return the iterator
	 */
	public Iterator<Integer> degrees() {
		return new Iterator<Integer>() {
			private int nextZero = 0;

			@Override
			public boolean hasNext() {
				if (nextZero == coeff.length)
					return false;
				return true;
			}

			@Override
			public Integer next() {
				while (coeff[nextZero] == 0 && nextZero < coeff.length)
					nextZero++;
				if (nextZero == coeff.length)
					throw new IndexOutOfBoundsException();
				return nextZero++;
			}

		};
	}

	/**
	 * Returns a string identifying this Poly. It uses x as variable.
	 *
	 * @return the string.
	 */
	@Override
	public String toString() {
		if (this.coeff.length == 0)
			return "0";
		String res = "";
		for (int deg = this.coeff.length - 1; deg >= 0; deg--) {
			if (this.coeff[deg] == 0)
				continue;
			if (this.coeff[deg] < 0) {
				res += "-";
				if (deg != this.coeff.length - 1)
					res += " ";
				res += -this.coeff[deg];
			} else if (deg != this.coeff.length - 1) {
				res += "+ ";
				if (this.coeff[deg] != 1 || deg == 0)
					res += this.coeff[deg];
			} else if (deg == 0 || this.coeff[deg] != 1)
				res += this.coeff[deg];
			switch (deg) {
				case 0:
					break;
				case 1:
					res += "x";
					break;
				default:
					res += "x^" + deg;
			}

			res += " ";
		}
		return res;
	}

	/**
	 * Compares the specified object with this Poly for equality. Two {@code Poly}s
	 * are defined to be equal if they contain the same coefficients in the same
	 * order
	 *
	 * @param o the object to be compared with this.
	 * @return true if this and o are equals.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Poly))
			return false;
		Poly other = (Poly) o;
		if (other.coeff.length != this.coeff.length)
			return false;
		for (int i = 0; i < coeff.length; i++) {
			if (other.coeff[i] != this.coeff[i])
				return false;
		}
		return true;
	}

	/**
	 * Returns the hash code value for this Poly.
	 *
	 * @return the hash code.
	 */
	@Override
	public int hashCode() {
		int c = 0;
		for (int i = 0; i < coeff.length; i++)
			c += 31 * i + coeff[i];
		return 31 * degree() + c;
	}

}
