package paths2;

import paths.IPath;
import paths.Point;
import processing.core.PApplet;

/**
 * A Traceable with a known perimeter, position, and size.
 * 
 * @author James Morrow
 *
 */
public interface IPath2 extends IPath {	
	/**
	 * Maps a given floating point number from 0 to this.getPerimeter() to a given Point
	 * along the perimeter of the Path.
	 * 
	 * @param pt The point.
	 * @param amt A number from 0 to this.getPerimeter().
	 */
	public default void trace2(Point pt, float amt) {
		amt = PApplet.map(amt, 0, this.getPerimeter(), 0, 1);
		if (amt > 1) amt %= 1;
		trace(pt, amt);
	}
	
	/**
	 * Maps a given floating point number from 0 to this.getPerimeter() to a Point
	 * along the perimeter of the Path.
	 * 
	 * @param amt A number from 0 to this.getPerimeter().
	 * @return The point.
	 */
	public default Point trace2(float amt) {
		amt = PApplet.map(amt, 0, getPerimeter(), 0, 1);
		if (amt > 1) amt %= 1;
		return trace(amt);
	}
	
	/**
	 * Returns the length of the Path.
	 * @return
	 */
	public float getPerimeter();
	
	/**
	 * Returns true if (x,y) is a point within the Path (which would imply that this is a closed path) and false otherwise.
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean inside(float x, float y);
	
	/**
	 * Returns true if pt is a Point within the Path (which would imply that this is a closed path) and false otherwise.
	 * @param pt
	 * @return
	 */
	public default boolean inside(Point pt) {
		return inside(pt.x, pt.y);
	}
	
	/**
	 * Returns the remainder of num / denom.
	 * @param num the numerator
	 * @param denom the denominator
	 * @return The remainder of num / denom
	 */
	public static float remainder(float num, float denom) {
        if (num % denom >= 0) return num % denom;        
        else return denom - ((-num) % denom);
    }
	
	/**
	 * Returns the slope of the Point on the Path at trace2(amt).
	 * @param amt
	 * @return the slope
	 */
	public default float slope2(float amt) {
		if (amt >= 0.001f) {
			Point a = this.trace(amt - this.getPerimeter()*0.001f);
			Point b = this.trace(amt);
			return Point.slope(a, b);
		}
		else {
			Point a = this.trace(amt + this.getPerimeter()*0.001f);
			Point b = this.trace(amt);
			return Point.slope(a, b);
		}
	}
	
	/**
	 * Returns true if this Path is considered a closed path and false otherwise.
	 * @return
	 */
	//public boolean isClosedPath();
	
	/**
	 * Returns the center x-coordinate of this Path.
	 * @return
	 */
	public float getCenx();
	
	
	/**
	 * Returns the center y-coordinate of this Path.
	 * @return
	 */
	public float getCeny();
	
	/**
	 * Sets the center point of this Path to (x,y).
	 * @param x
	 * @param y
	 */
	public default void setCenter(float x, float y) {
		float dx = x - this.getCenx();
		float dy = y - this.getCeny();
		translate(dx, dy);
	}
	
	/**
	 * Sets the center point of this Path to the given pt.
	 * @param pt
	 */
	public default void setCenter(Point pt) {
		setCenter(pt.x, pt.y);
	}
	
	public float getWidth();
	public float getHeight();
}