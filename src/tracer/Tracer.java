package tracer;

import easing.Easing;
import paths.IPath;

/**
 * A Point that moves along a Path at some rate of speed.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class Tracer {
	protected Point pt; //The Tracer's location in 2D space, accessible via the location() method 
	protected float x; //The Tracer's location in 1D space, relative to the Tracer's easing curve.
	protected float dx; //The Tracer's speed in 1D space, relative to the Tracer's easing curve.
	protected IPath path; //The Path to which the Tracer is attached
	protected Easing easing; //The easing curve determining how the Tracer moves in time.
	
	public Tracer(IPath path, float startx, float dx, Easing easing) {
		this.x = startx % 1;
		this.dx = dx;
		this.path = path;
		this.pt = new Point(0, 0);
		this.easing = easing;
	}
	
	public void step() {
		x = (x + dx) % 1;
	}
	
	public Point location() {
		float y = easing.val(x);
		path.trace(pt, y);
		return pt;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getDx() {
		return dx;
	}

	public void setDx(float dx) {
		this.dx = dx;
	}

	public IPath getPath() {
		return path;
	}

	public void setPath(IPath path) {
		this.path = path;
	}

	public Easing getEasing() {
		return easing;
	}

	public void setEasing(Easing easing) {
		this.easing = easing;
	}
}
