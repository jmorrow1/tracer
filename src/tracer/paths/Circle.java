package tracer.paths;

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
    private float startAngle;
    
    /**************************
     ***** Initialization *****
     **************************/
    
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
     * 
     * @param x the center x-coordinate
     * @param y the center y-coordinate
     * @param radius the radius
     */
    public Circle(float x, float y, float radius) {
        this(new Point(x, y), radius);
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
    
    /********************
     ***** Behavior *****
     ********************/

    @Override
    public void draw(PGraphics g) {
        style.apply(g);
        g.ellipseMode(RADIUS);
        g.ellipse(center.x, center.y, radius, radius);
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

        g.ellipseMode(RADIUS);
        g.arc(center.x, center.y, radius, radius, angle1, angle2);
    }

    @Override
    public void trace(Point target, float u) {
        u = Path.remainder(u, 1.0f);

        float radians = u * PApplet.TWO_PI;
        if (reversed) {
            radians *= -1;
        }
        target.x = center.x + radius * PApplet.cos(startAngle + radians);
        target.y = center.y + radius * PApplet.sin(startAngle + radians);
    }
    
    /******************
     ***** Events *****
     ******************/

    @Override
    public void translate(float dx, float dy) {
        center.translate(dx, dy);
    }

    /**
     * 
     * @param startAngle
     */
    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
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
     * Sets the center point of the Circle.
     * @param center The center poitn
     */
    public void setCenter(Point center) {
        this.center = center;
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
     * Set the radius.
     * 
     * @param radius the radius
     */
    public void setRadius(float radius) {
        this.radius = radius;
    }
    
    /*******************
     ***** Getters *****
     *******************/

    @Override
    public Circle clone() {
        return new Circle(this);
    }

    @Override
    public float getLength() {
        return PApplet.TWO_PI * radius;
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
     * Gives the diameter of the Circle.
     * @return the diameter
     */
    public float getDiameter() {
        return radius * 2;
    }

    /**
     * Gives the radius of the Circle.
     * @return the radius
     */
    public float getRadius() {
        return radius;
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
    public boolean isGap(float u) {
        return false;
    }
    
    public float getStartAngle() {
        return startAngle;
    }

    @Override
    public String toString() {
        return "Circle [center=" + center + ", radius=" + radius + "]";
    }
}
