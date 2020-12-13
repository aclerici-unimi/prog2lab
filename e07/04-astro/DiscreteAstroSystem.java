import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Discrete implementation of the {@link AstroSystem} interface which uses
 * {@code long}s as units.
 */
public class DiscreteAstroSystem implements AstroSystem<Long>, Iterable<AstroSystem.CelestialObject<Long>> {
	private List<CelestialObject<Long>> objects;

	/**
	 * Constructs a DiscreteAstroSystem from the given {@link Collection} of
	 * {@link CelestialObject}. Doesn't clone the elements.
	 * 
	 * @param objs collection
	 */
	public DiscreteAstroSystem(Collection<CelestialObject<Long>> objs) {
		objects = new LinkedList<CelestialObject<Long>>(objs);
	}

	@Override
	public Long energy() {
		long total = 0;
		for (CelestialObject<Long> o : objects)
			total += o.energy();
		return total;
	}

	@Override
	public void evolve() {
		for (CelestialObject<Long> o1 : objects)
			for (CelestialObject<Long> o2 : objects) {
				if (o1 instanceof Planet) {
					int vx = 0, vy = 0, vz = 0;
					ThreeDVector<Long> pos1 = o1.position(), pos2 = o2.position();
					if (!pos1.x().equals(pos2.x()))
						vx = pos1.x() < pos2.x() ? 1 : -1;
					if (!pos1.y().equals(pos2.y()))
						vy = pos1.y() < pos2.y() ? 1 : -1;
					if (!pos1.z().equals(pos2.z()))
						vz = pos1.z() < pos2.z() ? 1 : -1;
					((Planet<Long>) o1).deltaVelocity(new SimpleLong3DVector(vx, vy, vz));
				}
			}
		for (CelestialObject<Long> obj : objects)
			if (obj instanceof Planet)
				((Planet<Long>) obj).move();
	}

	@Override
	public Iterator<CelestialObject<Long>> iterator() {
		return objects.iterator();
	}

	/**
	 * Abstract implementation of a {@link AstroSystem.CelestialObject}, based on a
	 * {@link ThreeDVector}, which uses {@code Long}s as units.
	 */
	public static abstract class DiscreteCelestialObject implements CelestialObject<Long> {
		protected ThreeDVector<Long> position;
		private final String name;

		protected DiscreteCelestialObject(String name, long x, long y, long z) {
			position = new SimpleLong3DVector(Long.valueOf(x), Long.valueOf(y), Long.valueOf(z));
			this.name = name;
		}

		@Override
		public ThreeDVector<Long> position() {
			return position;
		}

		@Override
		public Long energy() {
			return kineticEnergy() * potentialEnergy();
		}

		@Override
		public Long potentialEnergy() {
			return (Long) position.norm();
		}

		@Override
		public abstract Long kineticEnergy();

		@Override
		public String name() {
			return name;
		}

	}

	/**
	 * Discrete implementation of the {@link FixedStar} interface which uses
	 * {@code Long}s as units.
	 */
	public static class DiscreteFixedStar extends DiscreteCelestialObject implements FixedStar<Long> {

		/**
		 * Constructs a DiscreteFixedStar with the given coordinates.
		 * 
		 * @param x x coordinate.
		 * @param y y coordinate.
		 * @param z z coordinate.
		 */
		public DiscreteFixedStar(String name, long x, long y, long z) {
			super(name, x, y, z);
		}

		@Override
		public Long kineticEnergy() {
			return Long.valueOf(0);
		}

		@Override
		public String toString() {
			return "Fixed star - name: " + name() + "; position: " + position();
		}

	}

	/**
	 * Discrete implementation of the {@link Planet} interface which uses
	 * {@code Long}s as units.
	 */
	public static class DiscretePlanet extends DiscreteCelestialObject implements Planet<Long> {
		protected ThreeDVector<Long> velocity;

		/**
		 * Constructs a DiscreteFixedStar with the given coordinates.
		 * 
		 * @param x x coordinate.
		 * @param y y coordinate.
		 * @param z z coordinate.
		 */
		public DiscretePlanet(String name, long x, long y, long z) {
			super(name, x, y, z);
			velocity = new SimpleLong3DVector(0, 0, 0);
		}

		@Override
		public ThreeDVector<Long> velocity() {
			return velocity;
		}

		@Override
		public Long kineticEnergy() {
			return velocity.norm();
		}

		@Override
		public void move() {
			position = position.plus(velocity);
		}

		@Override
		public void deltaVelocity(ThreeDVector<Long> v) {
			velocity = velocity.plus(v);
		}

		@Override
		public String toString() {
			return "Planet - name: " + name() + "; position: " + position() + "; velocity: " + velocity();
		}

	}

}
