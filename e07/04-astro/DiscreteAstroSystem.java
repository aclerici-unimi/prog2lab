import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
// use this command to build documentation: javadoc -d docs -tag repInv:class:"Representation Invariant" -tag absFun:class:"Abstraction Function" *.java

/**
 * Discrete implementation of the {@link AstroSystem} interface which uses
 * {@code long}s as units.
 * 
 * @absFun AF(this) = Massless astronomical system with the elements of
 *         this.objects (see their AF) as celestial objects.
 * @repInv objects is not null.
 */
public class DiscreteAstroSystem implements AstroSystem<Long> {
	private List<CelestialObject<Long>> objects;

	/**
	 * Constructs a DiscreteAstroSystem from the given {@link Collection} of
	 * {@link CelestialObject}s. Doesn't clone the elements.
	 * 
	 * @param objs collection
	 */
	public DiscreteAstroSystem(Collection<CelestialObject<Long>> objs) {
		objects = new LinkedList<CelestialObject<Long>>(objs);
		assert repOk();
	}

	/**
	 * Implementation of the representation invariant. Returns true if the
	 * representation respects all its requirements. Used in assertions.
	 * 
	 * @return true if the representation of this is ok; false otherwise.
	 */
	public boolean repOk() {
		return objects != null;
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
		for (CelestialObject<Long> o1 : objects) {
			if (!(o1 instanceof Planet))
				continue;
			for (CelestialObject<Long> o2 : objects) {
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
		assert repOk();
	}

	@Override
	public Iterator<CelestialObject<Long>> iterator() {
		return objects.iterator();
	}

	/**
	 * Abstract, discrete implementation of a {@link AstroSystem.CelestialObject},
	 * based on a {@link ThreeDVector}, which uses {@code Long}s as units.
	 * 
	 * @absFun AF(this) = Celestial object (specified by extension) of position
	 *         position.
	 * @repInv name and position are not null.
	 */
	public static abstract class DiscreteCelestialObject implements CelestialObject<Long> {
		/** Position of this object in space. */
		protected ThreeDVector<Long> position;
		private final String name;

		/**
		 * Constructs a DiscreteCelestialObject from the given name and position.
		 * 
		 * @param name the name.
		 * @param x    x coordinate.
		 * @param y    y coordinate.
		 * @param z    z coordinate.
		 * @throws NullPointerException if name is null.
		 */
		protected DiscreteCelestialObject(String name, long x, long y, long z) {
			if (name == null)
				throw new NullPointerException("you need to set a name for this celestial object");
			position = new SimpleLong3DVector(Long.valueOf(x), Long.valueOf(y), Long.valueOf(z));
			this.name = name;
		}

		/**
		 * Implementation of the representation invariant. Returns true if the
		 * representation respects all its requirements. Used in assertions.
		 * 
		 * @return true if the representation of this is ok; false otherwise.
		 */
		public boolean repOk() {
			return position != null && name != null;
		}

		@Override
		public ThreeDVector<Long> position() {
			return position;
		}

		@Override
		public Long energy() {
			// return kineticEnergy() + potentialEnergy();
			return kineticEnergy() * potentialEnergy(); // why though?
		}

		@Override
		public Long potentialEnergy() {
			return position.norm();
		}

		@Override
		public abstract Long kineticEnergy();

		@Override
		public String name() {
			return name;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this)
				return true;
			if (!(obj instanceof CelestialObject<?>))
				return false;
			CelestialObject<?> other = (CelestialObject<?>) obj;
			return this.position().equals(other.position()) && this.name().equals(other.name());
		}

		@Override
		public String toString() {
			return "name: " + name() + "; position: " + position();
		}

	}

	/**
	 * Discrete implementation of the {@link FixedStar} interface which uses
	 * {@code Long}s as units.
	 * 
	 * @absFun AF(this) = Star of name {@code name} and position {@code position}.
	 * @repInv {@code name}, {@code position} and {@code velocity} are not null.
	 */
	public static class DiscreteFixedStar extends DiscreteCelestialObject implements FixedStar<Long> {

		/**
		 * Constructs a DiscreteFixedStar with the given coordinates.
		 * 
		 * @param name this {@code DiscreteFixedStar}'s name.
		 * @param x    x coordinate.
		 * @param y    y coordinate.
		 * @param z    z coordinate.
		 */
		public DiscreteFixedStar(String name, long x, long y, long z) {
			super(name, x, y, z);
			assert repOk();
		}

		@Override
		public Long kineticEnergy() {
			return Long.valueOf(0);
		}

		@Override
		public boolean equals(Object obj) {
			if (!super.equals(obj))
				return false;
			return (obj instanceof FixedStar<?>);
		}

		@Override
		public String toString() {
			return "Fixed star - " + super.toString();
		}

	}

	/**
	 * Discrete implementation of the {@link Planet} interface which uses
	 * {@code Long}s as units.
	 * 
	 * @absFun AF(this) = Planet of name {@code name}, position {@code position} and
	 *         velocity {@code velocity}.
	 * @repInv {@code name}, {@code position} and {@code velocity} are not null.
	 */
	public static class DiscretePlanet extends DiscreteCelestialObject implements Planet<Long> {
		protected ThreeDVector<Long> velocity;

		/**
		 * Constructs a DiscreteFixedStar with the given coordinates.
		 * 
		 * @param name this {@code DiscretePlanet}'s name.
		 * @param x    x coordinate.
		 * @param y    y coordinate.
		 * @param z    z coordinate.
		 */
		public DiscretePlanet(String name, long x, long y, long z) {
			super(name, x, y, z);
			velocity = new SimpleLong3DVector(0, 0, 0);
			assert repOk() : this;
		}

		@Override
		public boolean repOk() {
			return super.repOk() && velocity != null;
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
			assert repOk();
		}

		@Override
		public void deltaVelocity(ThreeDVector<Long> v) {
			if (v == null)
				throw new NullPointerException("the delta vector can't be null");
			velocity = velocity.plus(v);
			assert repOk();
		}

		@Override
		public String toString() {
			return "Planet - " + super.toString() + "; velocity: " + velocity();
		}

		@Override
		public boolean equals(Object obj) {
			if (!super.equals(obj))
				return false;
			if (obj == this)
				return true;
			if (!(obj instanceof Planet<?>))
				return false;
			Planet<?> other = (Planet<?>) obj;
			return other.velocity().equals(this.velocity());
		}

	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof AstroSystem<?>))
			return false;
		AstroSystem<?> other = (AstroSystem<?>) obj;
		for (CelestialObject<Long> o1 : objects) {
			boolean found = false;
			for (CelestialObject<?> o2 : other)
				if (o1.equals(o2)) {
					found = true;
					break;
				}
			if (!found)
				return false;
		}
		return true;
	}

	@Override
	public String toString() {
		String res = "AstroSystem:\n";
		for (CelestialObject<Long> o : objects)
			res += o;
		return res;
	}

}
