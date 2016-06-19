package paths;

import processing.core.PApplet;

/**
 * 
 * See http://paulbourke.net/geometry/superellipse/ for more info on the superellipse.
 * 
 * @author James Morrow
 *
 */
public class Superellipse extends Path {
	private float cenx, ceny, xRadius, yRadius;
	private float n;
	private float twoOverN;
	private int granularity;
	
	/**************************
	 ***** Initialization *****
	 **************************/
	
	public Superellipse(float cenx, float ceny, float xRadius, float yRadius, float n, int granularity) {
		this.cenx = cenx;
		this.ceny = ceny;
		this.xRadius = xRadius;
		this.yRadius = yRadius;
		this.n = n;
		this.twoOverN = 2f / n;
		this.granularity = granularity;
	}
	
	/*************************
	 ***** Functionality *****
	 *************************/

	@Override
	public void trace(Point pt, float amt) {
		float theta = amt*PApplet.TWO_PI;
		if (reversed) theta *= -1;		
		float cosTheta = PApplet.cos(theta);
		pt.x = cenx + PApplet.pow(PApplet.abs(cosTheta), twoOverN) * xRadius * sgn(cosTheta);
		float sinTheta = PApplet.sin(theta);
		pt.y = ceny + PApplet.pow(PApplet.abs(sinTheta), twoOverN) * yRadius * sgn(sinTheta);
	}

	@Override
	public void display(PApplet pa) {
		display(pa, granularity);
	}

	@Override
	public void translate(float dx, float dy) {
		cenx += dx;
		ceny += dy;
	}
	
	private static int sgn(float x) {
		if (x > 0) return 1;
		if (x < 0) return -1;
		return 0;
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

	public float getN() {
		return n;
	}

	public void setN(float n) {
		this.n = n;
		this.twoOverN = 2f / n;
	}

	public int getGranularity() {
		return granularity;
	}

	public void setGranularity(int granularity) {
		this.granularity = granularity;
	}
}