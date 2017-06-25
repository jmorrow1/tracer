package tracer.paths;

import processing.core.PApplet;
import tracer.Point;

/**
 * 
 * An infinity symbol.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class InfinitySymbol extends Path {
    private Point cen;
    private float xRadius, yRadius;

    /**************************
     ***** Initialization *****
     **************************/
    
    /**
     * 
     * @param cen the center of the path
     * @param xRadius half the width
     * @param yRadius half the height
     */
    public InfinitySymbol(Point cen, float xRadius, float yRadius) {
        this.cen = cen;
        this.xRadius = xRadius;
        this.yRadius = yRadius;
        this.setSamplesPerUnitLength(Path.defaultSamplesPerUnitLength);
    }

    /**
     * Copy constructor.
     * 
     * @param s The infinity symbol to copy
     */
    public InfinitySymbol(InfinitySymbol s) {
        this(s.cen.clone(), s.xRadius, s.yRadius);
        setSampleCount(s.sampleCount);
    }
    
    /**
     * 
     * @param cenx the center x-coordinate
     * @param ceny the center y-coordinate
     * @param xRadius half the width
     * @param yRadius half the height
     */
    public InfinitySymbol(float cenx, float ceny, float xRadius, float yRadius) {
        this(new Point(cenx, ceny), xRadius, yRadius);
    }

    /**
     * Easy constructor.
     * 
     * @param x The x-coordinate of the path.
     * @param y The y-coordinate of the path.
     * @param r The radius of the path.
     */
    public InfinitySymbol(float x, float y, float r) {
        this(x, y, r, r);
    }
    
    /**
     * Easy constructor.
     * 
     * @param cen The center of the path
     * @param r The radius of the path.
     */
    public InfinitySymbol(Point cen, float r) {
        this(cen, r, r);
    }

    /********************
     ***** Behavior *****
     ********************/

    @Override
    public void trace(Point target, float u) {
        u = Path.remainder(u, 1.0f);
        
        float radians = u * PApplet.TWO_PI;
        if (reversed) {
            radians *= -1;
        }
        target.x = cen.x + xRadius * PApplet.sin(radians);
        target.y = cen.y + yRadius * PApplet.cos(radians) * PApplet.sin(radians);
    }

    /******************
     ***** Events *****
     ******************/
    
    @Override
    public void translate(float dx, float dy) {
        cen.translate(dx, dy);
    }
    
    /**
     * 
     * @param cen the center of the path
     */
    public void setCenter(Point cen) {
        this.cen = cen;
    }
    
    @Override
    public void setCenter(float x, float y) {
        this.cen.set(x, y);
    }
    
    /**
     * 
     * @param xRadius half the width
     */
    public void setXRadius(float xRadius) {
        this.xRadius = xRadius;
    }
    
    /**
     * 
     * @param yRadius half the height
     */
    public void setYRadius(float yRadius) {
        this.yRadius = yRadius;
    }

    /*******************
     ***** Getters *****
     *******************/
    
    /**
     * 
     * @return the center of the path
     */
    public Point getCenter() {
        return cen;
    }

    /**
     * 
     * @return the center x-coordinate
     */
    public float getCenx() {
        return cen.x;
    }

    /**
     * 
     * @return the center y-coordinate
     */
    public float getCeny() {
        return cen.y;
    }

    /**
     * 
     * @return half the width
     */
    public float getXRadius() {
        return xRadius;
    }

    /**
     * 
     * @return half the height
     */
    public float getYRadius() {
        return yRadius;
    }

    @Override
    public InfinitySymbol clone() {
        return new InfinitySymbol(this);
    }

    @Override
    public int getGapCount() {
        return 0;
    }

    @Override
    public float getGap(int i) {
        throw new IndexOutOfBoundsException(this.getClass().toString() + ".getGap(" + i + ")");
    }

    @Override
    public String toString() {
        return "InfinitySymbol [cen=" + cen + ", xRadius=" + xRadius + ", yRadius=" + yRadius + "]";
    }
}