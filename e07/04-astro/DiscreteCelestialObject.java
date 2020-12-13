/**
 * Abstract implementation of a {@link AstroSystem.CelestialObject} based on a
 * {@link ThreeDVector}.
 */
public abstract class DiscreteCelestialObject implements AstroSystem.CelestialObject<Long> {
	private ThreeDVector<Long> position;

	public DiscreteCelestialObject(long x, long y, long z) {
		position = new SimpleLong3DVector(Long.valueOf(x), Long.valueOf(y), Long.valueOf(z));
	}

	@Override
	public ThreeDVector<Long> position() {
		return position;
	}

	@Override
	public Long energy() {
		return kineticEnergy() + potentialEnergy();
	}

	@Override
	public Long potentialEnergy() {
		return (Long) position.norm();
	}

	@Override
	public abstract Long kineticEnergy();

}
