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
public class Flower extends GranularPath {
	private FlowerDefinition flowerDef;
	private int numVertices;
	
	private Flower(FlowerDefinition flowerDef, int numVertices) {
		super(flowerDef, numVertices);
		this.flowerDef = flowerDef;
		this.numVertices = numVertices;
	}
	
	public Flower(Flower f) {
		this(f.flowerDef, f.numVertices);
	}
	
	public static Flower createFlower(float cenx, float ceny, float xRadius, float yRadius, int freq1, int freq2, int numVertices) {
		return new Flower(new FlowerDefinition(cenx, ceny, xRadius, yRadius, freq1, freq2), numVertices);
	}
	
	public static Flower createFlower(float cenx, float ceny, float radius, int freq1, int freq2, int numVertices) {
		return new Flower(new FlowerDefinition(cenx, ceny, radius, radius, freq1, freq2), numVertices);
	}
	
	private static class FlowerDefinition implements PathDefinition {
		private float cenx, ceny, xRadius, yRadius, freq1, freq2;
		
		public FlowerDefinition(float cenx, float ceny, float radius, int freq1, int freq2) {
			this(cenx, ceny, radius, radius, freq1, freq2);
		}
		
		public FlowerDefinition(float cenx, float ceny, float xRadius, float yRadius, int freq1, int freq2) {
			this.cenx = cenx;
			this.ceny = ceny;
			this.xRadius = xRadius;
			this.yRadius = yRadius;
			this.freq1 = freq1;
	        this.freq2 = freq2;
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

		@Override
		public String toString() {
			return "Flower [cenx=" + cenx + ", ceny=" + ceny + ", xRadius=" + xRadius +
						", yRadius= " + yRadius + ", freq1=" + freq1 + ", freq2=" + freq2 + "]";
		}
	}
	
	@Override
	public Flower clone() {
		return new Flower(this);
	}
	
	@Override
	public String toString() {
		return flowerDef.toString();
	}
}