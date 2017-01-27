package paths;

import processing.core.PApplet;
import processing.core.PGraphics;
import tracer.Point;

/**
 * 
 * A circle.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class Circle extends Path {
    private Point center;
    private float radius;
    private float angleOffset;
    
    /**
     * 
     * @param x the center x-coordinate
     * @param y the center y-coordinate
     * @param radius the radius
     */
    public Circle(float x, float y, float radius) {
        this(new Point(x, y), radius);
    }

    /**
     * 
     * @param center the center
     * @param radius the radius
     */
    public Circle(Point center, float radius) {
        this.center = center;
        this.radius = radius;
    }

    /**
     * Copy constructor.
     * 
     * @param c the circle to copy
     */
    public Circle(Circle c) {
        this(c.center.clone(), c.radius);
        setSampleCount(c.sampleCount);
    }

    @Override
    public void draw(PGraphics g) {
        style.apply(g);
        g.ellipseMode(RADIUS);
        g.ellipse(center.x, center.y, radius, radius);
    }

    @Override
    public void draw(PGraphics g, float u1, float u2) {
        boolean inRange = (0 <= u1 && u1 <= 1 && 0 <= u2 && u2 <= 1);
        if (!inRange) {
            throw new IllegalArgumentException("draw(g, " + u1 + ", " + u2 + ") called with values outside the range 0 to 1.");
        }

        if (u1 > u2) {
            u2++;
        }

        g.ellipseMode(RADIUS);
        g.arc(center.x, center.y, radius, radius, u1 * PApplet.TWO_PI, u2 * PApplet.TWO_PI);
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
        pt.x = center.x + radius * PApplet.cos(angleOffset + radians);
        pt.y = center.y + radius * PApplet.sin(angleOffset + radians);
    }

    /**
     * Tells whether or not the circle contains the given point.
     * @param x The x-coordinate of the point
     * @param y The y-coordintae of the point
     * @return True if the circle contains the point and false otherwise
     */
    public boolean contains(float x, float y) {
        return PApplet.dist(this.getCenx(), this.getCeny(), x, y) <= radius;
    }

    @Override
    public void translate(float dx, float dy) {
        center.translate(dx, dy);
    }

    @Override
    public Circle clone() {
        return new Circle(this);
    }

    /*******************************
     ***** Getters and Setters *****
     *******************************/

    @Override
    public float getTotalDistance() {
        return PApplet.TWO_PI * radius;
    }
    
    /**
     * Sets the center point of the Circle.
     * @param center The center poitn
     */
    public void setCenter(Point center) {
        this.center = center;
    }
    
    /**
     * Gives the center point of the Circle.
     * @return The center point.
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Gives the x-coordinate of the center point of the Circle.
     * @return The x-coordinate
     */
    public float getCenx() {
        return center.x;
    }

    /**
     * Gives the y-coordinate of the center point of the Circle.
     * @return The y-coordinate
     */
    public float getCeny() {
        return center.y;
    }

    /**
     * Sets the center point of the Circle.
     * @param x The x-coordinate
     * @param y The y-coordinate
     */
    public void setCenter(float x, float y) {
        this.center.x = x;
        this.center.y = y;
    }

    /**
     * Gives the diameter of the Circle.
     * @return the diameter
     */
    public float getDiameter() {
        return radius * 2;
    }

    /**
     * Set the diameter of the Circle.
     * 
     * @param diam
     */
    public void setDiameter(float diam) {
        this.radius = 2 * diam;
    }

    /**
     * Gives the radius of the Circle.
     * @return the radius
     */
    public float getRadius() {
        return radius;
    }

    /**
     * Set the radius.
     * 
     * @param radius the radius
     */
    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public int getGapCount() {
        return 0;
    }

    @Override
    public float getGap(int i) {
        return -1;
    }
    
    @Override
    public boolean isGap(float u) {
        return false;
    }
}
