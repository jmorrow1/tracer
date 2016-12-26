package paths;

import processing.core.PApplet;
import processing.core.PGraphics;
import tracer.Point;

/**
 * A Bezier curve defined by two anchor points and two control points.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class CubicBezier extends Path {
	private float ax1, ay1, cx1, cy1, cx2, cy2, ax2, ay2;
	
	/**************************
	 ***** Initialization *****
	 **************************/
	
	/**
	 * Copy constructor
	 * @param b The Bezier to copy
	 */
	public CubicBezier(CubicBezier b) {
		this(b.ax1, b.ay1, b.cx1, b.cy1, b.cx2, b.cy2, b.ax2, b.ay2);
	}
	
	/**
	 * 
	 * @param ax1 the x-coordinate of the 1st anchor point
	 * @param ay1 the y-coordinate of the 1st anchor point
	 * @param cx1 the x-coordinate of the 1st control point
	 * @param cy1 the y-coordinate of the 1st control point
	 * @param cx2 the x-coordinate of the 2nd control point
	 * @param cy2 the y-coordinate of the 2nd control point
	 * @param ax2 the x-coordinate of the 2nd anchor point
	 * @param ay2 the y-coordinate of the 2nd anchor point
	 */
	public CubicBezier(float ax1, float ay1, float cx1, float cy1, float cx2, float cy2, float ax2, float ay2) {
		this.ax1 = ax1;
		this.ay1 = ay1;
		this.cx1 = cx1;
		this.cy1 = cy1;
		this.cx2 = cx2;
		this.cy2 = cy2;
		this.ax2 = ax2;
		this.ay2 = ay2;
	}
	
	/**
	 * Easy constructor.
	 * 
	 * @param x The x-coordinate of the path.
	 * @param y The y-coordinate of the path.
	 * @param r The radius of the path.
	 */
	public CubicBezier(float x, float y, float r) {
		this(x - r, y, x, y - r, x, y - r, x + r, y);
	}
	
	/*************************
	 ***** Functionality *****
	 *************************/
	
	@Override
	public void trace(Point pt, float amt) {
		if (reversed) amt = PApplet.map(amt, 0, 1, 1, 0);
		pt.x = bezierPoint(ax1, cx1, cx2, ax2, amt);
		pt.y = bezierPoint(ay1, cy1, cy2, ay2, amt);	
	}

	@Override
	public void draw(PGraphics g) {
		g.bezier(ax1, ay1, cx1, cy1, cx2, cy2, ax2, ay2);
	}

	@Override
	public void translate(float dx, float dy) {
		ax1 += dx;
		cx1 += dx;
		cx2 += dx;
		ax2 += dx;
		
		ay1 += dy;
		cy1 += dy;
		cy2 += dy;	
		ay2 += dy;
	}
	
	private static float bezierPoint(float a, float b, float c, float d, float t) {
	    float t1 = 1.0f - t;
	    return a*t1*t1*t1 + 3*b*t*t1*t1 + 3*c*t*t*t1 + d*t*t*t;
	}
	
	/*******************************
	 ***** Getters and Setters *****
	 *******************************/
	
	/**
	 * 
	 * @return the x-coordinate of the 1st anchor point
	 */
	public float getAnchorX1() {
		return ax1;
	}

	/**
	 * 
	 * @param ax1 the x-coordinate of the 1st anchor point
	 */
	public void setAnchorX1(float ax1) {
		this.ax1 = ax1;
	}

	/**
	 * 
	 * @return the y-coordinate of the 1st anchor point
	 */
	public float getAnchorY1() {
		return ay1;
	}

	/**
	 * 
	 * @param ay1 the y-coordinate of the 2nd anchor point
	 */
	public void setAnchorY1(float ay1) {
		this.ay1 = ay1;
	}

	/**
	 * 
	 * @return the x-coordinate of the 1st control point
	 */
	public float getControlX1() {
		return cx1;
	}

	/**
	 * 
	 * @param cx1 the x-coordinate of the 1st anchor point
	 */
	public void setControlX1(float cx1) {
		this.cx1 = cx1;
	}

	/**
	 * 
	 * @return the t-coordinate of the 1st control point
	 */
	public float getControlY1() {
		return cy1;
	}

	/**
	 * 
	 * @param cy1 the y-coordinate of the 1st control point
	 */
	public void setControlY1(float cy1) {
		this.cy1 = cy1;
	}

	/**
	 * 
	 * @return the x-coordinate of the 2nd control point
	 */
	public float getControlX2() {
		return cx2;
	}

	/**
	 * 
	 * @param cx2 the x-coordinate of the 2nd control point
	 */
	public void setControlX2(float cx2) {
		this.cx2 = cx2;
	}

	/**
	 * 
	 * @return the y-coordinate of the 2nd control point
	 */
	public float getControlY2() {
		return cy2;
	}

	/**
	 * 
	 * @param cy2 the y-coordinate of the 2nd control point
	 */
	public void setControlY2(float cy2) {
		this.cy2 = cy2;
	}

	/**
	 * 
	 * @return the x-coordinate of the 2nd anchor point
	 */
	public float getAnchorX2() {
		return ax2;
	}

	/**
	 * 
	 * @param ax2 the x-coordinate of the 2nd anchor point
	 */
	public void setAnchorX2(float ax2) {
		this.ax2 = ax2;
	}

	/**
	 * 
	 * @return the y-coordinate of the 2nd anchor point
	 */
	public float getAnchorY2() {
		return ay2;
	}

	/**
	 * 
	 * @param ay2 the y-coordinate of the 2nd anchor point
	 */
	public void setAnchorY2(float ay2) {
		this.ay2 = ay2;
	}
	
	@Override
	public CubicBezier clone() {
		return new CubicBezier(this);
	}

	@Override
	public int getGapCount() {
		if (ax1 == ax2 && ay1 == ay2) {
			return 0;
		}
		else {
			return 1;
		}
	}

	@Override
	public float getGap(int i) {
		if (getGapCount() == 1 && i == 0) {
			return 0;
		}
		else {
			return -1;
		}
	}
}
