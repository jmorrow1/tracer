package tracer.easings;

import processing.core.PApplet;

/**
 * Represents an easing curve (AKA a tweening curve) that controls the speed of
 * an animation over time.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public interface Easing {    
    /**
     * Given a normal value, returns a normal value.
     * 
     * Behavior is undefined for input values outside of 0 to 1.
     * 
     * @param t A normal value
     * @return A normal value (assuming t is a normal value)
     */
    public float val(float t); 
}
