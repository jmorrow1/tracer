package tracer.easings;

import processing.core.PApplet;

/**
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class Easings {
    private static Linear linear = new Linear();
    
    private static QuadEaseIn quadEaseIn;    
    private static QuadEaseOut quadEaseOut;
    private static QuadEaseInOut quadEaseInOut;   
    
    private static CubicEaseIn cubicEaseIn;
    private static CubicEaseOut cubicEaseOut;
    private static CubicEaseInOut cubicEaseInOut;
    
    private static CircEaseIn circEaseIn;
    private static CircEaseOut circEaseOut;
    private static CircEaseInOut circEaseInOut;
    
    private static LinearBackAndForth linearBackAndForth;
    
    private static QuadEaseInBackAndForth quadEaseInBackAndForth;
    private static QuadEaseOutBackAndForth quadEaseOutBackAndForth;
    private static QuadEaseInOutBackAndForth quadEaseInOutBackAndForth;
    
    private static CubicEaseInBackAndForth cubicEaseInBackAndForth;
    private static CubicEaseOutBackAndForth cubicEaseOutBackAndForth;
    private static CubicEaseInOutBackAndForth cubicEaseInOutBackAndForth;
    
    private static CircEaseInBackAndForth circEaseInBackAndForth;
    private static CircEaseOutBackAndForth circEaseOutBackAndForth;
    private static CircEaseInOutBackAndForth circEaseInOutBackAndForth;
    
    public static class Linear implements Easing {
        private Linear() {}
        
        public float val(float t) {
            return t;
        }
    }
    
    public static class LinearBackAndForth implements Easing {
        private LinearBackAndForth() {}
        
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
        private QuadEaseIn() {}
        
        public float val(float t) {
            return t * t;
        }
    }
    
    public static class QuadEaseInBackAndForth implements Easing {
        private QuadEaseInBackAndForth() {}
        
        public float val(float t) {
            if (t < 0.5f) {
                t *= 2;
                return t * t;
            }
            else {
                t = (t-0.5f) * 2;
                return 1f - (t * t);
            }
        }
    }

    public static class QuadEaseOut implements Easing {
        private QuadEaseOut() {}
        
        public float val(float t) {
            return -t * (t - 2);
        }
    }
    
    public static class QuadEaseOutBackAndForth implements Easing {
        private QuadEaseOutBackAndForth() {}
        
        public float val(float t) {
            if (t < 0.5f) {
                t *= 2;
                return -t * (t - 2);
            }
            else {
                t = (t-0.5f) * 2;
                return 1f + (t * (t - 2));
            }
        }
    }

    public static class QuadEaseInOut implements Easing {
        private QuadEaseInOut() {}
        
        public float val(float t) {
            t *= 2;
            if (t < 1) {
                return 0.5f * t * t;
            }               
            t--;
            return -0.5f * (t * (t - 2) - 1);
        }
    }
    
    public static class QuadEaseInOutBackAndForth implements Easing {
        private QuadEaseInOutBackAndForth() {}
        
        public float val(float t) {
            if (t < 0.5) {    
                t *= 4;
                if (t < 1) {
                    return 0.5f * t * t;
                }               
                t--;
                return -0.5f * (t * (t - 2) - 1);
            }
            else {
                t = (t - 0.5f) * 4f;
                if (t < 1) {
                    return 1f  - (0.5f * t * t * t);
                }
                t -= 2;
                return 1f - (0.5f * (t * t * t + 2));
            }
            
        }
    }

    public static class CubicEaseIn implements Easing {
        private CubicEaseIn() {}
        
        public float val(float t) {
            return t * t * t;
        }
    }
    
    public static class CubicEaseInBackAndForth implements Easing {
        private CubicEaseInBackAndForth() {}
        
        public float val(float t) {
            if (t < 0.5) {
                t *= 2;
                return t * t * t;
            }
            else {
                t = ((t - 0.5f) * 2f);
                return 1f - (t * t * t);
            }
        }
    }

    public static class CubicEaseOut implements Easing {
        private CubicEaseOut() {}
        
        public float val(float t) {
            t--;
            return t * t * t + 1;
        }
    }
    
    public static class CubicEaseOutBackAndForth implements Easing {
        private CubicEaseOutBackAndForth() {}
        
        public float val(float t) {
            if (t < 0.5f) {
                t = (2f * t) - 1f;
                return t * t * t + 1;
            }
            else {
                t = (2f * (t-0.5f)) - 1f;
                return 1f - (t * t * t + 1);
            }
        }
    }

    public static class CubicEaseInOut implements Easing {
        private CubicEaseInOut() {} 
        
        public float val(float t) {
            t *= 2;
            if (t < 1) {
                return 0.5f * t * t * t;
            }
            t -= 2;
            return 0.5f * (t * t * t + 2);
        }
    }
    
    public static class CubicEaseInOutBackAndForth implements Easing {
        private CubicEaseInOutBackAndForth() {}
        
        public float val(float t) {
            if (t < 0.5f) {
                t *= 4f;
                if (t < 1) {
                    return 0.5f * t * t * t;
                }
                t -= 2;
                return 0.5f * (t * t * t + 2);
            }
            else {
                t = (t-0.5f) * 4f;
                if (t < 1) {
                    return 1f - (0.5f * t * t * t);
                }
                t -= 2;
                return 1f - (0.5f * (t * t * t + 2));
            }
        }
    }

    public static class CircEaseIn implements Easing {
        private CircEaseIn() {}
        
        public float val(float t) {
            return -(PApplet.sqrt(1 - t * t) - 1);
        }
    }

    public static class CircEaseOut implements Easing {
        private CircEaseOut() {}
        
        public float val(float t) {
            t--;
            return PApplet.sqrt(1 - t * t);
        }
    }

    public static class CircEaseInOut implements Easing {
        private CircEaseInOut() {}
        
        public float val(float t) {
            t *= 2;
            if (t < 1) {
                return -0.5f * (PApplet.sqrt(1 - t * t) - 1);
            }  
            t -= 2;
            return 0.5f * (PApplet.sqrt(1 - t * t) + 1);
        }
    }
    
    public static class CircEaseInBackAndForth implements Easing {
        private CircEaseInBackAndForth () {}
        
        public float val(float t) {
            if (t < 0.5f) {
                t *= 2;
                return -(PApplet.sqrt(1 - t * t) - 1);
            }
            else {
                t = 2f * (t-0.5f);
                return 1f + (PApplet.sqrt(1 - t * t) - 1);
            }
            
        }
    }

    public static class CircEaseOutBackAndForth implements Easing {
        private CircEaseOutBackAndForth () {}
        
        public float val(float t) {
            if (t < 0.5f) {
                t = (2f * t) - 1f;
                return PApplet.sqrt(1 - t * t);
            }
            else {
                t = 2f * (t-0.5f) - 1f;
                return 1f - PApplet.sqrt(1 - t * t);
            }           
        }
    }

    public static class CircEaseInOutBackAndForth  implements Easing {
        private CircEaseInOutBackAndForth() {}
        
        public float val(float t) {
            if (t < 0.5f) {
                t *= 4;
                if (t < 1) {
                    return -0.5f * (PApplet.sqrt(1 - t * t) - 1);
                }
                t -= 2;
                return 0.5f * (PApplet.sqrt(1 - t * t) + 1);
            }
            else {
                t = (t - 0.5f) * 4f;
                if (t < 1) {
                    return 1f + (0.5f * (PApplet.sqrt(1 - t * t) - 1));
                }
                t -= 2;
                return 1f - (0.5f * (PApplet.sqrt(1 - t * t) + 1));
            }
        }
    }
    
    public static Linear getLinear() {
        if (linear == null) {
            linear = new Linear();
        }
        return linear;
    }
    
    public static LinearBackAndForth getLinearBackAndForth() {
        if (linearBackAndForth == null) {
            linearBackAndForth = new LinearBackAndForth();
        }
        return linearBackAndForth;
    }
    
    public static QuadEaseIn getQuadEaseIn() {
        if (quadEaseIn == null) {
            quadEaseIn = new QuadEaseIn();
        }
        return quadEaseIn;
    }
    
    public static QuadEaseOut getQuadEaseOut() {
        if (quadEaseOut == null) {
            quadEaseOut = new QuadEaseOut();
        }
        return quadEaseOut;
    }
    
    public static QuadEaseInOut getQuadEaseInOut() {
        if (quadEaseInOut == null) {
            quadEaseInOut = new QuadEaseInOut();
        }
        return quadEaseInOut;
    }
    
    public static QuadEaseInBackAndForth getQuadEaseInBackAndForth() {
        if (quadEaseInBackAndForth == null) {
            quadEaseInBackAndForth = new QuadEaseInBackAndForth();
        }
        return quadEaseInBackAndForth;
    }
    
    public static QuadEaseOutBackAndForth getQuadEaseOutBackAndForth() {
        if (quadEaseOutBackAndForth == null) {
            quadEaseOutBackAndForth = new QuadEaseOutBackAndForth();
        }
        return quadEaseOutBackAndForth;
    }
    
    public static QuadEaseInOutBackAndForth getQuadEaseInOutBackAndForth() {
        if (quadEaseInOutBackAndForth == null) {
            quadEaseInOutBackAndForth = new QuadEaseInOutBackAndForth();
        }
        return quadEaseInOutBackAndForth;
    }   
    
    public static CubicEaseIn getCubicEaseIn() {
        if (cubicEaseIn == null) {
            cubicEaseIn = new CubicEaseIn();
        }
        return cubicEaseIn;
    }
    
    public static CubicEaseOut getCubicEaseOut() {
        if (cubicEaseOut == null) {
            cubicEaseOut = new CubicEaseOut();
        }
        return cubicEaseOut;
    }
    
    public static CubicEaseInOut getCubicEaseInOut() {
        if (cubicEaseInOut == null) {
            cubicEaseInOut = new CubicEaseInOut();
        }
        return cubicEaseInOut;
    }

    public static CubicEaseInBackAndForth getCubicEaseInBackAndForth() {
        if (cubicEaseInBackAndForth == null) {
            cubicEaseInBackAndForth = new CubicEaseInBackAndForth();
        }
        return cubicEaseInBackAndForth;
    }
    
    public static CubicEaseOutBackAndForth getCubicEaseOutBackAndForth() {
        if (cubicEaseOutBackAndForth == null) {
            cubicEaseOutBackAndForth = new CubicEaseOutBackAndForth();
        }
        return cubicEaseOutBackAndForth;
    }
    
    public static CubicEaseInOutBackAndForth getCubicEaseInOutBackAndForth() {
        if (cubicEaseInOutBackAndForth == null) {
            cubicEaseInOutBackAndForth = new CubicEaseInOutBackAndForth();
        }
        return cubicEaseInOutBackAndForth;
    }
    
    public static CircEaseIn getCircEaseIn() {
        if (circEaseIn == null) {
            circEaseIn = new CircEaseIn();
        }
        return circEaseIn;
    }
    
    public static CircEaseOut getCircEaseOut() {
        if (circEaseOut == null) {
            circEaseOut = new CircEaseOut();
        }
        return circEaseOut;
    }
    
    public static CircEaseInOut getCircEaseInOut() {
        if (circEaseInOut == null) {
            circEaseInOut = new CircEaseInOut();
        }
        return circEaseInOut;
    }
    
    public static CircEaseInBackAndForth getCircEaseInBackAndForth() {
        if (circEaseInBackAndForth == null) {
            circEaseInBackAndForth = new CircEaseInBackAndForth();
        }
        return circEaseInBackAndForth;
    }
    
    public static CircEaseOutBackAndForth getCircEaseOutBackAndForth() {
        if (circEaseOutBackAndForth == null) {
            circEaseOutBackAndForth = new CircEaseOutBackAndForth();
        }
        return circEaseOutBackAndForth;
    }
    
    public static CircEaseInOutBackAndForth getCircEaseInOutBackAndForth() {
        if (circEaseInOutBackAndForth == null) {
            circEaseInOutBackAndForth = new CircEaseInOutBackAndForth();
        }
        return circEaseInOutBackAndForth;
    }
    
    public static int getEasingCount() {
        return 20;
    }
    
    public static Easing getEasing(int i) {
        switch (i) {
            case 0 : return getLinear();
            case 1 : return getQuadEaseIn();
            case 2 : return getQuadEaseOut();
            case 3 : return getQuadEaseInOut();
            case 4 : return getCubicEaseIn();
            case 5 : return getCubicEaseOut();
            case 6 : return getCubicEaseInOut();
            case 7 : return getCircEaseIn();
            case 8 : return getCircEaseOut();
            case 9 : return getCircEaseInOut();
            
            case 10: return getLinearBackAndForth();
            case 11 : return getQuadEaseInBackAndForth();
            case 12 : return getQuadEaseOutBackAndForth();
            case 13 : return getQuadEaseInOutBackAndForth();
            case 14 : return getCubicEaseInBackAndForth();
            case 15 : return getCubicEaseOutBackAndForth();
            case 16 : return getCubicEaseInOutBackAndForth();
            case 17 : return getCircEaseInBackAndForth();
            case 18 : return getCircEaseOutBackAndForth();
            case 19 : return getCircEaseInOutBackAndForth();
            
            default : throw new IndexOutOfBoundsException("Easing " + i + " does not exist.");
        }
    }
}
