package shapes.vector;

public class ConicalFrustum implements VectorShape {
	private double r1, r2, distC1C2;
	private Vector c1, c2;
	
	public ConicalFrustum(double r1, double r2, Vector c1, Vector c2) {
		if(r2 < r1) {
			this.r2 = r1;
			this.r1 = r2;
			this.c2 = c1;
			this.c1 = c2;			
		}
		else {
			this.r1 = r1;
			this.r2 = r2;
			this.c1 = c1;
			this.c2 = c2;			
		}
		distC1C2 = Vector.ordinal(Vector.sub(c1, c2));
	}

	@Override
	public boolean occupies(Vector v) {
		Vector projection = Vector.projectionOntoLine(c1, c2, v);
		double distFromC1 = Vector.ordinal(Vector.sub(c1, projection));
		
		double d = Vector.dist_to_line(c1, c2, v);
		return d <= r1 + distFromC1/distC1C2 * (r2-r1);
	}
}
