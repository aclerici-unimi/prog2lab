import java.util.Iterator;
import java.util.Scanner;

public class Test {
	static void iterateAndPrint(Iterator<Integer> it, int n) {
	}

	public static void main(String[] args) {
		OrderedIntList l = new OrderedIntList();
		Scanner sc = new Scanner(System.in);
		while (sc.hasNextInt()) {
			l.add(sc.nextInt());
		}
		sc.close();
		int n;
		Iterator<Integer> it = l.smallToBig();
		if (l.size() > 0 && it.hasNext()) { // hasNext is technically useless
			n = it.next();
			while (it.hasNext()) {
				System.out.print(n + ", ");
				n = it.next();
			}
			System.out.println(n);
		}
		it = l.bigToSmall();
		if (l.size() > 0 && it.hasNext()) { // hasNext is technically useless
			n = it.next();
			while (it.hasNext()) {
				System.out.print(n + ", ");
				n = it.next();
			}
			System.out.println(n);
		}
		// testing toString
		System.out.println(l);
	}
}
