package shapes;

import java.awt.*;

public class OctanTrailerRear implements Shaper {
	private double width, length, indent;
	
	public OctanTrailerRear(double width, double length, double indent) {
		this.width = width;
		this.length = length;
		this.indent = indent;
	}
	
	public Shape shape(double z) {
		Polygon p = new Polygon();

		p.addPoint(0, 0);
		p.addPoint(0, (int)Math.round(1.05*length-indent*z));
		int fwdDown = (int)Math.round(2.1*width/3.0*z);
		int w = (int)Math.round(Math.min(width, width/3+fwdDown));
		p.addPoint(w, (int)Math.round(length/6));
		p.addPoint(w, 1);
		
		return p;
	}
}
