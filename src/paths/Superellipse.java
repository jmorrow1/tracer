package paths;

import processing.core.PApplet;

/**
 * 
 * A more generalized version of the <a href="ellipse.html">ellipse</a>.
 * 
 * <br><br>
 * 
 * <a href="http://paulbourke.net/geometry/superellipse/"> More info on the superellipse </a>.
 * 
 * @author James Morrow
 *
 */
public class Superellipse extends Path {
	private float cenx, ceny, xRadius, yRadius;
	private float n;
	private float twoOverN;
	
	/**************************
	 ***** Initialization *****
	 **************************/
	
	public Superellipse(Superellipse e) {
		this(e.cenx, e.ceny, e.xRadius, e.yRadius, e.n, e.granularity);
	}
	
	public Superellipse(float cenx, float ceny, float xRadius, float yRadius, float n, int granularity) {
		super(granularity);
		this.cenx = cenx;
		this.ceny = ceny;
		this.xRadius = xRadius;
		this.yRadius = yRadius;
		this.n = n;
		this.twoOverN = 2f / n;
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
	
	public Superellipse clone() {
		return new Superellipse(this);
	}
}