import java.util.Iterator;
import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		ExtendedSortedIntSet s = new ExtendedSortedIntSet();
		Scanner sc = new Scanner(System.in);
		while (sc.hasNextInt()) {
			s.insert(sc.nextInt());
		}
		sc.close();
		int n;
		Iterator<Integer> it = s.reverseElements();
		if (s.size() > 0 && it.hasNext()) { // hasNext is technically useless
			n = it.next();
			while (it.hasNext()) {
				System.out.print(n + ", ");
				n = it.next();
			}
			System.out.println(n);
		}
	}
}
