package paths;

import processing.core.PApplet;

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
	
	public static Flower createFlower(float cenx, float ceny, float radius, int freq1, int freq2, int numVertices) {
		return new Flower(new FlowerDefinition(cenx, ceny, radius, freq1, freq2), numVertices);
	}
	
	private static class FlowerDefinition implements PathDefinition {
		private float cenx, ceny, radius, freq1, freq2;
		
		public FlowerDefinition(float cenx, float ceny, float radius, int freq1, int freq2) {
			this.cenx = cenx;
			this.ceny = ceny;
			this.radius = radius;
			this.freq1 = freq1;
	        this.freq2 = freq2;
		}
		
		@Override
		public void trace(Point pt, float amt) {
			float alpha = amt * PApplet.TWO_PI * freq1;
	        float beta = amt * PApplet.TWO_PI * freq2;
	        float x = cenx + radius*PApplet.cos(alpha);
	        float y = ceny + radius*PApplet.sin(alpha);
	        float lerpAmt = PApplet.map(PApplet.sin(beta), -1, 1, 0, 1);
	        pt.x = PApplet.lerp(cenx, x, lerpAmt);
	        pt.y = PApplet.lerp(ceny, y, lerpAmt);
		}

		@Override
		public String toString() {
			return "Flower [cenx=" + cenx + ", ceny=" + ceny + ", radius=" + radius + ", freq1=" + freq1
					+ ", freq2=" + freq2 + "]";
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