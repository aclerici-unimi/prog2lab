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
	 * Prints on System.out a sequence of the character c of length n, after a space
	 * padding of padding. If len is non positive, doesn't print the sequence; if
	 * padding is not positive, doesn't print any padding.<br>
	 *
	 * REQUIRES: c is a printable character (technically it works for every
	 * character, but the result might not be seeable)<br>
	 * EFFECTS: see description<br>
	 * MODIFIES: System.out
	 *
	 * @param c       character to print
	 * @param padding length of the space padding before the sequence
	 * @param len     length of the sequence
	 */
	static void printPaddedSequence(char c, int padding, int len) {
		printCharSequence(' ', padding);
		printCharSequence(c, len);
	}

	/**
	 * Prints on System.out an equilateral triangle of height height, made of c
	 * characters, with a left space padding of padding and pointing up if up, else
	 * poiting down. If height is non positive, doesn't print anything.<br>
	 *
	 * REQUIRES: padding is non negative; c is a printable character (technically it
	 * works for every character, but the result might not be seeable)<br>
	 * EFFECTS: see description<br>
	 * MODIFIES: System.out
	 *
	 * @param up      whether or not the triangle has to be pointing up
	 * @param padding left space padding of the whole triangle
	 * @param c       character the triangle is made of
	 * @param height  triangle height, corresponding to System.out lines
	 */
	static void printPaddedTriangle(boolean up, int padding, char c, int height) {
		if (up) {
			for (int row = 0; row < height; row++) {
				printPaddedSequence('*', height - row - 1 + padding, 2 * row + 1);
				System.out.println();
			}
		} else {
			for (int row = height - 1; row >= 0; row--) {
				printPaddedSequence('*', height - row - 1 + padding, 2 * row + 1);
				System.out.println();
			}
		}
	}

	/**
	 * Taken an integer number n from System.in, prints a rhombus made out of *s, of
	 * diagonals n+1. If n is negative, doesn't print anything.<br>
	 *
	 * REQUIRES: a number in System.in<br>
	 * EFFECTS: see description<br>
	 * MODIFIES: System.in, System.out
	 */
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int n = s.nextInt();
		printPaddedTriangle(true, 0, '*', n + 1);
		printPaddedTriangle(false, 1, '*', n);
		s.close();
	}
}
