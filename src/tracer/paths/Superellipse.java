package tracer.paths;

import processing.core.PApplet;
import processing.core.PGraphics;
import tracer.Point;

/**
 * 
 * A more generalized version of the <a href="Ellipse.html">ellipse</a>.
 * 
 * <br>
 * <br>
 * 
 * <a href="http://paulbourke.net/geometry/superellipse/"> More info on the
 * superellipse </a>.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class Superellipse extends Path {
    private Point cen;
    private float xRadius, yRadius;
    private float n;
    private float twoOverN;

    /**************************
     ***** Initialization *****
     **************************/

    /**
     * 
     * @param cen the center of the path
     * @param xRadius half the width
     * @param yRadius half the height
     * @param n controls the amount of pinching (smaller values give more pinching)
     */
    public Superellipse(Point cen, float xRadius, float yRadius, float n) {
        this.cen = cen;
        this.xRadius = xRadius;
        this.yRadius = yRadius;
        this.n = n;
        this.twoOverN = 2f / n;
        setSamplesPerUnitLength(Path.defaultSamplesPerUnitLength);
    }
    
    /**
     * Easy constructor.
     * 
     * @param x The x-coordinate of the path.
     * @param y The y-coordinate of the path.
     * @param r The radius of the path.
     */
    public Superellipse(float x, float y, float r) {
        this(x, y, r, r, 0.5f);
    }

    /**
     * Easy constructor.
     * 
     * @param cen The center of the path.
     * @param r The radius of the path.
     */
    public Superellipse(Point cen, float r) {
        this(cen, r, r, 0.5f);
    }
    
    /**
     * Copy constructor.
     * 
     * @param e the superellipse to copy
     */
    public Superellipse(Superellipse e) {
        this(e.cen.clone(), e.xRadius, e.yRadius, e.n);
        setSampleCount(e.sampleCount);
    }
    
    /**
     * 
     * @param cenx the center x-coordinate
     * @param ceny the center y-coordinate
     * @param xRadius half the width
     * @param yRadius half the height
     * @param n controls the amount of pinching (smaller values give more
     *            pinching)
     * @param sampleCount the number of sample points
     */
    public Superellipse(float cenx, float ceny, float xRadius, float yRadius, float n) {
        this(new Point(cenx, ceny), xRadius, yRadius, n);
    }
    
    /********************
     ***** Behavior *****
     ********************/

    @Override
    public void trace(Point target, float u) {
        u = Path.remainder(u, 1.0f);
        
        float theta = u * PApplet.TWO_PI;
        if (reversed) {
            theta *= -1;
        }
        float cosTheta = PApplet.cos(theta);
        target.x = cen.x + PApplet.pow(PApplet.abs(cosTheta), twoOverN) * xRadius * sign(cosTheta);
        float sinTheta = PApplet.sin(theta);
        target.y = cen.y + PApplet.pow(PApplet.abs(sinTheta), twoOverN) * yRadius * sign(sinTheta);
    }

    private static int sign(float x) {
        if (x > 0) {
            return 1;
        }
        else if (x < 0) {
            return -1;
        }
        else {
            return 0;
        }
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

    /**
     * 
     * @param cenx the center x-coordinate
     */
    public void setCenx(float cenx) {
        this.cen.x = cenx;
    }
    
    /**
     * 
     * @param ceny the center y-coordinate
     */
    public void setCeny(float ceny) {
        this.cen.y = ceny;
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

    /**
     * 
     * @param n the amount of pinching (smaller amounts of n give more pinching)
     */
    public void setN(float n) {
        this.n = n;
        this.twoOverN = 2f / n;
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
    
    /**
     * 
     * @return the amount of pinching (smaller amounts of n give more pinching)
     */
    public float getN() {
        return n;
    }

    @Override
    public Superellipse clone() {
        return new Superellipse(this);
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
        return "Superellipse [cen=" + cen + ", xRadius=" + xRadius + ", yRadius=" + yRadius + ", n=" + n + "]";
    }
}