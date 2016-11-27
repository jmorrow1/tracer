package paths;

import processing.core.PApplet;
import processing.core.PGraphics;
import tracer.Point;

/**
 * 
 * A more generalized version of the <a href="Ellipse.html">ellipse</a>.
 * 
 * <br><br>
 * 
 * <a href="http://paulbourke.net/geometry/superellipse/"> More info on the superellipse </a>.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class Superellipse extends Path {
	private float cenx, ceny, xRadius, yRadius;
	private float n;
	private float twoOverN;
	
	/**************************
	 ***** Initialization *****
	 **************************/
	
	/**
	 * Copy constructor.
	 * @param e the superellipse to copy
	 */
	public Superellipse(Superellipse e) {
		this(e.cenx, e.ceny, e.xRadius, e.yRadius, e.n, e.granularity);
	}
	
	/**
	 * 
	 * @param cenx the center x-coordinate
	 * @param ceny the center y-coordinate
	 * @param xRadius half the width
	 * @param yRadius half the height
	 * @param n controls the amount of pinching (smaller values give more pinching)
	 * @param granularity the number of sample points
	 */
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
	public void draw(PGraphics g) {
		draw(g, granularity);
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
	 * @return the amount of pinching (smaller amounts of n give more pinching)
	 */
	public float getN() {
		return n;
	}

	/**
	 * 
	 * @param n the amount of pinching (smaller amounts of n give more pinching)
	 */
	public void setN(float n) {
		this.n = n;
		this.twoOverN = 2f / n;
	}

	/**
	 * 
	 * @return the number of sample points
	 */
	public int getGranularity() {
		return granularity;
	}

	/**
	 * 
	 * @param granularity the number of sample points
	 */
	public void setGranularity(int granularity) {
		this.granularity = granularity;
	}
	
	@Override
	public Superellipse clone() {
		return new Superellipse(this);
	}
}