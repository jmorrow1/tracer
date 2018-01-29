package tracer.paths;

import java.util.ArrayList;
import java.util.Collection;

import processing.core.PApplet;
import processing.core.PGraphics;
import tracer.Point;

public class MultiPath extends Path {

    private ArrayList<Path> paths = new ArrayList<Path>();
    private ArrayList<Float> pathEndPts1D = new ArrayList<Float>();

    /**************************
     ***** Initialization *****
     **************************/
    
    public MultiPath(float x, float y, float r) {
        float a1 = 0.5f * PI;
        float a2 = 1.75f * PI;
        float r1 = 0.5f * r;
        float r2 = r;
        
        Arc arc1 = new Arc(x, y, r1, r1, a1, a2, RADIUS);
        Line ln1 = new Line(
                x + r1 * PApplet.cos(a2), 
                y + r1 * PApplet.sin(a2),
                x + r2 * PApplet.cos(a2),
                y + r2 * PApplet.sin(a2));
        Arc arc2 = new Arc(x, y, r2, r2, a1, a2, RADIUS);
        arc2.reverse();
        Line ln2 = new Line(
                x + r2 * PApplet.cos(a1), 
                y + r2 * PApplet.sin(a1),
                x + r1 * PApplet.cos(a1),
                y + r1 * PApplet.sin(a1));
        
        paths.add(arc2);
        paths.add(ln2);
        paths.add(arc1);
        paths.add(ln1);
        computePathEndPts1D();
    }
    
    public MultiPath(Collection<Path> paths) {
        this.paths.addAll(paths);
        computePathEndPts1D();
    }
    
    public MultiPath() {}
    
    private void computePathEndPts1D() {
        pathEndPts1D.clear();
        
        float totalLength = 0;
        for (Path p : paths) {
            totalLength += p.getLength();
        }
        
        float prevPathEndPt1D = 0;
        pathEndPts1D.add(prevPathEndPt1D);
        for (Path p : paths) {
            float pathEndPt1D = p.getLength() / totalLength;
            pathEndPt1D += prevPathEndPt1D;
            pathEndPts1D.add(pathEndPt1D);
            prevPathEndPt1D = pathEndPt1D;
        }
    }

    /********************
     ***** Behavior *****
     ********************/
    
    @Override
    public void trace(Point target, float u) {
        u = Path.remainder(u, 1.0f);
        
        for (int i = 1; i < pathEndPts1D.size(); i++) {
            float coord = pathEndPts1D.get(i);
            if (u < coord) {
                float prevCoord = pathEndPts1D.get(i - 1);
                float v = PApplet.map(u, prevCoord, coord, 0, 1);

                Path path = paths.get(i-1);

                path.trace(target, v);
                break;
            }
        }
    }
    
    /******************
     ***** Events *****
     ******************/

    public void makeProportional() {
        computePathEndPts1D();
    }
    
    public void add(Path path) {
        paths.add(path);
        computePathEndPts1D();
    }
    
    public void addAll(Collection<Path> paths) {
        paths.addAll(paths);
        computePathEndPts1D();
    }
    
    public void remove(Path path) {
        paths.remove(path);
        computePathEndPts1D();
    }

    @Override
    public void translate(float dx, float dy) {
        for (Path p : paths) {
            p.translate(dx, dy);
        }
    }

    /*******************
     ***** Getters *****
     *******************/
    
    @Override
    public Path clone() {
        ArrayList<Path> pathCopies = new ArrayList<Path>();
        for (Path path : paths) {
            pathCopies.add(path.clone());
        }
        return new MultiPath(pathCopies);
    }

    @Override
    public int getGapCount() {
        return paths.size();
    }

    @Override
    public float getGap(int i) {
        return pathEndPts1D.get(i);
    }
}
