package demos;

import java.util.ArrayList;

import processing.core.PApplet;
import tracer.paths.Arc;
import tracer.paths.Blender;
import tracer.paths.Circle;
import tracer.paths.Composite;
import tracer.paths.CubicBezier;
import tracer.paths.Ellipse;
import tracer.paths.InfinitySymbol;
import tracer.paths.Line;
import tracer.paths.Path;
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
public class TracingSegments extends PApplet {
    ArrayList<Path> paths = new ArrayList<Path>();
    Blender<InfinitySymbol, Superellipse> blender;

    float u = 0.75f;
    int cellSize = 100;

    public static void main(String[] args) {
        PApplet.main("demos.TracingSegments");
    }

    public void settings() {
        size(600, 600, P2D);
    }

    public void setup() {
        paths = initList();
        for (Path p : paths) {
            p.setStrokeWeight(1.5f);
            p.setStrokeColor(0);
            p.setFill(false);
        }
//        Path.addAllPathTypes(0.4f * cellSize, paths);
        reposition(paths);
//        map(paths, (Path p) -> p.reverse());
        
        for (Path p : paths) {
            System.out.println(p.toString() + " has a total distance of " + p.getLength());
        }
        
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
        paths.add(new Line(r * cos(0.25f * PI), r * sin(0.25f * PI), r * cos(1.25f * PI), r * sin(1.25f * PI)));
        paths.add(new Circle(0, 0, r));
        paths.add(new Ellipse(0, 0, 2 * r, r, CENTER));
        paths.add(Polygonize.makeRegularPolygon(0, 0, r, 6, 0));
        paths.add(new Rect(0, 0, r, r, RADIUS));
        paths.add(Polygonize.makePolygon(0, 0, r / 2, r, 4, QUARTER_PI));
        paths.add(new Arc(0, 0, r, r / 2, 0, 1.5f * PI, RADIUS/* , 50 */));
        paths.add(new Rose(0, 0, r, 4, 3));
        paths.add(new InfinitySymbol(0, 0, r, 0.75f * r));
        paths.add(new CubicBezier(random(-r, r), random(-r, r), random(-r, r), random(-r, r), random(-r, r), random(-r, r), random(-r, r), random(-r, r)));
        blender = new Blender(new InfinitySymbol(0, 0, r, 0.75f * r), new Superellipse(0, 0, r, r, 0.5f), 0.5f, 100);
        paths.add(blender);
        paths.add(new Superellipse(0, 0, r, r, 0.5f));
        Arc a = new Arc(0, 0, r, r, 0, PI, RADIUS);
        a.reverse();
        Arc b = new Arc(cellSize, 0, r, r, PI, TWO_PI, RADIUS);
        paths.add(new Composite(a, b));
        paths.add(new Supershape(0, 0, 0.5f * r, r, 5, 1, 1, 1));

        return paths;
    }

    private void reposition(ArrayList<Path> ts) {
        int x = cellSize / 2;
        int y = cellSize / 2;

        for (Path t : ts) {
            t.translate(x, y);

            x += (t instanceof Composite) ? 2 * cellSize : cellSize;
            if (x >= width) {
                x = cellSize / 2;
                y += cellSize;
            }
        }
    }

    public void draw() {
        background(255);

        noFill();
        strokeWeight(2);

        float v = (u + 0.5f) % 1f;

        for (Path p : paths) {
            p.draw(g, u, v);
        }

        u = (u + 0.005f) % 1f;
    }

    public void mouseMoved() {
        float blendAmt = map(mouseX, 0, width, 0, 1);
        blender.setBlendAmt(blendAmt);
        Superellipse superellipse = blender.getB();
        float n = map(mouseY, 0, height, 0.25f, 2);
        superellipse.setN(n);
    }
}
