import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class AcquistoTest {
	public static void main(String[] args) {
		Bancarella b = new Bancarella(args[0]);
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			b.aggiungi(sc.nextInt(), new Giocattolo(sc.next(), sc.next()), sc.nextInt());
		}
		sc.close();
		Iterator<Giocattolo> it = b.iterator();
		if (it.hasNext()) {
			Giocattolo maxG = it.next();
			while (it.hasNext()) {
				Giocattolo g = it.next();
				if (b.quantità(g) > b.quantità(maxG))
					maxG = g;
			}
			Map<Bancarella, Integer> mappa = new TreeMap<>();
			mappa.put(b, b.quantità(maxG));
			System.out.println(new Acquisto(maxG, mappa));
		}
	}
}
