import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

/**
 * Semplice implementazione di {@link Listino} che associa a n
 * {@link Giocattolo} di qualche tipo il costo della singola unità moltiplicato
 * per n.
 */
public class ListinoMoltiplicativo implements Listino {
	private final Map<Giocattolo, Integer> prezzi;

	/** Costruisce un nuovo ListinoMoltiplicativo vuoto. */
	public ListinoMoltiplicativo() {
		prezzi = new TreeMap<>();
	}

	/**
	 * Restituisce un nuovo ListinoMoltiplicativo che associa a per ogni entrata
	 * della mappa {@code giocattoli} alla chiave il suo prezzo. La mappa è
	 * utilizzata direttamente, è responsabilità del chiamante non modificarla.
	 * 
	 * @param giocattoli
	 *                           mappa che associa giocattoli e prezzi.
	 */
	public ListinoMoltiplicativo(Map<Giocattolo, Integer> giocattoli) {
		prezzi = giocattoli;
	}

	@Override
	public void inserisci(Giocattolo g, int prezzo) {
		if (g == null)
			throw new NullPointerException();
		if (prezzo < 0)
			throw new IllegalArgumentException();
		prezzi.put(g, prezzo);
	}

	@Override
	public int prezzo(Giocattolo g, int q) {
		if (!prezzi.containsKey(g))
			throw new NoSuchElementException();
		return prezzi.get(g) * q;
	}

	@Override
	public void rimuovi(Giocattolo g) {
		prezzi.remove(g);
	}

	@Override
	public Iterator<Giocattolo> iterator() {
		return prezzi.keySet().iterator();
	}

}
