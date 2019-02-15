package shapes.vector;

public class Cylinder implements VectorShape {
	private double r;
	private Vector a, b;
	
	public Cylinder(double r, Vector p1, Vector p2) {
		this.r = r;
		this.a = p1;
		this.b = p2;
	}
	public Cylinder(double r, double x1,double y1,double z1,double x2,double y2,double z2) {
		this(r, new Vector(x1,y1,z1), new Vector(x2,y2,z2));
	}
	public Cylinder(double r) {
		this(r, 0.5, 0.5, 0, 0.5, 0.5, 1);
	}

	@Override
	public boolean occupies(Vector v) {
		return Vector.dist_to_line(a, b, v) <= r;
	}
}
