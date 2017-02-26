package tracer;

import java.util.ArrayList;

import processing.core.PGraphics;

/**
 * 
 * A way of drawing the motions of a Tracer.
 * Its previous locations are drawn such that
 * the less recent the location, the more transparent
 * it's drawn.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class Trail implements Drawable {
    protected Tracer tracer;
    protected ArrayList<Point> pts;
    protected int maxPtCount;
    
    protected int color;
    protected int strokeWeight;

    /**
     * 
     * @param tracer A tracer, whose movements define the Trail
     */
    public Trail(Tracer tracer) {
        this(tracer, 100, 0xff000000, 5);
    }
    
    /**
     * 
     * @param tracer A tracer, whose movements define the Trail
     * @param maxPtCount The maximum number of points in the Trail
     */
    public Trail(Tracer tracer, int maxPtCount) {
        this(tracer, maxPtCount, 0xff000000, 5);
    }
    
    /**
     * 
     * @param tracer A tracer, whose movements define the Trail
     * @param maxPtCount The maximum number of points in the Trail
     * @param color The color of points in the Trail
     * @param strokeWeight The stroke weight of points in the Trail
     */
    public Trail(Tracer tracer, int maxPtCount, int color, int strokeWeight) {
        this.tracer = tracer;
        this.pts = new ArrayList<Point>();
        this.maxPtCount = maxPtCount;
        this.color = color;
        this.strokeWeight = strokeWeight;
    }

    @Override
    public void draw(PGraphics g) {
        g.strokeWeight(strokeWeight);

        float alpha = 0;
        float dAlpha = 255 / maxPtCount;
        for (int i = 0; i < pts.size(); i++) {
            Point pt = pts.get(i);
            g.stroke(color, alpha);
            g.point(pt.x, pt.y);
            alpha += dAlpha;
        }
    }

    /**
     * Updates the trail by updating its tracer. 
     */
    public void step() {
        addPoint(tracer.x, tracer.y);
        tracer.step();
    }

    private void addPoint(float x, float y) {
        Point pt = null;
        if (this.pts.size() == maxPtCount - 1) {
            pt = pts.remove(0);
            pt.x = x;
            pt.y = y;
        } else {
            pt = new Point(x, y);
        }

        this.pts.add(pt);
    }
}