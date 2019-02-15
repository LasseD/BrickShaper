package shapes.vector.rectilinear;

import shapes.vector.*;

public class MinifigHead implements VectorShape {
	private static final double[] heights = {0.1, 0.276, 0.852-0.176, 0.852, 1}; // 1 cm = 0.852
	private static final double[] widths = {0.565/0.889, 0.889/0.889, 0.425/0.889}; //Width: 0.889 
	private StackedShape stackedShape; 
	
	public MinifigHead() {
		stackedShape = new StackedShape();
		stackedShape.add(new Cylinder(widths[0]/2), 0, heights[0]);
		stackedShape.add(new Doughnut(widths[0], heights[0], 2*heights[1]-heights[0]), heights[0], heights[1]);
		stackedShape.add(new Cylinder(widths[1]/2), heights[1], heights[2]);
		stackedShape.add(new Doughnut(widths[0], 2*heights[2]-heights[3], heights[3]), heights[2], heights[3]);
		stackedShape.add(new Cylinder(widths[2]/2), heights[3], heights[4]);
	}
	
	@Override
	public boolean occupies(Vector v) {		
		return stackedShape.occupies(v);
	}
}
