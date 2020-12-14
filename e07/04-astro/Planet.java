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

	/**
	 * Affects this {@code Planet}'s velocity with the given {@link ThreeDVector}.
	 * The least complicated case is simply add the velocity vector to the given one
	 * and set the velocity to the sum.
	 * 
	 * @param v the affecting {@link ThreeDVector}.
	 * @throws NullPointerException if v is null.
	 */
	void deltaVelocity(ThreeDVector<T> v);

	/** Updates this {@code Planet}'s position in function of its velocity. */
	void move();

}
