package paths;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ease.Easing;
import processing.core.PApplet;
import processing.core.PGraphics;
import tracer.Point;

/**
 * 
 * Like a Shape but with temporal information attached to each vertex.
 * Ideal for creating paths out of mouse motions.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class Gesture extends Path {
    protected List<SpaceTimePoint> vertices = new ArrayList<SpaceTimePoint>();
    
    /**
     * Constructs a gesture by combining the spatial
     * information of a Shape with the temporal
     * information of an Easing.
     * @param shape The Shape
     * @param easing The Easing
     */
    public Gesture(Shape shape, Easing easing) {
        float u = 0;
        float du = 1.0f / shape.getSampleCount();
        for (int i=0; i<shape.getSampleCount(); i++) {
            Point pt = shape.trace(u);
            float t = easing.val(u);
            
            vertices.add(new SpaceTimePoint(pt, t));
            
            u += du;
        }
    }
    
    /**
     * Copy constructor
     * @param g
     */
    public Gesture(Gesture g) {
        for (int i=0; i<g.vertices.size(); i++) {
            this.vertices.add(g.vertices.get(i).clone());
        }
    }
    
    /**
     * 
     * @param vertices The vertices of the Path
     * @param ts The time coordinates of each vertex in the Path
     */
    public Gesture(List<Point> vertices, List<Float> ts) {
        int n = PApplet.min(vertices.size(), ts.size());
        
        for (int i=0; i<n; i++) {
            this.vertices.add(new SpaceTimePoint(vertices.get(i), ts.get(i)));
        }    
        
        this.vertices.sort(GesturePointComparator.instance);
    }
    
    /**
     * 
     * @param vertices The vertices of the Path
     * @param ts The time coordinates of each vertex in the Path
     */
    public Gesture(Point[] vertices, float[] ts) {
        int n = PApplet.min(vertices.length, ts.length);
        
        for (int i=0; i<n; i++) {
            this.vertices.add(new SpaceTimePoint(vertices[i], ts[i]));
        }
        
        this.vertices.sort(GesturePointComparator.instance);
    }
    
    /**
     * An empty gesture.
     */
    public Gesture() {
        this.vertices = new ArrayList<SpaceTimePoint>();
    }
    
    /**
     * 
     * @param vertices The vertices / time coordinates of the Path
     */
    public Gesture(List<SpaceTimePoint> vertices) {
        this.vertices = vertices;
    }
    
    @Override
    public void draw(PGraphics g) {
        style.apply(g);
        g.beginShape();
        for (int i=0; i<vertices.size(); i++) {
            SpaceTimePoint pt = vertices.get(i);
            g.vertex(pt.pt.x, pt.pt.y);
        }
        g.endShape();
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
        
        float t1 = getStartTime() + u * getTotalTime();
        
        for (int i=1; i<vertices.size(); i++) {
            float t2 = vertices.get(i).t;
            if (t1 < t2) {
                float t0 = vertices.get(i-1).t;
                float v = PApplet.map(t1, t0, t2, 0, 1);
                
                Point a = vertices.get(i-1).pt;
                Point b = vertices.get(i).pt;
               
                Line.trace(pt, a, b, v);
                break;
            }
        }
    }

    @Override
    public Path clone() {
       return new Gesture(this);
    }

    @Override
    public void translate(float dx, float dy) {
        for (SpaceTimePoint g : vertices) {
            g.pt.x += dx;
            g.pt.y += dy;
        }
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
            return -1;
        }
        else {
            return 0;
        }
    }
    
    /**
     * Tells whether or not the Path is closed.
     * @return True if the path is closed, false otherwise
     */
    public boolean isClosed() {
        return vertices.size() == 0 || vertices.get(vertices.size()-1).equals(vertices.get(0));
    }
    
    /**
     * Gives the first time coordinate of the Gesture.
     * @return The first time coordinate
     */
    public float getStartTime() {
        return (vertices.size() > 0) ? vertices.get(0).t : 0;
    }
    
    /**
     * Gives the final time coordinate of the Gesture.
     * @return The final time coordinate
     */
    public float getEndTime() {
        return (vertices.size() > 0) ? vertices.get(vertices.size()-1).t : 0;
    }
    
    /**
     * Gives the total time of the Gesture
     * @return The total time of the Gesture
     */
    public float getTotalTime() {
        return getEndTime() - getStartTime();
    }
    
    /**
     * Gives the number of vertices in the Path.
     * @return The number of vertices in the Path.
     */
    public int getVertexCount() {
        return vertices.size();
    }
    
    /**
     * Gives the ith vertex in the Path, a GesturePoint,
     * which is a combination of a spatial coordinate and a temporal coordinate.
     * @param i The index
     * @return The vertex
     */
    public SpaceTimePoint getVertex(int i) {
        return vertices.get(i);
    }
    
    /**
     * Gives the ith spatial vertex in the Path, which is just a Point.
     * @param i The index
     * @return The vertex
     */
    public Point getSpatialCoordinate(int i) {
        return vertices.get(i).pt;
    }
    
    /**
     * Gives the ith temporal vertex in the Path, which is a float.
     * @param i The index
     * @return The float representing a coordinate in time
     */
    public float getTemporalCoordinate(int i) {
        return vertices.get(i).t;
    }
    
    /**
     * Adds a vertex at time t.
     * @param pt The vertex
     * @param t The time
     */
    public void addVertex(Point pt, float t) {
        if (t < getStartTime()) {
            vertices.add(0, new SpaceTimePoint(pt, t));
        }
        else if (t < getEndTime()) {
            for (int i=0; i<vertices.size(); i++) {
                SpaceTimePoint g = vertices.get(i);
                if (t == g.t) {
                    vertices.set(i, new SpaceTimePoint(pt, t));
                    break;
                }
                else if (t > g.t) {
                    vertices.add(i+1, new SpaceTimePoint(pt, t));
                    break;
                }
            }
        }
        else if (t == getEndTime() && vertices.size() > 0) {
            vertices.set(vertices.size()-1, new SpaceTimePoint(pt, t));
        }
        else {
            vertices.add(new SpaceTimePoint(pt, t));
        }
    }
    
    /**
     * Adds a vertex / time coordinate to the Path.
     * @param g The vertex / time coordinate
     */
    public void addVertex(SpaceTimePoint g) {
        if (g.t < getStartTime()) {
            vertices.add(0, g);
        }
        else if (g.t < getEndTime()) {
            for (int i=0; i<vertices.size(); i++) {
                SpaceTimePoint h = vertices.get(i);
                if (g.t == h.t) {
                    vertices.set(i, g);
                    break;
                }
                else if (g.t > h.t) {
                    vertices.add(i+1, g);
                    break;
                }
            }
        }
        else if (g.t == getEndTime() && vertices.size() > 0) {
            vertices.set(vertices.size()-1, g);
        }
        else {
            vertices.add(g);
        }
       
    }
    
    /**
     * Removes the vertex at index i.
     * @param i The index
     */
    public void removeVertex(int i) {
        vertices.remove(i);
    }
    
    /**
     * Looks for a vertex at time coordinate t and, if found, removes it.
     * The method stops after removing the first vertex it finds or after iterating
     * through the entire list of vertices and finding nothing.
     * @param t The time coordinate
     */
    public void removeVertex(float t) {
        for (int i=0; i<vertices.size(); i++) {
            SpaceTimePoint g = vertices.get(i);
            if (t == g.t) {
                vertices.remove(i);
                break;
            }
        }
    }
    
    /**
     * Flattens the Gesture, which has 3 dimensions (2 spatial dimensions and 1 temporal dimension), 
     * to create an Easing, which has 2 dimensions (1 spatial dimension and 1 temporal dimension),
     * and returns the Easing.
     * 
     * @return The Easing
     */
    public Easing easing() {
        float[] xs = new float[vertices.size()]; //time coordinate
        float[] ys = new float[vertices.size()]; //space coordinate
        
        if (vertices.size() > 0) {
            Point prev = vertices.get(0).pt;
            xs[0] = vertices.get(0).t;
            ys[0] = 0;
            for (int i=1; i<vertices.size(); i++) {
                Point curr = vertices.get(i).pt;
                xs[i] = vertices.get(i).t;
                ys[i] = Line.dist(prev, curr);
            }
            
            normalize(xs);
            normalize(ys);
        }
        
        return new CustomEasing(Point.zip(xs, ys));
    }
    
    private static class CustomEasing implements Easing {
        private Point[] pts;
        
        private CustomEasing(Point[] pts) {
            this.pts = pts;
        }
        
        @Override
        public float val(float t) {
            for (int i=1; i<pts.length; i++) {
                if (t < pts[i].x) {
                    float u = PApplet.map(t, pts[i-1].x, pts[i].x, 0, 1);
                    return PApplet.lerp(pts[i-1].y, pts[i].y, u);
                }
            }
            return 0;
        }
    }

    /**
     * A coordinate in space and in time.
     * 
     * @author James Morrow [jamesmorrowdesign.com]
     *
     */
    public static class SpaceTimePoint {
        public Point pt;
        public float t;
        
        public SpaceTimePoint(Point pt, float t) {
            this.pt = pt;
            this.t = t;
        }
        
        @Override
        public SpaceTimePoint clone() {
            return new SpaceTimePoint(pt.clone(), t);
        }

        @Override
        public String toString() {
            return "GesturePoint [pt=" + pt + ", t=" + t + "]";
        }
    }

    private static class GesturePointComparator implements Comparator<SpaceTimePoint> {
        public static GesturePointComparator instance = new GesturePointComparator();
        
        private GesturePointComparator() {}
        
        @Override
        public int compare(SpaceTimePoint a, SpaceTimePoint b) {
            if (a.t < b.t) {
                return -1;
            }
            else if (a.t == b.t) {
                return 0;
            }
            else {
                return 1;
            }
        }
    }
    
    /**
     * Normalizes an array of floats (maps them to [0,1])
     * @param xs The array of floats
     */
    public static void normalize(float[] xs) {
        if (xs.length == 0) {
            return;
        } else {
            float min = xs[0];
            float max = xs[0];
            for (int i = 1; i < xs.length; i++) {
                if (xs[i] < min) {
                    min = xs[i];
                } else if (xs[i] > max) {
                    max = xs[i];
                }
            }

            if (min == max) {
                for (int i = 0; i < xs.length; i++) {
                    xs[i] = 0;
                }
            } else {
                for (int i = 0; i < xs.length; i++) {
                    xs[i] = PApplet.map(xs[i], min, max, 0, 1);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Gesture [vertices=" + vertices + "]";
    }
}
