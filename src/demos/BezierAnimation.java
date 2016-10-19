package demos;

import paths.Point;
import paths2.Line;
import processing.core.PApplet;

//TODO
public class BezierAnimation extends PApplet {
	public static void main(String[] args) {
		PApplet.main("demos.BezierAnimation");
	}
	

	private Point[][] pts;
	
	private float amt;
	
	public void settings() {
		size(600, 600);
	}
	
	public void setup() {
		pts = fromInput(regularPolygon(width/2, height/2, 10, 200));
	}
	
	private static Point[] regularPolygon(float x, float y, int n, float r) {
		Point[] pts = new Point[n];
		for (int i=0; i<n; i++) {
			float angle = PApplet.map(i, 0, 4, 0, TWO_PI);
			pts[i] = new Point(x + r*PApplet.cos(angle), y + r*PApplet.sin(angle));
		}
		return pts;
	}
	
	private static Point[][] fromInput(Point[] input) {
		Point[][] pts = new Point[input.length][];
		pts[0] = input;
		for (int i=1; i<pts.length; i++) {
			pts[i] = new Point[pts.length-i];
			for (int j=0; j<pts[i].length; j++) {
				pts[i][j] = new Point(0, 0);
			}
		}
		return pts;
	}
	
	public void draw() {
		background(255);
		stroke(0);
		fill(0);
		ellipseMode(CENTER);
		
		for (int i=0; i<pts.length-1; i++) {
			for (int j=0; j<pts[i].length-1; j++) {
				//draw
				line(pts[i][j].x, pts[i][j].y, pts[i][j+1].x, pts[i][j+1].y);
				
				//trace
				Line.trace(pts[i+1][j], pts[i][j], pts[i][j+1], amt);
				ellipse(pts[i+1][j].x, pts[i+1][j].y, 8, 8);
			}
		}
		
		amt = (amt + 0.005f) % 1f;
	}
}
