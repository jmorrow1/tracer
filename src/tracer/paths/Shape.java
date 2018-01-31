package tracer.paths;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.data.JSONObject;
import tracer.Point;

/**
 * 
 * A sequence of vertices attached by lines, analogous to using Processing's
 * beginShape(), vertex(), and endShape() functions.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class Shape extends Path {
    protected List<Point> vertices2D = new ArrayList<Point>();
    protected List<Float> vertices1D = new ArrayList<Float>();
    
    /**************************
     ***** Initialization *****
     **************************/

    /**
     * 
     * @param vertices The vertices of the Shape
     */
    public Shape(List<Point> vertices) {
        this.vertices2D = vertices;
        computeVertices1D();
        setSamplesPerUnitLength(Path.defaultSamplesPerUnitLength);
    }

    /**
     * Copy constructor
     * 
     * @param s The shape to copy
     */
    public Shape(Shape s) {
        super(s.sampleCount);
        for (int i = 0; i < s.vertices2D.size(); i++) {
            this.vertices2D.add(s.vertices2D.get(i).clone());
            this.vertices1D.add(new Float(s.vertices1D.get(i)));
        }
    }

    /**
     * Easy constructor
     * 
     * @param x The x-coordinate of the center of the Path
     * @param y The y-coordinate of the center of the Path
     * @param r The radius of the Path
     */
    public Shape(float x, float y, float r) {
        int n = 5;
        float dTheta = TWO_PI / n;
        for (int i = 0; i < n; i++) {
            float theta = dTheta * i;
            vertices2D.add(new Point(x + r * PApplet.cos(theta), y + r * PApplet.sin(theta)));
        }
        computeVertices1D();
        setSamplesPerUnitLength(Path.defaultSamplesPerUnitLength);
    }
    
    /**
     * 
     */
    public Shape() {
        this(new Point[] {});
    }

    /**
     * 
     * @param vertices The vertices of the Shape
     */
    public Shape(Point[] vertices) {
        this(listify(vertices));
    }
    
    private static ArrayList<Point> listify(Point[] xs) {
        ArrayList<Point> ys = new ArrayList<Point>();
        for (int i = 0; i < xs.length; i++) {
            ys.add(xs[i]);
        }
        return ys;
    }
    
    /********************
     ***** Behavior *****
     ********************/
    
    public void draw(PGraphics g, boolean disableStyle) {
        if (!disableStyle) {
            style.apply(g);
        }
            
        g.beginShape();
        for (int i = 0; i < vertices2D.size(); i++) {
            Point pt = vertices2D.get(i);
            g.vertex(pt.x, pt.y);
        }
        g.endShape();
    }
    
    @Override
    public void draw(PGraphics g) {
        draw(g, false);
    }
    
    @Override
    public void draw(PGraphics g, float u1, float u2) {
        u1 = Path.remainder(u1, 1.0f);
        u2 = Path.remainder(u2, 1.0f);
        
        style.apply(g);
        drawHelper(g, u1, u2);
    }
    
    private void drawHelper(PGraphics g, float u1, float u2) {
        if (u1 < u2) {    
            g.beginShape();
            trace(bufferPoint, u1);
            g.vertex(bufferPoint.x, bufferPoint.y);

            for (int i=1; i<vertices1D.size(); i++) {
                float vtx1D = vertices1D.get(i);
                
                boolean inSegment = (u1 < vtx1D && vtx1D < u2);
                
                if (inSegment) {
                    Point vtx2D = vertices2D.get(i);
                    g.vertex(vtx2D.x, vtx2D.y);
                }
            }
            
            trace(bufferPoint, u2);
            g.vertex(bufferPoint.x, bufferPoint.y);
            g.vertex(bufferPoint.x, bufferPoint.y); //writing the last vertex twice, because the P2D renderer requires at least 3 vertices
            g.endShape();
        }
        else {
            float u12 = PApplet.max(0.999f, 0.5f * (u1 + 1.0f));
            drawHelper(g, u1, u12);
            drawHelper(g, 0, u2);
        }
    }

    @Override
    public void trace(Point target, float u) {
        u = Path.remainder(u, 1.0f);
        
        for (int i = 1; i < vertices1D.size(); i++) {
            float coord = vertices1D.get(i);
            if (u < coord) {
                float prevCoord = vertices1D.get(i - 1);
                float v = PApplet.map(u, prevCoord, coord, 0, 1);

                Point a = vertices2D.get(i - 1);
                Point b = vertices2D.get(i);

                Line.trace(target, a, b, v);
                break;
            }
        }
    }
    
    /******************
     ***** Events *****
     ******************/
    
    /**
     * Adds a vertex to the Shape.
     * 
     * @param i The index
     * @param pt The Point to add
     */
    public void addVertex(int i, Point pt) {
        vertices2D.add(i, pt);
        computeVertices1D();
    }

    /**
     * Adds a vertex to the Shape.
     * 
     * @param pt The Point to add
     */
    public void addVertex(Point pt) {
        vertices2D.add(pt);
        computeVertices1D();
    }

    /**
     * Remove the vertex at index i.
     * 
     * @param i The index
     */
    public void removeVertex(int i) {
        vertices2D.remove(i);
        computeVertices1D();
    }

    /**
     * Sets the vertex at index i to the given Point.
     * 
     * @param i The index
     * @param pt The new vertex
     */
    public void setVertex(int i, Point pt) {
        vertices2D.set(i, pt);
        computeVertices1D();
    }

    /**
     * 
     */
    public void makeProportional() {
        computeVertices1D();
    }
    
    @Override
    public void translate(float dx, float dy) {
        for (Point pt : vertices2D) {
            pt.translate(dx, dy);
        }
    }
    
    @Override
    public void reverse() {
        super.reverse();
        reverse(vertices2D);
        reverse(vertices1D);
        
        int i=0;
        vertices1D.set(0, 0.0f);
        i++;
        while (i<vertices1D.size()-1) {
            Float f = remainder(1.0f - vertices1D.get(i), 1.0f);
            vertices1D.set(i, f);
            i++;
        }
        vertices1D.set(i, 1.0f);
    }

    private static void reverse(List xs) {
        int i = xs.size() - 1;
        while (i >= 0) {
            xs.add(xs.remove(i));
            i--;
        }
    }
    
    /**
     * Clears the Shape of all its vertices.
     */
    public void clear() {
        vertices1D.clear();
        vertices2D.clear();
    }
    
    @Override
    public void setCenter(float x, float y) {
        if (vertices2D.size() > 0) {
            Point pt = vertices2D.get(0);
            
            float x1 = pt.x;
            float y1 = pt.y;
            float x2 = pt.x;
            float y2 = pt.y;
            
            for (int i=1; i<vertices2D.size(); i++) {
                pt = vertices2D.get(i);
                
                if (pt.x < x1) {
                    x1 = pt.x;
                }
                else if (pt.x > x2) {
                    x2 = pt.x;
                }
                
                if (pt.y < y1) {
                    y1 = pt.y;
                }
                else if (pt.y > y2) {
                    y2 = pt.y;
                }
            }
            
            float currx = 0.5f * (x1 + x2);
            float curry = 0.5f * (y1 + y2);
            
            translate(x - currx, y - curry);
        }
    }
    
    /*******************
     ***** Getters *****
     *******************/

    protected void computeVertices1D() {
        vertices1D.clear();

        if (vertices2D.size() > 0) {
            float totalLength = getLength();
    
            Point a = vertices2D.get(0);
            float u1 = 0;
            vertices1D.add(u1);
            for (int i = 1; i < vertices2D.size(); i++) {
                Point b = vertices2D.get(i);
                float du = Line.dist(a, b) / totalLength;
                float u2 = (u1 + du);
                vertices1D.add(u2);
                u1 = u2;
                a = b;
            }
        }
    }

    @Override
    public Shape clone() {
        return new Shape(this);
    }

    @Override
    public int getGapCount() {
        if (isClosed()) {
            return 0;
        }
        else {
            return 1;
        }
    }

    @Override
    public float getGap(int i) {
        if (isClosed() || i != 0) {
            throw new IndexOutOfBoundsException(this.getClass().toString() + ".getGap(" + i + ")");
        } 
        else {
            return 0;
        }
    }

    @Override
    public float getLength() {
        if (vertices2D.size() > 0) {
            float dist = 0;
            Point a = vertices2D.get(0);
            for (int i = 1; i < vertices2D.size(); i++) {
                Point b = vertices2D.get(i);
                dist += Line.dist(a, b);
                a = b;
            }
            return dist;
        }
        else {
            return 0;
        }
    }
    
    /**
     * 
     * Gives the ith vertex of the Shape as a 1D coordinate.
     * @param i The index of the vertex
     * @return A number between 0 (inclusive) and 1 (exclusive)
     */
    public float getVertex1D(int i) {
        return vertices1D.get(i);
    }

    /**
     * 
     * Gives the ith vertex of the Shape as a 2D coordinate.
     * @param i The index of the vertex
     * @return A Point
     */
    public Point getVertex2D(int i) {
        return vertices2D.get(i);
    }

    /**
     * Returns the number of vertices in the Shape.
     * 
     * @return The number of vertices in the Shape
     */
    public int getVertexCount() {
        return vertices2D.size();
    }

    /**
     * Tells whether or not the Path is closed.
     * 
     * @return True if the path is closed, false otherwise
     */
    public boolean isClosed() {
        return vertices2D.size() == 0 || vertices2D.get(vertices2D.size() - 1).equals(vertices2D.get(0));
    }
    
    @Override
    public String toString() {
        return "Shape [vertices=" + vertices2D + "]";
    }
    
    /******************
     ***** Static *****
     ******************/

    /**
     * Blends two Paths and makes a Shape.
     * 
     * @param a The first Path
     * @param b The second Path
     * @param amt A value from 0 to 1, determining how much to weight Path b
     *            over Path a.
     * @param sampleCount The number of sample points to use
     * @return The Shape
     */
    public static Shape blend(Path a, Path b, float amt, int sampleCount) {
        ArrayList<Point> pts = new ArrayList<Point>();
        float u = 0;
        float du = 1f / sampleCount;
        Point ptA = new Point(0, 0);
        Point ptB = new Point(0, 0);
        for (int i = 0; i < sampleCount; i++) {
            a.trace(ptA, amt);
            b.trace(ptB, amt);
            pts.add(Point.lerp(ptA, ptB, amt));
            u += du;
        }
        return new Shape(pts);
    }
    
    /**
     * Converts an arbitrary type of Path into a Shape, using the sampleCount
     * from the Path.
     * 
     * @param path The path
     * @return The Shape
     */
    public static Shape toShape(Path path) {
        return toShape(path, path.getSampleCount());
    }

    /**
     * Converts an arbitrary type of Path into a Shape, using a given
     * sampleCount.
     * 
     * @param path The Path
     * @param sampleCount The number of sample points to use
     * @return The Shape
     */
    public static Shape toShape(Path path, int sampleCount) {
        ArrayList<Point> pts = new ArrayList<Point>();
        float u = 0;
        float du = 1f / sampleCount;
        for (int i = 0; i < sampleCount; i++) {
            pts.add(path.trace(u));
            u += du;
        }
        return new Shape(pts);
    }
    
    /**
     * Converts an arbitrary type of Path into a Shape, using a given
     * sampleCount, with its starting point offset by a one-dimensional coordinate.
     * 
     * @param path The Path
     * @param sampleCount The number of sample points to use
     * @param offset The one-dimensional coordinate
     * @return The Shape
     */
    public static Shape toShape(Path path, int sampleCount, float offset) {
        offset = Path.remainder(offset, 1);
        ArrayList<Point> pts = new ArrayList<Point>();
        float u = 0;
        float du = 1f / sampleCount;
        for (int i = 0; i < sampleCount; i++) {
            float v = Path.remainder(u + offset, 1);
            pts.add(path.trace(v));
            u += du;
        }
        return new Shape(pts);
    }
    
    /**
     * Creates a segment of the path starting at trace(u1) and ending at
     * trace(u2);
     * 
     * @param u1 The 1D coordinate of the segment's start, a value within [0, 1)
     * @param u2 The 1D coordinate of the segment's end, a value within [0, 1)
     * @return The segment
     */
    public Shape createSegment(float u1, float u2) {       
        u1 = Path.remainder(u1, 1.0f);
        u2 = Path.remainder(u2, 1.0f);
        
        if (u2 < u1) {
            float temp = u1;
            u1 = u2;
            u2 = temp;
        }

        Shape shape = new Shape();
        shape.addVertex(this.trace(u1));
        for (int i=0; i<this.vertices2D.size(); i++) {
            float vtx1D = vertices1D.get(i);
            Point vtx2D = vertices2D.get(i);
            
            if (u1 < vtx1D && vtx1D < u2) {
                shape.addVertex(vtx2D.clone());
            }
        }       
        shape.addVertex(this.trace(u2));
        
        return shape;
    }
}