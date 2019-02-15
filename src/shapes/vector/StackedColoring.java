package shapes.vector;

import java.util.*;

import colors.LEGOColor;

public class StackedColoring implements VectorColoring {
	private List<ColoringWithInterval> l;

	public StackedColoring() {
		l = new LinkedList<ColoringWithInterval>();
	}
	
	@Override
	public LEGOColor getColor(Vector v) {
		for(ColoringWithInterval swi : l) {
			if(swi.occupies(v))
				return swi.getColor(v);
		}
		return null;
	}
	
	public void add(VectorColoring s, double from, double to) {
		ColoringWithInterval swi = new ColoringWithInterval(s, from, to);
		l.add(swi);
	}
	public void add(VectorShape vs, double from, double to, LEGOColor c) {
		VectorColoring s = TrivialVectorColoring.colorAShape(vs, new SolidColoring(c));
		ColoringWithInterval swi = new ColoringWithInterval(s, from, to);
		l.add(swi);
	}
	
	private class ColoringWithInterval implements VectorColoring {
		private VectorColoring shape;
		private double from, to;
		
		public ColoringWithInterval(VectorColoring shape, double from, double to) {
			if(from < 0 || to > 1 || from > to)
				throw new IllegalArgumentException("Interval must be in [0,1]");
			this.shape = shape;
			this.from = from;
			this.to = to;
		}
		
		@Override
		public LEGOColor getColor(Vector v) {
			return shape.getColor(v);
		}

		@Override
		public boolean occupies(Vector v) {
			return from <= v.z && to >= v.z;
		}
	}

	@Override
	public boolean occupies(Vector v) {
		return getColor(v) != null;
	}
}
