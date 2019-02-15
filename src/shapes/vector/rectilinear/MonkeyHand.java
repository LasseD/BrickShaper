package shapes.vector.rectilinear;

import shapes.vector.*;

/**
 * Run for size: 12 12 10
 * @author LD
 */
public class MonkeyHand implements VectorShape {
	private final double FAT_DIAM;
	private final double THIN_DIAM;
	private final double CURVE_RADIUS;
	private final Vector TOP_ARM_POINT;
	private final Vector TOP_FINGER_POINT;
	private final Vector TOP_MID_POINT;

	public MonkeyHand(double scale) {		
		FAT_DIAM = 0.5*scale;
		THIN_DIAM = 0.2*scale;
		CURVE_RADIUS = (scale-FAT_DIAM/2-THIN_DIAM/2)/2;
		TOP_ARM_POINT = new Vector(FAT_DIAM/2, 0.5, 1);
		TOP_FINGER_POINT = new Vector(scale-THIN_DIAM/2, 0.5, 1);
		TOP_MID_POINT = Vector.mid(TOP_ARM_POINT, TOP_FINGER_POINT);
	}
	public MonkeyHand() {
		this(1);
	}
	
	@Override
	public boolean occupies(Vector v) {
		if(v.z < 0 || v.z > 1)
			return false;
		
		double dx = v.x - TOP_MID_POINT.x;
		double dz = v.z - TOP_MID_POINT.z;
		double angleOfXZProjection = Math.atan2(dz, dx);
		Vector midOfSphere = new Vector(
				CURVE_RADIUS * Math.cos(angleOfXZProjection),
				0,
				CURVE_RADIUS * Math.sin(angleOfXZProjection));
		midOfSphere = Vector.add(TOP_MID_POINT, midOfSphere);
		
		double angleRatio = (-angleOfXZProjection)/(Math.PI);
		double scaleDivisor = 0.3 + 0.7*angleRatio;
		v.x = midOfSphere.x + (v.x - midOfSphere.x)/scaleDivisor;
		v.z = midOfSphere.z + (v.z - midOfSphere.z)/scaleDivisor;
		
		return midOfSphere.dist(v) <= FAT_DIAM/2;
	}
}
