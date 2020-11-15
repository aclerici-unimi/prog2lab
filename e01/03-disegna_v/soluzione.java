import java.util.Scanner;

class soluzione {

	/**
	 * Prints ' ' <code>times</code> times on System.out. If times is non positive,
	 * doesn't print any.<br>
	 *
	 * EFFECTS: see description<br>
	 * MODIFIES: System.out
	 *
	 * @param times number of times to print ' '
	 */
	static void printSpace(int times) {
		for (int s = 0; s < times; s++)
			System.out.print(" ");
	}

	/**
	 * Taken a natural number n from System.in, prints a 'v' made out of *s, of
	 * height n. <br>
	 *
	 * REQUIRES: a natural number in System.in<br>
	 * EFFECTS: see description<br>
	 * MODIFIES: System.in, System.out
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		sc.close();
		int i;
		for (i = 0; i < n - 1; i++) {
			printSpace(i);
			System.out.print("*");
			printSpace(1 + 2 * (n - 2 - i));
			System.out.println("*");
		}
		printSpace(i);
		System.out.println("*");
	}
}

/*
 * note: while the method printSpace lacks of generality, it is private and a
 * generalization would only be pointless.
 */
