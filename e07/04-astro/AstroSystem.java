/**
 * Simple abstraction of an astronomical system. An {@code AstroSystem} has a
 * total energy and it can evolve with respect to a determined time unit
 * {@code T}.
 */
public interface AstroSystem<T extends Number> {

	/**
	 * Returns the total energy of this system.
	 * 
	 * @return total energy.
	 */
	T energy();

	/** Simulates the evolution of this AstroSystem in one time unit. */
	void evolve();

	/**
	 * Simple abstraction of a celestial (astronomical) object, having a position
	 * and an energy. {@code CelestialObject}s are mutable objects.
	 */
	public interface CelestialObject<T extends Number> {

		/**
		 * Returns a vector representing the position of this.
		 * 
		 * @return the vector position.
		 */
		ThreeDVector<T> position();

		/**
		 * Returns the total energy of this CelestialObject.
		 * 
		 * @return the total energy.
		 */
		T energy();

		/** Returns the kinetic energy of this CelestialObject. */

		/**
		 * Returns the kinetic energy of this CelestialObject.
		 * 
		 * @return the kinetic energy.
		 */
		T kineticEnergy();

		/**
		 * Returns the potential energy of this CelestialObject.
		 * 
		 * @return the potential energy.
		 */
		T potentialEnergy();

		/**
		 * Returns the name of this {@code DiscreteCelestialObject}.
		 * 
		 * @return the name.
		 */
		String name();

	}

}
