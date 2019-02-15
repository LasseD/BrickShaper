package shapes.vector;

import colors.LEGOColor;

public class MathShapes {
	public static VectorShape union(final VectorShape a, final VectorShape b) {
		return new VectorShape() {			
			@Override
			public boolean occupies(Vector v) {
				return a.occupies(v) || b.occupies(v);
			}
		};
	}

	public static VectorShape intersect(final VectorShape a, final VectorShape b) {
		return new VectorShape() {			
			@Override
			public boolean occupies(Vector v) {
				return a.occupies(v) && b.occupies(v);
			}
		};
	}

	public static VectorShape sub(final VectorShape a, final VectorShape b) {
		return new VectorShape() {			
			@Override
			public boolean occupies(Vector v) {
				return a.occupies(v) && !b.occupies(v);
			}
		};
	}
	
	public static VectorShape complement(final VectorShape vs) {
		return new VectorShape() {			
			@Override
			public boolean occupies(Vector v) {
				return !vs.occupies(v);
			}
		};
	}

	public static VectorShape mirror(final VectorShape vs, final Dimension d) {
		return new VectorShape() {			
			@Override
			public boolean occupies(Vector v) {
				switch(d) {
				case x:
					return vs.occupies(new Vector(1-v.x, v.y, v.z));
				case y:
					return vs.occupies(new Vector(v.x, 1-v.y, v.z));
				case z:
					return vs.occupies(new Vector(v.x, v.y, 1-v.z));
				default:
					throw new IllegalStateException("!xyz");
				}
			}
		};
	}
	
	public static VectorColoring stretch(final VectorColoring a, final Vector scales) {
		return new TrivialVectorColoring() {			
			@Override
			public LEGOColor getColor(Vector v) {
				return a.getColor(new Vector(v.x/scales.x, v.y/scales.y, v.z/scales.z));
			}
		};
	}
	public static VectorShape stretch(final VectorShape a, final Vector scales) {
		return new VectorShape() {			
			@Override
			public boolean occupies(Vector v) {
				return a.occupies(new Vector(v.x/scales.x, v.y/scales.y, v.z/scales.z));
			}
		};
	}
	public static VectorShape stretch(final VectorShape a, double scale) {
		return stretch(a, new Vector(scale, scale, scale));
	}
	public static VectorColoring move(final VectorColoring a, final Vector move) {
		return new TrivialVectorColoring() {			
			@Override
			public LEGOColor getColor(Vector v) {
				return a.getColor(new Vector(v.x-move.x, v.y-move.y, v.z-move.z));
			}
		};
	}
	public static VectorColoring rotateX(final VectorColoring a, final double xRadians) {
		return new TrivialVectorColoring() {			
			@Override
			public LEGOColor getColor(Vector v) {
				Vector v2 = Vector.add(v, new Vector(-0.5, -0.5, 0));
				// Rotate v2:
				double c = Math.cos(xRadians);
		        double s = Math.sin(xRadians);
		        v2 = new Vector(c*v2.x - s*v2.y, s*v2.x + c*v2.y, v.z);
		        // Move back
				v2 = Vector.add(v2, new Vector(0.5, 0.5, 0));
		        
				return a.getColor(v2);
			}
		};
	}
	public static VectorColoring flip(final VectorColoring vs, final Dimension d) {
		return new TrivialVectorColoring() {		
			@Override
			public LEGOColor getColor(Vector v) {
				switch(d) {
				case x:
					return vs.getColor(new Vector(v.x, v.z, v.y));
				case y:
					return vs.getColor(new Vector(v.z, v.y, v.x));
				case z:
					return vs.getColor(new Vector(v.y, v.x, v.z));
				default:
					throw new IllegalStateException("!xyz");
				}
			}
		};
	}
	public static VectorShape move(final VectorShape a, final Vector move) {
		return new VectorShape() {			
			@Override
			public boolean occupies(Vector v) {
				return a.occupies(new Vector(v.x-move.x, v.y-move.y, v.z-move.z));
			}
		};
	}

}
