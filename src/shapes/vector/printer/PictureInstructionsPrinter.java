package shapes.vector.printer;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import shapes.vector.*;

import javax.imageio.*;

import colors.LEGOColor;

public class PictureInstructionsPrinter implements Printer {
	public static final int BLOW_UP_FACTOR = 26; // larger, the better precision and longer computation time
	public static final Color[] colors = {
		Color.WHITE, // 0: None
		Color.RED, // 1: lv. 1
		Color.YELLOW, // 2: lv. 2
		Color.ORANGE, // 3: lv. 1+2
		Color.BLUE, // 4: lv. 3
		Color.PINK, // 5: lv. 1+3
		Color.MAGENTA, // 6: lv 2+3
		new Color(0, 200, 0), // 7: All
	};
	
	private int width, height, length;
	
	public PictureInstructionsPrinter(int w, int h, int l) {
		width = w;
		height = h;
		length = l;
	}
	
	private static LEGOColor[][][] createFigure(int width, int length, int height, VectorShape shape, LEGOColor c) {
		LEGOColor[][][] figure = new LEGOColor[width][length][height];
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < length; y++) {
				for(int z = 0; z < height; z++) {
					Vector v = new Vector((x+.5)/width, (y+.5)/length, (z+.5)/height);
					figure[x][y][z] = shape.occupies(v) ? c : null;
				}
			}
		}
		return figure;
	}
	private static LEGOColor[][][] createFigure(int width, int length, int height, VectorColoring shape) {
		LEGOColor[][][] figure = new LEGOColor[width][length][height];
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < length; y++) {
				for(int z = 0; z < height; z++) {
					Vector v = new Vector((x+.5)/width, (y+.5)/length, (z+.5)/height);
					figure[x][y][z] = shape.getColor(v);
				}
			}
		}
		return figure;
	}
	
	public static final int RULER_WIDTH = 140;
	
	private static void drawRuler(Graphics2D g2, int offsetX, int height, int layer, int layers) {
		int lowerLine = height-Math.round(height * layer / (float)layers); 
		int upperLine = height-Math.round(height * (layer+1) / (float)layers); 
		int lineStep = (lowerLine-upperLine)/3;
		assert(lineStep > 0);
		int midLine1 = upperLine+lineStep;
		int midLine2 = upperLine+2*lineStep;
		
		for(int i = 1; i < colors.length; i++) {
			int x = offsetX + RULER_WIDTH/7*(i-1);
			if((i & 4) == 4) {
				g2.setColor(colors[i]);
				g2.fillRect(x, upperLine, RULER_WIDTH/7, lineStep);			
				g2.setColor(Color.BLACK);
				g2.drawRect(x, upperLine, RULER_WIDTH/7, lineStep);			
			}
			if((i & 2) == 2) {
				g2.setColor(colors[i]);
				g2.fillRect(x, midLine1, RULER_WIDTH/7, lineStep);			
				g2.setColor(Color.BLACK);
				g2.drawRect(x, midLine1, RULER_WIDTH/7, lineStep);			
			}
			if((i & 1) == 1) {
				g2.setColor(colors[i]);
				g2.fillRect(x, midLine2, RULER_WIDTH/7, lineStep);			
				g2.setColor(Color.BLACK);
				g2.drawRect(x, midLine2, RULER_WIDTH/7, lineStep);			
			}
		}

		g2.setColor(Color.BLACK);
		g2.drawLine(offsetX, 0, offsetX, height);
		g2.drawLine(offsetX, lowerLine, offsetX+RULER_WIDTH, lowerLine);		
		g2.drawLine(offsetX, midLine1, offsetX+RULER_WIDTH, midLine1);		
		g2.drawLine(offsetX, midLine2, offsetX+RULER_WIDTH, midLine2);		
		g2.drawLine(offsetX, upperLine, offsetX+RULER_WIDTH, upperLine);		
		
		for(int i = 0; i < layers; i++) {
			int line = height-Math.round(height * i / (float)layers);
			g2.drawLine(offsetX, line, offsetX+5, line);		
		}
		
		if(layer != 0)
			g2.drawString(""+layer, offsetX+10, lowerLine+17);
		if(layer != layers-1)
			g2.drawString(""+(layers-layer-1), offsetX+10, upperLine - 5);
	}
	
	@Override
	public void print(VectorColoring shape) {
		// build:
		int wScaled = width*BLOW_UP_FACTOR;
		int lScaled = length*BLOW_UP_FACTOR;
		LEGOColor[][][] figure = createFigure(width, length, height, shape);
		
		// make directory:
		String dirname = shape.getClass().getName() + "_" + width + "_" + length + "_" + height;
		File dir = new File(dirname);
		dir.mkdir();

		// draw images:
		BufferedImage image = new BufferedImage(wScaled+10+RULER_WIDTH, lScaled, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D)image.getGraphics();
		g2.setBackground(Color.WHITE);
		for(int z = 0; z < height; z+=3) {
			g2.clearRect(0,  0, image.getWidth(), image.getHeight());
			// draw the small rectangles:
			for(int x = 0; x < width; x++) {
				for(int y = 0; y < length; y++) {
					LEGOColor l1 = figure[x][y][z];
					LEGOColor l2 = z+1 < height ? figure[x][y][z+1] : null;
					LEGOColor l3 = z+2 < height ? figure[x][y][z+2] : null;
					int colorIndex = (l1 != null ? 1 : 0) + (l2 != null ? 2 : 0) + (l3 != null ? 4 : 0);					
					g2.setColor(colors[colorIndex]);
					g2.fillRect(x*BLOW_UP_FACTOR, y*BLOW_UP_FACTOR, BLOW_UP_FACTOR, BLOW_UP_FACTOR);
					Color border = Color.BLACK;
					if((x+y) % 2 == 0)
						border = Color.LIGHT_GRAY;
					g2.setColor(border);
					g2.drawRect(x*BLOW_UP_FACTOR, y*BLOW_UP_FACTOR, BLOW_UP_FACTOR, BLOW_UP_FACTOR);
					if(colorIndex != 0) {
						int in = BLOW_UP_FACTOR/5;
						int ox = x*BLOW_UP_FACTOR + in;
						int oy = y*BLOW_UP_FACTOR+in;
						int ow = BLOW_UP_FACTOR-2*in;
						int oh = BLOW_UP_FACTOR-2*in;
						if(l1 != null) {
							g2.setColor(l1.getRGB());
							g2.fillOval(ox, oy, ow, oh);
						}
						else if(l2 != null) {
							g2.setColor(l2.getRGB());
							g2.fillOval(ox, oy, ow, oh);
						}
						else if(l3 != null) {
							g2.setColor(l3.getRGB());
							g2.fillOval(ox, oy, ow, oh);
						}
						g2.setColor(Color.BLACK);
						g2.drawOval(ox, oy, ow, oh);							
					}
				}
			}
			
			g2.setFont(new Font("Times New Roman", Font.BOLD, 16));
			drawRuler(g2, width*BLOW_UP_FACTOR+10, image.getHeight(), z/3, (int)Math.ceil(height/3.0));
			
			g2.setColor(Color.RED);
			g2.setFont(new Font("Times New Roman", Font.BOLD, 36));
			g2.drawString("Layer " + (z/3+1), 8, 30);
			// write image:
			try {
				ImageIO.write(image, "png", new File(dirname + File.separator + (z/3+1) + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
	}
	
	/*	
	//Legacy code to draw instructions.
	private static boolean colored(int[] pixels, int noColor) {
		int countNoColor = 0;
		for(int color : pixels) {
			if(color == noColor)
				countNoColor++;
		}
		return countNoColor*2 < pixels.length;
	}
	
	public static boolean[][][] createFigure(int w, int l, int h, Shaper p) {
		boolean[][][] out = new boolean[w][l][h];
		
		int wScaled = w*BLOW_UP_FACTOR;
		int lScaled = l*BLOW_UP_FACTOR;

		BufferedImage image = new BufferedImage(wScaled, lScaled, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D)image.getGraphics();
		for(int layer = 0; layer < h; layer++) {
			// clear:
			g2.setColor(Color.WHITE);
			g2.fillRect(0, 0, wScaled, lScaled);
			// draw shape:
			g2.setColor(Color.BLACK);
			Shape shape = p.shape((layer+0.5)/h);
			AffineTransform transform = AffineTransform.getScaleInstance(wScaled, lScaled);
			g2.fill(transform.createTransformedShape(shape));
			
			// draw the small rectangles:
			int[] rgbs = image.getRGB(0, 0, wScaled, lScaled, null, 0, wScaled);
			int color1 = rgbs[0];			
			for(int x = 0; x < w; x++)
				for(int y = 0; y < l; y++) {
					boolean colored = !colored(image.getRGB(
							x*BLOW_UP_FACTOR, 
							y*BLOW_UP_FACTOR, 
							BLOW_UP_FACTOR, 
							BLOW_UP_FACTOR, null, 0, BLOW_UP_FACTOR), color1);
					if(colored)
						out[x][y][layer] = true;
				}
		}
		return out;
	}*/
	
}
