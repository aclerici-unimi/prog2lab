import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		IntSet s;
		if (args[0].equals("S")) {
			s = new SmallIntSet();
		} else {
			s = new LargeIntSet();
		}
		Scanner sc = new Scanner(System.in);
		while (sc.hasNextInt())
			s.insert(sc.nextInt());
		sc.close();
		System.out.println(s.size());
	}
}
