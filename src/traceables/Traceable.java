package traceables;

import processing.core.PApplet;

/**
 * 
 * 
 * @author James Morrow
 *
 */
public interface Traceable {
	final static Point pt = new Point(0, 0);
	
	/**
	 * 
	 * @param pt
	 * @param amt
	 */
	public void trace(Point pt, float amt);
	
	/**
	 * 
	 * @param amt
	 * @return
	 */
	public default Point trace(float amt) {
		Point pt = new Point(0, 0);
		trace(pt, amt);
		return pt;
	}

	/**
	 * 
	 * @param pa
	 */
	public void display(PApplet pa);
	
	/**
	 * 
	 * @param pa
	 * @param granularity
	 */
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
	/**
	 * 
	 * @param dx
	 * @param dy
	 */
	public void translate(float dx, float dy);
	
	/**
	 * Returns the slope of the Point on the Path at trace(amt).
	 * @param amt
	 * @return the slope
	 */
	public default float slope(float amt) {
		if (amt >= 0.001f) {
			Point a = this.trace(amt - 0.001f);
			Point b = this.trace(amt);
			return Point.slope(a, b);
		}
		else {
			Point a = this.trace(amt + 0.001f);
			Point b = this.trace(amt);
			return Point.slope(a, b);
		}
	}
}