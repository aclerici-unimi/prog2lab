import java.util.Scanner;

class soluzione {

	/**
	 * Prints a sequence of the character c repeated n times on System.out. If n is
	 * non positive, doesn't print anything.<br>
	 *
	 * REQUIRES: c is a printable character (technically it works for every
	 * character, but the result might not be seeable)<br>
	 * EFFECTS: see description<br>
	 * MODIFIES: System.out
	 *
	 * @param c character to print
	 * @param n times to print the character
	 */
	static void printCharSequence(char c, int n) {
		for (int i = 0; i < n; i++)
			System.out.print(c);
	}

	/**
	 * Prints on System.out sequences of len seqLen, seqNum made out of even and
	 * seqNum of odd, alternating the two, starting with the even ones. <br>
	 *
	 * REQUIRES: even and odd are printable characters (technically it works for
	 * every character, but the result might not be seeable)<br>
	 * EFFECTS: see description<br>
	 * MODIFIES: System.out
	 *
	 * @param even   character the squares of even index are made of
	 * @param odd    character the squares of odd index are made of
	 * @param seqLen len of the single sequence
	 * @param seqNum number of sequences of each type
	 */
	static void printAlternateSequences(char even, char odd, int seqLen, int seqNum) {
		for (int seq = 0; seq < seqNum * 2; seq++)
			printCharSequence(seq % 2 == 0 ? even : odd, seqLen);
	}

	/**
	 * Prints on System.out squares of side sqDim, sqNum made out of even and sqNum
	 * of odd, alternating the two, starting with the even ones. If sqDim or sqNum
	 * are non positive, doesn't print any square.<br>
	 *
	 * REQUIRES: even and odd are printable characters (technically it works for
	 * every character, but the result might not be seeable)<br>
	 * EFFECTS: see description<br>
	 * MODIFIES: System.out
	 *
	 * @param even  character the squares of even index are made of
	 * @param odd   character the squares of odd index are made of
	 * @param sqDim single square side
	 * @param sqNum number of squares of each type
	 */
	static void printAlternateSquares(char even, char odd, int sqDim, int sqNum) {
		for (int line = 0; line < sqDim; line++) {
			printAlternateSequences(even, odd, sqDim, sqNum);
			System.out.println();
		}
	}

	/**
	 * Taken a natural number n from System.in, prints on System.out a chessboard in
	 * which each square has a side of n. <br>
	 *
	 * REQUIRES: a natural number in System.in<br>
	 * EFFECTS: see description<br>
	 * MODIFIES: System.in, System.out
	 */
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int n = s.nextInt();
		for (int bigRowCouple = 0; bigRowCouple < 4; bigRowCouple++) {
			printAlternateSquares('-', '#', n, 4);
			printAlternateSquares('#', '-', n, 4);
		}
		s.close();
	}
}
