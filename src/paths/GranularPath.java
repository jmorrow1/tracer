package paths;

import java.util.Arrays;

import json_lib.JSONable;
import processing.core.PApplet;
import processing.data.JSONObject;

/**
 * 
 * @author James Morrow
 *
 */
public class GranularPath<T extends Traceable> implements Path {
	private Point[] vertices; //TODO Listify
	private float[] segAmts; //TODO Listify
	private float cenx, ceny, width, height, perimeter;
	private T traceable;
	
	/**************************
	 ***** Initialization *****
	 **************************/
	
	public GranularPath(Point[] vertices) {
		this.vertices = vertices;
		update();
	}
	
	public GranularPath(T pathDef, int numVertices) {
		initVertices(pathDef, numVertices);
	}
	
	private GranularPath(GranularPath<T> path) {
		this.cenx = path.getCenx();
		this.ceny = path.getCeny();
		this.width = path.getWidth();
		this.height = path.getHeight();
		this.perimeter = path.getPerimeter();
		this.segAmts = Arrays.copyOf(path.segAmts, path.segAmts.length);
		this.vertices = new Point[path.vertices.length];
		for (int i=0; i<this.vertices.length; i++) {
			this.vertices[i] = new Point(path.vertices[i]);
		}
		this.traceable = path.traceable;
		update();
	}
	
	private void initVertices(Traceable def, int numVertices) {
		if (vertices == null || vertices.length != numVertices) {
			vertices = new Point[numVertices];
		}
		float dAmt = 1f / (numVertices-1);
		float amt = 0;
		for (int i=0; i<numVertices; i++) {
			if (vertices[i] == null) {
				vertices[i] = def.trace(amt);
			}
			else {
				def.trace(vertices[i], amt);
			}
			amt += dAmt;
		}
	}
	
	private void computeDimensions() {
		if (vertices.length > 0) {
			float minX = vertices[0].x, maxX = vertices[0].x;
			float minY = vertices[0].y, maxY = vertices[0].y;
			for (int i=1; i<vertices.length; i++) {
				if (vertices[i].x < minX) {
					minX = vertices[i].x;
				}
				else if (vertices[i].x > maxX) {
					maxX = vertices[i].x;
				}
				if (vertices[i].y < minY) {
					minY = vertices[i].y;
				}
				else if (vertices[i].y > maxY) {
					maxY = vertices[i].y;
				}
			}
			this.width = maxX - minX;
			this.height = maxY - minY;
			this.cenx = minX + this.width/2f;
			this.ceny = minY + this.height/2f;
		}
		else {
			this.cenx = 0;
			this.ceny = 0;
			this.width = 0;
			this.height = 0;
		}
	}
	
	private void computePerimeter() {
		float[] segLengths = new float[vertices.length];
		
		this.perimeter = 0;
		
		//compute the total length of the perimeter and lengths of line segments comprising the perimeter
		if (vertices.length > 0) {
			float ax = vertices[0].x;
			float ay = vertices[0].y;
			for (int i=1; i<vertices.length; i++) {
				float bx = vertices[i].x;
				float by = vertices[i].y;
				segLengths[i] += Line.getLength(ax, ay, bx, by);
				this.perimeter += segLengths[i];
				ax = bx;
				ay = by;
			}
		}
		
		//compute segAmts
		segAmts = new float[segLengths.length];
		if (vertices.length > 0) {
			float sum = 0;
			for (int i=0; i<segAmts.length-1; i++) {
				sum += segLengths[i];
				segAmts[i] = sum / perimeter;
			}
			segAmts[segAmts.length-1] = 1;
		}
	}
	
	/*************************
	 ***** Functionality *****
	 *************************/
	
	@Override
	public void display(PApplet pa) {
		pa.beginShape();
		for (int i=0; i<vertices.length; i++) {
			pa.vertex(vertices[i].x, vertices[i].y);
		}
		pa.endShape();
	}

	@Override
	public void trace(Point pt, float amt) {
		amt = Path.remainder(amt, 1);
		for (int i=1; i<segAmts.length; i++) {
			if (amt < segAmts[i]) {
				amt = PApplet.map(amt, segAmts[i-1], segAmts[i], 0, 1);
				Point a = vertices[i-1];
				Point b = (i != vertices.length) ? vertices[i] : vertices[0];
				Line.trace(pt, a.x, a.y, b.x, b.y, amt);
				break;
			}
		}
	}

	@Override
	public boolean inside(float x, float y) {
		// TODO Implement https://en.wikipedia.org/wiki/Point_in_polygon.
		return false;
	}

	@Override
	public void translate(float dx, float dy) {
		for (Point pt : vertices) {
			pt.x += dx;
			pt.y += dy;
		}
		if (vertices.length != 0) {
			cenx += dx;
			ceny += dy;
		}
	}

	@Override
	public GranularPath clone() {
		return new GranularPath(this);
	}
	
	/*******************************
	 ***** Getters and Setters *****
	 *******************************/
	
	public void update() {
		computeDimensions();
		computePerimeter();
	}
	
	public void setTraceable(Traceable def, int numVertices) {
		initVertices(def, numVertices);
		update();
	}	
	
	public boolean hasTraceable() {
		return traceable != null;
	}
	
	public T getTraceable() {
		return traceable;
	}
	
	@Override
	public float getPerimeter() {
		return perimeter;
	}

	@Override
	public float getCenx() {
		return cenx;
	}
	
	@Override
	public float getCeny() {
		return ceny;
	}

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public float getHeight() {
		return height;
	}
}