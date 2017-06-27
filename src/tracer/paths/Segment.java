package tracer.paths;

import processing.core.PApplet;
import processing.core.PGraphics;
import tracer.Point;

/**
 * 
 * A subpath of a parent Path.
 * If its parent changes, the Segment changes accordingly.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class Segment extends Path {
    private float u1, u2;
    private Path parent;
    
    /**************************
     ***** Initialization *****
     **************************/
    
    /**
     * 
     * @param parent The parent path
     * @param u1 The first 1D coordinate
     * @param u2 The second 1D coordinate
     */
    public Segment(Path parent, float u1, float u2) {
        this.parent = parent;
        setU1(u1);
        setU2(u2);
        setSamplesPerUnitLength(Path.defaultSamplesPerUnitLength);
    }
    
    /**
     * Copy constructor.
     * 
     * @param segment
     */
    public Segment(Segment segment) {
        this(segment.parent.clone(), segment.u1, segment.u2);
    }
    
    /**
     * Easy constructor.
     * 
     * @param x The center x-coordinate
     * @param y The center y-coordinate
     * @param r The radius
     */
    public Segment(float x, float y, float r) {
        this(new Rect(0, 0, r, r, RADIUS), 0, 0.33f);
    }
    
    /********************
     ***** Behavior *****
     ********************/
    
    @Override
    public void trace(Point target, float u) {
        u = Path.remainder(u, 1.0f);
        
        if (u1 < u2) {
            float v = PApplet.map(u, 0, 1, u1, u2);
            parent.trace(target, v);
        }
        else {
            float v = PApplet.map(u, 0, 1, u1, 1.0f + u2);
            if (v >= 1.0f) {
                v -= 1.0f;
            }
            parent.trace(target, v);
        }   
    }
    
    /******************
     ***** Events *****
     ******************/
    
    @Override
    public void translate(float dx, float dy) {
        parent.translate(dx, dy);
    }
    
    /**
     * Translates the segment's location within its path by the given 1D value.
     * @param du The 1D value
     */
    public void translate(float du) {
        u1 = remainder(u1 + du, 1);
        u2 = remainder(u2 + du, 1);
    }
    
    /**
     * Sets the first 1D coordinate of the Segment.
     * @param u1 The second 1D coordinate
     */
    public void setU1(float u1) {
        this.u1 = remainder(u1, 1);
    }

    /**
     * Sets the second 1D coordinate of the Segment.
     * @param u2 The second 1D coordinate
     */
    public void setU2(float u2) {
        this.u2 = remainder(u2, 1);
    }
    
    /*******************
     ***** Getters *****
     *******************/
    
    @Override
    public Path clone() {
        return new Segment(this);
    }
    
    @Override
    public int getGapCount() {
        int count = 0;
        
        if (u1 != u2) {
            count++;
        }
        
        for (int j=0; j<parent.getGapCount(); j++) {
            float gap = parent.getGap(j);
            if ((u1 < gap && gap < u2) || ((u1 > u2) && (u1 < gap || u2 > gap))) {
                count++;
            }
        } 
      
        return count;
    }
    
    @Override
    public float getGap(int i) {
        int originalIndex = i;
        
        if (i == 0) {
            return 0;
        }
        else if (i > 0) {
            i--;
            for (int j=0; j<parent.getGapCount(); j++) {
                float gap = parent.getGap(j);
                if (u1 < gap && gap < u2) {
                    if (i == 0) {
                        return PApplet.map(gap, u1, u2, 0.0f, 1.0f);
                    }
                    i--;
                }
                else if (u1 > u2) {
                    if (u1 < gap) {
                        return PApplet.map(gap, u1, u2+1.0f, 0.0f, 1.0f);
                    }
                    else if (gap < u2) {
                        return PApplet.map(gap, u1-1.0f, u2, 0.0f, 1.0f);
                    }
                }
            }
        }
      
        throw new IndexOutOfBoundsException(this.getClass().toString() + ".getGap(" + originalIndex + ")");
    }
    
    /**
     * Gives the first 1D coordinate of the Segment.
     * @return The first 1D coordinate
     */
    public float getU1() {
        return u1;
    }

    /**
     * Gives the second 1D coordinate of the Segment.
     * @return The second 1D coordinate
     */
    public float getU2() {
        return u2;
    }
    
    /**
     * Gives the x-coordinate of the first 2D coordinate of the Segment.
     * @return The x-coordinate of the first 2D coordinate
     */
    public float getX1() {
        parent.trace(bufferPoint, u1);
        return bufferPoint.x;
    }
    
    /**
     * Gives the y-coordinate of the first 2D coordinate of the Segment.
     * @return The y-coordinate of the first 2D coordinate
     */
    public float getY1() {
        parent.trace(bufferPoint, u1);
        return bufferPoint.y;
    }
    
    /**
     * Gives the x-coordinate of the second 2D coordinate of the Segment.
     * @return The x-coordinate of the second 2D coordinate
     */
    public float getX2() {
        parent.trace(bufferPoint, u2);
        return bufferPoint.x;
    }
    
    /**
     * Gives the y-coordinate of the second 2D coordinate of the Segment.
     * @return The y-coordinate of the second 2D coordinate
     */
    public float getY2() {
        parent.trace(bufferPoint, u2);
        return bufferPoint.y;
    }

    @Override
    public String toString() {
        return "Segment [u1=" + u1 + ", u2=" + u2 + ", parent=" + parent + "]";
    }
}
