/**
 * Abstract class which implements FDEMachine, composed by a memory, a set of
 * registers, a running state and methods to execute programs.
 */
public abstract class AbstractFDEMachine implements FDEMachine {
	// protected final MainMemory mm;
	// protected final RegisterFile reg;
	protected MainMemory mm;
	protected Memory reg; // register file
	protected boolean isRunning;

	/** Runs until a halting instruction stops the execution. */
	public void run() {
		isRunning = true;
		while (isRunning)
			runCycle();
	}

}
