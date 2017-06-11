package tracer.paths;

import processing.core.PApplet;
import processing.core.PGraphics;
import tracer.Point;

/**
 * A Bezier curve defined by two anchor points and two control points.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class CubicBezier extends Path {
    protected Point a1, c1, c2, a2; //anchor and control points

    /**************************
     ***** Initialization *****
     **************************/
    
    /**
     * 
     * @param a1 the first anchor point
     * @param c1 the first control point
     * @param c2 the second control point
     * @param a2 the second anchor point
     */
    public CubicBezier(Point a1, Point c1, Point c2, Point a2) {
        this.a1 = a1;
        this.c1 = c1;
        this.c2 = c2;
        this.a2 = a2;
    }

    /**
     * Copy constructor
     * 
     * @param b The Bezier to copy
     */
    public CubicBezier(CubicBezier b) {
        this(b.a1.clone(), b.c1.clone(), b.c2.clone(), b.a2.clone());
        setSampleCount(b.sampleCount);
    }

    /**
     * 
     * @param ax1 the x-coordinate of the 1st anchor point
     * @param ay1 the y-coordinate of the 1st anchor point
     * @param cx1 the x-coordinate of the 1st control point
     * @param cy1 the y-coordinate of the 1st control point
     * @param cx2 the x-coordinate of the 2nd control point
     * @param cy2 the y-coordinate of the 2nd control point
     * @param ax2 the x-coordinate of the 2nd anchor point
     * @param ay2 the y-coordinate of the 2nd anchor point
     */
    public CubicBezier(float ax1, float ay1, float cx1, float cy1, float cx2, float cy2, float ax2, float ay2) {
        this(new Point(ax1, ay1), new Point(cx1, cy1), new Point(cx2, cy2), new Point(ax2, ay2));
    }

    /**
     * Easy constructor.
     * 
     * @param x The x-coordinate of the path.
     * @param y The y-coordinate of the path.
     * @param r The radius of the path.
     */
    public CubicBezier(float x, float y, float r) {
        this(x - r, y, x, y - r, x, y - r, x + r, y);
    }
    
    /*************************
     ***** Functionality *****
     *************************/

    @Override
    public void trace(Point pt, float u) {
        if (u < 0 || u >= 1) {
            throw new IllegalArgumentException(CubicBezier.class.getName() + ".trace(pt, " + u + ") called where the second argument is outside the range 0 (inclusive) to 1 (exclusive).");
        }
        
        if (reversed) {
            u = 1.0f - u;
            if (u == 1.0f) {
                u = ALMOST_ONE;
            }
        }
            
        pt.x = bezierPoint(a1.x, c1.x, c2.x, a2.x, u);
        pt.y = bezierPoint(a1.y, c1.y, c2.y, a2.y, u);
    }

    @Override
    public void draw(PGraphics g) {
        style.apply(g);
        g.bezier(a1.x, a1.y, c1.x, c1.y, c2.x, c2.y, a2.x, a2.y);
    }

    @Override
    public void translate(float dx, float dy) {
        a1.x += dx;
        c1.x += dx;
        c2.x += dx;
        a2.x += dx;

        a1.y += dy;
        c1.y += dy;
        c2.y += dy;
        a2.y += dy;
    }

    private static float bezierPoint(float a, float b, float c, float d, float t) {
        float t1 = 1.0f - t;
        float t1Sq = t1 * t1;
        float tSq = t * t;
        return a * t1Sq * t1 +
               3 * b * t * t1Sq +
               3 * c * tSq * t1 +
               d * tSq * t;
    }

    /*******************************
     ***** Getters and Setters *****
     *******************************/

    /**
     * 
     * @return the x-coordinate of the 1st anchor point
     */
    public float getAnchorX1() {
        return a1.x;
    }

    /**
     * 
     * @param ax1 the x-coordinate of the 1st anchor point
     */
    public void setAnchorX1(float ax1) {
        this.a1.x = ax1;
    }

    /**
     * 
     * @return the y-coordinate of the 1st anchor point
     */
    public float getAnchorY1() {
        return a1.y;
    }

    /**
     * 
     * @param ay1 the y-coordinate of the 2nd anchor point
     */
    public void setAnchorY1(float ay1) {
        this.a1.y = ay1;
    }

    /**
     * 
     * @return the x-coordinate of the 1st control point
     */
    public float getControlX1() {
        return c1.x;
    }

    /**
     * 
     * @param cx1 the x-coordinate of the 1st anchor point
     */
    public void setControlX1(float cx1) {
        this.c1.x = cx1;
    }

    /**
     * 
     * @return the t-coordinate of the 1st control point
     */
    public float getControlY1() {
        return c1.y;
    }

    /**
     * 
     * @param cy1 the y-coordinate of the 1st control point
     */
    public void setControlY1(float cy1) {
        this.c1.y = cy1;
    }

    /**
     * 
     * @return the x-coordinate of the 2nd control point
     */
    public float getControlX2() {
        return c2.x;
    }

    /**
     * 
     * @param cx2 the x-coordinate of the 2nd control point
     */
    public void setControlX2(float cx2) {
        this.c2.x = cx2;
    }

    /**
     * 
     * @return the y-coordinate of the 2nd control point
     */
    public float getControlY2() {
        return c2.y;
    }

    /**
     * 
     * @param cy2 the y-coordinate of the 2nd control point
     */
    public void setControlY2(float cy2) {
        this.c2.y = cy2;
    }

    /**
     * 
     * @return the x-coordinate of the 2nd anchor point
     */
    public float getAnchorX2() {
        return a2.x;
    }

    /**
     * 
     * @param ax2 the x-coordinate of the 2nd anchor point
     */
    public void setAnchorX2(float ax2) {
        this.a2.x = ax2;
    }

    /**
     * 
     * @return the y-coordinate of the 2nd anchor point
     */
    public float getAnchorY2() {
        return a2.y;
    }

    /**
     * 
     * @param ay2 the y-coordinate of the 2nd anchor point
     */
    public void setAnchorY2(float ay2) {
        this.a2.y = ay2;
    }
    
    /**
     * 
     * @param a1 the first anchor point
     */
    public void setAnchor1(Point a1) {
        this.a1 = a1;
    }
    
    /**
     * 
     * @return the first anchor point
     */
    public Point getAnchor1() {
        return a1;
    }
    
    /**
     * 
     * @param c1 the first control point
     */
    public void setControl1(Point c1) {
        this.c1 = c1;
    }
    
    /**
     * 
     * @return the first control point
     */
    public Point getControl1() {
        return c1;
    }
    
    /**
     * 
     * @param c2 the second control point
     */
    public void setControl2(Point c2) {
        this.c2 = c2;
    }
    
    /**
     * 
     * @return the second control point
     */
    public Point getControl2() {
        return c2;
    }
    
    /**
     * 
     * @param a2 the second anchor point
     */
    public void setAnchor2(Point a2) {
        this.a2 = a2;
    }
    
    /**
     * 
     * @return the second anchor point
     */
    public Point getAnchor2() {
        return a2;
    }

    @Override
    public CubicBezier clone() {
        return new CubicBezier(this);
    }

    @Override
    public int getGapCount() {
        if (a1.x == a2.x && a1.y == a2.y) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public float getGap(int i) {
        if (getGapCount() == 1 && i == 0) {
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "CubicBezier [a1=" + a1 + ", c1=" + c1 + ", c2=" + c2 + ", a2=" + a2 + "]";
    }
}
