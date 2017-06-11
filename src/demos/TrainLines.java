package demos;

import java.util.ArrayList;

import processing.core.PApplet;
import tracer.Point;
import tracer.Tracer;
import tracer.paths.Shape;
import tracer.Tracer;

public class TrainLines extends PApplet {
    public static void main(String[] args) {
        PApplet.main("demos.TrainLines");
    }

    private Point quantizedMouse;
    private final int cellSqrt = 25;
    private ArrayList<Point> currTrack = new ArrayList<Point>();
    private ArrayList<Shape> tracks = new ArrayList<Shape>();
    private ArrayList<Tracer> tracers = new ArrayList<Tracer>();

    public void settings() {
        size(1200, 675);
    }

    public void setup() {
        noCursor();
        quantizedMouse = new Point(quantize(mouseX, 0, cellSqrt), quantize(mouseY, 0, cellSqrt));
        currTrack.add(quantizedMouse);
    }

    public void draw() {
        background(255);
        grid(cellSqrt);
        dot(3, color(0), mouseX, mouseY);
        dot(5, color(0), quantizedMouse.x, quantizedMouse.y);
        shape(2, color(0), currTrack);
        shapes(2, color(0), tracks);
        tracers(8, color(0), tracers);
    }

    public void mouseMoved() {
        quantizedMouse.x = quantize(mouseX, 0, cellSqrt);
        quantizedMouse.y = quantize(mouseY, 0, cellSqrt);
    }

    public void mousePressed() {
        if (mouseButton == LEFT) {
            quantizedMouse = new Point(quantizedMouse);

            for (int i = 0; i < currTrack.size() - 1; i++) {
                Point vtx = currTrack.get(i);
                if (vtx.x == quantizedMouse.x && vtx.y == quantizedMouse.y) {
                    Shape track = new Shape(currTrack);
                    tracks.add(track);
                    currTrack = new ArrayList<Point>();
                    
                    float du = 1f / track.getLength();
                    Tracer tracer = new Tracer(track, 0, du);
                    
                    tracers.add(tracer);
                }
            }

            currTrack.add(quantizedMouse);
        }
    }

    private void tracers(float strokeWeight, int color, ArrayList<Tracer> tracers) {
        strokeWeight(strokeWeight);
        stroke(color);
        for (Tracer t : tracers) {
            t.step();
            point(t.x, t.y);
        }
    }

    private void shapes(float strokeWeight, int color, ArrayList<Shape> shapes) {
        stroke(color);
        strokeWeight(strokeWeight);
        noFill();
        beginShape();
        for (Shape s : shapes) {
            s.draw(g);
        }
        endShape();
    }

    private void shape(float strokeWeight, int color, ArrayList<Point> pts) {
        stroke(color);
        strokeWeight(strokeWeight);
        noFill();
        beginShape();
        for (Point pt : pts) {
            vertex(pt.x, pt.y);
        }
        endShape();
    }

    private void dot(float strokeWeight, int color, float x, float y) {
        strokeWeight(strokeWeight);
        stroke(color);
        point(x, y);
    }

    private void grid(int cellSqrt) {
        strokeWeight(1);
        stroke(0, 100);

        int x = 0;
        while (x < width) {
            line(x, 0, x, height);
            x += cellSqrt;
        }

        int y = 0;
        while (y < height) {
            line(0, y, width, y);
            y += cellSqrt;
        }
    }

    public static int toIntCoord(float val, float min, float quantum) {
        val -= min;
        val /= quantum;
        return round(val);
    }

    public static float quantize(float val, float min, float quantum) {
        val -= min;
        val /= quantum;
        val = round(val);
        return min + val * quantum;
    }
}
