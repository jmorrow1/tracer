package paths;

import processing.core.PApplet;

/**
 * See http://paulbourke.net/geometry/supershape/ for more info on the supershape.
 * 
 * @author James Morrow
 *
 */
public class Supershape extends Path {
	private float cenx, ceny, xMaxRadius, yMaxRadius, m, n1, n2, n3;
	
	private float mOver4, n1Inverted;
	
	/**************************
	 ***** Initialization *****
	 **************************/
	
	public Supershape(Supershape s) {
		this(s.cenx, s.ceny, s.xMaxRadius, s.yMaxRadius, s.m, s.n1, s.n2, s.n3, s.granularity);
	}

	public Supershape(float cenx, float ceny, float xMaxRadius, float yMaxRadius,
			float m, float n1, float n2, float n3, int granularity) {
		this.cenx = cenx;
		this.ceny = ceny;
		this.xMaxRadius = xMaxRadius;
		this.yMaxRadius = yMaxRadius;
		this.m = m;
		this.n1 = n1;
		this.n2 = n2;
		this.n3 = n3;
		this.granularity = granularity;
		this.mOver4 = m / 4f;
		this.n1Inverted = 1f / n1;
	}
	
	public Supershape(float cenx, float ceny, float xMaxRadius, float yMaxRadius, float m, int granularity) {
		this(cenx, ceny, xMaxRadius, yMaxRadius, m, 1, 1, 1, granularity);
	}
	
	/*************************
	 ***** Functionality *****
	 *************************/

	@Override
	public void trace(Point pt, float amt) {
		float theta = (amt*PApplet.TWO_PI) % PApplet.TWO_PI;
		if (reversed) theta *= -1;
		float r = radius(theta);
		pt.x = cenx + xMaxRadius * r * PApplet.cos(theta);
		pt.y = ceny + yMaxRadius * r * PApplet.sin(theta);
	}
	
	private float radius(float theta) {
		theta *= mOver4;
		
		float part1 = PApplet.pow(PApplet.abs(PApplet.cos(theta)), n2);
		float part2 = PApplet.pow(PApplet.abs(PApplet.sin(theta)), n3);
		float part3 = PApplet.pow(part1 + part2, n1Inverted);
		return (part3 != 0) ? 1f / part3 : 0;	
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

	public float getM() {
		return m;
	}

	public void setM(float m) {
		this.m = m;
		this.mOver4 = m / 4f;
	}

	public float getN1() {
		return n1;
	}

	public void setN1(float n1) {
		this.n1 = n1;
		this.n1Inverted = 1f / n1;
	}

	public float getN2() {
		return n2;
	}

	public void setN2(float n2) {
		this.n2 = n2;
	}

	public float getN3() {
		return n3;
	}

	public void setN3(float n3) {
		this.n3 = n3;
	}

	public int getGranularity() {
		return granularity;
	}

	public void setGranularity(int granularity) {
		this.granularity = granularity;
	}
	
	public Supershape clone() {
		return new Supershape(this);
	}
}