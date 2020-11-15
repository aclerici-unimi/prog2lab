import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		int nEnq = 0;
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		IntQueue q = new IntQueue(n);
		while (sc.hasNextByte()) {
			byte input = sc.nextByte();
			if (input == +1) {
				if (q.isFull()) {
					break;
				}
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
