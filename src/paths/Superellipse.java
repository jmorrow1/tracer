package paths;

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
     * Copy constructor.
     * 
     * @param e the superellipse to copy
     */
    public Superellipse(Superellipse e) {
        this(e.cen.clone(), e.xRadius, e.yRadius, e.n, e.sampleCount);
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
    public Superellipse(float cenx, float ceny, float xRadius, float yRadius, float n, int sampleCount) {
        this(new Point(cenx, ceny), xRadius, yRadius, n, sampleCount);
    }

    /**
     * 
     * @param cen the center of the path
     * @param xRadius half the width
     * @param yRadius half the height
     * @param n controls the amount of pinching (smaller values give more
     *            pinching)
     * @param sampleCount the number of sample points
     */
    public Superellipse(Point cen, float xRadius, float yRadius, float n, int sampleCount) {
        super(sampleCount);
        this.cen = cen;
        this.xRadius = xRadius;
        this.yRadius = yRadius;
        this.n = n;
        this.twoOverN = 2f / n;
    }
    
    /**
     * Easy constructor.
     * 
     * @param x The x-coordinate of the path.
     * @param y The y-coordinate of the path.
     * @param r The radius of the path.
     */
    public Superellipse(float x, float y, float r) {
        this(x, y, r, r, 1, 100);
    }

    /**
     * Easy constructor.
     * 
     * @param cen The center of the path.
     * @param r The radius of the path.
     */
    public Superellipse(Point cen, float r) {
        this(cen, r, r, 1, 100);
    }
    
    /*************************
     ***** Functionality *****
     *************************/

    @Override
    public void trace(Point pt, float u) {
        if (u < 0 || u >= 1) {
            throw new IllegalArgumentException("trace(pt, " + u + ") called where the second argument is outside the range 0 (inclusive) to 1 (exclusive).");
        }
        
        float theta = u * PApplet.TWO_PI;
        if (reversed) {
            theta *= -1;
        }
        float cosTheta = PApplet.cos(theta);
        pt.x = cen.x + PApplet.pow(PApplet.abs(cosTheta), twoOverN) * xRadius * sgn(cosTheta);
        float sinTheta = PApplet.sin(theta);
        pt.y = cen.y + PApplet.pow(PApplet.abs(sinTheta), twoOverN) * yRadius * sgn(sinTheta);
    }

    @Override
    public void draw(PGraphics g) {
        style.apply(g);
        draw(g, sampleCount);
    }

    @Override
    public void translate(float dx, float dy) {
        cen.translate(dx, dy);
    }

    private static int sgn(float x) {
        if (x > 0)
            return 1;
        if (x < 0)
            return -1;
        return 0;
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

    /**
     * 
     * @return the amount of pinching (smaller amounts of n give more pinching)
     */
    public float getN() {
        return n;
    }

    /**
     * 
     * @param n the amount of pinching (smaller amounts of n give more pinching)
     */
    public void setN(float n) {
        this.n = n;
        this.twoOverN = 2f / n;
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
        return -1;
    }
}