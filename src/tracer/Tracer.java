package tracer;

import java.util.Collection;

import tracer.easings.Easing;
import tracer.easings.Easings;
import tracer.paths.Path;

/**
 * A Point that moves along a Path at some rate of speed, possibly speeding up and slowing down according to the curve specified by an Easing.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class Tracer<T extends Path> extends Point {
    protected float u; //@param The Tracer's location in 1D space, relative to the Tracer's easing curve.
    protected float du; //@param The Tracer's speed in 1D space, relative to the Tracer's easing curve.
    protected T path; //@param The Path to which the Tracer is attached
    protected Easing easing; //@param The easing curve determining how the Tracer moves in time.
    
    /**
     * Copy constructor.
     * @param t The Tracer to copy
     */
    public Tracer(Tracer<T> t) {
        this(t.path, t.u, t.du, t.easing);
    }

    /**
     * 
     * @param path The Path
     * @param startu The starting one-dimensional coordinate
     * @param du The one-dimensional speed
     */
    public Tracer(T path, float startu, float du) {
        this(path, startu, du, Easings.getLinear());
    }

    /**
     * 
     * @param path The Path
     * @param startu The starting one-dimensional coordinate
     * @param du The one-dimensional speed
     * @param easing The easing curve
     */
    public Tracer(T path, float startu, float du, Easing easing) {
        super(path.trace(startu));
        this.u = Path.remainder(startu, 1f);
        this.du = du;
        this.path = path;
        this.easing = easing;
    }

    /**
     * Moves the Tracer along its Path by its speed.
     */
    public void step() {
        u = remainder(u + du, 1f);
        trace();
    }

    /**
     * Moves the Tracer along its Path by its speed multiplied by the given time step.
     * @param dt The time step
     */
    public void step(int dt) {
        u = remainder(u + du * dt, 1f);
        trace();
    }

    private void trace() {
        float y = easing.val(u);
        path.trace(this, y);
    }
    
    /**
     * 
     * @return
     */
    public float getV() { //TODO name this better
        return easing.val(u);
    }

    /**
     * Gives the one-dimensional coordinate of the Tracer (a value between 0 (inclusive) and 1 (exclusive)).
     * @return The one-dimensional coordinate
     */
    public float getU() {
        return u;
    }

    /**
     * Sets the one-dimensional coordinate of the Tracer (a value between 0 (inclusive) and 1 (exclusive)).
     * @param u The one-dimensional coordinate
     */
    public void setU(float u) {
        this.u = remainder(u, 1.0f);
        trace();
    }

    /**
     * Gives the one-dimensional speed of the Tracer.
     * @return The one-dimensional speed of the Tracer
     */
    public float getDu() {
        return du;
    }

    /**
     * Sets the one-dimensional speed of the Tracer.
     * @param du The one-dimensional speed
     */
    public void setDu(float du) {
        this.du = du;
    }

    /**
     * Gives the Path.
     * @return The Path
     */
    public T getPath() {
        return path;
    }

    /**
     * Sets the Path.
     * @param path The Path
     */
    public void setPath(T path) {
        this.path = path;
        trace();
    }

    /**
     * Gives the Easing.
     * @return The Easing
     */
    public Easing getEasing() {
        return easing;
    }

    /**
     * Sets the Easing.
     * @param easing The Easing
     */
    public void setEasing(Easing easing) {
        this.easing = easing;
        trace();
    }
    
    @Override
    public Tracer clone() {
        return new Tracer(this);
    }

    @Override
    public String toString() {
        return "Tracer [u=" + u + ", du=" + du + ", path=" + path + "]";
    }
    
    /**
     * Steps every Tracer in the array
     * @param tracers The array of Tracers
     */
    public static void step(Tracer[] tracers) {
        for (Tracer t : tracers) {
            t.step();
        }
    }
    
    /**
     * Steps every Tracer in the array by the given time step.
     * @param tracers The array of Tracers
     * @param dt The time step
     */
    public static void step(Tracer[] tracers, int dt) {
        for (Tracer t : tracers) {
            t.step(dt);
        }
    }
    
    /**
     * Computes the remainder of num / denom.
     * 
     * @param num the numerator
     * @param denom the denominator
     * @return The remainder of num / denom
     */
    public static float remainder(float num, float denom) {
        if (0 <= num && num < denom) {
            return num;
        }
        else if (num > 0) {
            return num % denom;
        }
        else {
            float result = denom - ((-num) % denom);
            if (result == denom) {
                result = 0;
            }
            return result;
        }
    }
}
