package demos;

import java.util.ArrayList;

import paths.Arc;
import paths.Circle;
import paths.Ellipse;
import paths.Flower;
import paths.Line;
import paths.Path;
import paths.Point;
import paths.Polygonize;
import paths.Traceable;
import processing.core.PApplet;

/**
 * 
 * @author James Morrow
 *
 */
public class Tracing extends PApplet {
	ArrayList<Traceable> paths;
	Point pt = new Point(0, 0);
	float amt = 0;
	
	int cellSize = 100;
	
	public static void main(String[] args) {
		PApplet.main("demos.Tracing");
	}
	
	public void settings() {
		size(600, 600, P2D);
	}
	
	public void setup() {	
		paths = initList();
		repositionPaths(paths);
	}
	
	private ArrayList<Traceable> initList() {
		float r = 0.4f * cellSize;
		ArrayList<Traceable> paths = new ArrayList<Traceable>();
		paths.add(new Line(r*cos(0.25f*PI), r*sin(0.25f*PI), r*cos(1.25f*PI), r*sin(1.25f*PI)));
        paths.add(new Circle(0, 0, r));
        paths.add(new Ellipse(0, 0, 2*r, r, CENTER));
        paths.add(Polygonize.makeRegularPolygon(0, 0, r, 6, 0));
        paths.add(Polygonize.makeRegularPolygon(0, 0, 25, 4, QUARTER_PI));
        paths.add(Polygonize.makePolygon(0, 0, r/2, r, 4, QUARTER_PI));
        paths.add(new Arc(0, 0, r, r/2, 0, 1.5f*PI, RADIUS, 50));
        paths.add(new Flower(0, 0, r, 4, 3, 100));
        return paths;
	}
	
	private void repositionPaths(ArrayList<Traceable> paths) {
		int x = cellSize/2;
		int y = cellSize/2;
		
		for (Traceable p : paths) {
			p.translate(x, y);
			
			x += cellSize;
			if (x >= width) {
				x = cellSize/2;
				y += cellSize;
			}
		}
	}
	
	public void draw() {
		background(255);

		for (Traceable p : paths) {
			drawPath(p);
		}
		
		amt += 0.005f;
	}
	
	private void drawPath(Traceable p) {
		noFill();
		strokeWeight(2);
		p.display(this);
	
		p.trace(pt, amt);
		strokeWeight(6);
		pt.display(this);
	}
}
