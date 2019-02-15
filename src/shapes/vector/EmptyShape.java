package shapes.vector;

/**
 * Empty shape.
 */
public class EmptyShape implements VectorShape {
	@Override
	public boolean occupies(Vector v) {
		return false;
	}
}
