import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		MaxMinIntList l = new MaxMinIntList();
		while (sc.hasNextInt()) {
			l = (MaxMinIntList) l.addEl(sc.nextInt());
		}
		sc.close();
		System.out.println(l);
		System.out.println(l.min());
		System.out.println(l.max());
	}
}
