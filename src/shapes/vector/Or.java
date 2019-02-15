package shapes.vector;

import java.util.*;

public class Or implements VectorShape {
	private List<VectorShape> l;

	public Or() {
		l = new LinkedList<VectorShape>();
	}
	
	public void add(VectorShape s) {
		l.add(s);
	}
	
	@Override
	public boolean occupies(Vector v) {
		for(VectorShape s : l) {
			if(s.occupies(v))
				return true;
		}
		return false;
	}
	
}
