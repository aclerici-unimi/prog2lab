import java.util.Scanner;
import java.util.Arrays;

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
	 */
	static int reverseDecimal(int n) {
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
	 */
	static int countDecimalDigits(int n) {
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
	 */
	static byte[] baseTenDigitify(int n) {
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
	 * @param digits array of decimal digits (values between 0 and 9)
	 * @return number composed by the digits in digits
	 */
	static int baseTenUnDigitify(byte[] digits) {
		int res = 0;
		for (int i = 0; i < digits.length; i++) {
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
	 */
	static int sortDecimalDigits(int n) {
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
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		sc.close();
		while (n != 6174) {
			System.out.println(n);
			n = nextKaprekar(n);
		}
		System.out.println(6174);
	}
}
