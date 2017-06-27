package tracer.paths;

import processing.core.PApplet;
import processing.core.PGraphics;
import tracer.Point;

/**
 * 
 * A more generalized version of the
 * <a href="Superellipse.html">superellipse</a>
 * 
 * <br>
 * <br>
 * 
 * <a href="http://paulbourke.net/geometry/supershape/"> More info on the
 * supershape </a>
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class Supershape extends Path {
    private Point cen;
    private float xRadius, yRadius, m, n1, n2, n3;

    private float mOver4, n1Inverted;

    /**************************
     ***** Initialization *****
     **************************/
    
    /**
     * 
     * @param cen the center of the path
     * @param xRadius half the width
     * @param yRadius half the height
     * @param m controls the number of rotational symmetries
     * @param n1 controls the amount of pinching (lesser values of n1 give more
     *            pinching)
     * @param n2
     * @param n3
     */
    public Supershape(Point cen, float xRadius, float yRadius, float m, float n1, float n2, float n3) {
        this.cen  = cen;
        this.xRadius = xRadius;
        this.yRadius = yRadius;
        this.m = m;
        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;
        this.mOver4 = m / 4f;
        this.n1Inverted = 1f / n1;
        setSamplesPerUnitLength(Path.defaultSamplesPerUnitLength);
    }

    /**
     * Copy constructor.
     * 
     * @param s the supershape to copy
     */
    public Supershape(Supershape s) {
        this(s.cen.clone(), s.xRadius, s.yRadius, s.m, s.n1, s.n2, s.n3);
        setSampleCount(s.sampleCount);
    }

    /**
     * 
     * @param cenx the center x-coordinate
     * @param ceny the center y-coordinate
     * @param xRadius half the width
     * @param yRadius half the height
     * @param m controls the number of rotational symmetries
     * @param n1 controls the amount of pinching (lesser values of n1 give more
     *            pinching)
     * @param n2
     * @param n3
     */
    public Supershape(float cenx, float ceny, float xRadius, float yRadius, float m, float n1, float n2, float n3) {
        this(new Point(cenx, ceny), xRadius, yRadius, m, n1, n2, n3);
    }

    /**
     * 
     * @param cenx the center x-coordinate
     * @param ceny the center y-coordinate
     * @param xRadius half the width
     * @param yRadius half the height
     * @param m controls the number of rotational symmetries
     */
    public Supershape(float cenx, float ceny, float xRadius, float yRadius, float m) {
        this(cenx, ceny, xRadius, yRadius, m, 1, 1, 1);
    }

    /**
     * 
     * @param cen the center of the path
     * @param xRadius half the width
     * @param yRadius half the height
     * @param m controls the number of rotational symmetries
     */
    public Supershape(Point cen, float xRadius, float yRadius, float m) {
        this(cen, xRadius, yRadius, m, 1, 1, 1);
    }
    
    /**
     * Easy constructor.
     * 
     * @param x The x-coordinate of the path.
     * @param y The y-coordinate of the path.
     * @param r The radius of the path.
     */
    public Supershape(float x, float y, float r) {
        this(x, y, r, r, 3);
    }
    
    /**
     * Easy constructor.
     * 
     * @param cen The center of the path.
     * @param r The radius of the path.
     */
    public Supershape(Point cen, float r) {
        this(cen, r, r, 3);
    }

    /********************
     ***** Behavior *****
     ********************/

    @Override
    public void trace(Point target, float u) {
        u = Path.remainder(u, 1.0f);
        
        float theta = (u * PApplet.TWO_PI) % PApplet.TWO_PI;
        if (reversed) {
            theta *= -1;
        }

        float r = radius(theta);
        target.x = cen.x + xRadius * r * PApplet.cos(theta);
        target.y = cen.y + yRadius * r * PApplet.sin(theta);
    }

    private float radius(float theta) {
        theta *= mOver4;

        float part1 = PApplet.pow(PApplet.abs(PApplet.cos(theta)), n2);
        float part2 = PApplet.pow(PApplet.abs(PApplet.sin(theta)), n3);
        float part3 = PApplet.pow(part1 + part2, n1Inverted);
        return (part3 != 0) ? 1f / part3 : 0;
    }

    private static int sign(float x) {
        if (x > 0)
            return 1;
        if (x < 0)
            return -1;
        return 0;
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
    
    /**
     * 
     * @param m a variable that controls the number of rotational symmetries
     */
    public void setM(float m) {
        this.m = m;
        this.mOver4 = m / 4f;
    }
    
    /**
     * 
     * @param n2
     */
    public void setN2(float n2) {
        this.n2 = n2;
    }
    
    /**
     * 
     * @param n3
     */
    public void setN3(float n3) {
        this.n3 = n3;
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
     * @return a value that relates to the number of rotational symmetries
     */
    public float getM() {
        return m;
    }

    /**
     * 
     * @return the amount of pinching (smaller amounts of n1 give greater
     *         amounts of pinching)
     */
    public float getN1() {
        return n1;
    }

    /**
     * 
     * @param n1 the amount of pinching (smaller amounts of n1 give greater
     *            amounts of pinching)
     */
    public void setN1(float n1) {
        this.n1 = n1;
        this.n1Inverted = 1f / n1;
    }

    /**
     * 
     * @return the amount of pinching (smaller amounts of n1 give greater
     *         amounts of pinching)
     */
    public float getN2() {
        return n2;
    }

    /**
     * 
     * @return
     */
    public float getN3() {
        return n3;
    }

    @Override
    public Supershape clone() {
        return new Supershape(this);
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
        return "Supershape [cen=" + cen + ", xRadius=" + xRadius + ", yRadius=" + yRadius + ", m=" + m + ", n1=" + n1
                + ", n2=" + n2 + ", n3=" + n3 + ", mOver4=" + mOver4 + "]";
    }
}