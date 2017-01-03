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
        this(c.center, c.radius);
    }

    @Override
    public void draw(PGraphics g) {
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
    public void trace(Point pt, float amt) {
        float radians = amt * PApplet.TWO_PI;
        if (reversed) {
            radians *= -1;
        }
        pt.x = center.x + radius * PApplet.cos(angleOffset + radians);
        pt.y = center.y + radius * PApplet.sin(angleOffset + radians);
    }

    public boolean inside(float x, float y) {
        return PApplet.dist(x, y, x, y) <= radius;
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
    public float getPerimeter() {
        return PApplet.TWO_PI * radius;
    }
    
    public void setCenter(Point center) {
        this.center = center;
    }
    
    public Point getCenter() {
        return center;
    }

    public float getCenx() {
        return center.x;
    }

    public float getCeny() {
        return center.y;
    }

    public float getWidth() {
        return this.getDiameter();
    }

    public float getHeight() {
        return this.getDiameter();
    }

    public void setCenter(float x, float y) {
        this.center = new Point(x, y);
    }

    /**
     * 
     * @return the diameter
     */
    public float getDiameter() {
        return radius * 2;
    }

    /**
     * Set the diameter.
     * 
     * @param diam
     */
    public void setDiameter(float diam) {
        this.radius = 2 * diam;
    }

    /**
     * 
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
}
