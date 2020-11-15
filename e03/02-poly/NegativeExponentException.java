@SuppressWarnings("serial")
public class NegativeExponentException extends RuntimeException {
	/**
	 * Thrown to indicate that the chosen exponent is negative, thus it is
	 * impossible to create the polynomial
	 */

	/**
	 * Constructs an NegativeExponentException no detail message.
	 */
	public NegativeExponentException() {
		super();
	}

	/**
	 * Constructs an NegativeExponentException with the specified detail message.
	 *
	 * @param message the detail message
	 */
	public NegativeExponentException(String message) {
		super(message);
	}
}
