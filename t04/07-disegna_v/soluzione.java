import java.util.Scanner;

class soluzione {
	static void printSpace(int times) {
		for (int s = 0; s < times; s++)
			System.out.print(" ");
	}

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
