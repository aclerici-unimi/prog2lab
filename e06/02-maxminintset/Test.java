import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		MaxMinIntSet l = new MaxMinIntSet();
		while (sc.hasNextInt()) {
			l.insert(sc.nextInt());
		}
		sc.close();
		System.out.println(l.size());
		System.out.println(l.min());
		System.out.println(l.max());
	}
}
