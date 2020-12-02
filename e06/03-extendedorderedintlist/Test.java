import java.util.Iterator;
import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		ExtendedOrderedIntList l = new ExtendedOrderedIntList();
		Scanner sc = new Scanner(System.in);
		while (sc.hasNextInt()) {
			try {
				l.add(sc.nextInt());
			} catch (IllegalArgumentException ignored) {
			}
		}
		sc.close();
		int n;
		Iterator<Integer> it = l.bigToSmall();
		if (l.size() > 0 && it.hasNext()) { // hasNext is technically useless
			n = it.next();
			while (it.hasNext()) {
				System.out.print(n + ", ");
				n = it.next();
			}
			System.out.println(n);
		}
	}
}
