package shapes;

import java.awt.Polygon;
import java.awt.Shape;

public class OctanTrailerFront implements Shaper {
	private double height, length;
	
	public OctanTrailerFront(double height, double length) {
		this.length = length;
		this.height = height;
	}
	
	public Shape shape(double z) {
		Polygon p = new Polygon();

		p.addPoint(0, 0);
		p.addPoint(0, (int)Math.round(1.2*length*z));
		p.addPoint((int)Math.round(1.2*height*z), 1);
		
		return p;
	}
}
