package shapes.vector.rectilinear;

import colors.LEGOColor;
import shapes.vector.*;

/**
 * Run for size: 22 22 30
 * Height : all: 46cm Body: 30cm
 * Depth (fatness) at heights: 
 * - 47cm: (112-94=18cm) = 22 studs
 * Widths at heights:
 * - 29cm/0/0%: 0
 * - 31cm/2/7%: 13cm
 * - 40cm/11/37%: 11cm
 * - 47cm/18/60%: 17cm (Hips)
 * - 50cm/21/70%: 18cm (Belly)
 * - 58cm/29/97%: 6cm
 * - 59cm/30/100%: 0
 * Belly color change: 1.6cm ~9%
 */
public class MonkeyBody extends TrivialVectorColoring {
	public static final double SHOULDER_HEIGHT = 0.88, SHOULDER_WIDTH = 0.705;
	public static final double BREAST_HEIGHT = 0.63, BREAST_WIDTH = 0.61;
	public static final double HIP_HEIGHT = 0.4, HIP_WIDTH = 0.94;
	public static final double BELLY_HEIGHT = 0.38, BELLY_WIDTH = 1, BELLY_COLOR_CHANGE = 0.1;
	// NOTICE: BELLY_COLOR_CHANGE = 0.09; for large
	public static final double BUTT_HEIGHT = 0.03, BUTT_WIDTH = 0.33;
	
	public static final Vector CENTER = new Vector(0.5, 0.5, 0.5);

	private LEGOColor brown, tan;

	public MonkeyBody(LEGOColor brown, LEGOColor tan) {
		if(brown == null)
			throw new IllegalArgumentException("Brown is null!");
		if(tan == null)
			throw new IllegalArgumentException("Tan is null!");
		this.brown = brown;
		this.tan = tan;
	}
	
	private static boolean inSqueezedSphere(double centerZ, double xyRadius, double zRadius, Vector v) {
		if(v.z < centerZ-zRadius || v.z > centerZ+zRadius)
			return false;
		// Scale the z-value of v:
		double vz = centerZ - (xyRadius/zRadius)*(centerZ-v.z);
		return new Vector(CENTER.x, CENTER.y, centerZ).dist(new Vector(v.x, v.y, vz)) <= xyRadius;
	}
	
	private static boolean inChestCone(Vector v) {
		double dx = v.x - CENTER.x;
		double dy = v.y - CENTER.y;
		double dxy = Math.sqrt(dy*dy+dx*dx);
		double coneDiamAtVz = BREAST_WIDTH + (SHOULDER_WIDTH-BREAST_WIDTH)*(v.z - BREAST_HEIGHT)/(SHOULDER_HEIGHT-BREAST_HEIGHT);
		return dxy <= coneDiamAtVz/2;
	}
	
	private static boolean inTummy(Vector v) {
		return inSqueezedSphere(BELLY_HEIGHT, BELLY_WIDTH/2, BELLY_HEIGHT, v);
	}

	private static boolean inCutout(Vector v) {
		double dy = Math.abs(v.y - CENTER.y);
		double widthAtZ = BUTT_WIDTH + (HIP_WIDTH-BUTT_WIDTH)*(v.z - BUTT_HEIGHT)/(HIP_HEIGHT-BUTT_HEIGHT);
		return dy <= widthAtZ/2;
	}
	
	@Override
	public boolean occupies(Vector v) {		
		// Above shoulders is a half sphere:
		if(v.z >= SHOULDER_HEIGHT) {
			return inSqueezedSphere(SHOULDER_HEIGHT, SHOULDER_WIDTH/2, 1-SHOULDER_HEIGHT, v);
		}
		if(inChestCone(v))
			return true;
		if(!inTummy(v))
			return false;
		return inCutout(v);
		//*/
	}
	
	/**
	 * v: height between 0 and 1.
	 */
	@Override
	public LEGOColor getColor(Vector v) {
		if(!occupies(v))
			return null;
		return v.x > BELLY_COLOR_CHANGE ? brown : tan;
	}
}
