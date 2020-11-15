import java.util.Scanner;

class soluzione {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		for (int i = 1; i <= n; i++) {
			if (i % 3 == 0 && i % 7 == 0)
				System.out.println("Tico Taco");
			else if (i % 3 == 0)
				System.out.println("Tico");
			else if (i % 7 == 0)
				System.out.println("Taco");
			else
				System.out.println(i);
		}
		sc.close();
	}
}
