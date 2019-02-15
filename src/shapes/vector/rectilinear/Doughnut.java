package shapes.vector.rectilinear;

import shapes.vector.*;

public class Doughnut implements VectorShape {
	private double wBase, zBottom, zTop;
	
	public Doughnut(double wBase, double zBottom, double zTop) {
		this.wBase=wBase;
		this.zBottom=zBottom;
		this.zTop=zTop;
	}
	
	@Override
	public boolean occupies(Vector v) {
		if(v.z < zBottom || v.z > zTop)
			return false;
		double cos = 2*(v.z-zBottom)/(zTop-zBottom)-1;
		double w = wBase + (1-wBase)*(Math.sqrt(1-cos*cos)); // With of bowl
		
		double wz = 2*Math.sqrt((v.x-0.5)*(v.x-0.5)+(v.y-0.5)*(v.y-0.5));
				
		return wz <= w;
	}

}
