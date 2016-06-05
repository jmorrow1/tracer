package paths;

import processing.core.PApplet;

/**
 * Provides static functions for blending (interpolating) paths.
 * 
 * @author James Morrow
 *
 */
public class Blender<T extends IPath, U extends IPath> extends Path {
	private Point ptA = new Point(0, 0), ptB = new Point(0, 0);	
	private float x, y;
	private T a;
	private U b;
	private int drawGranularity;
	private float blendAmt;
	
	/**************************
	 ***** Initialization *****
	 **************************/
	
	public Blender(T a, U b, float blendAmt, int drawGranularity) {
		this.a = a;
		this.b = b;
		this.blendAmt = blendAmt;
		this.drawGranularity = drawGranularity;
	}
	
	/*************************
	 ***** Functionality *****
	 *************************/
	
	@Override
	public void trace(Point pt, float amt) {
		if (reversed) amt = PApplet.map(amt, 0, 1, 1, 0);
		a.trace(ptA, amt);
		b.trace(ptB, amt);
		pt.x = PApplet.lerp(ptA.x, ptB.x, blendAmt);
		pt.y = PApplet.lerp(ptA.y, ptB.y, blendAmt);
	}

	@Override
	public void display(PApplet pa) {
		display(pa, drawGranularity);
	}

	@Override
	public void translate(float dx, float dy) {
		a.translate(dx, dy);
		b.translate(dx, dy);
	}
	
	/*******************************
	 ***** Getters and Setters *****
	 *******************************/
	
	public T getA() {
		return a;
	}

	public void setA(T a) {
		this.a = a;
	}

	public U getB() {
		return b;
	}

	public void setB(U b) {
		this.b = b;
	}

	public int getDrawGranularity() {
		return drawGranularity;
	}

	public void setDrawGranularity(int drawGranularity) {
		this.drawGranularity = drawGranularity;
	}

	public float getBlendAmt() {
		return blendAmt;
	}

	public void setBlendAmt(float blendAmt) {
		this.blendAmt = blendAmt;
	}
	
	public void addToBlendAmt(float dAmt) {
		this.blendAmt = (this.blendAmt + dAmt) % 1; //TODO Use remainder function so as to deal with negative dAmt values
	}
}