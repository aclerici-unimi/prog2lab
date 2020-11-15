import java.util.Scanner;

class soluzione {
	public static int elemento(int riga, int colonna) {
		if (colonna == 1 || riga == colonna)
			return 1;
		return elemento(riga - 1, colonna - 1) + elemento(riga - 1, colonna);
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		for (int i = 1; i <= n; i++)
			System.out.print(elemento(n, i) + " ");
		sc.close();
	}
}
