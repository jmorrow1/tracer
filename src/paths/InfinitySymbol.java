package paths;

import processing.core.PApplet;
import processing.core.PGraphics;
import tracer.Point;

/**
 * 
 * An infinity symbol.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class InfinitySymbol extends Path {
	private float cenx, ceny, xRadius, yRadius;
	private int drawGranularity;
	
	/**************************
	 ***** Initialization *****
	 **************************/

	/**
	 * Copy constructor.
	 * @param s The infinity symbol to copy
	 */
	public InfinitySymbol(InfinitySymbol s) {
		this(s.cenx, s.ceny, s.xRadius, s.yRadius, s.drawGranularity);
	}
	
	/**
	 * 
	 * @param cenx the center x-coordinate
	 * @param ceny the center y-coordinate
	 * @param xRadius half the width
	 * @param yRadius half the height
	 * @param drawGranularity the number of sample points
	 */
	public InfinitySymbol(float cenx, float ceny, float xRadius, float yRadius, int drawGranularity) {
		this.cenx = cenx;
		this.ceny = ceny;
		this.xRadius = xRadius;
		this.yRadius = yRadius;
		this.drawGranularity = drawGranularity;
	}
	
	/*************************
	 ***** Functionality *****
	 *************************/

	@Override
	public void trace(Point pt, float amt) {
		float radians = amt * PApplet.TWO_PI;
		if (reversed) radians *= -1;
		pt.x = cenx + xRadius * PApplet.sin(radians);
		pt.y = ceny + yRadius * PApplet.cos(radians) * PApplet.sin(radians);
	}

	@Override
	public void draw(PGraphics g) {
		draw(g, drawGranularity);
	}

	@Override
	public void translate(float dx, float dy) {
		cenx += dx;
		ceny += dy;
	}
	
	/*******************************
	 ***** Getters and Setters *****
	 *******************************/
	
	/**
	 * 
	 * @return the center x-coordinate
	 */
	public float getCenx() {
		return cenx;
	}

	/**
	 * 
	 * @param cenx the center x-coordinate
	 */
	public void setCenx(float cenx) {
		this.cenx = cenx;
	}

	/**
	 * 
	 * @return the center y-coordinate
	 */
	public float getCeny() {
		return ceny;
	}

	/**
	 * 
	 * @param ceny the center y-coordinate
	 */
	public void setCeny(float ceny) {
		this.ceny = ceny;
	}

	/**
	 * 
	 * @return half the width
	 */
	public float getXRadius() {
		return xRadius;
	}

	/**
	 * 
	 * @param xRadius half the width
	 */
	public void setXRadius(float xRadius) {
		this.xRadius = xRadius;
	}

	/**
	 * 
	 * @return half the height
	 */
	public float getYRadius() {
		return yRadius;
	}

	/**
	 * 
	 * @param yRadius half the height
	 */
	public void setYRadius(float yRadius) {
		this.yRadius = yRadius;
	}

	/**
	 * 
	 * @return the number of sample points
	 */
	public int getDrawGranulariy() {
		return drawGranularity;
	}

	/**
	 * 
	 * @param drawGranularity the number of sample points
	 */
	public void setDrawGranularity(int drawGranularity) {
		this.drawGranularity = drawGranularity;
	}
	
	@Override
	public InfinitySymbol clone() {
		return new InfinitySymbol(this);
	}
}