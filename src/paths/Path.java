package paths;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import tracer.Point;
import tracer.Tracer;

/**
 * 
 * A continuous sequence of points in 2D space.
 * 
 * <br>
 * <br>
 * 
 * Usage:<br>
 * To get a point on an Path p, use p.trace(u) or p.trace(pt, u) where u is a
 * floating point value between 0 and 1 and pt is a Point (a coordinate in 2D
 * space).
 * 
 * <br>
 * <br>
 * 
 * p.trace(0) returns a Point at the beginning of the path. p.trace(0.5) returns
 * a Point in the middle of the path. p.trace(1) returns the point at the end of
 * the path. And so on.
 * 
 * <br>
 * <br>
 * 
 * Alternatively, you can use p.trace(pt, u) which, instead of returning a
 * Point, stores the result of the computation in the given pt. This method is
 * more efficient because it doesn't require a new Point to be allocated.
 * 
 * <br>
 * <br>
 * 
 * To display an Path p, use p.display(g) where g is a PGraphics object.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public abstract class Path implements PConstants {
    private final static Point pt = new Point(0, 0);
    protected boolean reversed;
    protected int granularity;

    public Path() {
        this(100);
    }

    /**
     * 
     * @param granularity the number of sample points
     */
    public Path(int granularity) {
        this.granularity = granularity;
    }
    
    /**
     * Derive a Path by connecting a sequence of points located on a source Path with lines.
     * @param src The source path
     * @param us 1-dimensional coordinates of the sequence of points
     * @return The derivative path
     */
    public static Shape derivePath(Path src, float[] us) {
        if (us.length == 0) {
            return new Shape(new Point[] {});
        }
        else {
            Point[] pts = new Point[us.length];
            for (int i=0; i<us.length; i++) {
                pts[i] = src.trace(us[i]);
            }
            return new Shape(pts);
        }
    }

    /**
     * Draws the path.
     * 
     * @param g A PGraphics object on which to draw the path
     */
    public void draw(PGraphics g) {
        if (granularity != -1) {
            draw(g, granularity);
        }
    }

    /**
     * Creates a segment of the path starting at trace(u1) and ending at
     * trace(u2);
     * 
     * @param u1 The 1D coordinate of the segment's start, a value between 0 and
     *            1
     * @param u2 The 1D coordinate of the segment's end, a value between 0 and 1
     * @return The segment
     */
    public Shape segment(float u1, float u2) {
        boolean inRange = (0 <= u1 && u1 <= 1 && 0 <= u2 && u2 <= 1);
        if (!inRange) {
            throw new IllegalArgumentException(
                    "draw(g, " + u1 + ", " + u2 + ") called with values outside the range 0 to 1.");
        }

        return new Shape(segmentPoints(u1, u2));
    }

    private ArrayList<Point> segmentPoints(float u1, float u2) {
        if (u1 > u2) {
            ArrayList<Point> pts = new ArrayList<Point>();
            pts.addAll(segmentPoints(u1, 1));
            pts.addAll(segmentPoints(0, u2));
            return pts;
        } else {
            float length = PApplet.abs(u1 - u2);
            int n = (int) (granularity * length);
            float du = length / n;

            ArrayList<Point> pts = new ArrayList<Point>();

            float u = u1;
            for (int i = 0; i < n; i++) {
                trace(pt, u);
                pts.add(new Point(pt));
                u = (u + du) % 1f;
            }

            return pts;
        }
    }

    // TODO WORK IN PROGRESS --- Need to incorporate gaps:
    private void continuousDraw(PGraphics g, float u1, float u2) {
        float length = PApplet.abs(u1 - u2);
        int n = (int) (granularity * length);
        float du = length / n;

        g.beginShape();
        float u = u1 - 0.001f;
        for (int i = 0; i < n; i++) {
            trace(pt, u);
            g.vertex(pt.x, pt.y);
            u = (u + du) % 1f;
        }
        g.endShape();
    }

    /**
     * Draws a segment of the path starting at trace(u1) and ending at
     * trace(u2).
     * 
     * @param g A PGraphics object on which to draw the path
     * @param u1 The 1D coordinate of the segment's start, a value between 0 and
     *            1
     * @param u2 The 1D coordinate of the segment's end, a value between 0 and 1
     */
    public void draw(PGraphics g, float u1, float u2) {
        boolean inRange = (0 <= u1 && u1 <= 1 && 0 <= u2 && u2 <= 1);
        if (!inRange) {
            throw new IllegalArgumentException(
                    "draw(g, " + u1 + ", " + u2 + ") called with values outside the range 0 to 1.");
        }

        // TODO WORK IN PROGRESS:
        // for (int i=0; i<getGapCount(); i++) {
        // float gap = getGap(i);
        // if (u1 < gap && gap < u2) {
        // continuousDraw(g, u1 + 0.001f, gap - 0.001f);
        // u1 = gap;
        // }
        // }
        // continuousDraw(g, u1 + 0.001f, u2 - 0.001f);

        if (u1 > u2) {
            draw(g, u1, 1);
            draw(g, 0, u2);
        } else {
            float length = PApplet.abs(u1 - u2);
            int n = (int) (granularity * length);
            float du = length / n;

            g.beginShape();
            float u = u1;
            for (int i = 0; i < n; i++) {
                trace(pt, u);
                g.vertex(pt.x, pt.y);
                u = (u + du) % 1f;
            }
            g.endShape();
        }
    }

    /**
     * 
     * Given a 1D coordinate, a float, maps the coordinate to a 2D coordinate
     * and stores it in a given Point.
     * 
     * <br>
     * <br>
     * 
     * Maps a given floating point number from 0 to 1 to a given Point along the
     * perimeter of the Path.
     * 
     * @param pt The Point in which the result is stored.
     * @param u A number from 0 to 1.
     */
    public abstract void trace(Point pt, float u);

    /**
     * Tells whether or not the Path is reversed.
     * 
     * @return true, if the path is set to reversed and false otherwise
     */
    public boolean isReversed() {
        return reversed;
    }

    /**
     * Reverses the orientation of the Path.
     */
    public void reverse() {
        reversed = !reversed;
    }

    @Override
    public abstract Path clone();

    /**
     * A continuous function from real values (floats) to Points.
     * 
     * <br>
     * <br>
     * 
     * Maps a given floating point number from 0 to 1 to a Point along the
     * perimeter of the Path.
     * 
     * @param u A number from 0 to 1.
     * @return The resulting point.
     */
    public Point trace(float u) {
        Point pt = new Point(0, 0);
        this.trace(pt, u);
        return pt;
    }

    /**
     * Returns the length of the Path.
     * 
     * @return the perimeter
     */

    // TODO Work in progress --- Need to incorporate gaps
    public float getTotalDistance() {
        trace(pt, 0);
        float x = pt.x;
        float y = pt.y;

        float du = 1f / granularity;
        float u = du;

        float perimeter = 0;

        for (int i = 0; i < granularity; i++) {
            trace(pt, u);

            perimeter += PApplet.dist(x, y, pt.x, pt.y);

            x = pt.x;
            y = pt.y;
            u += du;
        }

        return perimeter;
    }

    /**
     * Draws the path by approximating it with a given number of sample points,
     * and then connecting those points with lines.
     * 
     * <br>
     * <br>
     * 
     * This is a useful shortcut for classes that implement IPath to use. It
     * allows an IPath to define its proper display method in terms of this
     * function.
     * 
     * @param pa The PApplet to which the path is drawn.
     * @param granularity The number of sample points.
     */
    public void draw(PGraphics g, int granularity) {
        float amt = 0;
        float dAmt = 1f / granularity;
        g.beginShape();
        for (int i = 0; i < granularity + 1; i++) {
            trace(pt, amt);
            g.vertex(pt.x, pt.y);
            amt += dAmt;
        }
        g.endShape();
    }

    /**
     * Shifts this Path dx units in the x-direction and dy units in the
     * y-direction.
     * 
     * @param dx The number of pixels to shift the path right.
     * @param dy The number of pixels to shift the path down.
     */
    public abstract void translate(float dx, float dy);

    /**
     * Returns the slope of the Point on the Path at trace(u).
     * 
     * @param u The 1D coordinate of the Path
     * @return The slope at trace(u)
     */
    public float slope(float u) {
        if (u >= 0.001f) {
            Point a = this.trace(u - 0.001f);
            Point b = this.trace(u);
            return Point.slope(a, b);
        } else {
            Point a = this.trace(u + 0.001f);
            Point b = this.trace(u);
            return Point.slope(a, b);
        }
    }
    
    public void setGranularity(int granularity) {
        this.granularity = granularity;
    }
    
    public int getGranularity() {
        return granularity;
    }

    /**
     * Returns the number of discontinuities in the Path.
     * 
     * @return The number of discontinuities in the Path
     */
    public abstract int getGapCount();

    /**
     * 
     * Gives the ith discontinuity in the Path as a 1D coordinate.
     * 
     * Gives -1 if the index is valid.
     * 
     * @param i The index
     * @return The ith discontinuity as a value between 0 and 1
     */
    public abstract float getGap(int i);

    /**
     * Returns the remainder of num / denom.
     * 
     * @param num the numerator
     * @param denom the denominator
     * @return The remainder of num / denom
     */
    public static float remainder(float num, float denom) {
        if (num % denom >= 0)
            return num % denom;
        else
            return denom - ((-num) % denom);
    }

}
