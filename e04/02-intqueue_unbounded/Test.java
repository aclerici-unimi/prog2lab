import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		IntQueue q = new IntQueue();
		int nEnq = 0;
		Scanner sc = new Scanner(System.in);
		while (sc.hasNextByte()) {
			byte input = sc.nextByte();
			if (input == +1) {
				q.enqueue(++nEnq);
			} else {
				if (q.isEmpty())
					break;
				System.out.println(q.dequeue());
			}
		}
		System.out.println(q);
		System.out.println(q.size());
		sc.close();
	}
}
