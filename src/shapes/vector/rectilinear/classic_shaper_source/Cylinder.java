package shapes;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

public class Cylinder implements Shaper {
	private double w, l;
	private boolean ellipsiodal;
	
	public Cylinder(double w, double l) {
		this.w=w;
		this.l=l;
		ellipsiodal = true;
	}
	
	public Cylinder(double w, double l, double wAdd, double lAdd) {
		this.w=w+wAdd;
		this.l=l+lAdd;
		ellipsiodal = false;
	}
	
	public Shape shape(double z) {
		if(ellipsiodal)
			return new Ellipse2D.Double(0, 0, w, l);
		
		double archWidth = Math.min(w, l)/2;
		return new RoundRectangle2D.Double(0, 0, w, l, archWidth, archWidth);
	}
}
