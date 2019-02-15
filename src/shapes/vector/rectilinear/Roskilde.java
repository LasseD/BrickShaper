package shapes.vector.rectilinear;

import shapes.vector.*;

public class Roskilde implements VectorShape {
	private Or cvs;
	public static final double scaleY = 76.0/116.0;
	public static final double scaleZ = (97.0/3*5/4)/116.0;
	
	public Roskilde() {
		cvs = new Or();
		
		// 3 cylinders:
		Cylinder cEntrance = new Cylinder(0.542/2, 0.5, scaleY, -0.055, 
												   0.5, scaleY-0.156, -0.055-0.222+0.177);		
		cvs.add(MathShapes.sub(cEntrance, new RectilinearHalfSpace(0.5*scaleY, Dimension.y)));
		
		Cylinder cRear1 = new Cylinder(0.203, 0.201, 0.179, -0.081, 0.283, 0.265, -0.081-0.01);
		VectorShape cRear1Cut = MathShapes.sub(cRear1, MathShapes.complement(new RectilinearHalfSpace(0.5, Dimension.x)));
		VectorShape cutOut = new VectorShape() {			
			@Override
			public boolean occupies(Vector v) {
				return v.x+v.y < 0.194+0.167;
			}
		};
		cvs.add(MathShapes.sub(cRear1Cut, cutOut));
		
		VectorShape cRear2Cut = MathShapes.mirror(cRear1, Dimension.x);
		cvs.add(MathShapes.sub(cRear2Cut, MathShapes.mirror(cutOut, Dimension.x)));
		
		// main poles:
		Pole pLeft = new Pole(0.203,scaleY-0.239, scaleZ*1.11, 0.437, 0.412);
		pLeft.edge_vertical = true;
		cvs.add(pLeft);		
		
		VectorShape pRight = MathShapes.mirror(pLeft, Dimension.x);
		cvs.add(pRight);		

		// plane between two main poles towards front (bow at top becomes line at bottom, follow vv sides):
		Plane planeFront = new Plane(pLeft, scaleZ-0.222);
		cvs.add(planeFront);		

		// rear pole similar.
		Pole pRear = new Pole(0.5,0.096, scaleZ, 0.378, 0.339);
		pRear.c_r *= 0.98;
		cvs.add(pRear);		

		// let sides of rear pole linear progress to center line:
		VPlane vPlane = new VPlane(pRear, pLeft, scaleZ-0.222);
		cvs.add(vPlane);
		// TODO: cut side openings in occupies vv. 
		
	}
	
	@Override
	public boolean occupies(Vector v_orig) {
		Vector v = new Vector(v_orig);
		v.y *= scaleY;
		v.z *= scaleZ;
		return cvs.occupies(v);
	}
	
	private static class VPlane implements VectorShape {
		private Pole poleTop, poleLeft;
		private double mid_dip;		
		
		public VPlane(Pole poleTop, Pole poleLeft, double md) {
			this.poleTop=poleTop;
			this.poleLeft=poleLeft;
			this.mid_dip = md;
		}
		
		@Override
		public boolean occupies(Vector v) {
			double ptr = poleTop.radius_at(v.z);
			double plr = poleLeft.radius_at(v.z);
			double ptop_x = Math.abs(poleTop.x-poleLeft.x);
			double ptop_y = Math.abs(poleTop.y-poleLeft.y);
			double ptop = Math.sqrt(ptop_x*ptop_x + ptop_y*ptop_y);
			double angle1 = Math.acos(Math.abs(Math.abs(ptr-plr)/ptop));
			double angle2 = Math.acos(ptop_x/ptop);
			double angle = angle1+angle2;
			
			double scaleX = v.z/(mid_dip); // to interpolate ^^

			// point 1:
			double plx = poleLeft.x + Math.cos(angle)*plr; // OK
			double ply = poleLeft.y - Math.sin(angle)*plr*(1-scaleX); // OK
			double ptx = poleTop.x + Math.sin(90-angle)*ptr; // OK
			double pty = poleTop.y - Math.cos(90-angle)*ptr;

			double scaleY = (v.y - pty) / (ply-pty);
			double scaled_x = ptx - scaleY * (ptx-plx);
			
			double fix = 0.1;
			if(v.z > mid_dip+fix) {	// actual height at top:
				double pole_height_at_v = poleTop.height_at_radius(Math.abs(poleTop.x-v.x)+0.01);				
				double max_x_diff = poleTop.radius_at(0);
				double x_diff = Math.abs(v.x-poleTop.x);
				double mid_dip_at_x = (mid_dip) * (1-x_diff/max_x_diff);
				
				double d = ((poleLeft.y-poleTop.y)*(poleLeft.y-poleTop.y)-mid_dip_at_x*mid_dip_at_x)/2/mid_dip_at_x;
				double dom_r = mid_dip_at_x + d;
				double dy = poleLeft.y-v.y;
				double dip = Math.sqrt(dom_r*dom_r - dy*dy)-d;
				return v.y < poleLeft.y && v.y > poleTop.y && v.z <= pole_height_at_v - dip;//*/				
			}

			if(v.y > ply)
				return v.y < poleLeft.y && plx < v.x && v.x < (1-plx);
			
			if(v.y < pty || v.x < scaled_x || v.x > 1-scaled_x)// || v.x > 1-scaled_x)
				return false;
			
			double width_z0 = 0.5-scaled_x;
			double width_z1 = ptr + scaleY*scaleY*scaleY*scaleY*scaleY*scaleY*(0.5-ptr-plx);
			return Math.abs(0.5-v.x) <= width_z0*(1-scaleX)+width_z1*scaleX;
		}		
	}

