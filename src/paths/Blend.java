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
	 * @param pa The Processing instance to draw to.
	 */
	public static void draw(Path a, Path b, int numVertices, float blendAmt, PApplet pa) {
		float amt = 0;
		float dAmt = 1f / numVertices;
		
		pa.beginShape();
		for (int i=0; i<numVertices; i++) {
			a.trace(ptA, amt);
			b.trace(ptB, amt);
			pa.vertex(PApplet.lerp(ptA.x, ptB.x, blendAmt), 
	                  PApplet.lerp(ptA.y, ptB.y, blendAmt));
		}
		pa.endShape();
	}
}