package tracer;

import ease.Easing;
import ease.Easing.Linear;
import paths.IPath;

/**
 * A Point that moves along a Path at some rate of speed.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class Tracer {
	protected Point pt; //The Tracer's location in 2D space, accessible via the location() method 
	protected float u; //The Tracer's location in 1D space, relative to the Tracer's easing curve.
	protected float du; //The Tracer's speed in 1D space, relative to the Tracer's easing curve.
	protected IPath path; //The Path to which the Tracer is attached
	protected Easing easing; //The easing curve determining how the Tracer moves in time.
	protected boolean upToDate = false; //Flag that indicates whether or not the location stored in pt is up to date.
	
	public Tracer(IPath path, float startx, float dx) {
		this(path, startx, dx, new Linear());
	}
	
	public Tracer(IPath path, float startu, float du, Easing easing) {
		this.u = startu % 1;
		this.du = du;
		this.path = path;
		this.pt = new Point(0, 0);
		this.easing = easing;
		getLocation();
	}
	
	public void step() {
		u = (u + du) % 1;
		upToDate = false;
	}
	
	private void update() {
		float y = easing.val(u);
		path.trace(pt, y);
		upToDate = true;
	}
	
	public Point getLocation() {
		if (!upToDate) {
			update();
		}	
		return pt;
	}

	public float getX() {
		if (!upToDate) {
			update();
		}	
		return pt.x;
	}
	
	public float getY() {
		if (!upToDate) {
			update();
		}
		return pt.y;
	}
	
	public float getU() {
		return u;
	}

	public void setU(float u) {
		this.u = u;
		upToDate = false;
	}

	public float getDu() {
		return du;
	}

	public void setDu(float du) {
		this.du = du;
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
