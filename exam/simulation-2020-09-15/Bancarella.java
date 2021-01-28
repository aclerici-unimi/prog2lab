import java.util.Iterator;

/**
 * Astrazione di una bancarella di giocattoli.
 * 
 * @absFun AF(proprietario, inv, list) = bancarella di proprietario, di
 *         inventario AF(inv) e listino AF(list).
 * @repInv proprietario, inv e list non sono null, inve list contengono gli
 *         stessi oggetti.
 */
public class Bancarella implements Iterable<Giocattolo> {
	private final String proprietario;
	private final Inventario inv;
	private final Listino list;

	/**
	 * Costruisce la bancarella del proprietario {@code prop}.
	 * 
	 * @param prop
	 *                     proprietario.
	 * @throws NullPointerException
	 *                                      se prop è null.
	 */
	public Bancarella(String prop) {
		if (prop == null)
			throw new NullPointerException();
		proprietario = prop;
		inv = new Inventario();
		list = new ListinoMoltiplicativo();
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
		if (proprietario == null || inv == null || list == null)
			return false;

		int nInv = 0, nList = 0;
		Iterator<Giocattolo> it = inv.iterator();
		while (it.hasNext()) {
			it.next();
			nInv++;
		}
		it = list.iterator();
		while (it.hasNext()) {
			it.next();
			nList++;
		}
		if (nInv != nList)
			return false;
		for (Giocattolo gInv : inv) {
			boolean presente = false;
			for (Giocattolo gList : list)
				if (gInv.equals(gList))
					presente = true;
			if (!presente)
				return false;
		}
		return true;
	}

	/**
	 * Aggiunge il {@link Giocattolo} {@code g} all'inventario di questa bancarella,
	 * con prezzo di listino {@code p} e in quantità {@code q}. Se il giocattolo è
	 * già presente, ne aggiunge una quantità {@code q} e aggiorna il relativo
	 * prezzo a {@code p}.
	 * 
	 * @param g
	 *                  giocattolo da aggiungere.
	 * @param p
	 *                  prezzo.
	 * @param q
	 *                  quantità.
	 * @throws NullPointerException
	 *                                      se g è null.
	 */
	public void aggiungi(int q, Giocattolo g, int p) {
		inv.aggiungi(g, p);
		list.inserisci(g, p);
	}

	/**
	 * Rimuove {@code q} unità del Giocattolo {@code g} da questa Bancarella.
	 * 
	 * @param q
	 *                  quantità.
	 * @param g
	 *                  giocattolo da rimuovere.
	 * @throws IllegalArgumentException
	 *                                          se non è presente una quantità
	 *                                          {@code q} di {@code g}.
	 * @throws NullPointerException
	 *                                          se g è null.
	 */
	public void rimuovi(int q, Giocattolo g) {
		inv.rimuovi(q, g);
		if (quantità(g) == 0)
			list.rimuovi(g);
	}

	/**
	 * Restituisce il prezzo complessivo di una quantità {@code q} di
	 * {@link Giocattolo} {@code g} presso questa bancarella.
	 * 
	 * @param g
	 *                  tipo di {@link Giocattolo}.
	 * @param q
	 *                  quantità da acquistare.
	 *
	 * @throws NoSuchElementException
	 *                                        se il giocattolo non è presente nel
	 *                                        listino di questa bancarella.
	 */
	public int prezzo(Giocattolo g, int q) {
		return list.prezzo(g, q);
	}

	/**
	 * Restituisce il nome del proprietario di questa Bancarella.
	 * 
	 * @return proprietario.
	 */
	public String proprietario() {
		return proprietario;
	}

	/**
	 * Restituisce il numero di giocattoli del tipo indicato presenti
	 * nell'inventario di questa bancarella.
	 * 
	 * @param g
	 *                  tipo di {@link Giocattolo} richiesto.
	 * @return numero di giocattoli presenti.
	 * @throws NullPointerException
	 *                                      se g è null.
	 */
	public int quantità(Giocattolo g) {
		return inv.quantità(g);
	}

	/**
	 * Restituisce un Iterator che itera sui giocattoli presenti nell'inventario di
	 * questa bancarella, uno per tipo.
	 * 
	 * @return iteratore sui giocattoli di questa bancarella.
	 */
	@Override
	public Iterator<Giocattolo> iterator() {
		return inv.iterator();
	}

	@Override
	public String toString() {
		String res = "Bancarella di: " + proprietario + "\n";
		for (Giocattolo g : inv) {
			res += "num. " + inv.quantità(g) + " " + g + ", prezzo: " + list.prezzo(g);
		}
		return res;
	}

}
