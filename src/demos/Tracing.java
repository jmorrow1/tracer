package demos;

import java.util.ArrayList;

import processing.core.PApplet;
import tracer.Point;
import tracer.easings.Easings;
import tracer.paths.Arc;
import tracer.paths.Blender;
import tracer.paths.Circle;
import tracer.paths.Composite;
import tracer.paths.CubicBezier;
import tracer.paths.Ellipse;
import tracer.paths.InfinitySymbol;
import tracer.paths.Line;
import tracer.paths.Lissajous;
import tracer.paths.Path;
import tracer.paths.Plot;
import tracer.paths.Polygonize;
import tracer.paths.Rect;
import tracer.paths.Rose;
import tracer.paths.Superellipse;
import tracer.paths.Supershape;

/**
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class Tracing extends PApplet {
    ArrayList<Path> paths = new ArrayList<Path>();
    Blender<InfinitySymbol, Superellipse> blender;

    Point pt = new Point(0, 0);
    float u = 0;
    int cellSize = 100;

    public static void main(String[] args) {
        PApplet.main("demos.Tracing");
    }

    public void settings() {
        size(600, 600, P2D);
    }

    public void setup() {
        paths = initList();
        Path.addOneOfEachPathType(0.4f * cellSize, paths);
        for (Path p : paths) {
            p.setStrokeWeight(1.5f);
            p.setStrokeColor(0);
            p.setFill(false);
        }
        reposition(paths);
//        map(paths, (Path p) -> p.reverse());
    }
    
    private interface PathFunc {
        public void run(Path p);
    }
    
    private void map(ArrayList<Path> paths, PathFunc f) {
        for (Path p : paths) {
            f.run(p);
        }
    }
    
    private ArrayList<Path> initList() {
        float r = 0.4f * cellSize;
        ArrayList<Path> paths = new ArrayList<Path>();
        blender = new Blender(new InfinitySymbol(0, 0, r, 0.75f * r), new Superellipse(0, 0, r, r, 0.5f), 0.5f, 100);
        paths.add(blender);


        return paths;
    }

    private void reposition(ArrayList<Path> ts) {
        int x = cellSize / 2;
        int y = cellSize / 2;

        for (Path t : ts) {
            t.translate(x, y);

            x += (t instanceof Composite) ? 2 * cellSize : cellSize;
//            x += cellSize;
            if (x >= width) {
                x = cellSize / 2;
                y += cellSize;
            }
        }
    }

    public void draw() {
        background(255);

        for (Path p : paths) {
            drawPath(p);
        }

        u = (u + 0.005f) % 1f;
    }

    private void drawPath(Path t) {
        noFill();
        t.draw(g);

        strokeWeight(6);
        t.trace(pt, u);
        point(pt.x, pt.y);
    }

    public void mouseMoved() {
        float blendAmt = map(mouseX, 0, width, 0, 1);
        blender.setBlendAmt(blendAmt);
        Superellipse superellipse = blender.getB();
        float n = map(mouseY, 0, height, 0.25f, 2);
        superellipse.setN(n);
    }
}
