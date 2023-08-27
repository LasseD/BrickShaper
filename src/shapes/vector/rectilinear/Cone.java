package shapes.vector.rectilinear;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import shapes.vector.*;

/*
  A cone standing up (+z) with diameter w1 at the base (z=0) and w2 at the top (z=1)
 */
public class Cone implements VectorShape {
    private double diam1, diam2;

    public Cone(double diam1, double diam2) {
	this.diam1 = diam1;
	this.diam2 = diam2;
    }

    public boolean occupies(Vector v) {
	double coneWidth = diam1 + (v.z * (diam2-diam1));

	double dx = Math.abs(v.x - 0.5);
	double dy = Math.abs(v.y - 0.5);
	double distToCenterLine = Math.sqrt(dx*dx + dy*dy);
	return distToCenterLine <= coneWidth / 2;
    }
}
