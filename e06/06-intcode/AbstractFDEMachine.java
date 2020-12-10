/**
 * Canonical {@link FDEMachine}, composed by a memory, a set of registers, a
 * running state and methods to execute programs.
 */
public abstract class AbstractFDEMachine implements FDEMachine {
	protected final MainMemory mm;
	protected final Memory reg; // register file
	protected boolean isRunning;

	public AbstractFDEMachine(MainMemory main, Memory rFile) {
		if (main == null || rFile == null)
			throw new NullPointerException("can't create a machine with null memories");
		mm = main;
		reg = rFile;
	}

	/** Runs until a halting instruction stops the execution. */
	public void run() {
		isRunning = true;
		while (isRunning)
			runCycle();
	}

	@Override
	public String toString() {
		return "IntcodeVM :\n\t" + mm.toString() + "\n\tRegisters : " + reg;
	}

}
