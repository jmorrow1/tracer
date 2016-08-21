package paths;

import processing.core.PApplet;

/**
 * 
 * A flower-like pattern made from two sinusoidal motions of different frequencies.
 * 
 * @author James Morrow
 *
 */
public class Flower extends Path {
	private float cenx, ceny, xRadius, yRadius, freq1, freq2;
	private int drawGranularity;
	
	/**************************
	 ***** Initialization *****
	 **************************/
	
	/**
	 * Copy constructor.
	 * @param f the flower to copy
	 */
	public Flower(Flower f) {
		this(f.cenx, f.ceny, f.xRadius, f.yRadius, f.freq1, f.freq2, f.drawGranularity);
	}
	
	/**
	 * 
	 * @param cenx the center x-coordinate
	 * @param ceny the center y-coordinate
	 * @param radius the radius
	 * @param freq1 the first frequency
	 * @param freq2 the second frequency
	 * @param drawGranularity the number of sample points
	 */
	public Flower(float cenx, float ceny, float radius, float freq1, float freq2, int drawGranularity) {
		this(cenx, ceny, radius, radius, freq1, freq2, drawGranularity);
	}
	
	/**
	 * 
	 * @param cenx the center x-coordinate
	 * @param ceny the center y-coordinate
	 * @param xRadius half of the width
	 * @param yRadius half of the height
	 * @param freq1 the first frequency
	 * @param freq2 the second frequency
	 * @param drawGranularity the number of sample points
	 */
	public Flower(float cenx, float ceny, float xRadius, float yRadius, float freq1, float freq2, int drawGranularity) {
		this.cenx = cenx;
		this.ceny = ceny;
		this.xRadius = xRadius;
		this.yRadius = yRadius;
		this.freq1 = freq1;
        this.freq2 = freq2;
        this.drawGranularity = drawGranularity;
	}
	
	/*************************
	 ***** Functionality *****
	 *************************/
	
	@Override
	public void trace(Point pt, float amt) {
		if (reversed) amt *= -1;
		float alpha = amt * PApplet.TWO_PI * freq1;
        float beta = amt * PApplet.TWO_PI * freq2;
        float x = cenx + xRadius*PApplet.cos(alpha);
        float y = ceny + yRadius*PApplet.sin(alpha);
        float lerpAmt = PApplet.map(PApplet.sin(beta), -1, 1, 0, 1);
        pt.x = PApplet.lerp(cenx, x, lerpAmt);
        pt.y = PApplet.lerp(ceny, y, lerpAmt);
	}

	public void display(PApplet pa) {
		display(pa, drawGranularity);
	}
	
	@Override
	public void translate(float dx, float dy) {
		cenx += dx;
		ceny += dy;
	}
	
	@Override
	public String toString() {
		return "Flower [cenx=" + cenx + ", ceny=" + ceny + ", xRadius=" + xRadius +
					", yRadius= " + yRadius + ", freq1=" + freq1 + ", freq2=" + freq2 + "]";
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
	 * @return half of the width
	 */
	public float getXRadius() {
		return xRadius;
	}

	/**
	 * 
	 * @param xRadius half of the width
	 */
	public void setXRadius(float xRadius) {
		this.xRadius = xRadius;
	}

	/**
	 * 
	 * @return half of the height
	 */
	public float getYRadius() {
		return yRadius;
	}

	/**
	 * 
	 * @param yRadius half of the height
	 */
	public void setYRadius(float yRadius) {
		this.yRadius = yRadius;
	}

	/**
	 * 
	 * @return the first frequency
	 */
	public float getFreq1() {
		return freq1;
	}

	/**
	 * 
	 * @param freq1 the first frequency
	 */
	public void setFreq1(float freq1) {
		this.freq1 = freq1;
	}

	/**
	 * 
	 * @return the second frequency
	 */
	public float getFreq2() {
		return freq2;
	}

	/**
	 * 
	 * @param freq2 the second frequency
	 */
	public void setFreq2(float freq2) {
		this.freq2 = freq2;
	}

	/**
	 * 
	 * @return the number of sample points
	 */
	public int getDrawGranularity() {
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
	public Flower clone() {
		return new Flower(this);
	}
}