package shapes;

import java.awt.*;
import java.awt.geom.*;

public class Cement implements Shaper {
	private static double[] offsets = {6/20.0, 11/20.0};
	
	// just upper quarter of the barrel.
	public Shape shape(double z) {
		Path2D.Double nGon = new Path2D.Double();
		// z: 0 - w12
		// x down, y width.
		double w4 = 4/12.0;
		double w8 = 8/12.0;
		double w23 = 2/3.0;
		double w13 = 1/3.0;
		
		// first point:
		double offset_x = 0;
		double offset_y = 0;
		System.out.println("For z=" + z);
		double small_z = z/w23; // 0->1 on z 0->w4
		if(z > w23) {
			offset_x = offsets[0] * (z-w23) / w13;
			nGon.moveTo(0,0); // dummy for preventing inversion.
			nGon.lineTo(offset_x,0.01);
			System.out.println("ox:" + offset_x);
		}
		else {
			offset_y = w23 * Math.sqrt(1-small_z*small_z);
			nGon.moveTo(0,0);			
			nGon.lineTo(0,offset_y);			
			System.out.println("oy:" + offset_y);
		}
		
		// Two middle points.
		double w_center = Math.sqrt(1-z*z);		
		System.out.println("lt:" + offsets[0] + "," + w_center);
		nGon.lineTo(offsets[0],w_center);
		System.out.println("lt:" + offsets[1] + "," + w_center);
		nGon.lineTo(offsets[1],w_center);

		// last point:
		if(z > w23) { // z > 4
			System.out.println("et1:" + (1-(1-offsets[1])*(z-w23)/w13));
			nGon.lineTo(1-(1-offsets[1])*(z-w23)/w13, 0);			
		}
		else {
			nGon.lineTo(1, w23*Math.sqrt(1-small_z*small_z));			
			nGon.lineTo(1, 0);			
			System.out.println("et2:" + w23*Math.sqrt(1-small_z*small_z));
		}
		
		nGon.closePath();
		
		return nGon;//*/
	}
}
