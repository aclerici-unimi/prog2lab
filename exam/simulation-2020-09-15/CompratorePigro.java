import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Implementazione di un compratore di giocattoli "pigro". Un CompratorePigro
 * compra il giocattolo desiderato dalla prima bancarella che gli capita, nella
 * quantità che si avvicini maggiormente a quella voluta, andando eventualmente
 * alla prossima bancarella se necessario.
 */
public class CompratorePigro extends Compratore {

	/**
	 * Costruisce un nuovo CompratorePigro che può comprare nella lista di
	 * bancarelle specificata.
	 * 
	 * @param bancarelle
	 *                           lista di bancarelle da cui il compratore può
	 *                           acquistare.
	 */
	public CompratorePigro(List<Bancarella> bancarelle, int denaro) {
		super(bancarelle, denaro);
	}

	@Override
	public Acquisto compra(int num, Giocattolo giocattolo) {
		Map<Bancarella, Integer> acq = new TreeMap<>();
		int nuovoPortafogli = portafogli;
		for (Bancarella b : bancarelle) {
			int quantitàDisponibile = b.quantità(giocattolo);
			if (quantitàDisponibile >= num) {
				nuovoPortafogli -= b.prezzo(giocattolo, num);
				acq.put(b, num);
				num = 0;
				break;
			} else if (quantitàDisponibile != 0) {
				nuovoPortafogli -= b.prezzo(giocattolo, quantitàDisponibile);
				num -= quantitàDisponibile;
				acq.put(b, quantitàDisponibile);
			}
		}
		if (num > 0 || nuovoPortafogli < 0)
			throw new AcquistoImpossibileException();
		return new Acquisto(giocattolo, acq);
	}

}
