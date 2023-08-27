package shapes.vector.rectilinear;

import shapes.vector.*;

public class NGonCone implements VectorShape {
    private final double diam1, diam2, offsetAngle;
    private final int n;

    public NGonCone(int n, double diam1, double diam2) {
	this.n = n;
	this.diam1 = diam1;
	this.diam2 = diam2;
	offsetAngle = Math.PI / n;
    }

    public static boolean rightTurn(double x1, double y1, double x2, double y2, double x, double y) {
	return (x2-x1)*(y-y1) - (y2-y1)*(x-x1) < 0;
    }

    public boolean occupies(Vector v) {
	double d = (diam1 + (diam2-diam1)*v.z) / 2;

	for(int i = 0; i < n; i++) {
	    double a1 = offsetAngle + ( i )*2*Math.PI/n;
	    double a2 = offsetAngle + (i+1)*2*Math.PI/n;
	    
	    double x1 = 0.5 + d*Math.cos(a1);
	    double y1 = 0.5 + d*Math.sin(a1);

	    double x2 = 0.5 + d*Math.cos(a2);
	    double y2 = 0.5 + d*Math.sin(a2);
	    
	    if(rightTurn(x1, y1, x2, y2, v.x, v.y)) {
		return false;
	    }
	}
	return true;
    }
}
