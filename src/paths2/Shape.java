package paths2;

import java.util.ArrayList;
import java.util.List;

import paths.IPath;
import processing.core.PApplet;
import processing.core.PGraphics;
import tracer.Point;

/**
 * 
 * A finite sequence of vertices, linearly interpolated.
 * 
 * <br><br>
 * 
 * Usage:<br>
 * The most direct way to construct a Shape is to pass
 * a sequence of Points to act as the path's vertices. This can be
 * done either with a List of points or an array of Points.
 * 
 * <br><br>
 * 
 * Another way to construct a Shape is to provide an
 * <a href="../paths/IPath.html">IPath</a>, which will act as
 * the blueprint for a new Shape.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class Shape implements IPath2 {
	private List<Point> vertices;
	private List<Float> segAmts;
	private float cenx, ceny, width, height, perimeter;
	
	/**************************
	 ***** Initialization *****
	 **************************/
	
	/**
	 * Makes a path from the given sequence of vertices.
	 * 
	 * @param vertices the vertex sequence
	 */
	public Shape(Point[] vertices) {
		this(listify(vertices));
	}
	
	/**
	 * Makes a path from the given sequence of vertices.
	 * 
	 * @param vertices the vertex sequence
	 */
	public Shape(List<Point> vertices) {
		this.vertices = vertices;
		update();
	}
	
	/**
	 * Construct a Shape by taking a snapshot of an IPath.
	 * 
	 * @param pathDef the IPath which will be read and then discarded.
	 * @param numVertices the resolution of the snapshot.
	 */
	public Shape(IPath pathDef, int numVertices) {
		initVertices(pathDef, numVertices);
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param path the path to copy
	 */
	private Shape(Shape path) {
		this.cenx = path.getCenx();
		this.ceny = path.getCeny();
		this.width = path.getWidth();
		this.height = path.getHeight();
		this.perimeter = path.getPerimeter();
		this.segAmts = new ArrayList<Float>(path.segAmts.size());
		for (Float f : path.segAmts) {
			this.segAmts.add(f.floatValue());
		}
		this.vertices = new ArrayList<Point>(path.vertices.size());
		for (Point vtx : vertices) {
			this.vertices.add(new Point(vtx));
		}
		update();
	}
	
	private void initVertices(IPath def, int numVertices) {
		if (vertices == null || vertices.size() != numVertices) {
			vertices = new ArrayList<Point>(numVertices);
		}
		float dAmt = 1f / (numVertices-1);
		float amt = 0;
		for (int i=0; i<numVertices; i++) {
			if (vertices.get(i) == null) {
				vertices.set(i, def.trace(amt));
			}
			else {
				def.trace(vertices.get(i), amt);
			}
			amt += dAmt;
		}
	}
	
	private void computeDimensions() {
		if (vertices.size() > 0) {
			float minX = vertices.get(0).x, maxX = vertices.get(0).x;
			float minY = vertices.get(0).y, maxY = vertices.get(0).y;
			for (int i=1; i<vertices.size(); i++) {
				if (vertices.get(i).x < minX) {
					minX = vertices.get(i).x;
				}
				else if (vertices.get(i).x > maxX) {
					maxX = vertices.get(i).x;
				}
				if (vertices.get(i).y < minY) {
					minY = vertices.get(i).y;
				}
				else if (vertices.get(i).y > maxY) {
					maxY = vertices.get(i).y;
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
		float[] segLengths = new float[vertices.size()];
		
		this.perimeter = 0;
		
		//compute the total length of the perimeter and lengths of line segments comprising the perimeter
		if (vertices.size() > 0) {
			float ax = vertices.get(0).x;
			float ay = vertices.get(0).y;
			for (int i=1; i<vertices.size(); i++) {
				float bx = vertices.get(i).x;
				float by = vertices.get(i).y;
				segLengths[i] += PApplet.dist(ax, ay, bx, by);
				this.perimeter += segLengths[i];
				ax = bx;
				ay = by;
			}
		}
		
		//compute segAmts
		segAmts = new ArrayList<Float>(segLengths.length);
		if (segLengths.length > 0) {
			float sum = 0;
			for (int i=0; i<segLengths.length; i++) {
				sum += segLengths[i];
				segAmts.add(i, sum / perimeter);
			}
			segAmts.add(segAmts.size()-1, 1f);
		}
	}
	
	/*************************
	 ***** Functionality *****
	 *************************/
	
	@Override
	public void display(PGraphics g) {
		g.beginShape();
		for (int i=0; i<vertices.size(); i++) {
			
			g.vertex(vertices.get(i).x, vertices.get(i).y);
		}
		g.endShape();
	}

	@Override
	public void trace(Point pt, float amt) {
		amt = IPath2.remainder(amt, 1);
		for (int i=1; i<segAmts.size(); i++) {
//			
			if (amt < segAmts.get(i)) {
				amt = PApplet.map(amt, segAmts.get(i-1), segAmts.get(i), 0, 1);
				Point a = vertices.get(i-1);
				Point b = (i != vertices.size()) ? vertices.get(i) : vertices.get(0);
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
		if (vertices.size() != 0) {
			cenx += dx;
			ceny += dy;
		}
	}

	@Override
	public Shape clone() {
		return new Shape(this);
	}
	
	/*******************************
	 ***** Getters and Setters *****
	 *******************************/
	
	@Override
	public void reverse() {
		reverse(segAmts);
		reverse(vertices);
	}
	
	private void update() {
		computeDimensions();
		computePerimeter();
	}
	
	/**
	 * Reinitializes the Shape by taking a snapshot of an IPath.
	 * 
	 * @param pathDef the IPath which will be read and then discarded
	 * @param numVertices the resolution of the snapshot
	 */
	public void set(IPath pathDef, int numVertices) {
		initVertices(pathDef, numVertices);
		update();
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
	
	/****************************
	 ***** Static Functions *****
	 ****************************/
	
	private static List<Point> listify(Point[] xs) {
		List<Point> list = new ArrayList<Point>();
		for (int i=0; i<xs.length; i++) {
			list.add(xs[i]);
		}
		return list;
	}
	
	private static List reverse(List in) {
		List out = new ArrayList();
		int i = in.size() - 1;
		while (i >= 0) {
			out.add(in.get(i));
		}
		return out;
	}
}