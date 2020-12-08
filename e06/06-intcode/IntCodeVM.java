import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IntCodeVM extends AbstractFDEMachine {
	static final int IP = 0;
	static final int RBP = 1;

	/**
	 * Constructs an IntCode VM ready to execute the program {@code program}.
	 * 
	 * @param program
	 */
	public IntCodeVM(Integer[] program) {
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
				return new Add(rawOP, rawText);
			default:
				throw new IllegalArgumentException("Invalid instruction of OP code " + rawOP % 100);
		}
	}

	public abstract class ParameterInstruction implements Instruction {
		// parameters
		protected List<Integer> pars;

		// rules for data access
		protected List<Integer> pModes;

		protected final int sz;

		protected ParameterInstruction(int nPars, int rawParModes, List<Integer> parameters) {
			pModes = new ArrayList<>();
			for (int i = 0; i < nPars; rawParModes /= 10, i++)
				pModes.add(rawParModes % 10);
			pars = parameters;
			sz = nPars;
		}

		protected List<Integer> getActualArguments() {
			Iterator<Integer> it = pModes.iterator();
			List<Integer> res = new ArrayList<Integer>();
			for (int par : pars)
				switch (it.next()) {
					case 0:
						res.add(mm.get(par));
					case 1:
						res.add(par);
					case 2:
						res.add(mm.get(reg.get(RBP) + par));
					default:
						throw new IllegalArgumentException(
								"Invalid access mode for par " + par);
				}
			return res;
		}

		@Override
		public int size() {
			return sz;
		}

	}

	public class Add extends ParameterInstruction {

		public Add(int rawParCodes, List<Integer> parameters) {
			super(3, rawParCodes, parameters);
		}

		@Override
		public void execute() {
			// TODO Auto-generated method stub
		}

	}

}
