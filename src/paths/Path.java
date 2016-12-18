package paths;

import processing.core.PApplet;
import processing.core.PGraphics;
import tracer.Point;

/**
 * 
 * A continuous sequence of points in 2D space.
 * 
 * <br><br>
 * 
 * <a href="IPath.html">More information on paths</a>
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public abstract class Path implements IPath {
	protected boolean reversed;
	protected int granularity = 100;
	
	public Path() {}
	
	/**
	 * 
	 * @param granularity the number of sample points
	 */
	public Path(int granularity) {
		this.granularity = granularity;
	}
	
	@Override
	public void draw(PGraphics g) {
		if (granularity != -1) {
			draw(g, granularity);
		}
	}
	
	@Override
	public void draw(PGraphics g, float u1, float u2) {
		boolean inRange = (0 <= u1 && u1 <= 1 && 0 <= u2 && u2 <= 1);
		if (!inRange) {
			throw new IllegalArgumentException("draw(g, " + u1 + ", " + u2 + ") called with values outside the range 0 to 1.");
		}
	
		if (u1 > u2) {
			draw(g, u1, 1);
			draw(g, 0, u2);
		}
		else {
			float length = PApplet.abs(u1 - u2);
			int n = (int)(granularity * length);
			float du = length / n;
					
			g.beginShape();
			float u = u1;
			for (int i=0; i<n; i++) {
				trace(pt, u);
				g.vertex(pt.x, pt.y);
				u = (u+du) % 1f;
			}
			g.endShape();
		}
	}
	
	@Override
	public abstract void trace(Point pt, float u);

	/**
	 * 
	 * @return true, if the path is set to reversed and false otherwise
	 */
	public boolean isReversed() {
		return reversed;
	}
	
	@Override
	public void reverse() {
		reversed = !reversed;
	}
	
	@Override
	public abstract Path clone();
}
