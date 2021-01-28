import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Abstraction of a simple shell used in interacting with a {@link Filesystem}.
 */
public class Shell {

	private static List<String> parsePath(List<String> cwd, String path) {
		// could be done better by contatenating
		List<String> res = new LinkedList<>();
		if (path.charAt(0) != ':')
			for (String s : cwd)
				res.add(s);
		for (String s : path.split(":")) {
			if (!s.equals(""))
				res.add(s);
		}
		return res;
	}

	private static String printPath(List<String> path) {
		String res = "";
		for (String s : path)
			res += ":" + s;
		return res;
	}

	public static void main(String[] args) {
		Filesystem f = new Filesystem();
		List<String> cwd = new LinkedList<>(); // working directory
		Scanner sc = new Scanner(System.in);
		while (sc.hasNextLine()) {
			String[] cmd = sc.nextLine().split("\\s");
			switch (cmd[0]) {
			case "ls":
				if (cmd.length > 1)
					System.out.print(f.ls(parsePath(cwd, cmd[1])));
				else
					System.out.print(f.ls(cwd));
				break;
			case "size":
				if (cmd.length > 1)
					System.out.println(">>> " + f.size(parsePath(cwd, cmd[1])));
				else
					System.out.println(">>> " + f.size(cwd));
				break;
			case "mkdir":
				try {
					f.mkdir(parsePath(cwd, cmd[1]));
				} catch (IllegalArgumentException e) {
					System.err.println("cannot create the specified directory");
				}
				break;
			case "mkfile":
				try {
					f.mkfile(parsePath(cwd, cmd[1]), Integer.parseInt(cmd[2]));
				} catch (IllegalArgumentException e) {
					System.err.println("cannot create the specified file");
				}
				break;
			case "cd":
				if (cmd.length > 1) {
					// TODO: check if the directory is valid
					cwd = parsePath(cwd, cmd[1]);
				} else {
					cwd = new LinkedList<>();
				}
				break;
			case "pwd":
				System.out.println(printPath(cwd));
				break;
			default:
				System.err.println("Unknown command.");
			}
		}
		sc.close();
	}
}
