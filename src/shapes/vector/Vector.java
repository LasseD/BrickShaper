package shapes.vector;

/**
 * 3D Vector, simple implementation with double fields.
 * @author ld
 */
public class Vector {
	public double x, y, z;
	
	public Vector(double x, double y, double z) {
		this.x=x;
		this.y=y;
		this.z=z;
	}
	public Vector(Vector v) {
		this(v.x, v.y, v.z);
	}
	public Vector() {
		this(0,0,0);
	}
	
	public static Vector sub(Vector a, Vector b) {
		return new Vector(a.x-b.x, a.y-b.y, a.z-b.z);
	}
	public static Vector add(Vector a, Vector b) {
		return new Vector(a.x+b.x, a.y+b.y, a.z+b.z);
	}
	public static double dot(Vector a, Vector b) {
		return a.x*b.x + a.y*b.y + a.z*b.z;
	}
	public static double ordinal(Vector a) {
		return Math.sqrt(a.x*a.x + a.y*a.y + a.z*a.z);
	}
	public static double ordinal_sq(Vector a) {
		return a.x*a.x + a.y*a.y + a.z*a.z;
	}
	public double dist(Vector a) {
		return Math.sqrt((x-a.x)*(x-a.x) + (y-a.y)*(y-a.y) + (z-a.z)*(z-a.z));
	}
	public static Vector mult(Vector a, double b) {
		return new Vector(a.x*b, a.y*b, a.z*b);
	}
	public static Vector div(Vector a, double b) {
		return new Vector(a.x/b, a.y/b, a.z/b);
	}
	public static Vector mid(Vector a, Vector b) {
		return div(add(a, b), 2);
	}
	
	public static double dist_to_line(Vector p1, Vector p2, Vector p0) {
		double a = ordinal_sq(sub(p1,p0))*ordinal_sq(sub(p2,p1));
		double b = dot(sub(p1,p0),sub(p2,p1));		
		b *=b;
		double num = Math.sqrt(a-b);
		double denum = ordinal(sub(p2,p1));
		return num/denum;
	}

	public static Vector projectionOntoLine(Vector a, Vector b, Vector p) {
		Vector ap = sub(p,a);
		Vector ab = sub(b,a);
		return add(a, mult(ab, dot(ap, ab) / dot(ab, ab)));
	}
	
	public void checkInUnitBox() {
		if(x < 0 || x > 1 || y < 0 || y > 1 || z < 0 || z > 1)
			throw new IllegalStateException("Not in unit box: " + this.toString());
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
