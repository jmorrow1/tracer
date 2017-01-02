package tracer;

import ease.Easing;
import ease.Easing.Linear;
import paths.Path;

/**
 * A Point that moves along a Path at some rate of speed.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class Tracer {
    protected Point pt; // The Tracer's location in 2D space, accessible via the
                        // location() method
    protected float u; // The Tracer's location in 1D space, relative to the
                       // Tracer's easing curve.
    protected float du; // The Tracer's speed in 1D space, relative to the
                        // Tracer's easing curve.
    protected Path path; // The Path to which the Tracer is attached
    protected Easing easing; // The easing curve determining how the Tracer
                             // moves in time.
    protected boolean upToDate = false; // Flag that indicates whether or not
                                        // the location stored in pt is up to
                                        // date.

    public Tracer(Path path, float startu, float du) {
        this(path, startu, du, new Linear());
    }

    public Tracer(Path path, float startu, float du, Easing easing) {
        this.u = startu % 1;
        this.du = du;
        this.path = path;
        this.pt = new Point(0, 0);
        this.easing = easing;
        getLocation();
    }

    public void step() {
        u = remainder(u + du, 1f);
        upToDate = false;
    }

    public void step(int dt) {
        u = remainder(u + du * dt, 1f);
        upToDate = false;
    }

    private void update() {
        float y = easing.val(u);
        path.trace(pt, y);
        upToDate = true;
    }

    public static float remainder(float num, float denom) {
        if (0 <= num && num < denom)
            return num;
        else if (num > 0)
            return num % denom;
        else
            return denom - ((-num) % denom);
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

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
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
