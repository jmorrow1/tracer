package paths;

import processing.core.PApplet;
import processing.core.PGraphics;
import tracer.Point;

/**
 * 
 * An ellipse, analgous to Processing's ellipse() function.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class Ellipse extends Path { 
    //definition
    protected Point ab, cd;
    protected int ellipseMode;
    
    //helper fields
    protected float perimeter;
    protected boolean perimeterOutOfSync; //flag

    /**
     * Constructs an Ellipse analogously to Processing's native ellipse()
     * function. See the
     * <a href="https://processing.org/reference/ellipse_.html">Processing
     * documentation</a> for more information.
     * 
     * @param a the 1st ellipse argument, whose meaning is determined by the given ellipseMode
     * @param b the 2nd ellipse argument, whose meaning is determined by the given ellipseMode
     * @param c the 3rd ellipse argument, whose meaning is determined by the given ellipseMode
     * @param d the 4th ellipse argument, whose meaning is determined by the given ellipseMode
     * @param ellipseMode Determines the meaning of a, b, c, and d. The ellipseMode can be CENTER, RADIUS, CORNER, or CORNERS
     */
    public Ellipse(float a, float b, float c, float d, int ellipseMode) {
        set(a, b, c, d, ellipseMode);
    }
    
    public Ellipse(Point ab, float c, float d, int ellipseMode) {
        
    }
    
    public Ellipse(Point ab, Point cd, int ellipseMode) {
        
    }

    /**
     * Copy constructor.
     * 
     * @param ellipse The ellipse to copy
     */
    public Ellipse(Ellipse ellipse) {
        this(ellipse.getCenx(), ellipse.getCeny(), ellipse.getWidth(), ellipse.getHeight(), PApplet.CENTER);
        setSampleCount(ellipse.sampleCount);
    }

    /**
     * Easy constructor.
     * 
     * @param x The x-coordinate of the path.
     * @param y The y-coordinate of the path.
     * @param r The radius of the path.
     */
    public Ellipse(float x, float y, float r) {
        set(x, y, r, r, PApplet.RADIUS);
    }
    
    /**
     * 
     * Sets the position and size of the Ellipse.
     * 
     * @param a the 1st ellipse argument, whose meaning is determined by the given ellipseMode
     * @param b the 2nd ellipse argument, whose meaning is determined by the given ellipseMode
     * @param c the 3rd ellipse argument, whose meaning is determined by the given ellipseMode
     * @param d the 4th ellipse argument, whose meaning is determined by the given ellipseMode
     * @param ellipseMode Determines the meaning of a, b, c, and d. The ellipseMode can be CENTER, RADIUS, CORNER, or CORNERS
     */
    public void set(float a, float b, float c, float d, int ellipseMode) {
        if (ab == null || cd == null) {
            set(new Point(a, b), new Point(c, d), ellipseMode);
        }
        else {
            ab.x = a;
            ab.y = b;
            cd.x = c;
            cd.y = d;
            this.ellipseMode = ellipseMode;
            setHelperFields();
        }
    }
    
    /**
     * 
     * Sets the position and size of the ellipse.
     * 
     * @param ab the 1st and 2nd ellipse arguments, whose meaning is determined by the given ellipseMode
     * @param c the 3rd ellipse argument, whose meaning is determined by the given ellipseMode
     * @param d the 4th ellipse argument, whose meaning is determined by the given ellipseMode
     * @param ellipseMode Determines the meaning of a, b, c, and d. The ellipseMode can be CENTER, RADIUS, CORNER, or CORNERS.
     */
    public void set(Point ab, float c, float d, int ellipseMode) {
        if (ab == null || cd == null) {
            set(ab, new Point(c, d), ellipseMode);
        }
        else {
            this.ab = ab;
            cd.x = c;
            cd.y = d;
            this.ellipseMode = ellipseMode;
            setHelperFields();
        }
    }
    
    /**
     * 
     * Sets the position and size of the ellipse.
     * 
     * @param ab the 1st and 2nd ellipse arguments, whose meaning is determined by the given ellipseMode
     * @param cd the 3rd and 4th ellipse argument, whose meaning is determined by the given ellipseMode
     * @param ellipseMode Determines the meaning of a, b, c, and d. The ellipseMode can be CENTER, RADIUS, CORNER, or CORNERS.
     */
    public void set(Point ab, Point cd, int ellipseMode) {
        switch (ellipseMode) {
            case CORNERS:
                this.ab = ab;
                this.cd = cd;
                break;
            case CORNER:
            case CENTER:
            case RADIUS:
                this.ab = ab;
                this.cd = new Point(cd);
                break;
            default:
                System.err.println("Invalid ellipseMode. Use CORNERS, CORNER, CENTER, or RADIUS.");
                set(ab, cd, CORNER);
                break;
        }
        this.ellipseMode = ellipseMode;
        setHelperFields();
    }

    @Override
    public void draw(PGraphics g) {
        style.apply(g);
        g.ellipseMode(ellipseMode);
        g.ellipse(ab.x, ab.y, cd.x, cd.y);
    }

    @Override
    public void draw(PGraphics g, float u1, float u2) {
        boolean inRange = (0 <= u1 && u1 <= 1 && 0 <= u2 && u2 <= 1);
        if (!inRange) {
            throw new IllegalArgumentException(
                    "draw(g, " + u1 + ", " + u2 + ") called with values outside the range 0 to 1.");
        }

        if (u1 > u2) {
            u2++;
        }
            
        g.ellipseMode(ellipseMode);
        g.arc(ab.x, ab.y, cd.x, cd.y, u1 * PApplet.TWO_PI, u2 * PApplet.TWO_PI);
    }

    @Override
    public void trace(Point pt, float u) {
        if (u < 0 || u >= 1) {
            throw new IllegalArgumentException("trace(pt, " + u + ") called where the second argument is outside the range 0 (inclusive) to 1 (exclusive).");
        }
        
        float radians = u * PApplet.TWO_PI;
        if (reversed) {
            radians *= -1;
        }
        pt.x = getCenx() + getXRadius() * PApplet.cos(radians);
        pt.y = getCeny() + getYRadius() * PApplet.sin(radians);
    }

    public boolean inside(float x, float y) {
        float dx = x - this.getCenx();
        float dy = y - this.getCeny();
        float xRadius = getXRadius();
        float yRadius = getYRadius();
        return (dx * dx) / (xRadius * xRadius) + (dy * dy) / (yRadius * yRadius) <= 1;
    }

    @Override
    public void translate(float dx, float dy) {
        ab.translate(dx, dy);
        if (ellipseMode == CORNERS) {
            cd.translate(dx, dy);
        }
    }

    public void scale(float s) {
        set(getCenx(), getCeny(), s * getXRadius(), s * getYRadius(), RADIUS);
        perimeterOutOfSync = true;
    }

    @Override
    public Ellipse clone() {
        return new Ellipse(this);
    }

    /*******************************
     ***** Getters and Setters *****
     *******************************/

    @Override
    public float getTotalDistance() {
        if (perimeterOutOfSync) {
            setHelperFields();
        }
        return perimeter;
    }
    
    private void setHelperFields() {
        float a = PApplet.max(getXRadius(), getYRadius());
        float b = PApplet.min(getXRadius(), getYRadius());
        perimeter = PApplet.PI * (3 * (a + b) - PApplet.sqrt((3 * a + b) * (a + 3 * b)));
        perimeterOutOfSync = false;
    }

    /**
     * Gives the radius of the ellipse (the length of a line drawn from the
     * ellipse's center to its circumference with the given angle)
     * 
     * @param radians the angle in terms of radians
     * @return the radius of the ellipse given the angle
     */
    public float getRadiusAt(float radians) {
        return PApplet.dist(getCenx(), getCeny(),
                getCenx() + getXRadius() * PApplet.cos(radians),
                getCeny() + getYRadius() * PApplet.sin(radians));
    }
    
    /**
     * Gives the center x-coordinate of the ellipse.
     * 
     * @return The center x-coordinate of the ellipse
     */
    public float getCenx() {
        switch (ellipseMode) {
            case CENTER:
            case RADIUS:
                return ab.x;
            case CORNER:
                return ab.x + 0.5f * cd.x;
            case CORNERS:
                return ab.x + 0.5f * (cd.x - ab.x);
            default:
                return -1;
        }
    }

    /**
     * Gives the center y-coordinate of the ellipse
     * 
     * @return The center y-coordinate of the ellipse
     */
    public float getCeny() {
        switch (ellipseMode) {
            case CENTER:
            case RADIUS:
                return ab.y;
            case CORNER:
                return ab.y + 0.5f * cd.y;
            case CORNERS:
                return ab.y + 0.5f * (cd.y - ab.y);
            default:
                return -1;
        }
    }

    /**
     * Gives the minimum x-value of the ellipse.
     * 
     * @return The minimum x-value of the ellipse
     */
    public float getX1() {
        switch (ellipseMode) {
            case CENTER:
                return ab.x - 0.5f * cd.x;
            case RADIUS:
                return ab.x - cd.x;
            case CORNER:
            case CORNERS:
                return ab.x;
            default:
                return -1;
        }
    }

    /**
     * Gives the minimum y-value of the ellipse
     * 
     * @return The minimum y-value of the ellipse
     */
    public float getY1() {
        switch (ellipseMode) {
            case CENTER:
                return ab.y - 0.5f * cd.y;
            case RADIUS:
                return ab.y - cd.y;
            case CORNER:
            case CORNERS:
                return ab.y;
            default:
                return -1;
        }
    }

    /**
     * Gives the maximum x-value of the ellipse.
     * 
     * @return The maximum x-value of the ellipse
     */
    public float getX2() {
        switch (ellipseMode) {
            case CENTER:
                return ab.x + 0.5f * cd.x;
            case RADIUS:
                return ab.x + cd.x;
            case CORNER:
                return ab.x + cd.x;
            case CORNERS:
                return cd.x;
            default:
                return -1;
        }
    }

    /**
     * Gives the maximum y-value of the ellipse.
     * 
     * @return The maximum y-value of the ellipse
     */
    public float getY2() {
        switch (ellipseMode) {
            case CENTER:
                return ab.y + 0.5f * cd.y;
            case RADIUS:
                return ab.y + cd.y;
            case CORNER:
                return ab.y + cd.y;
            case CORNERS:
                return cd.y;
            default:
                return -1;
        }
    }

    /**
     * Gives the width of the ellipse
     * 
     * @return The width of the ellipse
     */
    public float getWidth() {
        switch (ellipseMode) {
            case CENTER:
            case CORNER:
                return cd.x;
            case RADIUS:
                return 2f * cd.x;
            case CORNERS:
                return cd.x - ab.x;
            default:
                return -1;
        }
    }

    /**
     * Gives the height of the ellipse.
     * 
     * @return The height of the ellipse
     */
    public float getHeight() {
        switch (ellipseMode) {
            case CENTER:
            case CORNER:
                return cd.y;
            case RADIUS:
                return 2f * cd.y;
            case CORNERS:
                return cd.y - ab.y;
            default:
                return -1;
        }
    }
    
    /**
     * Gives half the width of the ellipse.
     * 
     * @return Half the width of the ellipse
     */
    public float getXRadius() {
        switch (ellipseMode) {
            case CENTER:
            case CORNER:
                return 0.5f * cd.x;
            case RADIUS:
                return cd.x;
            case CORNERS:
                return 0.5f * (cd.x - ab.x);
            default:
                return -1;
        }
    }

    /**
     * Gives half the height of the ellipse.
     * 
     * @return Half the height of the ellipse
     */
    public float getYRadius() {
        switch (ellipseMode) {
            case CENTER:
            case CORNER:
                return 0.5f * cd.y;
            case RADIUS:
                return cd.y;
            case CORNERS:
                return 0.5f * (cd.y - ab.y);
            default:
                return -1;
        }
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
