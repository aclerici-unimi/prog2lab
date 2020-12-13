/**
 * Simple abstraction of a planet. A planet, in addition to the proprieties of a
 * celestial object, has a velocity.
 */
public interface Planet<T extends Number> extends AstroSystem.CelestialObject<T> {

	/**
	 * Returns a vector representing the velocity of this.
	 * 
	 * @return the vector velocity.
	 */
	ThreeDVector<T> velocity();

	/** Sets this {@code Planet}'s velocity to the given {@link ThreeDVector}. */
	void deltaVelocity(ThreeDVector<T> v);

	/** Updates this {@code Planet}'s position in function of its velocity. */
	void move();

}
