import java.util.Scanner;

class soluzione {
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int m = Integer.parseInt(args[1]);
		int matr[][] = new int[m][n];
		Scanner sc = new Scanner(System.in);
		for (int rig = 0; rig < n; rig++)
			for (int col = 0; col < m; col++)
				matr[col][rig] = sc.nextInt();
		for (int rig = 0; rig < m; rig++) {
			for (int col = 0; col < n; col++)
				System.out.print(matr[rig][col] + " ");
			System.out.println();
		}
		sc.close();
	}
}
