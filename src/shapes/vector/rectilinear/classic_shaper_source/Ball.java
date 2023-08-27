package shapes;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class Ball implements Shaper {
	private double w;
	
	/**
	 * 
	 * @param w Width, compared to the length of 1.
	 */
	public Ball(double w) {
		this.w=w;
	}
	
	public Shape shape(double z) {
		//z = Math.abs(1-2*z);
		z = 2*z-1;
		double width = w*Math.sqrt(1-z*z);
		double length = Math.sqrt(1-z*z);
		double offsetX = (w-width)/2-0.5;
		double offsetY = (1-length)/2-0.5;
		return new Ellipse2D.Double(offsetX, offsetY, width, length);
	}
}
