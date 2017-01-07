package paths;

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
     * Copy constructor.
     * 
     * @param s The infinity symbol to copy
     */
    public InfinitySymbol(InfinitySymbol s) {
        this(s.cen.clone(), s.xRadius, s.yRadius, s.sampleCount);
        setSampleCount(s.sampleCount);
    }

    /**
     * 
     * @param cenx the center x-coordinate
     * @param ceny the center y-coordinate
     * @param xRadius half the width
     * @param yRadius half the height
     * @param sampleCount the number of sample points
     */
    public InfinitySymbol(float cenx, float ceny, float xRadius, float yRadius, int sampleCount) {
        this(new Point(cenx, ceny), xRadius, yRadius, sampleCount);
    }
    
    /**
     * 
     * @param cen the center of the path
     * @param xRadius half the width
     * @param yRadius half the height
     * @param drawGranularity the number of sample points
     */
    public InfinitySymbol(Point cen, float xRadius, float yRadius, int drawGranularity) {
        super(drawGranularity);
        this.cen = cen;
        this.xRadius = xRadius;
        this.yRadius = yRadius;
    }

    /**
     * Easy constructor.
     * 
     * @param x The x-coordinate of the path.
     * @param y The y-coordinate of the path.
     * @param r The radius of the path.
     */
    public InfinitySymbol(float x, float y, float r) {
        this(x, y, r, r, 100);
    }
    
    /**
     * Easy constructor.
     * 
     * @param cen The center of the path
     * @param r The radius of the path.
     */
    public InfinitySymbol(Point cen, float r) {
        this(cen, r, r, 100);
    }

    /*************************
     ***** Functionality *****
     *************************/

    @Override
    public void trace(Point pt, float amt) {
        float radians = amt * PApplet.TWO_PI;
        if (reversed) {
            radians *= -1;
        }
        pt.x = cen.x + xRadius * PApplet.sin(radians);
        pt.y = cen.y + yRadius * PApplet.cos(radians) * PApplet.sin(radians);
    }

    @Override
    public void translate(float dx, float dy) {
        cen.translate(dx, dy);
    }

    /*******************************
     ***** Getters and Setters *****
     *******************************/
    
    /**
     * 
     * @return the center of the path
     */
    public Point getCenter() {
        return cen;
    }
    
    /**
     * 
     * @param cen the center of the path
     */
    public void setCenter(Point cen) {
        this.cen = cen;
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
     * @param cenx the center x-coordinate
     */
    public void setCenx(float cenx) {
        this.cen.x = cenx;
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
     * @param ceny the center y-coordinate
     */
    public void setCeny(float ceny) {
        this.cen.y = ceny;
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
     * @param xRadius half the width
     */
    public void setXRadius(float xRadius) {
        this.xRadius = xRadius;
    }

    /**
     * 
     * @return half the height
     */
    public float getYRadius() {
        return yRadius;
    }

    /**
     * 
     * @param yRadius half the height
     */
    public void setYRadius(float yRadius) {
        this.yRadius = yRadius;
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
        return -1;
    }
}