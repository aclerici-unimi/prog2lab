@SuppressWarnings("serial")
public class FullQueueException extends RuntimeException {
	/**
	 * Thrown to indicate that the requested queue is full, thus the operation is
	 * impossible.
	 */

	/**
	 * Constructs an FullQueueException with no detail message.
	 */
	public FullQueueException() {
		super();
	}

	/**
	 * Constructs an FullQueueException with the specified detail message.
	 *
	 * @param message the detail message
	 */
	public FullQueueException(String message) {
		super(message);
	}
}
