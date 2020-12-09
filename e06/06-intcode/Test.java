import java.util.ArrayList;
import java.util.List;

public class Test {
	public static void main(String[] args) {
		List<Integer> program = new ArrayList<>();
		for (String s : args[0].split(","))
			program.add(Integer.parseInt(s));
		IntCodeVM vm = new IntCodeVM(program);
		vm.run();
		// vm.debugRun(); // debug
		System.out.println(vm);
	}
}
