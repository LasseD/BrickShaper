package shapes.vector.rectilinear;

import shapes.vector.Vector;
import shapes.vector.VectorShape;

/**
 * A cement mixer bucket lying on XY plane with end in pos. X dir.
 * Line though center: 0,0,0 -> 1,0,0
 */
public class Cement implements VectorShape {
	public static final double DIAM_START = 22.0/29.0; // Middle diam is 1. 
	public static final double DIAM_END = 20.0/29.0; 
	public static final double CHECK_1 = 7.0/22.0; 
	public static final double CHECK_2 = 16.0/22.0;
	
	@Override
	public boolean occupies(Vector v) {
		double distToCenterLine = Math.sqrt(v.y*v.y + v.z*v.z);
		if(v.x <= CHECK_1) { // First conical frustum part.
			double diamAtX = DIAM_START + (1.0 - DIAM_START)*(v.x/CHECK_1);
			return distToCenterLine <= diamAtX;
		}
		else if(v.x >= CHECK_2) { // Second conical frustum part.
			double diamAtX = DIAM_END + (1.0 - DIAM_END)*((1-v.x)/(1-CHECK_2));
			return distToCenterLine <= diamAtX;			
		}
		else { // Middle cone:
			return distToCenterLine <= 1;
		}
	}
}
