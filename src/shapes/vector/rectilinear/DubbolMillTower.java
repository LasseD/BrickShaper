package shapes.vector.rectilinear;

import shapes.vector.*;

/*
  Dimensions:
  Dexcrition             height          width
  Top of tower              658            222
  Merge                     360            339
  Flair outer octagon       323            376
  Flair inner octagon       323            356
  Base                        0            428
 */
public class DubbolMillTower extends StackedShape implements MainVectorShape {
    public static final int N = 8;
    
    public static final double widthTop          = 222.0/428.0;
    public static final double widthMerge        = 339.0/428.0;
    public static final double widthOuterOctagon = 376.0/428.0;
    public static final double widthInnerOctagon = 356.0/428.0;
    public static final double widthBase         = 1;

    public static final double heightTop          = 658.0/658.0;
    public static final double heightMerge        = 360.0/658.0;
    public static final double heightOuterOctagon = 323.0/658.0;
    public static final double heightInnerOctagon = 323.0/658.0;
    public static final double heightBase         = 0;

    double octagonBoost = 1.05;
    
    public DubbolMillTower() {
	add(new NGonCone(N, widthBase*octagonBoost, widthInnerOctagon*octagonBoost), heightBase, heightInnerOctagon);

	Or nGonToCone = new Or();
	nGonToCone.add(new Cone(widthMerge, widthMerge));
	nGonToCone.add(new NGonCone(N, widthOuterOctagon*octagonBoost, widthMerge));
	add(nGonToCone, heightOuterOctagon, heightMerge);//*/

	add(new Cone(widthMerge, widthTop), heightMerge, heightTop);
    }

    @Override
    public int getHeightInPlates(int width) {
	double heightInBricks = width * 658.0 / 428.0;
	return (int)Math.round(3*heightInBricks*0.8);
    }

    @Override
    public int getLength(int width) {
	return width;
    }
}
