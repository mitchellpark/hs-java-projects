
/*
 * File: FeltBoard.java
 * --------------------
 * This program offers a simple example of the acm.graphics package
 * that draws a red rectangle and a green oval.  The dimensions of
 * the rectangle are chosen so that its sides are in proportion to
 * the "golden ratio" thought by the Greeks to represent the most
 * aesthetically pleasing geometry. 
 */
 
import acm.program.*;
import acm.graphics.*;
import java.awt.*;
 
public class Game extends GraphicsProgram {
 
/** Runs the program */
   public void run() {
      GRect rect = new GRect(100, 50, 100, 100 / PHI);
      rect.setFilled(true);
      rect.setColor(Color.RED);
      add(rect);
      GOval oval = new GOval(150, 50 + 50 / PHI, 100, 100 / PHI);
      oval.setFilled(true);
      oval.setColor(Color.GREEN);
      add(oval);
   }
 
/** Constant representing the golden ratio */
   public static final double PHI = 1.618;
 
} 