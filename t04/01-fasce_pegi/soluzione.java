import java.util.Scanner;

class soluzione {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int età = s.nextInt();
		if (età <= 6)
			System.out.println("fascia 3");
		else if (età <= 11)
			System.out.println("fascia 7");
		else if (età <= 15)
			System.out.println("fascia 12");
		else if (età <= 17)
			System.out.println("fascia 16");
		else
			System.out.println("fascia 18");
		s.close();
	}
}
