import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ListinoTest {
	public static void main(String[] args) {
		Listino l = new ListinoMoltiplicativo();
		List<Giocattolo> giocattoli = new LinkedList<>();
		Scanner sc = new Scanner(System.in);
		int nGiocattoli = Integer.parseInt(args[0]);
		while (sc.hasNext()) {
			Giocattolo g = new Giocattolo(sc.next(), sc.next());
			giocattoli.add(g);
			l.inserisci(g, sc.nextInt());
		}
		sc.close();
		for (Giocattolo g : giocattoli)
			System.out.println(g + " " + l.prezzo(g, nGiocattoli));
	}
}
