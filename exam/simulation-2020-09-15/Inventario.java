import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Astrazione di un Inventario di una bancarella di giocattoli.
 * 
 * @absFun AF(contenuto) = elenco di giocattoli corrispondenti alle chiavi di
 *         contenuto, in quantità rispettive corrispondenti ai valori
 *         rispettivi.
 * @repInv contenuto non è null, non ci sono chiavi o valori nulli o valori non
 *         positivi in contenuto.
 */
public class Inventario implements Iterable<Giocattolo> {
	private final Map<Giocattolo, Integer> contenuto;

	/** Costruisce un nuovo Inventario vuoto di oggetti {@link Giocattolo}. */
	public Inventario() {
		contenuto = new TreeMap<>();
		assert repOk();
	}

	/**
	 * Implementazione dell'invariante di rappresentazione. Restituisce {@code true}
	 * se la rappresentazione è corretta, {@code false} altrimenti. Utilizzato nelle
	 * asserzioni.
	 * 
	 * @return {@code true} se la rappresentazione è corretta, {@code false}
	 *         altrimenti.
	 */
	public boolean repOk() {
		return contenuto != null && !contenuto.containsKey(null) && !contenuto.containsValue(null)
				&& contenuto.containsValue(0);
	}

	/**
	 * Aggiunge il {@link Giocattolo} {@code g} a questo Inventario.
	 * 
	 * @param g
	 *                  giocattolo da aggiungere.
	 * @throws NullPointerException
	 *                                      se g è null.
	 */
	public void aggiungi(Giocattolo g) {
		if (g == null)
			throw new NullPointerException();
		contenuto.put(g, contenuto.getOrDefault(g, 0) + 1);
		assert repOk();
	}

	/**
	 * Rimuove il {@link Giocattolo} {@code g} in quantità {@code quant} da questo
	 * Inventario.
	 * 
	 * @param quant
	 *                      quantità di giocattoli {@code g} da rimuovere.
	 * @param g
	 *                      giocattolo da rimuovere.
	 * @throws IllegalArgumentException
	 *                                          se non è presente una quantità
	 *                                          {@code q} di {@code g}.
	 * @throws NullPointerException
	 *                                          se g è null.
	 */
	public void rimuovi(int quant, Giocattolo g) {
		if (g == null)
			throw new NullPointerException();
		if (quantità(g) < quant)
			throw new IllegalArgumentException();
		int nuovaQuantità = contenuto.get(g) - quant;
		if (nuovaQuantità == 0)
			contenuto.remove(g);
		else
			contenuto.put(g, nuovaQuantità);
		contenuto.put(g, contenuto.getOrDefault(g, 0) + 1);
		assert repOk();
	}

	/**
	 * Aggiunge il {@link Giocattolo} {@code g} a questo Inventario, in quantità
	 * {@code q}.
	 * 
	 * @param g
	 *                  giocattolo da aggiungere.
	 * @throws NullPointerException
	 *                                      se g è null.
	 */
	public void aggiungi(Giocattolo g, int q) {
		for (int i = 0; i < q; i++)
			aggiungi(g);
	}

	/**
	 * Restituisce il numero di giocattoli del tipo indicato presenti
	 * nell'inventario.
	 * 
	 * @param g
	 *                  tipo di {@link Giocattolo} richiesto.
	 * @return numero di giocattoli presenti.
	 * @throws NullPointerException
	 *                                      se g è null.
	 */
	public int quantità(Giocattolo g) {
		if (g == null)
			throw new NullPointerException();
		return contenuto.getOrDefault(g, 0);
	}

	@Override
	public Iterator<Giocattolo> iterator() {
		return contenuto.keySet().iterator();
	}

	/**
	 * Restituisce una stringa rappresentativa di questo Inventario. La stringa
	 * indica la quantità e il tipo di ciascun Giocattolo.
	 *
	 * @return stringa rappresentante questo Inventario.
	 */
	@Override
	public String toString() {
		String res = "";
		for (Giocattolo g : contenuto.keySet())
			res += contenuto.get(g) + " " + g;
		return res;
	}

}
