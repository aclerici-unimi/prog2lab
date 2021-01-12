import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/** Interpreter for ASCIIArt code. */
public class Interpreter {

	/**
	 * Interpretes ASCIIArt code from standard input.
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		BitMap b = new BitMap(1, 1);
		Map<Integer, boolean[][]> stamps = new TreeMap<>();
		Figure f;
		while (sc.hasNext()) {
			switch (sc.next()) {
			case "n":
				b = new BitMap(sc.nextInt(), sc.nextInt());
				break;
			case "c":
				b.clear();
				break;
			case "i":
				b.invert();
				break;
			case "x":
				b.on(sc.nextInt(), sc.nextInt());
				break;
			case "o":
				b.off(sc.nextInt(), sc.nextInt());
				break;
			case "h":
				f = new HSegment(sc.nextInt(), sc.nextInt(), sc.nextInt());
				f.drawOn(b);
				break;
			case "v":
				f = new VSegment(sc.nextInt(), sc.nextInt(), sc.nextInt());
				f.drawOn(b);
				break;
			case "r":
				f = new Rectangle(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
				f.drawOn(b);
				break;
			case "s":
				int m = sc.nextInt();
				int x = sc.nextInt();
				int y = sc.nextInt();
				if (x <= 1000 && y <= 1000 && x > 0 && y > 0) {
					boolean values[][] = new boolean[x][y];
					for (int i = 0; i < values.length; i++)
						for (int j = 0; j < values[0].length; j++)
							values[i][j] = sc.nextInt() != 0 ? true : false;
					stamps.put(m, values);
				}
				break;
			case "d":
				m = sc.nextInt();
				if (stamps.containsKey(m))
					new Stamp(sc.nextInt(), sc.nextInt(), stamps.get(m)).drawOn(b);
				;
				break;
			case "p":
				System.out.println(b);
				break;
			default:
				System.err.println("command not found");
				System.exit(127);
			}
		}
		sc.close();
	}

}
