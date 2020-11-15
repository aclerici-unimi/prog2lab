import java.util.Scanner;

class soluzione {
	static int sommaCifre(int n) {
		int res = n % 10;
		while (n >= 10) {
			n /= 10;
			res += n % 10;
		}
		if (res < 10)
			return res;
		return sommaCifre(res);
	}

	static boolean provaDelNove(int a, int b, int prod) {
		if (sommaCifre((a) * sommaCifre(b)) == sommaCifre(prod))
			return true;
		return false;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		for (int a = 1; a < n; a++)
			for (int b = 1; b < n; b++)
				for (int c = 1; c < n; c++) {
					if (a * b != c && provaDelNove(a, b, c))
						System.out.println(a + " " + b + " " + c);
				}
		sc.close();
	}
}
