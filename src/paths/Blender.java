package paths;

import processing.core.PApplet;

/**
 * Provides static functions for blending (interpolating) paths.
 * 
 * @author James Morrow
 *
 */
public class Blender<T extends Traceable, U extends Traceable> implements Traceable {
	
	/******************
	 ***** Static *****
	 ******************/
	
	private final static Point ptA = new Point(0, 0), ptB = new Point(0, 0);
	
	/**
	 * Draws the result of interpolating between two traceables.
	 * 
	 * @param a The first traceable
	 * @param b The second traceable
	 * @param numVertices Determines how precisely the drawn figure is approximated.
	 * @param blendAmt The interpolation amount, a value from [0,1].
	 * @param pa The Processing instance to draw to.
	 */
	public static void draw(Traceable a, Traceable b, int numVertices, float blendAmt, PApplet pa) {
		float amt = 0;
		float dAmt = 1f / numVertices;
		
		pa.beginShape();
		for (int i=0; i<numVertices; i++) {
			a.trace(ptA, amt);
			b.trace(ptB, amt);
			pa.vertex(PApplet.lerp(ptA.x, ptB.x, blendAmt), 
	                  PApplet.lerp(ptA.y, ptB.y, blendAmt));
		}
		pa.endShape();
	}
	
	/******************
	 ***** Fields *****
	 ******************/
	
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
		this.blendAmt = (this.blendAmt + dAmt) % 1;
	}
}