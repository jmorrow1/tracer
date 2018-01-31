package tracer.paths;

import processing.core.PApplet;
import processing.core.PGraphics;
import tracer.Point;

/**
 * A path that interpolates between two aggregate paths.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 * @param <T> the type of the first path
 * @param <U> the type of thes second path
 */
public class Blender<T extends Path, U extends Path> extends Path {
    private Point bufferPtA = new Point(0, 0);
    private Point bufferPtB = new Point(0, 0);
    private T a;
    private U b;
    private float blendAmt;

    /**************************
     ***** Initialization *****
     **************************/
    
    /**
     * 
     * @param a the first path
     * @param b the second path
     * @param blendAmt a value between within [0, 1) specifying how much to blend between a and b
     * @param sampleCount the number of sample points
     */
    public Blender(T a, U b, float blendAmt, int sampleCount) {
        super(sampleCount);
        this.a = a;
        this.b = b;
        this.blendAmt = blendAmt;
        setSamplesPerUnitLength(Path.defaultSamplesPerUnitLength);
    }

    /**
     * Copy constructor.
     * 
     * @param blender the blender to copy
     */
    public Blender(Blender<T, U> blender) {
        this((T)blender.a.clone(), (U)blender.b.clone(), blender.blendAmt, blender.sampleCount);
    }

    /**
     * 
     * @param a
     * @param b
     * @param blendAmt
     */
    public Blender(T a, U b, float blendAmt) {
        this(a, b, blendAmt, PApplet.max(a.sampleCount, b.sampleCount));
    }
    
    /********************
     ***** Behavior *****
     ********************/

    @Override
    public void trace(Point target, float u) {
        u = Path.remainder(u, 1.0f);
        if (reversed) {
            u = 1.0f - u;
            if (u == 1.0f) {
                u = ALMOST_ONE;
            }
        }
        a.trace(bufferPtA, u);
        b.trace(bufferPtB, u);
        target.x = PApplet.lerp(bufferPtA.x, bufferPtB.x, blendAmt);
        target.y = PApplet.lerp(bufferPtA.y, bufferPtB.y, blendAmt);
    }

    /******************
     ***** Events *****
     ******************/
    
    @Override
    public void translate(float dx, float dy) {
        a.translate(dx, dy);
        b.translate(dx, dy);
    }
    
    /**
     * 
     * @param a the first path
     */
    public void setA(T a) {
        this.a = a;
    }
    
    /**
     * 
     * @param b the second path
     */
    public void setB(U b) {
        this.b = b;
    }
    
    /**
     * 
     * @param blendAmt a value within [0, 1) specifying how much to blend between the first and second paths
     */
    public void setBlendAmt(float blendAmt) {
        this.blendAmt = blendAmt;
    }
    
    /**
     * 
     * @param dAmt how much to change the blendAmt
     */
    public void addToBlendAmt(float dAmt) {
        this.blendAmt = Path.remainder(this.blendAmt + dAmt, 1);
    }
    
    /*******************
     ***** Getters *****
     *******************/

    /**
     * 
     * @return the first path
     */
    public T getA() {
        return a;
    }

    /**
     * 
     * @return the second path
     */
    public U getB() {
        return b;
    }

    /**
     * 
     * @return a value within [0, 1) specifying how much to blend between the first and second paths
     */
    public float getBlendAmt() {
        return blendAmt;
    }

    @Override
    public Blender<T, U> clone() {
        return new Blender(this);
    }

    @Override
    public int getGapCount() {
        int j = 0; //loops through a's gaps
        int k = 0; //loops through b's gaps
        int count = 0; //loops through all gaps
        
        while (j < a.getGapCount() || k < b.getGapCount()) {
            float gapA = (j < a.getGapCount()) ? a.getGap(j) : -1;
            float gapB = (k < b.getGapCount()) ? b.getGap(k) : -1;
            if (gapA < gapB) {
                j++;
            }
            else if (gapA == gapB) {
                j++;
                k++;
            }
            else {
                k++;
            }
            count++;
        }

        return count;
    }

    @Override
    public float getGap(int i) {        
        if (!reversed) {
            int j = 0; //loops through a's gaps
            int k = 0; //loops through b's gaps
            int count = 0; //loops through all gaps
            
            while (j < a.getGapCount() || k < b.getGapCount()) {
                float gapA = (j < a.getGapCount()) ? a.getGap(j) : -1;
                float gapB = (k < b.getGapCount()) ? b.getGap(k) : -1;
                if (gapA < gapB) {
                    if (i == count) {
                        return gapA;
                    }
                    j++;
                }
                else if (gapA == gapB) {
                    if (i == count) {
                        return gapA;
                    }
                    j++;
                    k++;
                }
                else {
                    if (i == count) {
                        return gapB;
                    }
                    k++;
                }
                count++;
            }
        }
        //TODO test
        else {
            int j = a.getGapCount()-1; //loops through a's gaps
            int k = b.getGapCount()-1; //loops through b's gaps
            int count = 0; //loops through all gaps
            
            while (j < a.getGapCount() || k < b.getGapCount()) {
                float gapA = (j >= 0) ? a.getGap(j) : -1;
                float gapB = (k >= 0) ? b.getGap(k) : -1;
                
                gapA = 1.0f - gapA;
                if (gapA == 1.0f) {
                    gapA = 0.0f;
                }
                gapB = 1.0f - gapB;
                if (gapB == 1.0f) {
                    gapB = 0.0f;
                }
                
                if (gapA < gapB) {
                    if (i == count) {
                        return gapA;
                    }
                    j--;
                }
                else if (gapA == gapB) {
                    if (i == count) {
                        return gapA;
                    }
                    j--;
                    k--;
                }
                else {
                    if (i == count) {
                        return gapB;
                    }
                    k--;
                }
                count++;
            }
        }

        throw new IndexOutOfBoundsException(this.getClass().toString() + ".getGap(" + i + ")");
    }
    
    @Override
    public boolean isGap(float u) {       
        for (int i=0; i<a.getGapCount(); i++) {
            if (u == a.getGap(i)) {
                return true;
            }
        }
        
        for (int i=0; i<b.getGapCount(); i++) {
            if (u == b.getGap(i)) {
                return true;
            }
        }

        return false;
    }
    
    /**
     * Blends two Paths and makes a Shape.
     * @param a The first Path
     * @param b The second Path
     * @param amt A value within [0, 1), determining how much to weight Path b over Path a.
     * @param sampleCount The number of sample points to use
     * @return The Shape
     */
    public static Shape blend(Path a, Path b, float amt, int sampleCount) {
        return Shape.blend(a, b, amt, sampleCount);
    }

    @Override
    public String toString() {
        return "Blender [a=" + a.toString() + ", b=" + b.toString() + ", blendAmt=" + blendAmt + "]";
    }
}