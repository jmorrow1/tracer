package paths;

import processing.core.PApplet;

/**
 * 
 * @author James Morrow
 *
 */
public interface Traceable {
	final static Point pt = new Point(0, 0);
	
	public void trace(Point pt, float amt);
	public default Point trace(float amt) {
		Point pt = new Point(0, 0);
		trace(pt, amt);
		return pt;
	}
	public void display(PApplet pa);
	public default void display(PApplet pa, int granularity) {
		float amt = 0;
		float dAmt = 1f/granularity;
		pa.beginShape();
		for (int i=0; i<granularity+1; i++) {
			trace(pt, amt);
			pa.vertex(pt.x, pt.y);
			amt += dAmt;
		}
		pa.endShape();
	}
	public void translate(float dx, float dy);
}