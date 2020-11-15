import java.util.Scanner;

class Test {

	/**
	 * Inserts a series of integers in a set, then prints its cardinality.
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		IntSet s = new IntSet();
		while (sc.hasNextInt())
			s.insert(sc.nextInt());
		sc.close();
		System.out.println(s.size());
	}
}
