package shapes.vector.rectilinear;

import shapes.vector.Vector;
import shapes.vector.VectorShape;

public class Propane implements VectorShape {
	public static final double DIAM_SMALL = 8.0/14.0; // Middle diam is 1. 
	public static final double CHECK_1 = 1.0/6.0; 
	public static final double CHECK_2 = 1.0/6.0; 
	public static final double CHECK_3 = 1.0/6.0; 
	
	@Override
	public boolean occupies(Vector v) {
		double distToCenterLine = Math.sqrt(v.y*v.y + v.z*v.z);
		if(v.x <= CHECK_1) { // First conical frustum part.
			double diamAtX = DIAM_SMALL + (1.0 - DIAM_SMALL)*(v.x/CHECK_1);
			return distToCenterLine <= diamAtX;
		}
		else if(v.x >= 1-CHECK_1) { // Second conical frustum part.
			double diamAtX = DIAM_SMALL + (1.0 - DIAM_SMALL)*((1-v.x)/(CHECK_1));
			return distToCenterLine <= diamAtX;			
		}
		else { // Middle cone:
			return distToCenterLine <= 1;
		}
	}
}
