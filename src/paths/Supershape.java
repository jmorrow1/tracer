package paths;

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
     * Copy constructor.
     * 
     * @param s the supershape to copy
     */
    public Supershape(Supershape s) {
        this(s.cen.clone(), s.xRadius, s.yRadius, s.m, s.n1, s.n2, s.n3, s.sampleCount);
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
     * @param sampleCount the number of sample points
     */
    public Supershape(float cenx, float ceny, float xRadius, float yRadius, float m, float n1, float n2, float n3, int sampleCount) {
        this(new Point(cenx, ceny), xRadius, yRadius, m, n1, n2, n3, sampleCount);
    }
    
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
     * @param sampleCount the number of sample points
     */
    public Supershape(Point cen, float xRadius, float yRadius, float m, float n1, float n2, float n3, int sampleCount) {
        super(sampleCount);
        this.cen  = cen;
        this.xRadius = xRadius;
        this.yRadius = yRadius;
        this.m = m;
        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;
        this.mOver4 = m / 4f;
        this.n1Inverted = 1f / n1;
    }

    /**
     * 
     * @param cenx the center x-coordinate
     * @param ceny the center y-coordinate
     * @param xRadius half the width
     * @param yRadius half the height
     * @param m controls the number of rotational symmetries
     * @param sampleCount The number of sample points
     */
    public Supershape(float cenx, float ceny, float xRadius, float yRadius, float m, int sampleCount) {
        this(cenx, ceny, xRadius, yRadius, m, 1, 1, 1, sampleCount);
    }

    /**
     * 
     * @param cen the center of the path
     * @param xRadius half the width
     * @param yRadius half the height
     * @param m controls the number of rotational symmetries
     * @param sampleCount The number of sample points
     */
    public Supershape(Point cen, float xRadius, float yRadius, float m, int sampleCount) {
        this(cen, xRadius, yRadius, m, 1, 1, 1, sampleCount);
    }
    
    /**
     * Easy constructor.
     * 
     * @param x The x-coordinate of the path.
     * @param y The y-coordinate of the path.
     * @param r The radius of the path.
     */
    public Supershape(float x, float y, float r) {
        this(x, y, r, r, 3, 100);
    }
    
    /**
     * Easy constructor.
     * 
     * @param cen The center of the path.
     * @param r The radius of the path.
     */
    public Supershape(Point cen, float r) {
        this(cen, r, r, 3, 100);
    }

    /*************************
     ***** Functionality *****
     *************************/

    @Override
    public void trace(Point pt, float amt) {
        float theta = (amt * PApplet.TWO_PI) % PApplet.TWO_PI;
        if (reversed)
            theta *= -1;
        float r = radius(theta);
        pt.x = cen.x + xRadius * r * PApplet.cos(theta);
        pt.y = cen.y + yRadius * r * PApplet.sin(theta);
    }

    private float radius(float theta) {
        theta *= mOver4;

        float part1 = PApplet.pow(PApplet.abs(PApplet.cos(theta)), n2);
        float part2 = PApplet.pow(PApplet.abs(PApplet.sin(theta)), n3);
        float part3 = PApplet.pow(part1 + part2, n1Inverted);
        return (part3 != 0) ? 1f / part3 : 0;
    }

    @Override
    public void draw(PGraphics g) {
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
     * @return a value that relates to the number of rotational symmetries
     */
    public float getM() {
        return m;
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
     * @param n2
     */
    public void setN2(float n2) {
        this.n2 = n2;
    }

    /**
     * 
     * @return
     */
    public float getN3() {
        return n3;
    }

    /**
     * 
     * @param n3
     */
    public void setN3(float n3) {
        this.n3 = n3;
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
        return -1;
    }
}