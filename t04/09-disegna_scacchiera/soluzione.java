import java.util.Scanner;

class soluzione {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int n = s.nextInt();
		boolean canc = false;
		for (int rig = 0; rig < 8 * n; rig++) {
			if (rig % n == 0)
				canc = !canc;
			for (int col = 0; col < 8 * n; col++) {
				if (col % n == 0)
					canc = !canc;
				System.out.print(canc ? "#" : "-");
			}
			System.out.println();
		}
		s.close();
	}
}
