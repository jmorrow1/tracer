package paths;

import processing.core.PApplet;

public class InfinitySymbol implements Traceable {
	private float cenx, ceny, xRadius, yRadius;
	private int drawGranularity;
	
	/**************************
	 ***** Initialization *****
	 **************************/

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

	public int getGranularity() {
		return drawGranularity;
	}

	public void setGranularity(int granularity) {
		this.drawGranularity = granularity;
	}
}