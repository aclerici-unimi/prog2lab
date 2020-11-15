import java.util.Scanner;
import java.util.Arrays;

// note: given the discussion had with the professors regarding the usage of javadoc+Liskov's specifications, this file still uses my old notation (since it is an enhanced version of a previous exercise), while the next ones (e03+) will be upgraded.
class soluzione {

	/**
	 * Returns the number obtained by reversing the order of the digits in the
	 * decimal representation of n. <br>
	 *
	 * REQUIRES: n is non negative<br>
	 * EFFECTS: see description
	 *
	 * @param n the number to return the reverse of
	 * @return the decimal reverse of n
	 * @throws IllegalArgumentException if n<0
	 */
	static int reverseDecimal(int n) {
		if (n < 0)
			throw new IllegalArgumentException("Input " + n + " is not non-negative");
		int res = 0;
		while (n > 0) {
			res += n % 10;
			n /= 10;
			res *= 10;
		}
		return res / 10;
	}

	/**
	 * Returns the number of decimal digits of n. <br>
	 *
	 * REQUIRES: n is non negative<br>
	 * EFFECTS: see description
	 *
	 * @param n number to calculate the number of decimal digits of
	 * @return number of decimal digits of n
	 * @throws IllegalArgumentException if n<0
	 */
	static int countDecimalDigits(int n) {
		if (n < 0)
			throw new IllegalArgumentException("Input " + n + " is not non-negative");
		if (n == 0)
			return 1;
		int res = 0;
		while (n > 0) {
			n /= 10;
			res++;
		}
		return res;
	}

	/**
	 * Returns an array containing the separated decimal digits of n in
	 * representation order. <br>
	 *
	 * REQUIRES: n is non negative<br>
	 * EFFECTS: see description
	 *
	 * @param n number to decompose in decimal digits
	 * @return array of decimal digits of n
	 * @throws IllegalArgumentException if n<0
	 */
	static byte[] baseTenDigitify(int n) {
		if (n < 0)
			throw new IllegalArgumentException("Input " + n + " is not non-negative");
		int digitNumber = countDecimalDigits(n);
		byte[] res = new byte[digitNumber];
		for (int i = 0; i < digitNumber; i++) {
			res[i] = (byte) (n % 10);
			n /= 10;
		}
		return res;
	}

	/**
	 * Returns the number composed by the decimal digits contained in the array
	 * digits. <br>
	 *
	 * REQUIRES: digits contains decimal digits (values between 0 and 9)<br>
	 * EFFECTS: see description
	 *
	 * @param digits array of decimal digits
	 * @return number composed by the digits in digits
	 * @throws IllegalArgumentException if an element of digits is not a single
	 *                                  decimal digit number
	 * @throws NullPointerException     if receives an empty array
	 */
	static int baseTenUnDigitify(byte[] digits) {
		if (digits == null)
			throw new NullPointerException();
		int res = 0;
		for (int i = 0; i < digits.length; i++) {
			if (digits[i] >= 10 || digits[i] < 0)
				throw new IllegalArgumentException("The element " + digits[i] + " at index " + i
						+ " is not between 0 and 9");
			res += digits[i];
			res *= 10;
		}
		return res / 10;
	}

	/**
	 * Reverses the order of the elements of the array a. <br>
	 *
	 * EFFECTS: see description<br>
	 * MODIFIES: a
	 *
	 * @param a array to reverse the elements of
	 */
	static void reverseArrayOrder(byte[] a) {
		for (int i = 0; i < a.length / 2; i++) {
			byte tmp = a[i];
			a[i] = a[a.length - i - 1];
			a[a.length - i - 1] = tmp;
		}
	}

	/**
	 * Returns the number obtained by sorting n's decimal digits in descending
	 * order. <br>
	 *
	 * REQUIRES: n is non negative<br>
	 * EFFECTS: see description<br>
	 *
	 * @param n number to sort the digits of
	 * @return number obtained by sorting the digits of n
	 * @throws IllegalArgumentException if n<0
	 */
	static int sortDecimalDigits(int n) {
		if (n < 0)
			throw new IllegalArgumentException("Input " + n + " is not non-negative");
		byte[] digits = baseTenDigitify(n);
		Arrays.sort(digits);
		reverseArrayOrder(digits);
		return baseTenUnDigitify(digits);
	}

	/**
	 * Returns the Kaprekar transformation of n. <br>
	 *
	 * REQUIRES: n is a positive number of four decimal digits<br>
	 * EFFECTS: see description
	 *
	 * @param n number to calculate the Kaprekar transformation of
	 * @return Kaprekar transformation of n
	 */
	static int nextKaprekar(int n) {
		if (n < 1000 || n > 9999)
			throw new IllegalArgumentException(n + " is not a four decimal digits number");
		int descending = sortDecimalDigits(n);
		return descending - reverseDecimal(descending);
	}

	/**
	 * Taken a positive number of four decimal digits from System.in, prints to
	 * System.out the sequence obtained applying the Kaprekar transformation, until
	 * getting the Kaprekar constant. <br>
	 *
	 * REQUIRES: a positive number of four decimal digits in System.in<br>
	 * EFFECTS: see description<br>
	 * MODIFIES: System.in, System.out
	 *
	 * @throws IllegalStateException if integer is missing in System.in
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		if (!sc.hasNextInt()) {
			sc.close();
			throw new IllegalStateException("Standard input is missing an integer");
		}
		int n = sc.nextInt();
		sc.close();
		while (n != 6174) {
			System.out.println(n);
			n = nextKaprekar(n);
		}
		System.out.println(6174);
	}
}
