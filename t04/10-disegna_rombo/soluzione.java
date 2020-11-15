import java.util.Scanner;

class soluzione {
	static void printChar(char c, int times) {
		for (int s = 0; s < times; s++)
			System.out.print(c);
	}

	static void printLine(int rig) {
		printChar(' ', n - rig);
		printChar('*', 2 * rig + 1);
		System.out.println();
	}

	static int n;

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		n = s.nextInt();
		for (int rig = 0; rig < n + 1; rig++) {
			printLine(rig);
		}
		for (int rig = n - 1; rig >= 0; rig--) {
			printLine(rig);
		}
		s.close();
	}
}
