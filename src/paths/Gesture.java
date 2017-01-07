package paths;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import processing.core.PApplet;
import tracer.Point;

/**
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class Gesture extends Path {
    protected List<GesturePoint> vertices = new ArrayList<GesturePoint>();
    
    
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
            this.vertices.add(new GesturePoint(vertices.get(i), ts.get(i)));
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
            this.vertices.add(new GesturePoint(vertices[i], ts[i]));
        }
        
        this.vertices.sort(GesturePointComparator.instance);
    }
    
    /**
     * An empty gesture.
     */
    public Gesture() {
        this.vertices = new ArrayList<GesturePoint>();
    }
    
    /**
     * 
     * @param vertices The vertices / time coordinates of the Path
     */
    public Gesture(List<GesturePoint> vertices) {
        this.vertices = vertices;
    }

    @Override
    public void trace(Point pt, float u) {
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
        for (GesturePoint g : vertices) {
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
            return 1;
        }
    }
    
    /**
     * Tells whether or not the Path is closed.
     * @return True if the path is closed, false otherwise
     */
    public boolean isClosed() {
        return vertices.size() == 0 || vertices.get(vertices.size()-1).equals(vertices.get(0));
    }
    
    private float getStartTime() {
        return (vertices.size() > 0) ? vertices.get(0).t : 0;
    }
    
    private float getEndTime() {
        return (vertices.size() > 0) ? vertices.get(vertices.size()-1).t : 0;
    }
    
    private float getTotalTime() {
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
    public GesturePoint getVertex(int i) {
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
            vertices.add(0, new GesturePoint(pt, t));
        }
        else if (t < getEndTime()) {
            for (int i=0; i<vertices.size(); i++) {
                GesturePoint g = vertices.get(i);
                if (t == g.t) {
                    vertices.set(i, new GesturePoint(pt, t));
                    break;
                }
                else if (t > g.t) {
                    vertices.add(i+1, new GesturePoint(pt, t));
                    break;
                }
            }
        }
        else if (t == getEndTime() && vertices.size() > 0) {
            vertices.set(vertices.size()-1, new GesturePoint(pt, t));
        }
        else {
            vertices.add(new GesturePoint(pt, t));
        }
    }
    
    /**
     * Adds a vertex / time coordinate to the Path.
     * @param g The vertex / time coordinate
     */
    public void addVertex(GesturePoint g) {
        if (g.t < getStartTime()) {
            vertices.add(0, g);
        }
        else if (g.t < getEndTime()) {
            for (int i=0; i<vertices.size(); i++) {
                GesturePoint h = vertices.get(i);
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
            GesturePoint g = vertices.get(i);
            if (t == g.t) {
                vertices.remove(i);
                break;
            }
        }
    }

    public static class GesturePoint {
        public Point pt;
        public float t;
        
        public GesturePoint(Point pt, float t) {
            this.pt = pt;
            this.t = t;
        }
        
        @Override
        public GesturePoint clone() {
            return new GesturePoint(pt.clone(), t);
        }

        @Override
        public String toString() {
            return "GesturePoint [pt=" + pt + ", t=" + t + "]";
        }
    }

    public static class GesturePointComparator implements Comparator<GesturePoint> {
        public static GesturePointComparator instance = new GesturePointComparator();
        
        private GesturePointComparator() {}
        
        @Override
        public int compare(GesturePoint a, GesturePoint b) {
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
}
