package tracer.paths;

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
    private Point ab, cd;
    protected int ellipseMode;
    
    /**************************
     ***** Initialization *****
     **************************/

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
        setSamplesPerUnitLength(Path.defaultSamplesPerUnitLength);
    }
    
    /**
     * 
     * @param ab
     * @param c
     * @param d
     * @param ellipseMode
     */
    public Ellipse(Point ab, float c, float d, int ellipseMode) {
        set(ab, c, d, ellipseMode);
        setSamplesPerUnitLength(Path.defaultSamplesPerUnitLength);
    }
    
    /**
     * 
     * @param ab
     * @param cd
     * @param ellipseMode
     */
    public Ellipse(Point ab, Point cd, int ellipseMode) {
        set(ab, cd, ellipseMode);
        setSamplesPerUnitLength(Path.defaultSamplesPerUnitLength);
    }
    
    /**
     * Easy constructor.
     * 
     * @param x The x-coordinate of the path.
     * @param y The y-coordinate of the path.
     * @param r The radius of the path.
     */
    public Ellipse(float x, float y, float r) {
        set(x, y, r, 0.5f*r, PApplet.RADIUS);
        setSamplesPerUnitLength(Path.defaultSamplesPerUnitLength);
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
            case CORNER:
            case CENTER:
            case RADIUS:
                this.ab = ab;
                this.cd = cd;
                break;
            default:
                System.err.println("Invalid ellipseMode. Use CORNERS, CORNER, CENTER, or RADIUS.");
                set(ab, cd, CORNER);
                return;
        }
        this.ellipseMode = ellipseMode;
    }
    
    /********************
     ***** Behavior *****
     ********************/

    @Override
    public void draw(PGraphics g) {
        style.apply(g);
        g.ellipseMode(ellipseMode);
        g.ellipse(ab.x, ab.y, cd.x, cd.y);
    }

    @Override
    public void draw(PGraphics g, float u1, float u2) {
        u1 = Path.remainder(u1, 1.0f);
        u2 = Path.remainder(u2, 1.0f);
        
        int direction = reversed ? -1 : 1;
        float angle1 = u1 * PApplet.TWO_PI * direction;
        float angle2 = u2 * PApplet.TWO_PI * direction;

        if (angle1 > angle2) {
            angle2 += PApplet.TWO_PI;
        }
            
        g.ellipseMode(ellipseMode);
        g.arc(ab.x, ab.y, cd.x, cd.y, angle1, angle2);
    }

    @Override
    public void trace(Point target, float u) {
        u = Path.remainder(u, 1.0f);
        
        float radians = u * PApplet.TWO_PI;
        if (reversed) {
            radians *= -1;
        }
        target.x = getCenx() + getXRadius() * PApplet.cos(radians);
        target.y = getCeny() + getYRadius() * PApplet.sin(radians);
    }
    
    /*******************
     ***** Events ******
     *******************/

    @Override
    public void translate(float dx, float dy) {
        ab.translate(dx, dy);
        if (ellipseMode == CORNERS) {
            cd.translate(dx, dy);
        }
    }

    public void scale(float s) {
        set(getCenx(), getCeny(), s * getXRadius(), s * getYRadius(), RADIUS);
    }
    
    @Override
    public void setCenter(float x, float y) {
         translate(x - getCenx(), y - getCeny());
    }
    
    /**
     * 
     * @param ab
     */
    public void setAB(Point ab) {
        this.ab = ab;
    }
    
    /**
     * 
     * @param cd
     */
    public void setCD(Point cd) {
        this.cd = cd;
    }
    
    /**
     * 
     * @param ellipseMode
     */
    public void setEllipseMode(int ellipseMode) {
        this.ellipseMode = ellipseMode;
    }

    /*******************
     ***** Getters *****
     *******************/
    
    @Override
    public Ellipse clone() {
        return new Ellipse(this);
    }

    @Override
    public float getLength() {
        float xRadius = getXRadius();
        float yRadius = getYRadius();
        float a = PApplet.max(xRadius, yRadius);
        float b = PApplet.min(xRadius, yRadius);
        return PApplet.PI * (3 * (a + b) - PApplet.sqrt((3 * a + b) * (a + 3 * b)));
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
    
    /**
     * 
     * @return
     */
    public Point getAB() {
        return ab;
    }
    
    /**
     * 
     * @return
     */
    public Point getCD() {
        return cd;
    }

    @Override
    public int getGapCount() {
        return 0;
    }

    @Override
    public float getGap(int i) {
        throw new IndexOutOfBoundsException(this.getClass().toString() + ".getGap(" + i + ")");
    }
    
    /**
     * 
     * @return
     */
    public int getEllipseMode() {
        return ellipseMode;
    }

    @Override
    public String toString() {
        return "Ellipse [ab=" + ab + ", cd=" + cd + ", ellipseMode=" + ellipseModeToString(ellipseMode) + "]";
    }
    
    public static String ellipseModeToString(int ellipseMode) {
        switch (ellipseMode) {
            case CORNER : return "CORNER";
            case CENTER: return "CENTER";
            case CORNERS : return "CORNERS";
            case RADIUS : return "RADIUS";
            default : return "UNKNOWN";
        }
    }
}
