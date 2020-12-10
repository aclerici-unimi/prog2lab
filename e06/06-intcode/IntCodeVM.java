import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class IntCodeVM extends AbstractFDEMachine {
	private static final int IP = 0;
	private static final int RBP = 1;

	/*
	 * Abstraction Function: AF(IP, RBP, reg, mm, isRunning) = Machine with a set of
	 * registers reg, indexed with Instruction Pointer at IP and Relative Base
	 * Pointer at RBP, a memory mm and a running state of isRunning. See AF of the
	 * memories.
	 *
	 * Representation Invariant: mm and reg are not null.
	 */

	/**
	 * Constructs an IntCode VM ready to execute the program {@code program}.
	 * 
	 * @param program the program.
	 */
	public IntCodeVM(List<Integer> program) {
		super(new BasicMainMemory(program), new BasicMemory());
		assert repOk();
	}

	/**
	 * Implementation of the representation invariant. Returns true if the
	 * representation respects all its requirements. Used in assertions.
	 * 
	 * @return true if the representation is ok; false otherwise.
	 */
	public boolean repOk() {
		return mm != null && reg != null;
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

	// constants
	private static class Instructions {
		private static final int ADD = 1;
		private static final int MUL = 2;
		private static final int INP = 3;
		private static final int OUT = 4;
		private static final int BNE = 5;
		private static final int BEQ = 6;
		private static final int SLT = 7;
		private static final int SEQ = 8;
		private static final int UPD = 9;
		private static final int HAL = 99;

		private Instructions() {
			throw new AssertionError("this class must not be instantiated");
		}

	}

	/**
	 * Translates raw text into properly instantiated IntCode {@link Instruction}s
	 * by extracting the OPcode and instantiating the correct Instruction type.
	 * 
	 * @param rawText raw data in input.
	 * @return the instruction ready to execute.
	 * @throws IllegalArgumentException if the opcode is not valid.
	 */
	private Instruction instructionFrom(List<Integer> rawText) {
		int rawOP = rawText.remove(0);
		switch (rawOP % 100) {
			case Instructions.ADD:
				return new Add(rawOP / 100, rawText);
			case Instructions.MUL:
				return new Mul(rawOP / 100, rawText);
			case Instructions.INP:
				return new Input(rawOP / 100, rawText);
			case Instructions.OUT:
				return new Output(rawOP / 100, rawText);
			case Instructions.BNE:
				return new BranchOnNotZero(rawOP / 100, rawText);
			case Instructions.BEQ:
				return new BranchOnZero(rawOP / 100, rawText);
			case Instructions.SLT:
				return new SetOnLessThen(rawOP / 100, rawText);
			case Instructions.SEQ:
				return new SetOnEquals(rawOP / 100, rawText);
			case Instructions.UPD:
				return new UpdateRBP(rawOP / 100, rawText);
			case Instructions.HAL:
				return new Halt();
			default:
				throw new IllegalArgumentException("Invalid instruction of OP code " + rawOP % 100);
		}
	}

	/**
	 * Abstract class which gathers IntCode's parameter based {@link Instruction}s.
	 * A ParameterInstruction has a list of parameters, each with its access mode.
	 * The actual arguments of the elaboration are fetched, accordingly to
	 * parameters and modes, during the execution phase.
	 */
	public abstract class ParameterInstruction implements Instruction {
		// parameters
		protected List<Integer> pars;

		// rules for data access
		protected List<Integer> pModes;

		// number of memory units the raw data occupied
		protected final int sz;

		/*
		 * Abstraction Function: AF(pars, pModes, sz) = parameter based instruction of
		 * raw size sz, with parameters pars and respective access modes (par at
		 * pars.get(0) has its access mode at pModes.get(0)).
		 *
		 * Representation Invariant: sz>0; pars and pModes are not null or empty and are
		 * in the same size.
		 */

		/**
		 * Constructs a new parameter based instruction.
		 * 
		 * @param nPars       number of parameters.
		 * @param rawParModes raw code containing the data access mode for each
		 *                    parameter.
		 * @param subsText    portion of memory raw data to fetch the parameters from
		 *                    (subsequent to the portion which contained the OPcode).
		 *                    Its size is enough to keep the biggest instruction's
		 *                    parameters.
		 * @param writeOnLast true if the last parameters will be used for writing (thus
		 *                    direct addressing), false otherwise.
		 */
		protected ParameterInstruction(int nPars, int rawParModes, List<Integer> subsText,
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
			pars = subsText.subList(0, nPars);
			sz = nPars + 1;
			assert repOk();
		}

		/**
		 * Implementation of the representation invariant. Returns true if the
		 * representation respects all its requirements. Used in assertions.
		 * 
		 * @return true if the representation is ok; false otherwise.
		 */
		public boolean repOk() {
			return pars != null && pModes != null && sz > 0 && !pars.isEmpty()
					&& pars.size() == pModes.size();
		}

		/**
		 * Fetches the arguments to work with from the instruction parameters and their
		 * access modes.
		 * 
		 * @return the actual arguments.
		 */
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
		public boolean equals(Object obj) {
			if (obj.getClass() != this.getClass())
				return false;
			ParameterInstruction other = (ParameterInstruction) obj;
			return pars.equals(other.pars) && pModes.equals(other.pModes);
		}

		@Override
		public String toString() {
			return getClass().getSimpleName() + "; parameters: " + listToString(pars)
					+ "; parameter modes: " + listToString(pModes);
		}

	}

	/**
	 * toString of a list of {@code Integer}s.
	 * 
	 * @param l the list.
	 * @return the string.
	 */
	private static String listToString(List<Integer> l) {
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

	/**
	 * Intcode {@link Instruction} which adds two numbers and puts their sum in
	 * memory.
	 */
	public class Add extends ParameterInstruction {

		/**
		 * Constructs a new Add {@link Instruction} extracting its parameters and their
		 * modes from a given portion of memory.
		 * 
		 * @param rawParModes number to extract the parameter access modes from (first
		 *                    three digits of the complete OPcode).
		 * @param subsText    portion of memory text, subsequent of the OPcode,
		 *                    containing (also) the Instruction's parameters.
		 */
		public Add(int rawParModes, List<Integer> subsText) {
			super(3, rawParModes, subsText, true);
			assert super.repOk();
		}

		@Override
		public void execute() {
			List<Integer> args = getActualArguments();
			int res = args.remove(0) + args.remove(0);
			mm.set(args.remove(0), res);
		}

		@Override
		public int hashCode() {
			return 31 * Instructions.ADD + pars.hashCode() + pModes.hashCode();
		}

	}

	/**
	 * Intcode {@link Instruction} which multiplies two numbers and puts their
	 * product in memory.
	 */
	public class Mul extends ParameterInstruction {

		/**
		 * Constructs a new Mul {@link Instruction} extracting its parameters and their
		 * modes from a given portion of memory.
		 * 
		 * @param rawParModes number to extract the parameter access modes from (first
		 *                    three digits of the complete OPcode).
		 * @param subsText    portion of memory text, subsequent of the OPcode,
		 *                    containing (also) the Instruction's parameters.
		 */
		public Mul(int rawParModes, List<Integer> parameters) {
			super(3, rawParModes, parameters, true);
			assert super.repOk();
		}

		@Override
		public void execute() {
			List<Integer> args = getActualArguments();
			int res = args.remove(0) * args.remove(0);
			mm.set(args.remove(0), res);
		}

		@Override
		public int hashCode() {
			return 31 * Instructions.MUL + pars.hashCode() + pModes.hashCode();
		}

	}

	/**
	 * Intcode {@link Instruction} which takes standard input from the user and puts
	 * it in a memory cell.
	 */
	public class Input extends ParameterInstruction {

		/**
		 * Constructs a new Input {@link Instruction} extracting its parameter and its
		 * mode from a given portion of memory.
		 * 
		 * @param rawParModes number to extract the parameter access mode from (first
		 *                    three digits of the complete OPcode).
		 * @param subsText    portion of memory text, subsequent of the OPcode,
		 *                    containing (also) the Instruction's parameter.
		 */
		public Input(int rawParModes, List<Integer> parameters) {
			super(1, rawParModes, parameters, true);
			assert super.repOk();
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

		@Override
		public int hashCode() {
			return 31 * Instructions.INP + pars.hashCode() + pModes.hashCode();
		}

	}

	/**
	 * Intcode {@link Instruction} which takes input from a cell and puts it on
	 * standard output.
	 */
	public class Output extends ParameterInstruction {

		/**
		 * Constructs a new Output {@link Instruction} extracting its parameter and its
		 * mode from a given portion of memory.
		 * 
		 * @param rawParModes number to extract the parameter access mode from (first
		 *                    three digits of the complete OPcode).
		 * @param subsText    portion of memory text, subsequent of the OPcode,
		 *                    containing (also) the Instruction's parameter.
		 */
		public Output(int rawParModes, List<Integer> parameters) {
			super(1, rawParModes, parameters, false);
			assert super.repOk();
		}

		@Override
		public void execute() {
			List<Integer> args = getActualArguments();
			System.out.println(args.remove(0));
		}

		@Override
		public int hashCode() {
			return 31 * Instructions.OUT + pars.hashCode() + pModes.hashCode();
		}

	}

	/**
	 * Intcode {@link Instruction} which branches to the second argument's memory
	 * address if the first argument is not zero.
	 */
	public class BranchOnNotZero extends ParameterInstruction {

		/**
		 * Constructs a new BranchOnNotZero {@link Instruction} extracting its
		 * parameters and their modes from a given portion of memory.
		 * 
		 * @param rawParModes number to extract the parameter access modes from (first
		 *                    three digits of the complete OPcode).
		 * @param subsText    portion of memory text, subsequent of the OPcode,
		 *                    containing (also) the Instruction's parameters.
		 */
		public BranchOnNotZero(int rawParModes, List<Integer> parameters) {
			super(2, rawParModes, parameters, false);
			assert super.repOk();
		}

		@Override
		public void execute() {
			List<Integer> args = getActualArguments();
			if (args.remove(0) != 0)
				reg.set(IP, args.remove(0));
		}

		@Override
		public int hashCode() {
			return 31 * Instructions.BNE + pars.hashCode() + pModes.hashCode();
		}

	}

	/**
	 * Intcode {@link Instruction} which branches to the second argument's memory
	 * address if the first argument is zero.
	 */
	public class BranchOnZero extends ParameterInstruction {

		/**
		 * Constructs a new BranchOnZero {@link Instruction} extracting its parameters
		 * and their modes from a given portion of memory.
		 * 
		 * @param rawParModes number to extract the parameter access modes from (first
		 *                    three digits of the complete OPcode).
		 * @param subsText    portion of memory text, subsequent of the OPcode,
		 *                    containing (also) the Instruction's parameters.
		 */
		public BranchOnZero(int rawParModes, List<Integer> parameters) {
			super(2, rawParModes, parameters, false);
			assert super.repOk();
		}

		@Override
		public void execute() {
			List<Integer> args = getActualArguments();
			if (args.remove(0) == 0)
				reg.set(IP, args.remove(0));
		}

		@Override
		public int hashCode() {
			return 31 * Instructions.BEQ + pars.hashCode() + pModes.hashCode();
		}

	}

	/**
	 * Intcode {@link Instruction} which sets the memory cell addressed by the third
	 * parameter if the first parameter is less then the second one.
	 */
	public class SetOnLessThen extends ParameterInstruction {

		/**
		 * Constructs a new SetOnLessThen {@link Instruction} extracting its parameters
		 * and their modes from a given portion of memory.
		 * 
		 * @param rawParModes number to extract the parameter access modes from (first
		 *                    three digits of the complete OPcode).
		 * @param subsText    portion of memory text, subsequent of the OPcode,
		 *                    containing (also) the Instruction's parameters.
		 */
		public SetOnLessThen(int rawParModes, List<Integer> parameters) {
			super(3, rawParModes, parameters, true);
			assert super.repOk();
		}

		@Override
		public void execute() {
			List<Integer> args = getActualArguments();
			int res = args.remove(0) < args.remove(0) ? 1 : 0;
			mm.set(args.remove(0), res);
		}

		@Override
		public int hashCode() {
			return 31 * Instructions.SLT + pars.hashCode() + pModes.hashCode();
		}

	}

	/**
	 * Intcode {@link Instruction} which sets the memory cell addressed by the third
	 * parameter if the first parameter is equal to the second one.
	 */
	public class SetOnEquals extends ParameterInstruction {

		/**
		 * Constructs a new SetOnEquals {@link Instruction} extracting its parameters
		 * and their modes from a given portion of memory.
		 * 
		 * @param rawParModes number to extract the parameter access modes from (first
		 *                    three digits of the complete OPcode).
		 * @param subsText    portion of memory text, subsequent of the OPcode,
		 *                    containing (also) the Instruction's parameters.
		 */
		public SetOnEquals(int rawParModes, List<Integer> parameters) {
			super(3, rawParModes, parameters, true);
			assert super.repOk();
		}

		@Override
		public void execute() {
			List<Integer> args = getActualArguments();
			int res = args.remove(0) == args.remove(0) ? 1 : 0;
			mm.set(args.remove(0), res);
		}

		@Override
		public int hashCode() {
			return 31 * Instructions.SEQ + pars.hashCode() + pModes.hashCode();
		}

	}

	/**
	 * Intcode {@link Instruction} which sets the Relative Base Pointer to the given
	 * argument.
	 */
	public class UpdateRBP extends ParameterInstruction {

		/**
		 * Constructs a new Input {@link Instruction} extracting its parameter and its
		 * mode from a given portion of memory.
		 * 
		 * @param rawParModes number to extract the parameter access mode from (first
		 *                    three digits of the complete OPcode).
		 * @param subsText    portion of memory text, subsequent of the OPcode,
		 *                    containing (also) the Instruction's parameter.
		 */
		public UpdateRBP(int rawParModes, List<Integer> parameters) {
			super(1, rawParModes, parameters, false);
			assert super.repOk();
		}

		@Override
		public void execute() {
			List<Integer> args = getActualArguments();
			reg.set(RBP, reg.get(RBP) + args.remove(0));
		}

		@Override
		public int hashCode() {
			return 31 * Instructions.UPD + pars.hashCode() + pModes.hashCode();
		}

	}

	/**
	 * Intcode {@link Instruction} which halts the machine.
	 */
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

		@Override
		public boolean equals(Object obj) {
			return (obj instanceof Halt);
		}

		@Override
		public int hashCode() {
			return 31 * 99;
		}

	}

	@Override
	public String toString() {
		String res = "IntcodeVM :\n\t" + mm.toString() + "\n\tRegisters : [IP = " + reg.get(IP) + ", RBP = "
				+ reg.get(RBP) + "]";
		return res;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof IntCodeVM))
			return false;
		IntCodeVM other = (IntCodeVM) obj;
		return this.isRunning == other.isRunning && this.mm.equals(other.mm) && this.reg.equals(other.reg);
	}

	@Override
	public int hashCode() {
		return (isRunning ? 31 : 1) * reg.hashCode() + mm.hashCode();
	}

}
