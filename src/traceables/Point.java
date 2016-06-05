package traceables;

import json_lib.JSONable;
import processing.core.PApplet;
import processing.core.PVector;
import processing.data.JSONObject;

/**
 * 
 * @author James Morrow
 *
 */
public class Point implements JSONable {
	public float x, y;
	
	public Point(Point pt) {
		this.x = pt.x;
		this.y = pt.y;
	}
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(JSONObject j) {
		x = j.getFloat("x");
		y = j.getFloat("y");
	}
	
	@Override
	public JSONObject toJSON() {
		JSONObject j = new JSONObject();
		j.setFloat("x", x);
		j.setFloat("y", y);
		return j;
	}
	
	/**
	 * Displays the point.
	 * @param pa
	 */
	public void display(PApplet pa) {
		pa.point(x, y);
	}
	
	//convienent function if you want to get an array of points from an array of alternating x, y values
	public static Point[] points(float... values) {
	    Point[] pts = new Point[values.length/2];
	    for (int i=0; i<pts.length; i++) {
	        pts[i] = new Point(values[i*2], values[i*2 + 1]);
	    }
	    return pts;
	}
	
	public static Point lerp(Point a, Point b, float amt) {
		return new Point(PApplet.lerp(a.x, a.y, amt), 
						 PApplet.lerp(b.x, b.y, amt));
	}

	public static Point avg(Point a, Point b) {
		return new Point((a.x+b.x)/2f, (a.y+b.y)/2f);
	}
	
	public static Point avg(Point... pts) {
		float xAvg = 0;
		float yAvg = 0;
		for (int i=0; i<pts.length; i++) {
			xAvg += pts[i].x;
			yAvg += pts[i].y;
		}
		xAvg /= pts.length;
		yAvg /= pts.length;
		return new Point(xAvg, yAvg);
	}
	
	public static float dist(Point a, Point b) {
		return PApplet.dist(a.x, a.y, b.x, b.y);
	}
	
	public static float slope(Point a, Point b) {
		return (b.y - a.y)/(b.x - a.x);
	}
	
	public static Point slopeVector(Point a, Point b) {
		float x = (b.x - a.x);
		float y = (b.y - a.y);
		PVector pv = new PVector(x, y);
		pv.normalize();
		return new Point(pv.x, pv.y);
	}
	
	public static Point add(Point a, Point b) {
		return new Point(a.x+b.x, a.y+b.y);
	}
	
	public static Point sum(Point... pts) {
		Point sum = new Point(0, 0);
		for (int i=0; i<pts.length; i++) {
			sum.x += pts[i].x;
			sum.y += pts[i].y;
		}
		return sum;
	}
	
	public static Point sub(Point a, Point b) {
		return new Point(a.x-b.x, a.y-b.y);
	}
	
	/**
	 * Multiply this point by the given multiplier.
	 * @param multiplier
	 */
	public void mult(float multiplier) {
		x *= multiplier;
		y *= multiplier;
	}
	
	/**
	 * Add the given point to this point.
	 * @param pt
	 */
	public void add(Point pt) {
		x += pt.x;
		y += pt.y;
	}
	
	/**
	 * Divide this point by a given divisor.
	 * @param divisor
	 */
	public void div(float divisor) {
		x /= divisor;
		y /= divisor;
	}
	
	/**
	 * Divide a point by a divisor and return the result.
	 * @param pt the point to divide
	 * @param divisor the divisor
	 * @return a new point which is the point divided by the divisor
	 */
	public static Point div(Point pt, float divisor) {
		return new Point(pt.x/divisor, pt.y/divisor);
	}
	
	/**
	 * Multiply a point by a multiplier and return the result.
	 * @param pt the point to multiply
	 * @param multiplier the multiplier
	 * @return a new point which is the product of pt and multiplier
	 */
	public static Point mult(Point pt, float multiplier) {
		return new Point(pt.x*multiplier, pt.y*multiplier);
	}
	
	/**
	 * 
	 * @param pt
	 * @return true if the points are equivalent, false otherwise
	 */
	public boolean equals(Point pt) {
		return pt.x == this.x && pt.y == this.y;
	}
	
	/**
	 * Shifts this Point dx units in the x-direction and dy units in the y-direction.
	 * @param dx
	 * @param dy
	 */
	public void translate(float dx, float dy) {
		x += dx;
		y += dy;
	}

	/**
	 * Rotates this point around (vx,vy).
	 * @param angle
	 * @param vx
	 * @param vy
	 */
	public void rotate(float angle, float vx, float vy) {
        float c = (float)Math.cos(angle);
        float s = (float)Math.sin(angle); 
     
        x -= vx; 
        y -= vy; 
     
        float tempx = x; 
        float tempy = y; 
     
        x = tempx*c - tempy*s; 
        y = tempx*s + tempy*c; 
     
        x += vx; 
        y += vy; 
    }

	@Override
	public Point clone() {
		return new Point(this);
	}
	
	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}
}
