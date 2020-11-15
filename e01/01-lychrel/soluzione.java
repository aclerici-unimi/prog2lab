import java.util.Scanner;

class soluzione {

	/**
	 * Verifies if the given string is palindrome (including all characters). If the
	 * string is empty it is considered palindrome (hence the returned value will be
	 * true).<br>
	 *
	 * EFFECTS: returns <code>true</code> if the s is palindrome, <code>false</code>
	 * otherwise.
	 *
	 * @param s the string to verify whether or not it's palindrome
	 * @return true if the s is palindrome, false otherwise
	 */
	static boolean isPalindrome(String s) {
		int len = s.length();
		for (int i = 0; i < len; i++)
			if (s.charAt(i) != s.charAt(len - 1 - i))
				return false;
		return true;

	}

	/**
	 * Returns the number obtained by reversing the order of the digits in the
	 * decimal representation of n. <br>
	 *
	 * REQUIRES: n is non negative<br>
	 * EFFECTS: see description
	 *
	 * @param n the number to return the reverse of
	 * @return the decimal reverse of n
	 */
	static long reverseDecimal(long n) {
		long res = 0;
		while (n > 0) {
			res += n % 10;
			n /= 10;
			res *= 10;
		}
		return res / 10;
	}

	/**
	 * Returns the next Lychrel transformation of n. <br>
	 *
	 * REQUIRES: n is non negative<br>
	 * EFFECTS: see description
	 *
	 * @param n the number to return the Lychrel transformation of
	 * @return the Lychrel transformation of n
	 */
	static long lychrelTransform(long n) {
		return n + reverseDecimal(n);
	}

	/**
	 * Taken a natural number n from System.in, prints to System.out the lychrel
	 * sequence of n. The procedure continues until a palindrome number is
	 * encountered. The potential long overflow is not controlled.<br>
	 *
	 * REQUIRES: a natural number n, not exceeding the limits of long, in
	 * System.in<br>
	 * EFFECTS: see description<br>
	 * MODIFIES: System.in, System.out
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		long n = sc.nextInt();
		sc.close();
		while (!isPalindrome(Long.toString(n))) {
			System.out.println(n);
			n = lychrelTransform(n);
		}
		System.out.println(n);
	}
}
