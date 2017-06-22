package tracer.renders;

import java.util.Collection;

import processing.core.PGraphics;
import tracer.Point;
import tracer.Tracer;

/**
 * Draws a list of Point objects as a shape using Processing's beginShape() and
 * endShape() method.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class RenderShape extends Render {
    // style
    protected boolean closeShape = true;

    /**
     * 
     */
    public RenderShape() {
        super();
    }
    
    /**
     * 
     * @param ts
     */
    public RenderShape(Collection<? extends Point> ts) {
        super(ts);
    }

    /**
     * 
     * @param ts
     */
    public RenderShape(Point[] ts) {
        this(listify(ts));
    }
    
    /**
     * 
     * @param ts
     */
    public RenderShape(Tracer[] ts) {
        this(listify(ts));
    }
    
    /********************
     ***** Behavior *****
     ********************/
    
    @Override
    public void draw(PGraphics g) {
        // stroke
        if (style.stroke) {
            g.strokeWeight(style.strokeWeight);
            g.strokeCap(style.strokeCap);
            g.strokeJoin(style.strokeJoin);
            g.stroke(style.strokeColor);
        } else {
            g.noStroke();
        }

        // fill
        if (style.fill) {
            g.fill(style.fillColor);
        } else {
            g.noFill();
        }

        // shape
        g.beginShape();
        for (int i = 0; i < pts.size(); i++) {
            Point pt = pts.get(i);
            g.vertex(pt.x, pt.y);
        }
        if (closeShape) {
            g.endShape(CLOSE);
        } else {
            g.endShape();
        }
    }

    /******************
     ***** Events *****
     ******************/
    
    /**
     * 
     * @param closeShape
     */
    public void setCloseShape(boolean closeShape) {
        this.closeShape = closeShape;
    }
}