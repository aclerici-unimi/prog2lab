import java.util.List;
import java.util.ArrayList;

/**
 * Support class used by Poly, represents a polynomial term (non zero monomial).
 */
class Term {
	final int coeff;
	final int exp;

	/*
	 * Abstraction Function: AF(coeff, exp) = coeff x^exp.
	 *
	 * Representation Invariant: exp is non negative, coeff is not zero.
	 */

	Term(int c, int n) {
		this.coeff = c;
		this.exp = n;
		assert repOk();
	}

	boolean repOk() {
		return exp >= 0 && coeff != 0;
	}
}

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
	private final Term terms[];

	/*
	 * Abstraction Function: AF(terms) = Polynomial: terms[0] + terms[1] + ... +
	 * terms[terms.length-1] or AF(terms) = 0 if terms.length = 0.
	 *
	 * Representation Invariant: every Term is ok; terms is not null; the terms are
	 * ordered by decreasing exps.
	 */

	/**
	 * Constructs a zero polynomial.
	 */
	public Poly() {
		this.terms = new Term[0];
		assert repOk();
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
			this.terms = new Term[0];
			return;
		}
		if (n < 0)
			throw new NegativeExponentException("can't create monomial: negative degree");
		this.terms = new Term[1];
		this.terms[0] = new Term(c, n);
		assert repOk();
	}

	private Poly(int n) {
		this.terms = new Term[n];
		// this constructor doesn't constructs a valid representation, although its
		// result is meant to be accomplished by the minus() method, which also checks
		// its final representation.
	}

	private Poly(List<Term> l) {
		this.terms = new Term[l.size()];
		int i = 0;
		for (Term term : l) {
			// the fact that it's not a copy is part of the advantage of this private
			// constructor.
			this.terms[i] = term;
			i++;
		}
		assert repOk();
	}

	/**
	 * Implementation of the representation invariant. Returns true if the
	 * representation respects all its requirements. Used in assertions.
	 * 
	 * @return true if the representation is ok; false otherwise.
	 */
	public boolean repOk() {
		if (terms == null)
			return false;
		if (terms.length == 0)
			return true;
		int lastExp;
		if (!terms[0].repOk())
			return false;
		lastExp = terms[0].exp;
		for (int i = 1; i < terms.length; i++) {
			if (!terms[i].repOk() || terms[i].exp >= lastExp)
				return false;
			lastExp = terms[i].exp;
		}
		return true;
	}

	/**
	 * Returns the sum of this and q.
	 *
	 * @param q addend (other than this)
	 * @return this+q
	 */
	public Poly add(Poly q) {
		/*
		 * About the implementation: despite the long appearance, the idea is pretty
		 * simple: it's basically a classic merge algorithm. The only differences
		 * between the two are that this one doesn't add terms with the same keys (exp)
		 * and opposite coefficients and that it sums the remaining terms with the same
		 * key.
		 */
		List<Term> listRes = new ArrayList<Term>();
		int indexThis = 0, indexQ = 0;
		while (indexThis < this.terms.length && indexQ < q.terms.length) {
			if (this.terms[indexThis].exp > q.terms[indexQ].exp) {
				listRes.add(new Term(this.terms[indexThis].coeff, this.terms[indexThis].exp));
				indexThis++;
			} else if (this.terms[indexThis].exp == q.terms[indexQ].exp) {
				if (this.terms[indexThis].coeff != -q.terms[indexQ].coeff) {
					listRes.add(new Term(this.terms[indexThis].coeff + q.terms[indexQ].coeff,
							this.terms[indexThis].exp));
				}
				indexQ++;
				indexThis++;

			} else {
				listRes.add(new Term(q.terms[indexQ].coeff, q.terms[indexQ].exp));
				indexQ++;
			}
		}
		if (indexThis < this.terms.length)
			while (indexThis < this.terms.length) {
				listRes.add(new Term(this.terms[indexThis].coeff, this.terms[indexThis].exp));
				indexThis++;
			}
		else
			while (indexQ < q.terms.length) {
				listRes.add(new Term(q.terms[indexQ].coeff, q.terms[indexQ].exp));
				indexQ++;
			}
		Poly res = new Poly(listRes);
		assert res.repOk();
		return res;
	}

	/**
	 * Returns the algebraic sum of this and -q.
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
		if (this.terms.length == 0 || q.terms.length == 0)
			return new Poly();
		List<Term> listRes = new ArrayList<Term>();
		for (int currentDeg = this.terms[0].exp
				+ q.terms[0].exp; currentDeg >= this.terms[this.terms.length - 1].exp
						+ q.terms[q.terms.length - 1].exp; currentDeg--) {
			int currentCoeff = 0;
			for (int i = 0; i <= currentDeg; i++) {
				int coeffPiece = this.coeff(currentDeg - i) * q.coeff(i);
				currentCoeff += coeffPiece;
			}
			if (currentCoeff != 0)
				listRes.add(new Term(currentCoeff, currentDeg));
		}
		Poly res = new Poly(listRes);
		assert res.repOk();
		return res;
	}

	/**
	 * Returns the opposite of this.
	 *
	 * @return -this
	 */
	public Poly minus() {
		Poly res = new Poly(this.terms.length);
		for (int i = 0; i < this.terms.length; i++)
			res.terms[i] = new Term(-terms[i].coeff, terms[i].exp);
		assert res.repOk();
		return res;
	}

	/**
	 * Returns the degree of this.
	 *
	 * @return degree of this
	 */
	public int degree() {
		if (this.terms.length == 0)
			return -1;
		return this.terms[0].exp;
	}

	/**
	 * Returns the coefficient of the term of dth degree.
	 * 
	 * @param d degree
	 * @return coefficient
	 */
	public int coeff(int d) {
		int sx = 0, dx = this.terms.length;
		while (sx < dx) {
			int m = (sx + dx) / 2;
			if (this.terms[m].exp == d)
				return this.terms[m].coeff;
			else if (d < this.terms[m].exp)
				sx = m + 1;
			else
				dx = m;
		}
		return 0;
	}

	/**
	 * Returns a string identifying this polynomial. It uses x as variable.
	 *
	 * @return a string identifying this set.
	 */
	@Override
	public String toString() {
		if (this.terms.length == 0)
			return "0";
		String res = "";
		for (int i = 0; i < this.terms.length; i++) {
			if (this.terms[i].coeff < 0) {
				res += "-";
				if (i != 0)
					res += " ";
				res += -this.terms[i].coeff;
			} else if (i != 0) {
				res += "+ ";
				if (this.terms[i].coeff != 1 || this.terms[i].exp == 0)
					res += this.terms[i].coeff;
			} else if (this.terms[i].exp == 0 || this.terms[i].coeff != 1)
				res += this.terms[i].coeff;
			switch (this.terms[i].exp) {
				case 0:
					break;
				case 1:
					res += "x";
					break;
				default:
					res += "x^" + this.terms[i].exp;
			}
			res += " ";
		}
		return res;
	}
}
