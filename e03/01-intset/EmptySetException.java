@SuppressWarnings("serial")
public class EmptySetException extends RuntimeException {
	/**
	 * Thrown to indicate that the requested set is empty, thus the operation is
	 * impossible.
	 */

	/**
	 * Constructs an EmptySetException no detail message.
	 */
	public EmptySetException() {
		super();
	}

	/**
	 * Constructs an EmptySetException with the specified detail message.
	 *
	 * @param message the detail message
	 */
	public EmptySetException(String message) {
		super(message);
	}
}
