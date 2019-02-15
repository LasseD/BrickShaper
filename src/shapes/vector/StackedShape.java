package shapes.vector;

import java.util.*;

public class StackedShape implements VectorShape {
	private List<ShaperWithInterval> l;

	public StackedShape() {
		l = new LinkedList<ShaperWithInterval>();
	}
	
	@Override
	public boolean occupies(Vector v) {
		for(ShaperWithInterval swi : l) {
			if(swi.occupies(v))
				return true;
		}
		return false;
	}
	
	public void add(VectorShape s, double from, double to) {
		ShaperWithInterval swi = new ShaperWithInterval(s, from, to);
		l.add(swi);
	}
	
	private class ShaperWithInterval implements VectorShape {
		private VectorShape shape;
		private double from, to;
		
		public ShaperWithInterval(VectorShape shape, double from, double to) {
			if(from < 0 || to > 1 || from > to)
				throw new IllegalArgumentException("Interval must be in [0,1]");
			this.shape = shape;
			this.from = from;
			this.to = to;
		}
		
		@Override
		public boolean occupies(Vector v) {
			return from <= v.z && to >= v.z && shape.occupies(v);
		}
	}

}
