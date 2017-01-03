package demos;

import java.util.ArrayList;

import paths.Arc;
import paths.CubicBezier;
import paths.Ellipse;
import paths.Blender;
import paths.Circle;
import paths.Composite;
import paths.Flower;
import paths.Path;
import paths.Polygonize;
import paths.InfinitySymbol;
import paths.Line;
import paths.Superellipse;
import paths.Supershape;
import processing.core.PApplet;
import tracer.Point;

/**
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class Tracing extends PApplet {
    ArrayList<Path> paths;
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
        reposition(paths);
    }

    private ArrayList<Path> initList() {
        float r = 0.4f * cellSize;
        ArrayList<Path> paths = new ArrayList<Path>();
        paths.add(new Line(r * cos(0.25f * PI), r * sin(0.25f * PI), r * cos(1.25f * PI), r * sin(1.25f * PI)));
        paths.add(new Circle(0, 0, r));
        paths.add(new Ellipse(0, 0, 2 * r, r, CENTER));
        paths.add(Polygonize.makeRegularPolygon(0, 0, r, 6, 0));
        paths.add(Polygonize.makeRegularPolygon(0, 0, 25, 4, QUARTER_PI));
        paths.add(Polygonize.makePolygon(0, 0, r / 2, r, 4, QUARTER_PI));
        paths.add(new Arc(0, 0, r, r / 2, 0, 1.5f * PI, RADIUS/* , 50 */));
        paths.add(new Flower(0, 0, r, 4, 3, 100));
        paths.add(new InfinitySymbol(0, 0, r, 0.75f * r, 50));
        paths.add(new CubicBezier(random(-r, r), random(-r, r), random(-r, r), random(-r, r), random(-r, r),
                random(-r, r), random(-r, r), random(-r, r)));
        blender = new Blender(new InfinitySymbol(0, 0, r, 0.75f * r, 50), new Superellipse(0, 0, r, r, 0.5f, 50), 0.5f,
                100);
        paths.add(blender);
        paths.add(new Superellipse(0, 0, r, r, 0.5f, 50));
        Arc a = new Arc(0, 0, r, r, 0, PI, RADIUS/* , 50 */);
        a.reverse();
        Arc b = new Arc(cellSize, 0, r, r, PI, TWO_PI, RADIUS/* , 50 */);
        paths.add(new Composite(a, b));
        paths.add(new Supershape(0, 0, 0.5f * r, r, 5, 1, 1, 1, 300));

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

        for (Path p : paths) {
            drawPath(p);
        }

        u = (u + 0.005f) % 1f;
    }

    private void drawPath(Path t) {
        noFill();
        strokeWeight(2);
        t.draw(g);

        strokeWeight(6);
        t.trace(pt, u);
        pt.display(this);
    }

    public void mouseMoved() {
        float blendAmt = map(mouseX, 0, width, 0, 1);
        blender.setBlendAmt(blendAmt);
        Superellipse superellipse = blender.getB();
        float n = map(mouseY, 0, height, 0.25f, 2);
        superellipse.setN(n);
    }
}
