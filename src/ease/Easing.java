package ease;

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

    public static class Linear implements Easing {
        public float val(float t) {
            return t;
        }
    }
    
    public static class BackAndForth implements Easing {
        public float val(float t) {
            if (t < 0.5f) {
                return 2f * t;
            }
            else {
                return 2f * (1f - t);
            }
        }
    }

    public static class QuadEaseIn implements Easing {
        public float val(float t) {
            return t * t;
        }
    }

    public static class QuadEaseOut implements Easing {
        public float val(float t) {
            return -t * (t - 2);
        }
    }

    public static class QuadEaseInOut implements Easing {
        public float val(float t) {
            t *= 2;
            if (t < 1)
                return 0.5f * t * t;
            t--;
            return -0.5f * (t * (t - 2) - 1);
        }
    }

    public static class CubicEaseIn implements Easing {
        public float val(float t) {
            return t * t * t;
        }
    }

    public static class CubicEaseOut implements Easing {
        public float val(float t) {
            t--;
            return t * t * t + 1;
        }
    }

    public static class CubicEaseInOut implements Easing {
        public float val(float t) {
            t *= 2;
            if (t < 1)
                return 0.5f * t * t * t;
            t -= 2;
            return 0.5f * (t * t * t + 2);
        }
    }

    public static class CircEaseIn implements Easing {
        public float val(float t) {
            return -(PApplet.sqrt(1 - t * t) - 1);
        }
    }

    public static class CircEaseOut implements Easing {
        public float val(float t) {
            t--;
            return PApplet.sqrt(1 - t * t);
        }
    }

    public static class CircEaseInOut implements Easing {
        public float val(float t) {
            t *= 2;
            if (t < 1)
                return -0.5f * (PApplet.sqrt(1 - t * t) - 1);
            t -= 2;
            return 0.5f * (PApplet.sqrt(1 - t * t) + 1);
        }
    }
}
