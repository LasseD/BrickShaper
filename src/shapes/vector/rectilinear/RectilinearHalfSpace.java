package shapes.vector.rectilinear;

import shapes.vector.*;

public class RectilinearHalfSpace implements VectorShape {
	private double a;
	private Dimension d;
	
	/*
	 * Occupy the half space xyz < a
	 */
	public RectilinearHalfSpace(double a, Dimension d) {
		this.a=a;
		this.d=d;
	}
	
	@Override
	public boolean occupies(Vector v) {
		switch(d) {
		case x:
			return v.x < a;
		case y:
			return v.y < a;
		case z:
			return v.z < a;
		default:
			throw new IllegalStateException("!xyz");
		}
	}
}
