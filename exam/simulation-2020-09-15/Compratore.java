import java.util.List;

public abstract class Compratore {
	protected final List<Bancarella> bancarelle;
	protected int portafogli;

	/**
	 * Costruisce un nuovo Compratore che può comprare nella lista di bancarelle
	 * specificata.
	 * 
	 * @param bancarelle
	 *                           lista di bancarelle da cui il compratore può
	 *                           acquistare.
	 */
	public Compratore(List<Bancarella> bancarelle, int denaro) {
		this.bancarelle = bancarelle;
		portafogli = denaro;
	}

	/**
	 * Compra il giocattolo {@code giocattolo} in quantità {@code num} dalle
	 * bancarelle scelte da questo Compratore.
	 * 
	 * @param num
	 *                           quantità di giocattolo.
	 * @param giocattolo
	 *                           tipo di giocattolo.
	 * @return {@link Acquisto} da effettuare.
	 */
	public abstract Acquisto compra(final int num, final Giocattolo giocattolo);

}
