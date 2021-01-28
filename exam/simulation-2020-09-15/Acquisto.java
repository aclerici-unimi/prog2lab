import java.util.Map;

/**
 * Astrazione dell'acquisto di un {@link Giocattolo} in una certa quantità da
 * una o più bancarelle. Gli oggetti acquisto sono immutabili.
 */
public class Acquisto {
	private final Giocattolo gioc;
	private final Map<Bancarella, Integer> quant;

	/**
	 * Costruisce l'Acquisto di un {@link Giocattolo} {@code g} dalle bancarelle
	 * indicate dalla mappa {@code q} nelle quantità indicate rispettivamente. I
	 * valori contenuti nella mappa non sono copiati, quindi è responsabilità del
	 * chiamante non modificarla.
	 * 
	 * @param g
	 *                  tipo di {@link Giocattolo} da acquistare.
	 * @param q
	 *                  mappa che rappresenta gli abbinamenti bancarella - quantità.
	 * @throws IllegalArgumentException
	 *                                          se qualche quantità della mappa non
	 *                                          è positiva.
	 * @throws NullPointerException
	 *                                          se g o q, oppure qualche entrata di
	 *                                          q, sono null.
	 */
	public Acquisto(Giocattolo g, Map<Bancarella, Integer> q) {
		if (g == null || q == null)
			throw new IllegalArgumentException();
		for (Map.Entry<Bancarella, Integer> e : q.entrySet()) {
			if (e.getKey() == null || e.getValue() == null)
				throw new NullPointerException();
			if (e.getValue().intValue() <= 0)
				throw new IllegalArgumentException();
		}
		gioc = g;
		quant = q;
	}

	/**
	 * Effettua l'acquisto.
	 * 
	 * @throws IllegalArgumentException
	 *                                          se non è presente la quantità
	 *                                          richiesta di giocattolo da qualche
	 *                                          bancarella.
	 */
	public void acquista() {
		for (Map.Entry<Bancarella, Integer> e : quant.entrySet())
			e.getKey().rimuovi(e.getValue(), gioc);
	}

	/**
	 * Restituisce il costo totale di questo acquisto.
	 * 
	 * @return costo totale.
	 */
	public int costoTotale() {
		int res = 0;
		for (Map.Entry<Bancarella, Integer> e : quant.entrySet()) {
			res += e.getKey().prezzo(gioc, e.getValue());
		}
		return res;
	}

	/**
	 * Restituisce la quantità totale di giocattoli da acquistare in questo
	 * acquisto.
	 * 
	 * @return quantità totale.
	 */
	public int quantitàTotale() {
		int res = 0;
		for (int q : quant.values()) {
			res += q;
		}
		return res;
	}

	@Override
	public String toString() {
		String res = "Acquisto di: " + gioc + ", per un costo di: " + costoTotale() + ", numero: "
				+ quantitàTotale() + " di cui:\n";
		for (Map.Entry<Bancarella, Integer> e : quant.entrySet()) {
			res += e.getValue() + " da " + e.getKey().proprietario() + "\n";
		}
		return res;
	}

}
