package paths;

import processing.core.PApplet;

/**
 * 
 * @author James Morrow
 *
 */
public interface PathDefinition {
	public void trace(Point pt, float amt);
	public default Point trace(float amt) {
		Point pt = new Point(0, 0);
		trace(pt, amt);
		return pt;
	}
}