	private static class Plane implements VectorShape {
		private Pole p;
		private double mid_dip;
		
		public Plane(Pole p, double mid_dip) {
			this.p=p;
			this.mid_dip=mid_dip;
		}
		
		@Override
		public boolean occupies(Vector v) {
			double vx = v.x >= 0.5 ? 1-v.x : v.x;
			boolean in_x = vx >= p.x; // between pole and mirror
			if(!in_x)
				return false;
			double pr = p.radius_at(v.z);
			boolean in_y = Math.abs(v.y-p.y) <= pr; // not wider than pole.
			if(!in_y)
				return false;
			
			// actual height at top:
			double pole_height_at_y = p.height_at_radius(Math.abs(p.y-v.y));
				
			double max_y_diff = p.radius_at(0);
			double y_diff = Math.abs(v.y-p.y);
			double mid_dip_at_y = mid_dip * (1-y_diff/max_y_diff);
			
			/*
			 * b = diff x
			 * a = mid_dip_at_y
			 * d = dom_r-a
			 * r^2 = (a+d)^2 = d^2+b^2
			 * a^2 + d^2 + 2ad = d^2+b^2 => 
			 * a^2 + 2ad = b^2 =>
			 * d = (b^2-a^2)/2a =>
			 */
			double d = ((0.5-p.x)*(0.5-p.x)-mid_dip_at_y*mid_dip_at_y)/2/mid_dip_at_y;
			double dom_r = mid_dip_at_y + d;
			double dx = 0.5-vx;
			double dip = Math.sqrt(dom_r*dom_r - dx*dx)-d;
//			double dip = (dom_r-d)*dx/(0.5-p.x);
			return v.z <= pole_height_at_y - dip;
//			return v.z <= dip;
		}		
	}
	
	private static class Pole implements VectorShape {
		public double x, y, c_x, c_z, c_r;
		public boolean edge_vertical;
		
		/*
		 * Cone on [0,z] at (x,y) with radius c_x-dist-to-circle.
		 */
		public Pole(double x, double y, double z, double c_x, double c_z) {
			this.x=x;
			this.y=y;
			this.c_x=c_x;
			this.c_z=c_z;
			c_r = Math.sqrt(c_x*c_x+(c_z-z)*(c_z-z));
		}

		/*
		 * r = cx - sqrt(cr^2 - dz^2)
		 * (cx-r)^2 = cr^2 - dz^2
		 * dz = sqrt(cr^2 - (cx-r)^2)
		 */
		public double radius_at(double z) {
			return c_x - Math.sqrt(c_r*c_r - (c_z-z)*(c_z-z));
		}
		
		public double height_at_radius(double r) {
			double c_diff_x = c_x-r;
			double c_diff_z = Math.sqrt(c_r*c_r-c_diff_x*c_diff_x);
			return c_z-c_diff_z;
		}
		
		@Override
		public boolean occupies(Vector v) {
			double vx = v.x;
			double vy = v.y;
			double b = Math.sin(Math.PI/7)*radius_at(v.z);
			double c = Math.cos(Math.PI/7)*radius_at(v.z);
			if(!edge_vertical) {
				if(Math.abs(vx - x) < b) {
					return Math.abs(vy - y) < c+0.7*(b-Math.abs(vx - x));
				}
			}
			else {
				if(Math.abs(vy - y) < b) {
					return Math.abs(vx - x) < c+0.7*(b-Math.abs(vy - y));
				}
			}	
			
			double pole_to_v = Math.sqrt((vx-x)*(vx-x)+(vy-y)*(vy-y));
			return pole_to_v <= radius_at(v.z);
		}		
	}
}
