import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		switch (args[0]) {
			case "M":
				Map<String, Integer> m = new SimpleMap<>();
				String word;
				if (sc.hasNext()) {
					word = sc.next();
					m.put(word, sc.nextInt());
					while (sc.hasNext())
						m.put(sc.next(), sc.nextInt());
					System.out.println(m.get(word));
				}
				break;
			case "Q":
				Queue<Integer> q = new SimpleQueue<>();
				while (sc.hasNextInt())
					q.enqueue(sc.nextInt());
				System.out.println(q);
				break;
			case "S":
				Set<String> s = new SimpleSet<>();
				while (sc.hasNext())
					s.add(sc.next());
				System.out.println(s.size());
				break;
			default:
				System.err.println("Error on input.");
				sc.close();

		}
	}
}
