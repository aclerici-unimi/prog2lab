import java.util.List;

/**
 * Interface implemented by machines which adopt the usual instruction cycle:
 * fetch, decode, execute.
 */
public interface FDEMachine {

	/**
	 * Fetches enough data to contain any type of instruction and its (meta)data.
	 * 
	 * @return raw data.
	 */
	List<Integer> fetch();

	/**
	 * Uses the data previously fetched to construct an instruction, complete of
	 * task and metadata.
	 * 
	 * @param ri
	 */
	Instruction decode(List<Integer> rawText);

	/**
	 * Executes i.
	 * 
	 * @param i the instruction to be executed.
	 */
	void execute(Instruction i);

	/**
	 * Executes an entire instruction cycle (fetch-decode-execute).
	 */
	default void runCycle() {
		execute(decode(fetch()));
	}

}
