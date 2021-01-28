import java.util.Scanner;

public class BancarellaTest {
	public static void main(String[] args) {
		Bancarella b = new Bancarella(args[0]);
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			b.aggiungi(sc.nextInt(), new Giocattolo(sc.next(), sc.next()), sc.nextInt());
		}
		sc.close();
		System.out.println(b);
	}
}
