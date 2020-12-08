/**
 * Abstraction of the concept of instruction, used in machines. An instruction
 * can be executed and its effects are different depending on its type.
 */
public interface Instruction {

	/**
	 * Returns the quantity of memory units used by this.
	 * 
	 * @return the size.
	 */
	int size();

	/**
	 * Executes this.
	 */
	void execute();

}
