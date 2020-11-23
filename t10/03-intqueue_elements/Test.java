import java.util.Iterator;
import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		if (sc.hasNextInt()) {
			int n = sc.nextInt();
			IntQueue q = new IntQueue(n);
			if (sc.hasNextInt())
				q.enqueue(sc.nextInt());
			for (int i = 1; sc.hasNextInt(); i = ++i % 3)
				if (i % 3 == 0)
					q.dequeue();
				else
					q.enqueue(sc.nextInt());
			for (Iterator<Integer> it = q.elements(); it.hasNext();)
				System.out.println(it.next());
			sc.close();
		}
	}
}
