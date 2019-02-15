package shapes.vector.rectilinear.coloring;

import colors.LEGOColor;
import shapes.vector.TrivialVectorColoring;
import shapes.vector.Vector;

/*
 * Colors like a lying barber shop sign. 
 * Sign axis is the x-axis. (along vector 1,0,0)
 * 1 full rotation per unit length.
 * Clockwise rotations along the positive x-axle direction.
 */
public class MColoring extends TrivialVectorColoring {
	private LEGOColor primary, secondary;
	
	private static final double M_BOTTOM = 0.4;
	private static final double M_V_BOTTOM = 0.55;
	private static final double M_TOP = 0.8;
	private static final double M_STROKE_WIDTH = 0.05;
	private static final double M_WIDTH = 0.35;		

	public MColoring(LEGOColor primary, LEGOColor secondary) {
		this.primary = primary;
		this.secondary = secondary;
	}
	
	@Override
	public LEGOColor getColor(Vector v) {
		return isM(v) ? primary : secondary;
	}	

	public static boolean isM(Vector v) {
		if(v.z < M_BOTTOM)
			return false;
		if(v.z > M_TOP)
			return false;
		
		double dx = Math.abs(v.x - 0.5);
		double dy = Math.abs(v.y - 0.5);
		// swap:
		if(dx > dy) {
			double tmp = v.x;
			v.x = v.y;
			v.y = tmp;
			tmp = dx;
			dx = dy;
			dy = tmp;
		}
		
		// Lines on sides:
		if(Math.abs(dx-M_WIDTH/2) < M_STROKE_WIDTH)
			return true;
		
		// V lines in middle:
		if(v.z < M_V_BOTTOM)
			return false;
		double targetDX = (v.z-M_V_BOTTOM)/(M_TOP-M_V_BOTTOM)*M_WIDTH/2;
		
		return Math.abs(dx-targetDX) < M_STROKE_WIDTH;
	}	
}
