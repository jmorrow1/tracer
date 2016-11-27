package paths;

import processing.core.PApplet;
import processing.core.PGraphics;
import tracer.Point;

/**
 * 
 * A more generalized version of the <a href="Superellipse.html">superellipse</a>
 * 
 * <br><br>
 * 
 * <a href="http://paulbourke.net/geometry/supershape/"> More info on the supershape </a>
 * 
 * @author James Morrow
 *
 */
public class Supershape extends Path {
	private float cenx, ceny, xRadius, yRadius, m, n1, n2, n3;
	
	private float mOver4, n1Inverted;
	
	/**************************
	 ***** Initialization *****
	 **************************/
	
	/**
	 * Copy constructor.
	 * @param s the supershape to copy
	 */
	public Supershape(Supershape s) {
		this(s.cenx, s.ceny, s.xRadius, s.yRadius, s.m, s.n1, s.n2, s.n3, s.granularity);
	}

	/**
	 * 
	 * @param cenx the center x-coordinate
	 * @param ceny the center y-coordinate
	 * @param xRadius half the width
	 * @param yRadius half the height
	 * @param m controls the number of rotational symmetries
	 * @param n1 controls the amount of pinching (lesser values of n1 give more pinching) 
	 * @param n2
	 * @param n3
	 * @param granularity the number of sample points
	 */
	public Supershape(float cenx, float ceny, float xRadius, float yRadius,
			float m, float n1, float n2, float n3, int granularity) {
		this.cenx = cenx;
		this.ceny = ceny;
		this.xRadius = xRadius;
		this.yRadius = yRadius;
		this.m = m;
		this.n1 = n1;
		this.n2 = n2;
		this.n3 = n3;
		this.granularity = granularity;
		this.mOver4 = m / 4f;
		this.n1Inverted = 1f / n1;
	}
	
	/**
	 * 
	 * @param cenx the center x-coordinate
	 * @param ceny the center y-coordinate
	 * @param xRadius half the width
	 * @param yRadius half the height
	 * @param m controls the number of rotational symmetries
	 * @param granularity The number of sample points
	 */
	public Supershape(float cenx, float ceny, float xRadius, float yRadius, float m, int granularity) {
		this(cenx, ceny, xRadius, yRadius, m, 1, 1, 1, granularity);
	}
	
	/*************************
	 ***** Functionality *****
	 *************************/

	@Override
	public void trace(Point pt, float amt) {
		float theta = (amt*PApplet.TWO_PI) % PApplet.TWO_PI;
		if (reversed) theta *= -1;
		float r = radius(theta);
		pt.x = cenx + xRadius * r * PApplet.cos(theta);
		pt.y = ceny + yRadius * r * PApplet.sin(theta);
	}
	
	private float radius(float theta) {
		theta *= mOver4;
		
		float part1 = PApplet.pow(PApplet.abs(PApplet.cos(theta)), n2);
		float part2 = PApplet.pow(PApplet.abs(PApplet.sin(theta)), n3);
		float part3 = PApplet.pow(part1 + part2, n1Inverted);
		return (part3 != 0) ? 1f / part3 : 0;	
	}

	@Override
	public void display(PGraphics g) {
		display(g, granularity);
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
	 * @return a value that relates to the number of rotational symmetries
	 */
	public float getM() {
		return m;
	}

	/**
	 * 
	 * @param m a variable that controls the number of rotational symmetries
	 */
	public void setM(float m) {
		this.m = m;
		this.mOver4 = m / 4f;
	}

	/**
	 * 
	 * @return the amount of pinching (smaller amounts of n1 give greater amounts of pinching)
	 */
	public float getN1() {
		return n1;
	}

	/**
	 * 
	 * @param n1 the amount of pinching (smaller amounts of n1 give greater amounts of pinching) 
	 */
	public void setN1(float n1) {
		this.n1 = n1;
		this.n1Inverted = 1f / n1;
	}

	/**
	 * 
	 * @return the amount of pinching (smaller amounts of n1 give greater amounts of pinching)
	 */
	public float getN2() {
		return n2;
	}

	/**
	 * 
	 * @param n2
	 */
	public void setN2(float n2) {
		this.n2 = n2;
	}
	
	/**
	 * 
	 * @return
	 */
	public float getN3() {
		return n3;
	}

	/**
	 * 
	 * @param n3
	 */
	public void setN3(float n3) {
		this.n3 = n3;
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
	public Supershape clone() {
		return new Supershape(this);
	}
}