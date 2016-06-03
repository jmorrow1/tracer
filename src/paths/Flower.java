package paths;

import processing.core.PApplet;

//TODO Make it so Flowers can be ellipitical

/**
 * 
 * Flower-like patterns made from two sinusoidal motions of different frequencies.
 * 
 * @author James Morrow
 *
 */
public class Flower implements Traceable {
	private float cenx, ceny, xRadius, yRadius, freq1, freq2;
	private int granularity;
	
	public Flower(float cenx, float ceny, float radius, int freq1, int freq2, int granularity) {
		this(cenx, ceny, radius, radius, freq1, freq2, granularity);
	}
	
	public Flower(float cenx, float ceny, float xRadius, float yRadius, int freq1, int freq2, int granularity) {
		this.cenx = cenx;
		this.ceny = ceny;
		this.xRadius = xRadius;
		this.yRadius = yRadius;
		this.freq1 = freq1;
        this.freq2 = freq2;
        this.granularity = granularity;
	}
	
	@Override
	public void trace(Point pt, float amt) {
		float alpha = amt * PApplet.TWO_PI * freq1;
        float beta = amt * PApplet.TWO_PI * freq2;
        float x = cenx + xRadius*PApplet.cos(alpha);
        float y = ceny + yRadius*PApplet.sin(alpha);
        float lerpAmt = PApplet.map(PApplet.sin(beta), -1, 1, 0, 1);
        pt.x = PApplet.lerp(cenx, x, lerpAmt);
        pt.y = PApplet.lerp(ceny, y, lerpAmt);
	}

	public void display(PApplet pa) {
		display(pa, granularity);
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
}