package shapes.vector.rectilinear;

import colors.LEGOColor;
import shapes.vector.Ball;
import shapes.vector.ConicalFrustum;
import shapes.vector.MathShapes;
import shapes.vector.StackedColoring;
import shapes.vector.Vector;
import shapes.vector.VectorShape;

/**
 * @author LD
 *
 * Run for 27 27 22
 *
 * Height:
 *  21 (leg) + 6 (foot)
 * Diam: 
 *  Full = Foot turning => 18
 *  Top ball = 8.5/18
 *  Wrist = 5.5/18
 */
public class MonkeyLeg extends StackedColoring {
	public static final double BALL_SHIFT_HEIGHT = 23.0/27.0;
	public static final double WRIST_HEIGHT = 5.0/27.0;	
	public static final double BALL_DIAM = 9.7/27.0;
	public static final double WRIST_DIAM = 6/27.0;

	public MonkeyLeg(LEGOColor brown, LEGOColor tan) {
		// Top ball:
		VectorShape ball = new Ball();
		ball = MathShapes.stretch(ball, new Vector(BALL_DIAM/2,BALL_DIAM/2,1-BALL_SHIFT_HEIGHT));
		ball = MathShapes.move(ball, new Vector(0.5, 0.5, BALL_SHIFT_HEIGHT));
		add(ball, BALL_SHIFT_HEIGHT, 1, brown);
		// Cone leg:
		ConicalFrustum cone = new ConicalFrustum(WRIST_DIAM/2, BALL_DIAM/2, 
				new Vector(0.5, 0.5, WRIST_HEIGHT), new Vector(0.5, 0.5, BALL_SHIFT_HEIGHT));
		add(cone, WRIST_HEIGHT, BALL_SHIFT_HEIGHT, brown);
		// Hand:
		VectorShape hand = new MonkeyHand(2*WRIST_DIAM);
		//hand = MathShapes.stretch(hand, new Vector(1, 1, 0.55));
		hand = MathShapes.move(hand, new Vector(0.39, 0, -1+WRIST_HEIGHT));
		add(hand, 0, WRIST_HEIGHT, tan);
	}	
}
