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
	    if(v.z >= swi.from && v.z <= swi.to) {
		Vector v2 = new Vector(v.x, v.y, (v.z-swi.from) / (swi.to-swi.from));
		return swi.shape.occupies(v2);
	    }
	}
	return false; // No shape at this height.
    }
	
    public void add(VectorShape s, double from, double to) {
	ShaperWithInterval swi = new ShaperWithInterval(s, from, to);
	l.add(swi);
    }
	
    private class ShaperWithInterval {
	private VectorShape shape;
	private double from, to;

	public ShaperWithInterval(VectorShape shape, double from, double to) {
	    if(from < 0 || to > 1 || from >= to)
		throw new IllegalArgumentException("Interval must be in [0,1]");
	    this.shape = shape;
	    this.from = from;
	    this.to = to;
	}
    }
}
