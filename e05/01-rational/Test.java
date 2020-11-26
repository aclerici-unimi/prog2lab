import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;

public class Test {
	static String nerfedToString(Rational r) {
		return r.num() + "/" + r.den();
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ArrayList<Rational> numbers = new ArrayList<Rational>();

		Rational r = new Rational(sc.nextInt(), sc.nextInt());
		Rational sum = r, product = r, quotient = r;
		numbers.add(r);
		while (sc.hasNextInt()) {
			r = new Rational(sc.nextInt(), sc.nextInt());
			numbers.add(r);
			sum = sum.add(r);
			product = product.mul(r);
			quotient = quotient.div(r);
		}
		sc.close();

		int nEquals = 0;
		ListIterator<Rational> it1 = numbers.listIterator();
		while (it1.hasNext()) {
			r = it1.next();
			ListIterator<Rational> it2 = numbers.listIterator(it1.nextIndex());
			while (it2.hasNext()) {
				if (it2.next().equals(r)) {
					nEquals++;
					break;
				}
			}
		}
		System.out.println(nerfedToString(sum.reduce()));
		System.out.println(nerfedToString(product.reduce()));
		System.out.println(nerfedToString(quotient.reduce()));
		System.out.println(nEquals);
	}
}
