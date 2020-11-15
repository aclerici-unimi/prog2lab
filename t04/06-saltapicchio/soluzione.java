import java.util.Scanner;

class soluzione {
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		boolean occorre[] = new boolean[n - 1];
		Scanner sc = new Scanner(System.in);
		int prev = sc.nextInt();
		int curr;
		for (int i = 0; i < n - 1; i++) {
			curr = sc.nextInt();
			int diff = Math.abs(curr - prev);
			if (diff >= n)
				break;
			occorre[diff - 1] = true;
			prev = curr;
		}
		sc.close();
		for (boolean val : occorre)
			if (!val)
				return;
		System.out.println("saltapicchio");
	}
}
