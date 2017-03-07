package render;

import java.util.List;

import processing.core.PApplet;
import processing.core.PGraphics;
import tracer.Point;
import tracer.Tracer;

/**
 * Draws a list of Tracers as a clique (a fully-connected graph), where lines
 * are drawn between each Tracer and every other Tracer
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class RenderClique extends Render {
    public RenderClique(List<Point> ts) {
        super(ts);
    }

    public RenderClique(Point[] ts) {
        super(ts);
    }

    @Override
    public void draw(PGraphics g) {
        g.strokeCap(style.strokeCap);
        g.stroke(style.strokeColor);
        g.strokeWeight(style.strokeWeight);

        for (int i = 0; i < pts.size(); i++) {
            for (int j = i + 1; j < pts.size(); j++) {
                Point a = pts.get(i);
                Point b = pts.get(j);
                g.line(a.x, a.y, b.x, b.y);
            }
        }
    }
}
