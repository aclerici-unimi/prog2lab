public interface Equiparator<E> {

	/**
	 * Returns true if {@code a} and {@code b} are equivalent in a chosen relation.
	 * 
	 * @param a first operand.
	 * @param b second operand.
	 * @return true if {@code a} and {@code b} are equivalent, false otherwise.
	 */
	boolean equiparate(E a, E b);

}
