package tracer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.data.JSONArray;
import processing.data.JSONObject;

/**
 * A point in 2D space.
 * 
 * @author James Morrow
 *
 */
public class Point {
    public float x, y;
    
    /**************************
     ***** Initialization *****
     **************************/

    /**
     * Copy constructor.
     * 
     * @param pt the point to copy
     */
    public Point(Point pt) {
        this.x = pt.x;
        this.y = pt.y;
    }

    /**
     * 
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Sets this point to (x,y)
     * @param x
     * @param y
     */
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Sets this point to the given point
     * @param pt
     */
    public void set(Point pt) {
        this.x = pt.x;
        this.y = pt.y;
    }
    
    /********************
     ***** Behavior *****
     ********************/
    
    /**
     * Updates the Point.
     */
    public void step() {}
    
    /**
     * Updates the Point.
     * @param dt The change in time
     */
    public void step(int dt) {}
    
    /******************
     ***** Events *****
     ******************/
    
    /**
     * Multiply this point by the given multiplier.
     * 
     * @param multiplier the multiplier
     */
    public void mult(float multiplier) {
        x *= multiplier;
        y *= multiplier;
    }

    /**
     * Add the given point to this point.
     * 
     * @param pt the point to add
     */
    public void add(Point pt) {
        x += pt.x;
        y += pt.y;
    }
    
    /**
     * 
     * @param x
     * @param y
     */
    public void add(float x, float y) {
        this.x += x;
        this.y += y;
    }

    /**
     * Divide this point by a given divisor.
     * 
     * @param divisor
     */
    public void div(float divisor) {
        x /= divisor;
        y /= divisor;
    }

    /**
     * Shifts this Point dx units in the x-direction and dy units in the
     * y-direction.
     * 
     * @param dx
     * @param dy
     */
    public void translate(float dx, float dy) {
        x += dx;
        y += dy;
    }
    
    /**
     * Shifts this Point by (translation.x, translation.y)
     * @param translation The amount to shift the Point
     */
    public void translate(Point translation) {
        x += translation.x;
        y += translation.y;
    }

    /**
     * Rotates this point around (vx,vy).
     * 
     * @param angle
     * @param vx
     * @param vy
     */
    public void rotate(float angle, float vx, float vy) {
        float c = (float) Math.cos(angle);
        float s = (float) Math.sin(angle);

        x -= vx;
        y -= vy;

        float tempx = x;
        float tempy = y;

        x = tempx * c - tempy * s;
        y = tempx * s + tempy * c;

        x += vx;
        y += vy;
    }
    
    /*******************
     ***** Getters *****
     *******************/
    
    /**
     * 
     * @param pt
     * @return true if the points are equivalent, false otherwise
     */
    public boolean equals(Point pt) {
        return pt.x == this.x && pt.y == this.y;
    }
    
    @Override
    public Point clone() {
        return new Point(this);
    }

    @Override
    public String toString() {
        return "Point [x=" + x + ", y=" + y + "]";
    }  
    
    /******************
     ***** Static *****
     ******************/

    /**
     * Interpolates a Point a and a Point b by a given amount between 0 and 1.
     * 
     * @param a Point a
     * @param b Point b
     * @param amt A value between 0 and 1 specifying the interpolation amount
     * @return the Point in between a and b
     */
    public static Point lerp(Point a, Point b, float amt) {
        return new Point(PApplet.lerp(a.x, a.y, amt), PApplet.lerp(b.x, b.y, amt));
    }

    /**
     * Averages two points.
     * 
     * @param a point a
     * @param b point b
     * @return The Point halfway between a and b.
     */
    public static Point avg(Point a, Point b) {
        return new Point(0.5f * (a.x + b.x), 0.5f * (a.y + b.y));
    }

    /**
     * Averages a sequence or array of points.
     * 
     * @param pts the points
     * @return the average
     */
    public static Point avg(Point... pts) {
        float xAvg = 0;
        float yAvg = 0;
        for (int i = 0; i < pts.length; i++) {
            xAvg += pts[i].x;
            yAvg += pts[i].y;
        }
        xAvg /= pts.length;
        yAvg /= pts.length;
        return new Point(xAvg, yAvg);
    }

    /**
     * Computes the distance between two points.
     * 
     * @param a the first point
     * @param b the second point
     * @return the distance
     */
    public static float dist(Point a, Point b) {
        return PApplet.dist(a.x, a.y, b.x, b.y);
    }
    
