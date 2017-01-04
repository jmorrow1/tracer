package render;

import java.util.ArrayList;
import java.util.List;

import processing.core.PGraphics;
import tracer.Point;
import tracer.Tracer;

public class RenderPaths extends Render {
    public final static int INFINITY = -1;
    private int ptCount = INFINITY;
    private List<List<Point>> paths = new ArrayList<List<Point>>();

    public RenderPaths(List<Tracer> ts) {
        super(ts);
        for (int i = 0; i < ts.size(); i++) {
            paths.add(new ArrayList<Point>());
        }
    }

    public RenderPaths(Tracer[] ts) {
        super(ts);
        for (int i = 0; i < ts.length; i++) {
            paths.add(new ArrayList<Point>());
        }
    }

    @Override
    public void step() {
        super.step();
        for (int i = 0; i < ts.size(); i++) {
            List<Point> p = paths.get(i);
            Tracer t = ts.get(i);
            p.add(new Point(t.x, t.y));
            if (p.size() == ptCount) {
                p.remove(0);
            }
        }
    }

    @Override
    public void draw(PGraphics g) {
        if (style.stroke) {
            g.strokeWeight(style.strokeWeight);
            g.stroke(style.strokeColor);
            g.strokeCap(style.strokeCap);
            g.strokeJoin(style.strokeJoin);
        }
        else {
            g.noStroke();
        }
        
        if (style.fill) {
            g.fill(style.fillColor);
        }
        else {
            g.noFill();
        }

        for (List<Point> p : paths) {
            g.beginShape();
            for (int i = 0; i < p.size(); i++) {
                Point vtx = p.get(i);
                g.vertex(vtx.x, vtx.y);
            }
            g.endShape();
        }
    }

    public int getPointCount() {
        return ptCount;
    }

    public void setPointCount(int ptCount) {
        if (this.ptCount > ptCount && ptCount != INFINITY) {
            for (List<Point> p : paths) {
                p = p.subList(0, ptCount);
            }
        }
        this.ptCount = ptCount;
    }
}
