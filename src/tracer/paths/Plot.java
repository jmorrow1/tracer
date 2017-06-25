package tracer.paths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import processing.core.PApplet;
import processing.core.PGraphics;
import tracer.Point;
import tracer.easings.Easing;
import tracer.easings.Easings;

/**
 * 
 * An x/y plot of data.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class Plot extends Path {
    protected Rect rect;
    protected float[] xs, ys; //normalized coordinates
    
    /**************************
     ***** Initialization *****
     **************************/
    
    public Plot(ArrayList<Point> pts) {
        
        //sort the list of points in ascending order by their x-values
        pts.sort(Point.XValueComparator.getInstance());
        
        //wrap a rectangle around the points
        if (pts.size() > 0) {
            Point pt = pts.get(0);
            float x1 = pt.x;
            float y1 = pt.y;
            float x2 = pt.x;
            float y2 = pt.y;
            for (int i=1; i<pts.size(); i++) {
                pt = pts.get(i);
                
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
            this.rect = new Rect(x1, y1, x2, y2, CORNERS);
        }
        else {
            this.rect = new Rect(0, 0, 0, 0, CORNER);
        }
        
        //normalize the points and store them in the xs and ys arrays
        this.xs = new float[pts.size()];
        this.ys = new float[pts.size()];
        for (int i=0; i<pts.size(); i++) {
            Point pt = pts.get(i);
            this.xs[i] = PApplet.map(pt.x, rect.getX1(), rect.getX2(), 0, 1);
            this.ys[i] = PApplet.map(pt.y, rect.getY1(), rect.getY2(), 0, 1);
        }
        
        setSampleCount(pts.size());
    }
    
    /**
     * Copy constructor
     * @param plot The plot to copy
     */
    public Plot(Plot plot) {
        this.rect = (Rect) plot.rect.clone();
        this.xs = plot.xs;
        this.ys = plot.ys;
        setSampleCount(plot.sampleCount);
    }
    
    /**
     * 
     * @param rect The rectangle within which to draw the plot
     * @param easing The easing curve to form the basis for the plot
     * @param n The number of desired sample points
     */
    public Plot(Rect rect, Easing easing, int n) {
        this.rect = rect;
        
        float du = 1.0f / (n - 1);    
        this.xs = new float[n];
        this.ys = new float[n];
        
        for (int i=0; i<n; i++) {           
           xs[i] = du * i;
           ys[i] = easing.val(xs[i]);
        }
        
        setSampleCount(n);
    }
    
    /**
     * 
     * @param rect The rectangle within which to draw the plot
     * @param xs An array of x values
     * @param ys An array of y values
     */
    public Plot(Rect rect, float[] xs, float[] ys) {
        this.rect = rect;
        
        int n = PApplet.min(xs.length, ys.length);
        this.xs = PApplet.sort(Arrays.copyOfRange(xs, 0, n));
        this.ys = Arrays.copyOfRange(ys, 0, n);
        
        if (n > 0) {
            float minX = xs[0];
            float maxX = xs[xs.length-1];
            float minY = PApplet.min(ys);
            float maxY = PApplet.max(ys);
            
            for (int i=0; i<n; i++) {
                xs[i] = PApplet.map(xs[i], minX, maxX, 0, 1);
                ys[i] = PApplet.map(ys[i], minY, maxY, 0, 1);
            }
        }
        
        setSampleCount(n);
    }
    
    /**
     * 
     * @param rect The rectangle within which to draw the plot
     * @param ys An array of normalized y coordinates
     */
    public Plot(Rect rect, float[] ys) {
        this.rect = rect;
        
        float du = 1.0f / (ys.length-1);
        this.xs = new float[ys.length];
        for (int i=0; i<xs.length; i++) {
            this.xs[i] = du * i;
        }
        this.ys = ys;
        
        setSampleCount(xs.length);
    }
    
    /**
     * Easy constructor
     * 
     * @param x The x-coordinate of the center
     * @param y The y-coordinate of the center
     * @param radius The radius of the rectangle that contains the plot
     */
    public Plot(float x, float y, float radius) {
        this(new Rect(x, y, radius, radius, RADIUS), Easings.getCircEaseIn(), 100);
    }
    
    /********************
     ***** Behavior *****
     ********************/
    
    @Override
    public void draw(PGraphics g) {
        style.apply(g);
        g.beginShape();
        if (xs.length == 2) {
            g.line(rect.getX1() + xs[0] * rect.getWidth(), PApplet.map(ys[0], 0, 1, rect.getY2(), rect.getY1()),
                    rect.getX1() + xs[1] * rect.getWidth(), PApplet.map(ys[1], 0, 1, rect.getY2(), rect.getY1()));
        }
        else {
            for (int i=0; i<xs.length; i++) {
                g.vertex(rect.getX1() + xs[i] * rect.getWidth(), PApplet.map(ys[i], 0, 1, rect.getY2(), rect.getY1()));
            }
        }
        g.endShape();
    }

    @Override
    public void trace(Point target, float u) {
        u = Path.remainder(u, 1.0f);
        
        if (reversed) {
            u = 1.0f - u;
            if (u == 1.0f) {
                u = ALMOST_ONE;
            }
        }
        
        for (int i=1; i<xs.length; i++) {
            if (u < xs[i]) {
                float y = PApplet.map(u, xs[i-1], xs[i], ys[i-1], ys[i]);
                
                target.x = PApplet.map(u, 0, 1, rect.getX1(), rect.getX2());
                target.y = PApplet.map(y, 0, 1, rect.getY2(), rect.getY1());
                break;
            }
        }
    }
    
    /******************
     ***** Events *****
     ******************/
    
    @Override
    public void translate(float dx, float dy) {
        rect.translate(dx, dy);
    }
    
    @Override
    public void setCenter(float x, float y) {
        rect.setCenter(x, y);
    }

    /*******************
     ***** Getters *****
     *******************/

    @Override
    public Path clone() {
        return new Plot(this);
    }

    @Override
    public int getGapCount() {
        return 1;
    }

    @Override
    public float getGap(int i) {
        if (i == 0) {
            return 0;
        }
        else {
            throw new IndexOutOfBoundsException(this.getClass().toString() + ".getGap(" + i + ")");
        }
    }

    @Override
    public float getLength() {
        if (xs.length == 0) {
            return 0;
        }
        
        float dist = 0;
        
        float x1 = rect.getX1();
        float y2 = rect.getY2();
        float w = rect.getWidth();
        float h = rect.getHeight();
        
        float ax = x1 + w*xs[0];
        float ay = y2 - h*ys[0];
        for (int i=1; i<xs.length; i++) {
            float bx = x1 + w*xs[i];
            float by = y2 - h*ys[i];
            dist += PApplet.dist(ax, ay, bx, by);
            ax = bx;
            ay = by;
        }
        
        return dist;
    }

    @Override
    public String toString() {
        return "Plot [rect=" + rect + ", xs=" + Arrays.toString(xs) + ", ys=" + Arrays.toString(ys) + "]";
    }  
}
