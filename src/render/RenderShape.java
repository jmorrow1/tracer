package render;

import java.util.List;

import processing.core.PGraphics;
import tracer.Point;
import tracer.Tracer;

/**
 * Draws a list of Tracer objects as a shape using Processing's beginShape() and
 * endShape() method.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class RenderShape extends Render {
    // style
    protected boolean closeShape = true;

    public RenderShape(List<Tracer> ts) {
        super(ts);
    }

    public RenderShape(Tracer[] ts) {
        this(listify(ts));
    }

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
        for (int i = 0; i < ts.size(); i++) {
            Point pt = ts.get(i);
            g.vertex(pt.x, pt.y);
        }
        if (closeShape) {
            g.endShape(CLOSE);
        } else {
            g.endShape();
        }
    }

    public void setCloseShape(boolean closeShape) {
        this.closeShape = closeShape;
    }
}