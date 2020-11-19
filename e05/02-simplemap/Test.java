import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		SimpleMap m = new SimpleMap();
		while (sc.hasNext()) {
			// System.out.println(m);
			if (sc.next().equals("+")) {
				m.put(sc.next(), sc.nextInt());
			} else {
				String key = sc.next();
				if (m.isIn(key)) {
					System.out.println(m.get(key));
					m.remove(key);
				}
			}
		}
		System.out.println(m.size());
		sc.close();
	}
}
