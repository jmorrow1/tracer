package traceables;

import processing.core.PApplet;

/**
 * 
 * @author James Morrow
 *
 */
public class Bezier extends Path {
	private float ax1, ay1, cx1, cy1, cx2, cy2, ax2, ay2;
	
	/**************************
	 ***** Initialization *****
	 **************************/
	
	public Bezier(float ax1, float ay1, float cx1, float cy1, float cx2, float cy2, float ax2, float ay2) {
		this.ax1 = ax1;
		this.ay1 = ay1;
		this.cx1 = cx1;
		this.cy1 = cy1;
		this.cx2 = cx2;
		this.cy2 = cy2;
		this.ax2 = ax2;
		this.ay2 = ay2;
	}
	
	/*************************
	 ***** Functionality *****
	 *************************/
	
	@Override
	public void trace(Point pt, float amt) {
		pt.x = bezierPoint(ax1, cx1, cx2, ax2, amt);
		pt.y = bezierPoint(ay1, cy1, cy2, ay2, amt);	
	}

	@Override
	public void display(PApplet pa) {
		pa.bezier(ax1, ay1, cx1, cy1, cx2, cy2, ax2, ay2);
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
	
	public float getAnchorX1() {
		return ax1;
	}

	public void setAnchorX1(float ax1) {
		this.ax1 = ax1;
	}

	public float getAnchorY1() {
		return ay1;
	}

	public void setAnchorY1(float ay1) {
		this.ay1 = ay1;
	}

	public float getControlX1() {
		return cx1;
	}

	public void setControlX1(float cx1) {
		this.cx1 = cx1;
	}

	public float getControlY1() {
		return cy1;
	}

	public void setControlY1(float cy1) {
		this.cy1 = cy1;
	}

	public float getControlX2() {
		return cx2;
	}

	public void setControlX2(float cx2) {
		this.cx2 = cx2;
	}

	public float getControlY2() {
		return cy2;
	}

	public void setControlY2(float cy2) {
		this.cy2 = cy2;
	}

	public float getAnchorX2() {
		return ax2;
	}

	public void setAnchorX2(float ax2) {
		this.ax2 = ax2;
	}

	public float getAnchorY2() {
		return ay2;
	}

	public void setAnchorY2(float ay2) {
		this.ay2 = ay2;
	}
}
