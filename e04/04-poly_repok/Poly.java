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
	 * Representation Invariant: coeff is not null; the first element can't be zero.
	 */

	/**
	 * Constructs a zero polynomial.
	 */
	public Poly() {
		this.coeff = new int[0];
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
			this.coeff = new int[0];
			return;
		}
		if (n < 0)
			throw new NegativeExponentException("can't create monomial: negative degree");
		this.coeff = new int[n + 1];
		this.coeff[n] = c;
		assert repOk();
	}

	/**
	 * Implementation of the representation invariant. Returns true if the
	 * representation respects all its requirements. Used in assertions.
	 * 
	 * @return true if the representation is ok; false otherwise.
	 */
	public boolean repOk() {
		return coeff != null && coeff[coeff.length - 1] == 0;
	}

	/**
	 * Returns the sum of this and q.
	 *
	 * @param q addend (other than this)
	 * @return this+q
	 */
	public Poly add(Poly q) {
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
			for (int k = this.coeff.length - 1; k >= 0; k--)
				if (coeff[k] + q.coeff[k] != 0)
					break;
				else
					newDeg--;
		Poly res = new Poly(newDeg + 1);
		int i;
		for (i = 0; i < small.coeff.length && i <= newDeg; i++)
			res.coeff[i] = small.coeff[i] + large.coeff[i];
		for (int j = i; j <= newDeg; j++)
			res.coeff[j] = large.coeff[j];
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
		return this.add(q.minus());
	}

	/**
	 * Returns the product of this and -q.
	 *
	 * @param q factor (other than this)
	 * @return this*q
	 */
	public Poly mul(Poly q) {
		if (q.coeff.length == -1 || this.coeff.length == -1)
			return new Poly();
		Poly r = new Poly(this.coeff.length + q.coeff.length - 1);
		for (int i = 0; i < this.coeff.length; i++)
			for (int j = 0; j <= q.coeff.length - 1; j++)
				r.coeff[i + j] = r.coeff[i + j] + this.coeff[i] * q.coeff[j];
		assert r.repOk();
		return r;
	}

	private Poly(int n) {
		this.coeff = new int[n];
		assert repOk();
	}

	/**
	 * Returns the opposite of this.
	 *
	 * @return -this
	 */
	public Poly minus() {
		Poly res = new Poly(this.coeff.length);
		for (int n = 0; n < this.coeff.length; n++)
			res.coeff[n] = -this.coeff[n];
		assert res.repOk();
		return res;
	}

	/**
	 * Returns the degree of this.
	 *
	 * @return degree of this
	 */
	public int degree() {
		return this.coeff.length - 1;
	}

	/**
	 * Returns the coefficient of the term of dth degree.
	 * 
	 * @param d degree
	 * @return coefficient
	 */
	public int coeff(int d) {
		return this.coeff[d];
	}

	/**
	 * Returns a string identifying this polynomial. It uses x as variable.
	 *
	 * @return a string identifying this set.
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
}
