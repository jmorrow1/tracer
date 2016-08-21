package paths;

import processing.core.PApplet;

/**
 * An infinity symbol.
 * 
 * @author James Morrow
 *
 */
public class InfinitySymbol extends Path {
	private float cenx, ceny, xRadius, yRadius;
	private int drawGranularity;
	
	/**************************
	 ***** Initialization *****
	 **************************/

	public InfinitySymbol(InfinitySymbol s) {
		this(s.cenx, s.ceny, s.xRadius, s.yRadius, s.drawGranularity);
	}
	
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
	public void display(PApplet pa) {
		display(pa, drawGranularity);
	}

	@Override
	public void translate(float dx, float dy) {
		cenx += dx;
		ceny += dy;
	}
	
	/*******************************
	 ***** Getters and Setters *****
	 *******************************/
	
	public float getCenx() {
		return cenx;
	}

	public void setCenx(float cenx) {
		this.cenx = cenx;
	}

	public float getCeny() {
		return ceny;
	}

	public void setCeny(float ceny) {
		this.ceny = ceny;
	}

	public float getxRadius() {
		return xRadius;
	}

	public void setxRadius(float xRadius) {
		this.xRadius = xRadius;
	}

	public float getyRadius() {
		return yRadius;
	}

	public void setyRadius(float yRadius) {
		this.yRadius = yRadius;
	}

	public int getDrawGranulariy() {
		return drawGranularity;
	}

	public void setDrawGranularity(int drawGranularity) {
		this.drawGranularity = drawGranularity;
	}
	
	public InfinitySymbol clone() {
		return new InfinitySymbol(this);
	}
}