package shapes.vector;

import colors.LEGOColor;

public class SolidColoring implements VectorColoring {
	private LEGOColor color;
	
	public SolidColoring(LEGOColor color) {
		this.color = color;
	}
	
	@Override
	public boolean occupies(Vector v) {
		return true;
	}

	@Override
	public LEGOColor getColor(Vector v) {
		return color;
	}
}
