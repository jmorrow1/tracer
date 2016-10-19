package paths2;

import paths.Point;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.data.JSONObject;

/**
 * 
 * An ellipse.
 * 
 * @author James Morrow
 *
 */
public class Ellipse extends Path2 {
	private float x, y, xRadius, yRadius;
	private float perimeter;
	private boolean perimeterOutOfSync;
	
	/**
	 * Constructs an Ellipse analogously to Processing's native ellipse() function.
	 * See the <a href="https://processing.org/reference/ellipse_.html">Processing documentation</a> for more information.
	 * 
	 * @param a the 1st ellipse argument, whose meaning is determined by the given ellipseMode
	 * @param b the 2nd ellipse argument, whose meaning is determined by the given ellipseMode
	 * @param c the 3rd ellipse argument, whose meaning is determined by the given ellipseMode
	 * @param d the 4th ellipse argument, whose meaning is determined by the given ellipseMode
	 * @param ellipseMode Determines the meaning of a, b, c, and d. The ellipseMode can be CENTER, RADIUS, CORNER, or CORNERS.
	 */
	public Ellipse(float a, float b, float c, float d, int ellipseMode) {
		switch (ellipseMode) {
			case PApplet.RADIUS:
				this.x = a;
				this.y = b;
				this.xRadius = c;
				this.yRadius = d;
				break;
			case PApplet.CENTER:
				this.x = a;
				this.y = b;
				this.xRadius = c/2f;
				this.yRadius = d/2f;
				break;
			case PApplet.CORNER:
				this.xRadius = c/2f;
				this.yRadius = d/2f;
				this.x = a + xRadius;
				this.y = b + yRadius;
				break;
			case PApplet.CORNERS:
				this.xRadius = (c-a)/2f;
				this.yRadius = (d-b)/2f;
				this.x = a + xRadius;
				this.y = b + yRadius;
				break;
		}
		recomputePerimeter();
	}
	
	public Ellipse(Ellipse ellipse) {
		this(ellipse.getCenx(), ellipse.getCeny(), ellipse.getWidth(), ellipse.getHeight(), PApplet.CENTER);
	}
	
	public Ellipse(JSONObject j) {
		x = j.getFloat("x");
		y = j.getFloat("y");
		xRadius = j.getFloat("xRadius");
		yRadius = j.getFloat("yRadius");
		perimeter = j.getFloat("perimeter");
	}
	
	public JSONObject toJSON() {
		JSONObject j = new JSONObject();
		j.setFloat("x", x);
		j.setFloat("y", y);
		j.setFloat("xRadius", xRadius);
		j.setFloat("yRadius", yRadius);
		j.setFloat("perimeter", perimeter);
		return j;
	}
	
	private void recomputePerimeter() {
		float a = PApplet.max(xRadius, yRadius);
		float b = PApplet.min(xRadius, yRadius);
		perimeter = PApplet.PI * (3*(a+b) - PApplet.sqrt((3*a + b) * (a + 3*b)));
		perimeterOutOfSync = false;
	}
	
	@Override
	public void display(PGraphics g) {
		g.ellipseMode(PApplet.RADIUS);
		g.ellipse(x, y, xRadius, yRadius);
	}

	@Override
	public void trace(Point pt, float amt) {
		float radians = amt*PApplet.TWO_PI;
		if (reversed) radians *= -1;
		pt.x = x + xRadius*PApplet.cos(radians);
		pt.y = y + yRadius*PApplet.sin(radians);
	}

	@Override
	public boolean inside(float x, float y) {
		float dx = x - this.x;
        float dy = y - this.y;
        return (dx*dx)/(xRadius*xRadius) + (dy*dy)/(yRadius*yRadius) <= 1;
	}
	
	@Override
	public void translate(float dx, float dy) {
		x += dx;
		y += dy;
	}
	
	public void scale(float s) {
		xRadius *= s;
		yRadius *= s;
		perimeterOutOfSync = true;
	}
	
	@Override
	public Ellipse clone() {
		return new Ellipse(this);
	}

	/*******************************
	 ***** Getters and Setters *****
	 *******************************/
	
	@Override
	public float getPerimeter() {
		if (perimeterOutOfSync) {
			recomputePerimeter();
		}
		return perimeter;
	}
	
	/**
	 * Gives the radius of the ellipse (the length of a line drawn from the ellipse's center to its circumference with the given angle)
	 * @param radians the angle in terms of radians
	 * @return the radius of the ellipse given the angle
	 */
	public float getRadiusAt(float radians) {
		return PApplet.dist(x, y, x + xRadius*PApplet.cos(radians), y + yRadius*PApplet.sin(radians));
	}
	
	@Override
	public float getCenx() {
		return x;
	}
	
	@Override
	public float getCeny() {
		return y;
	}
	
	@Override
	public float getWidth() {
		return 2*xRadius;
	}
	
	/**
	 * Set the width of the ellipse.
	 * @param width
	 */
	public void setWidth(float width) {
		this.xRadius = width/2f;
		perimeterOutOfSync = true;
	}
	
	@Override
	public float getHeight() {
		return 2*yRadius;
	}
	
	/**
	 * Set the height of the ellipse.
	 * @param height
	 */
	public void setHeight(float height) {
		this.yRadius = height/2f;
		perimeterOutOfSync = true;
	}
}
