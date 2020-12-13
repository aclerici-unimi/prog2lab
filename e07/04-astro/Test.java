import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Test {

	private static String italianToString(AstroSystem.CelestialObject<Long> obj) {
		if (obj instanceof Planet<?>) {
			Planet<Long> planet = (Planet<Long>) obj;
			return "Pianeta, nome: " + planet.name() + ", pos: " + planet.position() + ", vel: "
					+ planet.velocity();
		} else
			return "Stella fissa, nome: " + obj.name() + ", pos: " + obj.position();
	}

	public static void main(String[] args) {
		List<AstroSystem.CelestialObject<Long>> sysList = new LinkedList<>();
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			char type = sc.next().charAt(0); // can be P or S
			String name = sc.next();
			if (type == 'P')
				sysList.add(new DiscreteAstroSystem.DiscretePlanet(name, sc.nextInt(), sc.nextInt(),
						sc.nextInt()));
			else
				sysList.add(new DiscreteAstroSystem.DiscreteFixedStar(name, sc.nextInt(), sc.nextInt(),
						sc.nextInt()));
		}
		sc.close();
		DiscreteAstroSystem sys = new DiscreteAstroSystem(sysList);
		for (int i = 0; i < Integer.valueOf(args[0]); i++)
			sys.evolve();
		sysList.sort(new Comparator<AstroSystem.CelestialObject<Long>>() {

			@Override
			public int compare(AstroSystem.CelestialObject<Long> arg0,
					AstroSystem.CelestialObject<Long> arg1) {
				return arg0.name().compareTo(arg1.name());
			}

		});
		for (AstroSystem.CelestialObject<Long> obj : sysList) {
			System.out.println(italianToString(obj));
		}
		System.out.println("Energia totale: " + sys.energy());

	}

}
