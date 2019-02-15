package shapes.vector.rectilinear;

import colors.LEGOColor;
import shapes.vector.*;

/**
 * Run for size: 30 25 15
 * @author ld
 */
public class MonkeyHead extends TrivialVectorColoring {
	public static final double SKULL_LENGTH = 13.0/20.0;
	public static final double SKULL_WIDTH = 15.0/24.0; 	
	public static final double EAR_LENGTH = 1.6/20.0; 	
	public static final double EAR_HEIGHT = 5.0/15.0;
	public static final double EAR_HEIGHT_MID = 2.0/3.0;	
	public static final double EYE_HEIGHT = 2.0/15.0;	
	public static final double EYE_MID = 11.0/15.0;		
	public static final double EYE_Y = 9.0/24.0;		
	public static final double MOUTH_RADIUS_Z = 1.0/3.0;
	public static final double MOUTH_GAP = 1.0/20.0;
	
	public static final Vector CENTER = new Vector(0.5, 0.5, 0.5);

	private LEGOColor brown, tan, black;

	public MonkeyHead(LEGOColor brown, LEGOColor tan, LEGOColor black) {
		if(brown == null)
			throw new IllegalArgumentException("Brown is null!");
		if(tan == null)
			throw new IllegalArgumentException("Tan is null!");
		if(black == null)
			throw new IllegalArgumentException("Black is null!");
		this.brown = brown;
		this.tan = tan;
		this.black = black;
	}
	
	private static boolean isEar(Vector v) {
		if(v.x <= SKULL_LENGTH - EAR_LENGTH)
			return false; // Out of x!
		if(Math.abs(v.z-EAR_HEIGHT_MID) > EAR_HEIGHT/2)
			return false; // out of z!
		if(v.y > EAR_HEIGHT/2 && v.y < 1-EAR_HEIGHT/2)
			return true;
		return inCylinder(v, EAR_HEIGHT/2*SKULL_WIDTH, EAR_HEIGHT_MID, EAR_HEIGHT/2) ||
		       inCylinder(v, 1-EAR_HEIGHT/2*SKULL_WIDTH, EAR_HEIGHT_MID, EAR_HEIGHT/2);
	}
	
	private static boolean isEye(Vector v) {
		return inCylinder(v, EYE_Y, EYE_MID, EYE_HEIGHT/2) || inCylinder(v, 1-EYE_Y, EYE_MID, EYE_HEIGHT/2);
	}
	
	private static boolean inCylinder(Vector v, double y, double z, double radiusZ) {
		double dy = (1/SKULL_WIDTH)*(y-v.y);
		double dz = z-v.z;
		return Math.sqrt(dy*dy+dz*dz) <= radiusZ;
	}
	
	/**
	 * v: height between 0 and 1.
	 */
	@Override
	public LEGOColor getColor(Vector v) {
		if(v.x <= SKULL_LENGTH) {
			// Skull part:
			if(v.x < 0.5) {
				// Ball:
				v.y = 0.5 - (1/SKULL_WIDTH)*(0.5-v.y);
				return CENTER.dist(v) <= 0.5 ? brown : null;
			}
			else {
				// Cylinder:
				if(isEar(v)) {					
					return isEye(v) ? black : tan;
				}
				return inCylinder(v, 0.5, 0.5, 0.5) ? brown : null;
			}
		}
		else {
			if(!inCylinder(v, 0.5, MOUTH_RADIUS_Z, MOUTH_RADIUS_Z))
				return null; // Out of cylinder
			double centerX = 1-MOUTH_RADIUS_Z;
			double centerZ = MOUTH_RADIUS_Z;
			if(v.z > centerZ)
				centerX -= MOUTH_GAP; // Top mouth part.
				
			if(v.x < centerX)
				return tan; // Cylinder part
			
			v.y = 0.5 - (1/SKULL_WIDTH)*(0.5-v.y);
			return new Vector(centerX, 0.5, centerZ).dist(v) <= MOUTH_RADIUS_Z ? tan : null;
		}
	}
}
