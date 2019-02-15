package shapes;

import java.io.*;
import java.util.List;

import colors.*;
import colors.parsers.*;
import shapes.vector.*;
import shapes.vector.printer.*;
import shapes.vector.rectilinear.*;
import shapes.vector.rectilinear.coloring.MColoring;

/**
 * A program to build shapes using LEGO bricks.
 * @author LD
 */
public class FigureCreator {
	public static void main(String[] args) throws IOException {
		if(args.length < 3) {
			System.out.println("Usage: java " + FigureCreator.class.getName() + " length width height");
			System.exit(0);	
		}
		int length = Integer.parseInt(args[0]);
		int width = Integer.parseInt(args[1]);
		int height = Integer.parseInt(args[2])*3;

		//Balloon model = new Balloon(12);
		List<LEGOColor> colors = ColorSheetParser.readColorsFile();		
		LEGOColor red = null;
		LEGOColor white = null;
		LEGOColor black = null;
		LEGOColor tan = null;
		LEGOColor blue = null;
		LEGOColor brown = null;
		String[] lDrawToRGB = new String[1000];
		for(LEGOColor color : colors) {
			for(ColorIdNamePair lDrawID : color.getLDraw()) {
				int r = color.getRGB().getRed();
				int g = color.getRGB().getGreen();
				int b = color.getRGB().getBlue();
				lDrawToRGB[lDrawID.getID()] = String.format("%02x%02x%02x", r, g, b);
			}
			
			if(color.getName().equals("White"))
				white = color;
			else if(color.getName().equals("Red"))
				red = color;
			else if(color.getName().equals("Reddish Brown"))
				brown = color;
			else if(color.getName().equals("Black"))
				black = color;
			else if(color.getName().equals("Blue"))
				blue = color;
			else if(color.getName().equals("Tan"))
				tan = color;
		}
		for(int jj = 0; jj < 1000; ++jj) {
			if(lDrawToRGB[jj] != null)
				System.out.println("ldraw_colors[" + jj + "] = 0x" + lDrawToRGB[jj] + ";");
		}
		//VectorColoring model = new MonkeyHead(brown, tan, black);
		//VectorColoring model = new MonkeyBody(brown, tan);
		//VectorShape shape = new MonkeyHand(1); 
		//VectorColoring model =  TrivialVectorColoring.colorAShape(shape, new SolidColoring(tan));
					
		
		// Leg:
		MonkeyLeg shape = new MonkeyLeg(brown, tan);
		VectorColoring model = MathShapes.flip(shape, Dimension.x);
		model = MathShapes.flip(model, Dimension.y);
		model = MathShapes.rotateX(model, Math.atan2(-3.6, 9));
		model = MathShapes.move(model, new Vector(-0.25, 0, 0));//*/
		model = MathShapes.flip(model, Dimension.z);
		
		//VectorColoring shape = TrivialVectorColoring.colorAShape(model, new SolidColoring(tan));
		//VectorColoring shape = TrivialVectorColoring.colorAShape(model, new MColoring(white, red));
		//shape = MathShapes.stretch(shape, new Vector(1, 0.5*29.0/30.0, 0.5));
		//shape = MathShapes.move(shape, new Vector(0, 4.0/14.0, -10.0/14.0));
		//VectorColoring model = TrivialVectorColoring.colorAShape(halo, new SolidColoring(blue));
		
		// build:
		Printer printer = new PictureInstructionsPrinter(width, height, length);
		Printer printer2 = new LDRPrinter(new File(shape.getClass().getName() + ".ldr"), width, length, height);

		printer.print(model);
		printer2.print(model);
		System.out.println();
		System.out.println("Shapes created.");
	}
}
