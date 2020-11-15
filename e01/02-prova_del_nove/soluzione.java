import java.util.Scanner;

class soluzione {

	/**
	 * Calcola la somma delle cifre della rappresentazione decimale di n, ripetendo
	 * il procedimento fino a ottenere un numero di una cifra. <br>
	 *
	 * REQUIRES: n è non negativo<br>
	 * EFFECTS: restituisce la somma delle cifre decimali di n se essa è minore di
	 * 10, altrimenti ripete il procedimento finché tale condizione non è
	 * soddisfatta.
	 *
	 * @param n intero di cui calcolare la somma delle cifre
	 * @return somma delle cifre di n
	 */
	static int sommaCifre(int n) {
		int res = n % 10;
		while (n >= 10) {
			n /= 10;
			res += n % 10;
		}
		if (res < 10)
			return res;
		return sommaCifre(res);
	}

	/**
	 * Controlla se il candidato prodotto prod di a e b rispetta la prova del nove.
	 * <br>
	 *
	 * EFFECTS: restituisce <code>true</code> se prod rispetta la prova del nove di
	 * a e b, <code>false</code> altrimenti.
	 * 
	 * @param a    primo fattore
	 * @param b    secondo fattore
	 * @param prod candidato prodotto
	 * @return <code>true</code> se prod rispetta la prova del nove di a e b,
	 *         <code>false</code> altrimenti.
	 */
	static boolean provaDelNove(int a, int b, int prod) {
		if (sommaCifre((a) * sommaCifre(b)) == sommaCifre(prod))
			return true;
		return false;
	}

	/**
	 * Prende in System.in un numero naturale n, quindi stampa in System.out tutte e
	 * sole le possibili terne di interi positivi a, b, c, ciascuno minore di n, per
	 * cui a*b è diverso da c ma la terna passa la prova del nove. <br>
	 *
	 * REQUIRES: intero naturale in System.in<br>
	 * EFFECTS: vedi descrizione<br>
	 * MODIFIES: System.in, System.out
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		sc.close();
		for (int a = 1; a < n; a++)
			for (int b = 1; b < n; b++)
				for (int c = 1; c < n; c++) {
					if (a * b != c && provaDelNove(a, b, c))
						System.out.println(a + " " + b + " " + c);
				}
	}
}
