package shapes.vector.rectilinear;

import shapes.vector.Vector;
import shapes.vector.VectorShape;

public class Balloon implements VectorShape {
	public static final double HEIGHT_OF_BALL = 0.72; 
	public static final double LOWER_RADIUS = 0.05; 
	public static final double LOWER_FAT_ADD = 0.1; 	
	//public static final double SIDE_DEPRESSION_PART = 0.95; 	
	public static final double SIDE_DEPRESSION_PART = 0.9; 	
	private int sides;

	public Balloon(int sides) {
		this.sides = sides;
	}
	
	/**
	 * v: height between 0 and 1.
	 */
	@Override
	public boolean occupies(Vector v) {
		v.x = 0.5 - 0.98*(0.5-v.x);
		v.y = 0.5 - 0.98*(0.5-v.y);
		double dx = Math.abs(v.x-0.5);
		double dy = Math.abs(v.y-0.5);
		double angle = Math.atan2(v.y-0.5, v.x-0.5)*sides/2.0;
		
		double dzBallUpper = Math.abs(v.z-(1-HEIGHT_OF_BALL/2.0))/HEIGHT_OF_BALL;
		double distToCenterBallUpper = Math.sqrt(dx*dx + dy*dy + dzBallUpper*dzBallUpper);
		double radiusMultiplier = SIDE_DEPRESSION_PART + (1-SIDE_DEPRESSION_PART)*Math.abs(Math.sin(angle));
		
		double lowerPartHeight = 1-HEIGHT_OF_BALL/2;
		if(v.z > lowerPartHeight) {
			return distToCenterBallUpper <= 0.5 * radiusMultiplier;
		}
		else {
			double distToCenterLine = Math.sqrt(dx*dx + dy*dy);
			double coneRadius = LOWER_RADIUS + (0.5-LOWER_RADIUS)*v.z/lowerPartHeight + LOWER_FAT_ADD*Math.sin(Math.PI*v.z/lowerPartHeight);

			return distToCenterLine < coneRadius * radiusMultiplier;
		}
	}
}
