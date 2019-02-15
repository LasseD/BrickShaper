package shapes.vector.rectilinear;

import colors.LEGOColor;
import shapes.vector.TrivialVectorColoring;
import shapes.vector.Vector;

/*
 * Colors like a lying barber shop sign. 
 * Sign axis is the x-axis. (along vector 1,0,0)
 * 1 full rotation per unit length.
 * Clockwise rotations along the positive x-axle direction.
 */
public class ScrewColoring extends TrivialVectorColoring {
	private LEGOColor[] colors;

	public ScrewColoring(LEGOColor[] colors) {
		this.colors = colors;
	}
	
	public ScrewColoring(LEGOColor primary, LEGOColor secondary, int colorsPrCircle) {
		colors = new LEGOColor[colorsPrCircle];
		colors[0] = primary;
		colors[1] = secondary;
		for(int i = 2; i < colorsPrCircle; ++i) {
			colors[i] = colors[i%2];
		}
	}
	
	@Override
	public LEGOColor getColor(Vector v) {
		double rot = Math.atan(v.z/v.y);
		double part = (rot + Math.PI/2);
		if(v.y < 0)
			part += Math.PI;
		// x => current rotation:
		part += v.x*Math.PI*2;
		int colorIndex = (int)Math.round(part / Math.PI / 2 * colors.length);
		return colors[colorIndex % colors.length];
	}	
}
