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
    protected float input1D; //@param The Tracer's location in 1D space, relative to the Tracer's easing curve.
    protected float speed1D; //@param The Tracer's speed in 1D space, relative to the Tracer's easing curve.
    protected T path; //@param The Path to which the Tracer is attached
    protected Easing easing; //@param The easing curve determining how the Tracer moves in time.
    
    /**************************
     ***** Initialization *****
     **************************/
    
    /**
     * Copy constructor.
     * @param t The Tracer to copy
     */
    public Tracer(Tracer<T> t) {
        this(t.path, t.input1D, t.speed1D, t.easing);
    }

    /**
     * 
     * @param path The Path
     * @param initInput1D The starting one-dimensional coordinate
     * @param speed1D The one-dimensional speed
     */
    public Tracer(T path, float initInput1D, float speed1D) {
        this(path, initInput1D, speed1D, Easings.getLinear());
    }

    /**
     * 
     * @param path The Path
     * @param initInput1D The starting one-dimensional coordinate
     * @param speed1D The one-dimensional speed
     * @param easing The easing curve
     */
    public Tracer(T path, float initInput1D, float speed1D, Easing easing) {
        super(path.trace(initInput1D));
        this.input1D = Path.remainder(initInput1D, 1f);
        this.speed1D = speed1D;
        this.path = path;
        this.easing = easing;
    }

    /********************
     ***** Behavior *****
     ********************/
    
    /**
     * Moves the Tracer along its Path by its speed.
     */
    public void step() {
        input1D = remainder(input1D + speed1D, 1f);
        trace();
    }

    /**
     * Moves the Tracer along its Path by its speed multiplied by the given time step.
     * @param dt The time step
     */
    public void step(int dt) {
        input1D = remainder(input1D + speed1D * dt, 1f);
        trace();
    }
    
    private void trace() {
        float y = easing.val(input1D);
        path.trace(this, y);
    }
    
    /******************
     ***** Events *****
     ******************/
    
    /**
     * Sets the one-dimensional coordinate of the Tracer (a value between 0 (inclusive) and 1 (exclusive)).
     * @param input1D The one-dimensional coordinate
     */
    public void setInput1D(float input1D) {
        this.input1D = remainder(input1D, 1.0f);
        trace();
    }
    
    /**
     * Sets the one-dimensional speed of the Tracer.
     * @param speed1D The one-dimensional speed
     */
    public void setSpeed1D(float speed1D) {
        this.speed1D = speed1D;
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
     * Sets the Easing.
     * @param easing The Easing
     */
    public void setEasing(Easing easing) {
        this.easing = easing;
        trace();
    }
    
    /*******************
     ***** Getters *****
     *******************/
    
    /**
     * 
     * @return
     */
    public float getOutput1D() {
        return easing.val(input1D);
    }

    /**
     * Gives the one-dimensional coordinate of the Tracer (a value between 0 (inclusive) and 1 (exclusive)).
     * @return The one-dimensional coordinate
     */
    public float getInput1D() {
        return input1D;
    }

    /**
     * Gives the one-dimensional speed of the Tracer.
     * @return The one-dimensional speed of the Tracer
     */
    public float getSpeed1D() {
        return speed1D;
    }

    /**
     * Gives the Path.
     * @return The Path
     */
    public T getPath() {
        return path;
    }

    /**
     * Gives the Easing.
     * @return The Easing
     */
    public Easing getEasing() {
        return easing;
    }
    
    @Override
    public Tracer clone() {
        return new Tracer(this);
    }

    @Override
    public String toString() {
        return "Tracer [input1D=" + input1D + ", speed1D=" + speed1D + ", path=" + path + "]";
    }
    
    /******************
     ***** Static *****
     ******************/
    
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
