public class Simple3DVector<T extends Number> implements ThreeDVector<T> {
	private final T x;
	private final T y;
	private final T z;

	public Simple3DVector(T x, T y, T z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public T x() {
		return x;
	}

	@Override
	public T y() {
		return y;
	}

	@Override
	public T z() {
		return z;
	}

	@Override
	public ThreeDVector<T> plus(ThreeDVector<T> other) {
		// ?????????????? TODO
		return null;
	}

	@Override
	public T norm() {
		// ????????????? TODO
		return null;
	}

}
