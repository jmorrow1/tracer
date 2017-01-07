package paths;

import java.util.Arrays;

import ease.Easing;
import processing.core.PApplet;
import processing.core.PGraphics;
import tracer.Point;

/**
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class Plot extends Path {
    protected Rect rect;
    protected float[] xs, ys; //normalized coordinates
    
    /**
     * Copy constructor
     * @param plot The plot to copy
     */
    public Plot(Plot plot) {
        this.rect = plot.rect;
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
    }
    
    /**
     * Easy constructor
     * 
     * @param x The x-coordinate of the center
     * @param y The y-coordinate of the center
     * @param radius The radius of the rectangle that contains the plot
     */
    public Plot(float x, float y, float radius) {
        this(new Rect(x, y, radius, radius, RADIUS), new Easing.Linear(), 2);
    }
    
    @Override
    public void draw(PGraphics g) {
        style.apply(g);
        g.beginShape();
        for (int i=0; i<xs.length; i++) {
            g.vertex(rect.getX1() + xs[i] * rect.getWidth(), PApplet.map(ys[i], 0, 1, rect.getY2(), rect.getY1()));
        }
        g.endShape();
    }

    @Override
    public void trace(Point pt, float u) {
        for (int i=1; i<xs.length; i++) {
            if (u < xs[i]) {
                float y = PApplet.map(u, xs[i-1], xs[i], ys[i-1], ys[i]);
                
                pt.x = PApplet.map(u, 0, 1, rect.getX1(), rect.getX2());
                pt.y = PApplet.map(y, 0, 1, rect.getY2(), rect.getY1());
                break;
            }
        }
    }

    @Override
    public Path clone() {
        return new Plot(this);
    }

    @Override
    public void translate(float dx, float dy) {
        rect.translate(dx, dy);
    }

    @Override
    public int getGapCount() {
        return 1;
    }

    @Override
    public float getGap(int i) {
        return 1;
    }

    @Override
    public float getTotalDistance() {
        if (xs.length == 0) {
            return 0;
        }
        
        float dist = 0;
        
        float ax = xs[0];
        float ay = ys[0];
        for (int i=1; i<xs.length; i++) {
            float bx = xs[i];
            float by = ys[i];
            dist += PApplet.dist(ax, ay, bx, by);
            ax = bx;
            ay = by;
        }
        
        return dist;
    }
    
}
