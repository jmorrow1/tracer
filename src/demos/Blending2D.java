package demos;

import processing.core.PApplet;
import tracer.Point;
import tracer.paths.Blender;
import tracer.paths.Ellipse;
import tracer.paths.InfinitySymbol;
import tracer.paths.Path;
import tracer.paths.Rose;
import tracer.paths.Superellipse;

/**
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class Blending2D extends PApplet {
    private Blender<Blender, Blender> p1;
    private Path p2;
    private Point pt = new Point(0, 0);
    private float amt = 0;

    public static void main(String[] args) {
        PApplet.main("demos.Blending2D");
    }

    public void settings() {
        size(600, 600, P2D);
    }

    public void setup() {
        float cenx = width / 2;
        float ceny = height / 2;
        float r = 120;

        p1 = new Blender(
                new Blender(new InfinitySymbol(cenx, ceny, r, 0.75f * r),
                            new Superellipse(cenx, ceny, r, r, 0.4f), 0.5f),
                new Blender(new Ellipse(cenx, ceny, 2 * r, r, RADIUS), new InfinitySymbol(cenx, ceny, r, 1.25f * r),
                        0.5f, 75),
                0.5f, 150);

        // p1 = new Blender(new Blender(new Flower(cenx, ceny, width/2f, 11, 13,
        // 300),
        // new Flower(cenx, ceny, width/2f, 17, 19, 300), 0.5f, 150),
        // new Blender(new Flower(cenx, ceny, width/2f, 23, 27, 200),
        // new Flower(cenx, ceny, width/2f, 29, 31, 200), 0.5f, 150),
        // 0.5f,
        // 300);

        // p2 = new Lissajous(150, cenx, ceny, 200, 200, 5, 3, 0);
        p2 = new Ellipse(cenx, ceny, width / 2f, height / 2f, CENTER);
        // p2 = new InfinitySymbol(cenx, ceny, width/2f, width/4f, 50);
        // p2 = new Flower(cenx, ceny, width/2f, 5, 3, 200);
    }

    public void draw() {
        background(255);

        p2.trace(pt, amt);
        dot(pt.x, pt.y);
        amt = (amt + 0.004f) % 1f;

        strokeWeight(2);
        stroke(0);
        noFill();
        float blendAmt1 = map(pt.x, 0, width, 0, 1);
        Blender a = p1.getA();
        a.setBlendAmt(blendAmt1);

        float blendAmt2 = map(pt.y, 0, height, 0, 1);
        Blender b = p1.getB();
        b.setBlendAmt(blendAmt2);

        // p1.display(this);
        strokeWeight(4);
        stroke(0);
        Point pt = new Point(0, 0);
        int n = 1250;
        float amt = 0;
        float dAmt = 1f / n;
        for (int i = 0; i < n; i++) {
            p1.trace(pt, amt);
            point(pt.x, pt.y);
            amt += dAmt;
        }
    }

    private void dot(float x, float y) {
        noStroke();
        fill(50);
        ellipseMode(CENTER);
        // ellipse(x, y, 10, 10);
    }
}
