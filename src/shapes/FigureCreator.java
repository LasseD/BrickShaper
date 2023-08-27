package shapes;

import java.io.*;
import java.util.List;

import colors.*;
import colors.parsers.*;
import shapes.vector.*;
import shapes.vector.printer.*;
import shapes.vector.rectilinear.*;
import shapes.vector.rectilinear.coloring.*;

/**
 * A program to build shapes using LEGO bricks.
 * @author LD
 */
public class FigureCreator {
    public static void main(String[] args) throws IOException {
	if(args.length != 1) {
	    System.out.println("Usage: java " + FigureCreator.class.getName() + " width");
	    System.exit(0);
	}
	int width = Integer.parseInt(args[0]);

	List<LEGOColor> colors = ColorSheetParser.readColorsFile();
	LEGOColor red = null, white = null, black = null, blue = null;
	for(LEGOColor color : colors) {
	    if(color.getName().equals("White"))
		white = color;
	    else if(color.getName().equals("Red"))
		red = color;
	    else if(color.getName().equals("Black"))
		black = color;
	    else if(color.getName().equals("Blue"))
		blue = color;
	}
	MainVectorShape shape = new DubbolMillTower();
	VectorColoring model = TrivialVectorColoring.colorAShape(shape, new SolidColoring(white));

	// build:
	int length = shape.getLength(width);
	int height = shape.getHeightInPlates(width);
	Printer printer = new PictureInstructionsPrinter(width, height, length);
	Printer printer2 = new LDRPrinter(new File(shape.getClass().getName() + ".ldr"), width, length, height);

	printer.print(model);
	printer2.print(model);
	System.out.println();
	System.out.println("Shapes created.");
    }
}
