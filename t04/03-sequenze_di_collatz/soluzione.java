import java.util.Scanner;

class soluzione {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		System.out.print(n);
		int len = 1;
		while (n != 1) {
			if (n % 2 == 0) {
				n /= 2;
			} else
				n = n * 3 + 1;
			System.out.print(" " + n);
			len++;
		}
		System.out.println(" " + len);
		sc.close();
	}
}