    /**
     * Computes the squared distance between two points.
     * 
     * @param a the first point
     * @param b the second point
     * @return the squared distance
     */
    public static float distSq(Point a, Point b) {
        return (b.y - a.y) * (b.y - a.y) + (b.x - a.x) * (b.x - a.x);
    }

    /**
     * The slope between two points.
     * 
     * @param a the first point
     * @param b the second point
     * @return the slope
     */
    public static float slope(Point a, Point b) {
        return (b.y - a.y) / (b.x - a.x);
    }

    /**
     * Adds two points.
     * 
     * @param a the first point
     * @param b the second point
     * @return the sum
     */
    public static Point add(Point a, Point b) {
        return new Point(a.x + b.x, a.y + b.y);
    }

    /**
     * Adds a sequence or array of points.
     * 
     * @param pts a sequence or array of points
     * @return the sum of the points
     */
    public static Point sum(Point... pts) {
        Point sum = new Point(0, 0);
        for (int i = 0; i < pts.length; i++) {
            sum.x += pts[i].x;
            sum.y += pts[i].y;
        }
        return sum;
    }

    /**
     * Subtracts two points.
     * 
     * @param a the first point
     * @param b the second point
     * @return a - b
     */
    public static Point sub(Point a, Point b) {
        return new Point(a.x - b.x, a.y - b.y);
    }

    /**
     * Divide a point by a divisor and return the result.
     * 
     * @param pt the point to divide
     * @param divisor the divisor
     * @return a new point which is the point divided by the divisor
     */
    public static Point div(Point pt, float divisor) {
        return new Point(pt.x / divisor, pt.y / divisor);
    }

    /**
     * Multiply a point by a multiplier and return the result.
     * 
     * @param pt the point to multiply
     * @param multiplier the multiplier
     * @return a new point which is the product of pt and multiplier
     */
    public static Point mult(Point pt, float multiplier) {
        return new Point(pt.x * multiplier, pt.y * multiplier);
    }

    /**
     * Takes two float arrays, xs and ys, to make a Point array, pts,
     * such that pts[i] == new Point(xs[i], ys[i]), as i varies from 0 to min(xs.length, ys.length)-1
     * 
     * @param xs The array of x-values
     * @param ys The array of y-values
     * @return The array of Points
     */
    public static Point[] zip(float[] xs, float[] ys) {
        int n = PApplet.min(xs.length, ys.length);
        Point[] pts = new Point[n];
        for (int i=0; i<n; i++) {
            pts[i] = new Point(xs[i], ys[i]);
        }
        return pts;
    }
    
    /**
     * Steps every Point in the array
     * @param points The array of Points
     */
    public static void step(Point[] points) {
        for (Point pt : points) {
            pt.step();
        }
    }
    
    /**
     * Steps every Point in the collection by the given time step.
     * @param points The collection of Points
     * @param dt The time step
     */
    public static void step(Collection<? extends Point> points, int dt) {
        for (Point pt : points) {
            pt.step(dt);
        }
    }
    
    /**
     * Steps every Point in the collection
     * @param points The collection of Points
     */
    public static void step(Collection<? extends Point> points) {
        for (Point pt : points) {
            pt.step();
        }
    }
    
    /**
     * Steps every Point in the array by the given time step.
     * @param points The array of Points
     * @param dt The time step
     */
    public static void step(Point[] points, int dt) {
        for (Point pt : points) {
            pt.step(dt);
        }
    }
  
    /**
     * 
     * Compares Points by comparing their x-values.
     * 
     * @author James Morrow [jamesmorrowdesign.com]
     *
     */
    public static class XValueComparator implements Comparator<Point> {
        private static XValueComparator instance;
        
        private XValueComparator() {}
        
        @Override
        public int compare(Point a, Point b) {
            if (a.x < b.x) {
                return -1;
            }
            else if (a.x == b.x) {
                return 0;
            }
            else {
                return 1;
            }
        }
        
        public static XValueComparator getInstance() {
            if (instance == null) {
                instance = new XValueComparator();
            }
            return instance;
        }
        
    }
    
    /**
     * 
     * Compares Points by comparing their y-values
     * 
     * @author James Morrow [jamesmorrowdesign.com]
     *
     */
    public static class YValueComparator implements Comparator<Point> {
        private static YValueComparator instance;
        
        private YValueComparator() {}
        
        @Override
        public int compare(Point a, Point b) {
            if (a.y < b.y) {
                return -1;
            }
            else if (a.y == b.y) {
                return 0;
            }
            else {
                return 1;
            }
        }
        
        public static YValueComparator getInstance() {
            if (instance == null) {
                instance = new YValueComparator();
            }
            return instance;
        }
    }
}
