import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		SimpleMap m = new SimpleMap();
		String asd;
		// while (sc.hasNextLine()) {
		while (sc.hasNext()) {
			asd = sc.next();
			System.out.println(asd);
			if (sc.next() == "+") {
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
