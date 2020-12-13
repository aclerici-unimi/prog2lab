/**
 * Simple abstraction of a fixed star. A fixed star has no velocity and its
 * coordinates cannot change.
 */
public interface FixedStar<T extends Number> extends AstroSystem.CelestialObject<T> {
}
