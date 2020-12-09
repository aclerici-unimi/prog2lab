import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class IntCodeVM extends AbstractFDEMachine {
	static final int IP = 0;
	static final int RBP = 1;

	/**
	 * Constructs an IntCode VM ready to execute the program {@code program}.
	 * 
	 * @param program
	 */
	public IntCodeVM(List<Integer> program) {
		mm = new IntCodeMainMemory(program);
		reg = new BasicMemory();
	}

	@Override
	public List<Integer> fetch() {
		int IPval = reg.get(IP);
		return mm.blobGet(IPval, IPval + 4);
	}

	@Override
	public Instruction decode(List<Integer> rawText) {
		Instruction res = instructionFrom(rawText);
		reg.set(IP, reg.get(IP) + res.size());
		return res;
	}

	@Override
	public void execute(Instruction i) {
		i.execute();
	}

	/** Runs until a halting instruction stops the execution. */
	void debugRun() {
		isRunning = true;
		while (isRunning) {
			System.out.println(this);
			List<Integer> rawInst = fetch();
			System.out.println("Raw:\t\t" + listString(rawInst));
			Instruction inst = decode(rawInst);
			System.out.println("Decoded:\t" + inst);
			execute(inst);
			System.out.println("");
		}
		System.out.println("Halted.");
	}

	/**
	 * Translates raw text into properly instantiated IntCode {@link Instruction}s.
	 * 
	 * @param rawText raw data in input.
	 * @return the instruction ready to execute.
	 */
	private Instruction instructionFrom(List<Integer> rawText) {
		int rawOP = rawText.remove(0);
		switch (rawOP % 100) {
			case 1:
				return new Add(rawOP / 100, rawText);
			case 2:
				return new Mul(rawOP / 100, rawText);
			case 3:
				return new Input(rawOP / 100, rawText);
			case 4:
				return new Output(rawOP / 100, rawText);
			case 5:
				return new BranchOnNotZero(rawOP / 100, rawText);
			case 6:
				return new BranchOnZero(rawOP / 100, rawText);
			case 7:
				return new SetOnLessThen(rawOP / 100, rawText);
			case 8:
				return new SetOnEquals(rawOP / 100, rawText);
			case 9:
				return new UpdateRBP(rawOP / 100, rawText);
			case 99:
				return new Halt();
			default:
				throw new IllegalArgumentException("Invalid instruction of OP code " + rawOP % 100);
		}
	}

	@Override
	public String toString() {
		String res = "IntcodeVM :\n\t" + mm.toString() + "\n\tRegisters : [IP = " + reg.get(IP) + ", RBP = "
				+ reg.get(RBP) + "]";
		return res;
	}

	public abstract class ParameterInstruction implements Instruction {
		// parameters
		protected List<Integer> pars;

		// rules for data access
		protected List<Integer> pModes;

		// number of memory units the raw data occupied
		protected final int sz;

		/**
		 * Constructs a new parameter based instruction.
		 * 
		 * @param nPars       number of parameters.
		 * @param rawParModes raw code containing the data access mode for each
		 *                    parameter.
		 * @param parameters  list of parameters.
		 * @param writeOnLast true if the last parameters is for writing (thus direct
		 *                    addressing), false otherwise.
		 */
		protected ParameterInstruction(int nPars, int rawParModes, List<Integer> parameters,
				boolean writeOnLast) {
			pModes = new ArrayList<>();
			if (writeOnLast) {
				for (int i = 0; i < nPars - 1; rawParModes /= 10, i++) {
					pModes.add(rawParModes % 10);
				}
				pModes.add(1);
			} else
				for (int i = 0; i < nPars; rawParModes /= 10, i++) {
					pModes.add(rawParModes % 10);
				}
			pars = parameters.subList(0, nPars);
			sz = nPars + 1;
		}

		protected List<Integer> getActualArguments() {
			Iterator<Integer> it = pModes.iterator();
			List<Integer> res = new LinkedList<Integer>();
			int parMode;
			for (int par : pars) {
				parMode = it.next();
				switch (parMode) {
					case 0:
						res.add(mm.get(par));
						break;
					case 1:
						res.add(par);
						break;
					case 2:
						res.add(mm.get(reg.get(RBP) + par));
						break;
					default:
						throw new IllegalArgumentException(
								"Invalid access mode for par " + par + ": " + parMode);
				}
			}
			return res;
		}

		@Override
		public int size() {
			return sz;
		}

		@Override
		public String toString() {
			return getClass().getSimpleName() + "; parameters: " + listString(pars) + "; parameter modes: "
					+ listString(pModes);
		}

	}

	private static String listString(List<Integer> l) {
		String res = "[";
		if (l.size() > 0) {
			Iterator<Integer> it = l.iterator();
			int el = it.next();
			while (it.hasNext()) {
				res += el + ", ";
				el = it.next();
			}
			res += el;
		}
		return res + "]";
	}

	public class Add extends ParameterInstruction {

		public Add(int rawParCodes, List<Integer> parameters) {
			super(3, rawParCodes, parameters, true);
		}

		@Override
		public void execute() {
			List<Integer> args = getActualArguments();
			int res = args.remove(0) + args.remove(0);
			mm.set(args.remove(0), res);
		}

	}

	public class Mul extends ParameterInstruction {

		public Mul(int rawParCodes, List<Integer> parameters) {
			super(3, rawParCodes, parameters, true);
		}

		@Override
		public void execute() {
			List<Integer> args = getActualArguments();
			int res = args.remove(0) * args.remove(0);
			mm.set(args.remove(0), res);
		}

	}

	public class Input extends ParameterInstruction {

		public Input(int rawParCodes, List<Integer> parameters) {
			super(1, rawParCodes, parameters, true);
		}

		@Override
		public void execute() {
			List<Integer> args = getActualArguments();
			Scanner sc = new Scanner(System.in);
			if (sc.hasNextInt())
				mm.set(args.remove(0), sc.nextInt());
			else {
				sc.close();
				throw new IllegalStateException("Can't parse integer from standard input.");
			}
			sc.close();
		}

	}

	public class Output extends ParameterInstruction {

		public Output(int rawParCodes, List<Integer> parameters) {
			super(1, rawParCodes, parameters, false);
		}

		@Override
		public void execute() {
			List<Integer> args = getActualArguments();
			System.out.println(args.remove(0));
		}

	}

	public class BranchOnNotZero extends ParameterInstruction {

		public BranchOnNotZero(int rawParCodes, List<Integer> parameters) {
			super(2, rawParCodes, parameters, false);
		}

		@Override
		public void execute() {
			List<Integer> args = getActualArguments();
			if (args.remove(0) != 0)
				reg.set(IP, args.remove(0));
		}

	}

	public class BranchOnZero extends ParameterInstruction {

		public BranchOnZero(int rawParCodes, List<Integer> parameters) {
			super(2, rawParCodes, parameters, false);
		}

		@Override
		public void execute() {
			List<Integer> args = getActualArguments();
			if (args.remove(0) == 0)
				reg.set(IP, args.remove(0));
		}

	}

	public class SetOnLessThen extends ParameterInstruction {

		public SetOnLessThen(int rawParCodes, List<Integer> parameters) {
			super(3, rawParCodes, parameters, true);
		}

		@Override
		public void execute() {
			List<Integer> args = getActualArguments();
			int res = args.remove(0) < args.remove(0) ? 1 : 0;
			mm.set(args.remove(0), res);
		}

	}

	public class SetOnEquals extends ParameterInstruction {

		public SetOnEquals(int rawParCodes, List<Integer> parameters) {
			super(3, rawParCodes, parameters, true);
		}

		@Override
		public void execute() {
			List<Integer> args = getActualArguments();
			int res = args.remove(0) == args.remove(0) ? 1 : 0;
			mm.set(args.remove(0), res);
		}

	}

	public class UpdateRBP extends ParameterInstruction {

		public UpdateRBP(int rawParCodes, List<Integer> parameters) {
			super(1, rawParCodes, parameters, false);
		}

		@Override
		public void execute() {
			List<Integer> args = getActualArguments();
			reg.set(RBP, reg.get(RBP) + args.remove(0));
		}

	}

	public class Halt implements Instruction {

		@Override
		public int size() {
			return 1;
		}

		@Override
		public void execute() {
			isRunning = false;
		}

		@Override
		public String toString() {
			return getClass().getSimpleName();
		}

	}

}
