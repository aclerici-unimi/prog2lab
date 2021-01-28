import java.util.NoSuchElementException;

/**
 * Astrazione di un listino prezzi per una collezione di oggetti
 * {@link Giocattolo} in vendita in una bancarella.
 */
public interface Listino extends Iterable<Giocattolo> {

	/**
	 * Inserisce il prezzo {@code prezzo} per il giocattolo {@code g} in questo
	 * listino. Se il giocattolo è già presente, aggiorna il suo prezzo a
	 * {@code prezzo}.
	 * 
	 * @param g
	 *                       giocattolo.
	 * @param prezzo
	 *                       prezzo.
	 *
	 * @throws NullPointerException
	 *                                          se g è null.
	 * @throws IllegalArgumentException
	 *                                          se prezzo è negativo.
	 */
	public void inserisci(Giocattolo g, int prezzo);

	/**
	 * Rimuove il giocattolo {@code g} da questo Listino, se presente. Se il
	 * giocattolo non è presente l'operazione non ha effetto.
	 * 
	 * @param g
	 *                  giocattolo da rimuovere.
	 */
	public void rimuovi(Giocattolo g);

	/**
	 * Restituisce il prezzo complessivo di una quantità {@code q} di
	 * {@link Giocattolo} {@code g}.
	 * 
	 * @param g
	 *                  tipo di {@link Giocattolo}.
	 * @param q
	 *                  quantità da acquistare.
	 *
	 * @throws NoSuchElementException
	 *                                        se il giocattolo non è presente nel
	 *                                        listino.
	 */
	public int prezzo(Giocattolo g, int q);

	/**
	 * Restituisce il prezzo di una singola unità del {@link Giocattolo}
	 * specificato.
	 * 
	 * @param g
	 *                  tipo di {@link Giocattolo}.
	 * @return prezzo di {@code g}.
	 */
	default public int prezzo(Giocattolo g) {
		return prezzo(g, 1);
	}

}
