/**
 * Astrazione di un giocattolo vendibile a una bancarella. Un giocattolo ha un
 * nome e un materiale.
 *
 * @absFun AF(nome, materiale) = giocattolo di nome {@code nome} e materiale
 *         {@code materiale}.
 * @repInv nome e materiale non sono null.
 */
public class Giocattolo {
	private final String nome;
	private final String materiale;

	/**
	 * Costruisce un nuovo Giocattolo di nome {@code nome} e materiale
	 * {@code materiale}.
	 * 
	 * @param nome
	 * @param materiale
	 */
	public Giocattolo(String nome, String materiale) {
		if (nome == null || materiale == null)
			throw new IllegalArgumentException(
					"è necessario determinare un nome e un materiale per costruire di un giocattolo");
		this.nome = nome;
		this.materiale = materiale;
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
		return nome != null && materiale != null;
	}

	@Override
	public int hashCode() {
		return 31 * nome.hashCode() + materiale.hashCode();
	}

	/**
	 * Restituisce {@code true} se questo Giocattolo e obj sono uguali. Due
	 * giocattoli sono uguali se hanno stesso nome e materiale.
	 * 
	 * @return {@code true} se questo Giocattolo è uguale a obj.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Giocattolo))
			return false;
		Giocattolo other = (Giocattolo) obj;
		return this.nome.equals(other.nome) && this.materiale.equals(other.materiale);
	}

	/**
	 * Restituisce una stringa rappresentativa di questo Giocattolo. Un Giocattolo
	 * di nome bambola e di materiale pezza ha la stringa rappresentativa "bambola
	 * di pezza".
	 *
	 * @return stringa rappresentante questo Giocattolo.
	 */
	@Override
	public String toString() {
		return nome + " di " + materiale;
	}

}
