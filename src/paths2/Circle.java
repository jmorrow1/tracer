package paths2;

import json_lib.JSONable;
import paths.Point;
import processing.core.PApplet;
import processing.data.JSONObject;

/**
 * 
 * @author James Morrow
 *
 */
public class Circle extends Path2 implements JSONable {
	private float x, y;
	private float radius;
	private float angleOffset;
	
	public Circle(float x, float y, float radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	
	public Circle(Circle c) {
		this(c.getCenx(), c.getCeny(), c.getRadius());
	}
	
	public Circle(JSONObject j) {
		x = j.getFloat("x");
		y = j.getFloat("y");
		radius = j.getFloat("radius");
		angleOffset = j.getFloat("angleOffset");
	}
	
	@Override
	public JSONObject toJSON() {
		JSONObject j = new JSONObject();
		j.setFloat("x", x);
		j.setFloat("y", y);
		j.setFloat("radius", radius);
		j.setFloat("angleOffset", angleOffset);
		return j;
	}
	
	@Override
	public void display(PApplet pa) {
		pa.ellipseMode(pa.RADIUS);
		pa.ellipse(x, y, radius, radius);
	}

	@Override
	public void trace(Point pt, float amt) {
		float radians = amt * PApplet.TWO_PI;
		if (reversed) radians *= -1;
		pt.x = x + radius*PApplet.cos(angleOffset+radians);
		pt.y = y + radius*PApplet.sin(angleOffset+radians);
	}

	@Override
	public boolean inside(float x, float y) {
		return PApplet.dist(x, y, x, y) <= radius;
	}
	
	@Override
	public void translate(float dx, float dy) {
		x += dx;
		y += dy;
	}
	
	@Override
	public Circle clone() {
		return new Circle(this);
	}
	
	/*******************************
	 ***** Getters and Setters *****
	 *******************************/
	
	@Override
	public float getPerimeter() {
		return PApplet.TWO_PI * radius;
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
		return this.getDiameter();
	}
	
	@Override
	public float getHeight() {
		return this.getDiameter();
	}
	
	@Override
	public void setCenter(float x, float y) {
		x = x;
		y = y;
	}
	
	/**
	 * 
	 * @return the diameter
	 */
	public float getDiameter() {
		return radius*2;
	}
	
	/**
	 * Set the diameter.
	 * @param diam
	 */
	public void setDiameter(float diam) {
		this.radius = 2*diam;
	}
	
	/**
	 * 
	 * @return the radius
	 */
	public float getRadius() {
		return radius;
	}
	
	/**
	 * Set the radius.
	 * @param radius
	 */
	public void setRadius(float radius) {
		this.radius = radius;
	}
}
