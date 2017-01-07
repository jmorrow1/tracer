package paths;

import processing.core.PApplet;
import processing.core.PGraphics;
import tracer.Point;

/**
 * A path drawn along the perimeter of an ellipse.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class Arc extends Path {
    private float cenx, ceny, xRadius, yRadius, startAngle, endAngle;

    /**************************
     ***** Initialization *****
     **************************/

    /**
     * Copy constructor.
     * 
     * @param arc the arc to copy
     */
    public Arc(Arc arc) {
        this(arc.cenx, arc.ceny, arc.xRadius, arc.yRadius, arc.startAngle, arc.endAngle, PApplet.RADIUS);
        setSampleCount(arc.sampleCount);
    }

    /**
     * 
     * Constructs an Arc analogously to Processing's native ellipse() function.
     * See the <a href="https://processing.org/reference/arc_.html">Processing
     * documentation</a> for more information.
     * 
     * @param a the 1st ellipse argument, whose meaning is determined by the
     *            given ellipseMode
     * @param b the 2nd ellipse argument, whose meaning is determined by the
     *            given ellipseMode
     * @param c the 3rd ellipse argument, whose meaning is determined by the
     *            given ellipseMode
     * @param d the 4th ellipse argument, whose meaning is determined by the
     *            given ellipseMode
     * @param startAngle the angle at which to start the arc
     * @param endAngle the angle at which to start the arc
     * @param ellipseMode determines the meaning of a,b,c, and d and can be of
     *            value RADIUS, CENTER, CORNER, or CORNERS
     */
    public Arc(float a, float b, float c, float d, float startAngle, float endAngle, int ellipseMode) {
        switch (ellipseMode) {
        case PApplet.RADIUS:
            this.cenx = a;
            this.ceny = b;
            this.xRadius = c;
            this.yRadius = d;
            break;
        case PApplet.CENTER:
            this.cenx = a;
            this.ceny = b;
            this.xRadius = c / 2f;
            this.yRadius = d / 2f;
            break;
        case PApplet.CORNER:
            this.xRadius = c / 2f;
            this.yRadius = d / 2f;
            this.cenx = a + xRadius;
            this.ceny = b + yRadius;
            break;
        case PApplet.CORNERS:
            this.xRadius = (c - a) / 2f;
            this.yRadius = (d - b) / 2f;
            this.cenx = a + xRadius;
            this.ceny = b + yRadius;
            break;
        }
        this.startAngle = startAngle;
        this.endAngle = endAngle;
    }

    /**
     * Easy constructor.
     * 
     * @param x The x-coordinate of the path.
     * @param y The y-coordinate of the path.
     * @param r The radius of the path.
     */
    public Arc(float x, float y, float r) {
        this(x, y, r, r, 0, PApplet.PI, PApplet.RADIUS);
    }

    /*************************
     ***** Functionality *****
     *************************/

    @Override
    public void draw(PGraphics g) {
        style.apply(g);
        g.ellipseMode(RADIUS);
        g.arc(cenx, ceny, xRadius, yRadius, startAngle, endAngle);
    }

    @Override
    public void trace(Point pt, float amt) {
        if (reversed)
            amt = PApplet.map(amt, 0, 1, 1, 0);
        float angle = PApplet.map(amt % 1, 0, 1, startAngle, endAngle);
        pt.x = cenx + xRadius * PApplet.cos(angle);
        pt.y = ceny + yRadius * PApplet.sin(angle);
    }

    @Override
    public void translate(float dx, float dy) {
        cenx += dx;
        ceny += dy;
    }

    @Override
    public String toString() {
        return "Arc [cenx=" + cenx + ", ceny=" + ceny + ", xRadius=" + xRadius + ", yRadius=" + yRadius
                + ", startAngle=" + startAngle + ", endAngle=" + endAngle + "]";
    }

    /*******************************
     ***** Getters and Setters *****
     *******************************/

    // @Override
    // public float getPerimeter() {
    // if (xRadius == yRadius) {
    // return ((endAngle - startAngle) / TWO_PI) * (PI * xRadius*xRadius);
    // }
    // else {
    // return super.getPerimeter();
    // }
    // }

    /**
     * 
     * @return the center x-coordinate
     */
    public float getCenx() {
        return cenx;
    }

    /**
     * @param cenx the center x-coordinate
     */
    public void setCenx(float cenx) {
        this.cenx = cenx;
    }

    /**
     * 
     * @return the center y-coordinate
     */
    public float getCeny() {
        return ceny;
    }

    /**
     * 
     * @param ceny the center y-coordinate
     */
    public void setCeny(float ceny) {
        this.ceny = ceny;
    }

    /**
     * 
     * @return the width of the ellipse
     */
    public float getWidth() {
        return 2 * xRadius;
    }

    /**
     * 
     * @param width the width of the ellipse
     */
    public void setWidth(float width) {
        this.xRadius = width / 2f;
    }

    /**
     * 
     * @return the height of the ellipse
     */
    public float getHeight() {
        return 2f * yRadius;
    }

    /**
     * 
     * @param height the height of the ellipse
     */
    public void setHeight(float height) {
        this.yRadius = height / 2f;
    }

    /**
     * 
     * @return the angle where the arc starts
     */
    public float getStartAngle() {
        return startAngle;
    }

    /**
     * 
     * @param startAngle the angle where the arc starts
     */
    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
    }

    /**
     * 
     * @return the angle where the arc ends
     */
    public float getEndAngle() {
        return endAngle;
    }

    /**
     * 
     * @param endAngle the angle where the arc ends
     */
    public void setEndAngle(float endAngle) {
        this.endAngle = endAngle;
    }

    @Override
    public int getGapCount() {
        if (PApplet.abs((endAngle - startAngle) - TWO_PI) <= 0.01f) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public float getGap(int i) {
        if (getGapCount() == 1 && i == 0) {
            return (endAngle - startAngle) / TWO_PI;
        } else {
            return -1;
        }
    }

    @Override
    public Arc clone() {
        return new Arc(this);
    }
}