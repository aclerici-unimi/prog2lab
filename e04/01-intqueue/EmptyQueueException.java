@SuppressWarnings("serial")
public class EmptyQueueException extends RuntimeException {
	/**
	 * Thrown to indicate that the requested queue is empty, thus the operation is
	 * impossible.
	 */

	/**
	 * Constructs an EmptyQueueException with no detail message.
	 */
	public EmptyQueueException() {
		super();
	}

	/**
	 * Constructs an EmptyQueueException with the specified detail message.
	 *
	 * @param message the detail message
	 */
	public EmptyQueueException(String message) {
		super(message);
	}
}
