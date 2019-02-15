package shapes.vector;

import colors.LEGOColor;

public abstract class TrivialVectorColoring implements VectorColoring {
	@Override
	public boolean occupies(Vector v) {
		return getColor(v) != null;
	}
	
	public static VectorColoring colorAShape(final VectorShape model, final VectorColoring coloring) {
		return new VectorColoring() {

			@Override
			public boolean occupies(Vector v) {
				return model.occupies(v);
			}

			@Override
			public LEGOColor getColor(Vector v) {
				if(!model.occupies(v))
					return null;
				return coloring.getColor(v);
			}
			
		};
	}
}
