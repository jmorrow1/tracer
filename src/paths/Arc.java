package paths;

import processing.core.PApplet;
import processing.core.PGraphics;
import tracer.Point;

/**
 * A path drawn along the perimeter of an arc.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class Arc extends Path {
    //definition
    protected Point ab, cd;
    protected float startAngle, endAngle;
    protected int ellipseMode;
    
    //helper fields
    protected float perimeter;
    protected boolean perimeterOutOfSync;

    /**************************
     ***** Initialization *****
     **************************/

    /**
     * Copy constructor.
     * 
     * @param arc the arc to copy
     */
    public Arc(Arc arc) {
        this(arc.getCenx(), arc.getCeny(), arc.getXRadius(), arc.getYRadius(), arc.startAngle, arc.endAngle, PApplet.RADIUS);
        setSampleCount(arc.sampleCount);
    }

    /**
     * 
     * Constructs an Arc analogously to Processing's native arc() function.
     * See the <a href="https://processing.org/reference/arc_.html">Processing
     * documentation</a> for more information.
     * 
     * @param a the 1st arc argument, whose meaning is determined by the
     *            given ellipseMode
     * @param b the 2nd arc argument, whose meaning is determined by the
     *            given ellipseMode
     * @param c the 3rd arc argument, whose meaning is determined by the
     *            given ellipseMode
     * @param d the 4th arc argument, whose meaning is determined by the
     *            given ellipseMode
     * @param startAngle the angle at which to start the arc
     * @param endAngle the angle at which to start the arc
     * @param ellipseMode determines the meaning of a,b,c, and d and can be of
     *            value RADIUS, CENTER, CORNER, or CORNERS
     */
    public Arc(float a, float b, float c, float d, float startAngle, float endAngle, int ellipseMode) {
        setEllipse(a, b, c, d, ellipseMode);
        this.startAngle = startAngle;
        this.endAngle = endAngle;
    }

    /**
     * Easy constructor.
     * 
     * @param x The x-coordinate of the path
     * @param y The y-coordinate of the path
     * @param r The maximum radius of the path
     */
    public Arc(float x, float y, float r) {
        this(x, y, r, r, 0, PApplet.PI, PApplet.RADIUS);
    }
    
    /**
     * 
     * Sets the position and size of the ellipse underlying the arc.
     * 
     * @param a the 1st ellipse argument, whose meaning is determined by the given ellipseMode
     * @param b the 2nd ellipse argument, whose meaning is determined by the given ellipseMode
     * @param c the 3rd ellipse argument, whose meaning is determined by the given ellipseMode
     * @param d the 4th ellipse argument, whose meaning is determined by the given ellipseMode
     * @param ellipseMode Determines the meaning of a, b, c, and d. The ellipseMode can be CENTER, RADIUS, CORNER, or CORNERS
     */
    public void setEllipse(float a, float b, float c, float d, int ellipseMode) {
        if (ab == null || cd == null) {
            setEllipse(new Point(a, b), new Point(c, d), ellipseMode);
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
     * Sets the position and size of the ellipse underlying the arc.
     * 
     * @param ab the 1st and 2nd ellipse arguments, whose meaning is determined by the given ellipseMode
     * @param c the 3rd ellipse argument, whose meaning is determined by the given ellipseMode
     * @param d the 4th ellipse argument, whose meaning is determined by the given ellipseMode
     * @param ellipseMode Determines the meaning of a, b, c, and d. The ellipseMode can be CENTER, RADIUS, CORNER, or CORNERS.
     */
    public void setEllipse(Point ab, float c, float d, int ellipseMode) {
        if (ab == null || cd == null) {
            setEllipse(ab, new Point(c, d), ellipseMode);
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
     * Sets the position and size of the ellipse underlying the arc.
     * 
     * @param ab the 1st and 2nd arc arguments, whose meaning is determined by the given ellipseMode
     * @param cd the 3rd and 4th arc argument, whose meaning is determined by the given ellipseMode
     * @param ellipseMode Determines the meaning of a, b, c, and d. The ellipseMode can be CENTER, RADIUS, CORNER, or CORNERS.
     */
    public void setEllipse(Point ab, Point cd, int ellipseMode) {
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
                setEllipse(ab, cd, CORNER);
                break;
        }
        this.ellipseMode = ellipseMode;
        setHelperFields();
    }
    
    /*************************
     ***** Functionality *****
     *************************/

    @Override
    public void draw(PGraphics g) {
        style.apply(g);
        g.ellipseMode(ellipseMode);
        g.arc(ab.x, ab.y, cd.x, cd.y, startAngle, endAngle);
    }

    @Override
    public void trace(Point pt, float u) {
        if (u < 0 || u >= 1) {
            throw new IllegalArgumentException("trace(pt, " + u + ") called where the second argument is outside the range 0 (inclusive) to 1 (exclusive).");
        }
        
        if (reversed) {
            u = 1.0f - u;
            if (u == 1.0f) {
                u = 0.0f;
            }
        }
            
        float angle = startAngle + u * (endAngle - startAngle);
        pt.x = getCenx() + getXRadius() * PApplet.cos(angle);
        pt.y = getCeny() + getYRadius() * PApplet.sin(angle);
    }

    @Override
    public void translate(float dx, float dy) {
        ab.translate(dx, dy);
        if (ellipseMode == CORNERS) {
            cd.translate(dx, dy);
        }
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
     * Gives the center x-coordinate of the ellipse underlying the arc.
     * 
     * @return The center x-coordinate of the ellipse underlying the arc
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
     * Gives the center y-coordinate of the ellipse underlying the arc.
     * 
     * @return The center y-coordinate of the ellipse underlying the arc
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
     * Gives the minimum x-value of the ellipse underlying the arc.
     * 
     * @return The minimum x-value of the ellipse underlying the arc
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
     * Gives the minimum y-value of the ellipse underlying the arc.
     * 
     * @return The minimum y-value of the ellipse underlying the arc
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
     * Gives the maximum x-value of the ellipse underlying the arc.
     * 
     * @return The maximum x-value of the ellipse underlying the arc
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
     * Gives the maximum y-value of the ellipse underlying the arc.
     * 
     * @return The maximum y-value of the ellipse underlying arc
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
     * Gives the width of the ellipse underlying the arc.
     * 
     * @return The width of the ellipse underlying the arc
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
     * Gives the height of the ellipse underlying the arc.
     * 
     * @return The height of the ellipse underlying the arc
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
     * Gives half the width of the ellipse underlying the arc.
     * 
     * @return Half the width of the ellipse underlying the arc
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
     * Gives half the height of the ellipse underlying the arc.
     * 
     * @return Half the height of the ellipse underlying the arc
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
     * Gives the start angle.
     * @return the angle where the arc starts
     */
    public float getStartAngle() {
        return startAngle;
    }

    /**
     * Sets the start angle.
     * @param startAngle the angle where the arc starts
     */
    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
    }

    /**
     * Gives the end angle.
     * @return the angle where the arc ends
     */
    public float getEndAngle() {
        return endAngle;
    }

    /**
     * Sets the end angle.
     * @param endAngle the angle where the arc ends
     */
    public void setEndAngle(float endAngle) {
        this.endAngle = endAngle;
    }

    @Override
    public int getGapCount() {
        if (endAngle - startAngle >= TWO_PI - 0.001f) {
            return 0;
        }
        else {
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
    public boolean isGap(float u) {
        return u == 0;
    }

    @Override
    public Arc clone() {
        return new Arc(this);
    }
    
    @Override
    public String toString() {
        return "Arc [cenx=" + getCenx() + ", ceny=" + getCeny() + ", xRadius=" + getXRadius() + ", yRadius=" + getYRadius()
                + ", startAngle=" + startAngle + ", endAngle=" + endAngle + "]";
    }
}