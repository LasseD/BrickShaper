package shapes.vector.printer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import building.Optimizer;
import building.Part;
import building.PartType;

import colors.LEGOColor;
import shapes.vector.Vector;
import shapes.vector.VectorColoring;

public class LDRPrinter implements Printer {
	private File file;
	private int w, l, h;
	
	public LDRPrinter(File file, int w, int l, int h) {
		if(file == null || w <= 0 || l <= 0 || h <= 0)
			throw new IllegalArgumentException();
		this.file = file;
		this.w = w;
		this.l = l;
		this.h = h;
	}

	@Override
	public void print(VectorColoring shape) {
		LEGOColor[][][] figure = new LEGOColor[h][l][w];
		for(int z = 0; z < h; z++) {
			for(int y = 0; y < l; y++) {
				for(int x = 0; x < w; x++) {
					Vector v = new Vector((x+.5)/w, (y+.5)/l, (z+.5)/h);
					figure[z][y][x] = shape.getColor(v);
				}
			}
		}
		//figure = makeTestModel();
		
		Optimizer optimizer = new Optimizer(figure);
		ArrayList<Part> placedParts = new ArrayList<Part>(optimizer.placedParts);
		//hollow(figure); // No need to draw invisible parts.
		
		try {
			print(placedParts);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private LEGOColor[][][] makeTestModel() {
		int depth = 0;
		for(PartType pt : PartType.partTypes) {
			depth += 1 + pt.getDepth();
		}
		
		LEGOColor[][][] ret = new LEGOColor[3][depth][16];
		int startY = 0;

		for(PartType pt : PartType.partTypes) {
			System.out.println("Adding part: " + pt.getID() + ": " + pt.getDepth() + " x " + pt.getWidth());
			for(int y = 0; y < pt.getDepth(); ++y) {
				for(int x = 0; x < pt.getWidth(); ++x) {
					for(int z = 0; z < 1; ++z) {
						ret[z][startY][x] = LEGOColor.BLACK;
					}
				}
				startY++;
			}
			++startY;
		}
		
		return ret;
	}
	
	private void print(List<Part> placedParts) throws IOException {
		FileOutputStream outStream = new FileOutputStream(file, false);
		PrintWriter out = new PrintWriter(outStream);
		String fileName = file.getName();
		if(!fileName.endsWith(".ldr"))
			fileName += ".ldr";
		out.println("0 " + fileName); // 0 Untitled
		out.println("0 Name: " + fileName); // 0 Name: Germany.ldr
		out.println("0 Unofficial Model"); // 0 Unofficial Model
		out.println("0 ROTATION CENTER 0 0 0 1 \"Custom\""); // 0 ROTATION CENTER 0 0 0 1 "Custom"
		out.println("0 ROTATION CONFIG 0 0"); // 0 ROTATION CONFIG 0 0 
		out.println("0 ROTSTEP 0 30 0 REL");
		
		build(out, 20, 20, -8, placedParts);
		
		out.println();
		outStream.flush();
		out.close();
		outStream.close();
	}
	
	private static void build(PrintWriter out, int xMult, int yMult, int zMult, List<Part> placedParts) {
		boolean anyStep = false;
		boolean first = true;
		boolean lastWasBrick = false;
		int prevZ = 0;
		
		for(Part p : placedParts) {
			boolean isBrick = p.type.getCategory() == PartType.Category.Brick;
			if(!first && (prevZ != p.z || isBrick && !lastWasBrick)) {
				out.println("0 STEP");
				anyStep = true;
			}
			prevZ = p.z;
			first = false;
			
			p.printLDR(out, xMult, yMult, zMult);
			lastWasBrick = isBrick;
		}
				
		if(!anyStep)
			out.println("0 STEP");			
	}	
}
