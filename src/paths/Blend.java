package paths;

import processing.core.PApplet;

/**
 * Provides static functions for blending (interpolating) paths.
 * 
 * @author James Morrow
 *
 */
public class Blend {
	private static Point ptA, ptB;
	
	/**
	 * Draws the result of interpolating between two paths.
	 * 
	 * @param a The first path
	 * @param b The second path
	 * @param numVertices Determines how precisely the drawn path is approximated.
	 * @param blendAmt The interpolation amount, a value from [0,1].
	 */
	public static void draw(Path a, Path b, int numVertices, float blendAmt) {
		float amt = 0;
		float dAmt = 1f / numVertices;
		for (int i=0; i<numVertices; i++) {
			a.trace(ptA, amt);
		}
	}
	
	/**
	 * Creates a new path that is the result of interpolating between two paths.
	 * 
	 * @param a The first path 
	 * @param b The second path
	 * @param numVertices Determines how precisely the resulting path is approximated.
	 * @param blendAmt The interpolation amount, a value from [0,1].
	 * @return The resulting path
	 */
	public static Path blend(Path a, Path b, int numVertices, float blendAmt) {
		return new GranularPath(new BlendedPathDefinition(a, b, blendAmt), numVertices);
	}
	
	/**
	 * 
	 * @author James Morrow
	 *
	 */
	public static class BlendedPathDefinition implements PathDefinition {
		private Path a, b;
		private float blendAmt;
		private Point ptA, ptB;
		
		public BlendedPathDefinition(Path a, Path b, float blendAmt) {
			this.a = a;
			this.b = b;
			this.blendAmt = blendAmt;
			this.ptA = new Point(0, 0);
			this.ptB = new Point(0, 0);
		}

		@Override
		public void trace(Point pt, float amt) {
			a.trace(this.ptA, amt);
			b.trace(this.ptB, amt);
			pt.x = PApplet.lerp(this.ptA.x, this.ptB.x, blendAmt);
			pt.y = PApplet.lerp(this.ptA.y, this.ptB.y, blendAmt);
		}
	}
}