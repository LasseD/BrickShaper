package shapes.vector;

public class Ball implements VectorShape {
	private double r;
	private Vector c;
	
	public Ball(Vector c, double r) {
		this.c = c;
		this.r = r;
	}
	public Ball(double r) {
		this(new Vector(), r);
	}
	public Ball() { // Often used for stretching and moving.
		this(1);
	}

	@Override
	public boolean occupies(Vector v2) {
		return c.dist(v2) <= r;
	}
}
