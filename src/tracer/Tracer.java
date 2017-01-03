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
public class Tracer extends Point {
    protected float u; // The Tracer's location in 1D space, relative to the
                       // Tracer's easing curve.
    protected float du; // The Tracer's speed in 1D space, relative to the
                        // Tracer's easing curve.
    protected Path path; // The Path to which the Tracer is attached
    protected Easing easing; // The easing curve determining how the Tracer
                             // moves in time.

    public Tracer(Path path, float startu, float du) {
        this(path, startu, du, new Linear());
    }

    public Tracer(Path path, float startu, float du, Easing easing) {
        super(path.trace(startu));
        this.u = startu % 1;
        this.du = du;
        this.path = path;
        this.easing = easing;
    }

    public void step() {
        u = remainder(u + du, 1f);
        update();
    }

    public void step(int dt) {
        u = remainder(u + du * dt, 1f);
        update();
    }

    private void update() {
        float y = easing.val(u);
        path.trace(this, y);
    }

    public static float remainder(float num, float denom) {
        if (0 <= num && num < denom)
            return num;
        else if (num > 0)
            return num % denom;
        else
            return denom - ((-num) % denom);
    }
    
    public float getU() {
        return u;
    }

    public void setU(float u) {
        this.u = u;
        update();
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
        update();
    }

    public Easing getEasing() {
        return easing;
    }

    public void setEasing(Easing easing) {
        this.easing = easing;
        update();
    }
}
