package tracer;

import ease.Easing;
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
	protected boolean upToDate = false; //Flag that indicates whether or not the location stored in pt is up to date.
	
	public Tracer(IPath path, float startx, float dx, Easing easing) {
		this.x = startx % 1;
		this.dx = dx;
		this.path = path;
		this.pt = new Point(0, 0);
		this.easing = easing;
		location();
	}
	
	public void step() {
		x = (x + dx) % 1;
		upToDate = false;
	}
	
	public Point location() {
		if (!upToDate) {
			float y = easing.val(x);
			path.trace(pt, y);
			upToDate = true;
		}	
		return pt;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
		upToDate = false;
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
		upToDate = false;
	}

	public Easing getEasing() {
		return easing;
	}

	public void setEasing(Easing easing) {
		this.easing = easing;
		upToDate = false;
	}
}
