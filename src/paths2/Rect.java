package paths2;

import paths.Point;
import processing.core.PApplet;
import processing.data.JSONObject;

/**
 * 
 * A rect.
 * 
 * @author James Morrow
 *
 */
public class Rect extends Path2 {
	private float cenx, ceny, width, height;
	private float perimeter;
	private float[] breaks = new float[4];
	
	/**************************
	 ***** Initialization *****
	 **************************/
	
	public JSONObject toJSON() {
		JSONObject j = new JSONObject();
		j.setFloat("cenx", cenx);
		j.setFloat("ceny", ceny);
		j.setFloat("width", width);
		j.setFloat("height", height);
		return j;
	}
		
	public Rect(JSONObject j) {
		cenx = j.getFloat("cenx");
		ceny = j.getFloat("ceny");
		width = j.getFloat("width");
		height = j.getFloat("height");
		recompute();
	}
	
	/**
	 * 
	 * @param a the 1st rect argument, whose meaning is determined by the given rectMode
	 * @param b the 2nd rect argument, whose meaning is determined by the given rectMode
	 * @param c the 3rd rect argument, whose meaning is determined by the given rectMode
	 * @param d the 4th rect argument, whose meaning is determined by the given rectMode
	 * @param rectMode Determines the meaning of a, b, c, and d. The rectMode can be CENTER, RADIUS, CORNER, or CORNERS.
	 */
	public Rect(float a, float b, float c, float d, int rectMode) {
		if (rectMode == PApplet.CENTER) {
			this.cenx = a;
			this.ceny = b;
			this.width = c;
			this.height = d;
		}
		else if (rectMode == PApplet.RADIUS) {
			this.cenx = a;
			this.ceny = b;
			this.width = 2*c;
			this.height = 2*d;
		}
		else if (rectMode == PApplet.CORNER) {
			this.width = c;
			this.height = d;
			this.cenx = a + 0.5f*width;
			this.ceny = b + 0.5f*height;
		}
		else if (rectMode == PApplet.CORNERS) {
			this.width = c - a;
			this.height = d - b;
			this.cenx = a + 0.5f*width;
			this.ceny = b + 0.5f*height;
		}
		recompute();
	}
	
	/**
	 * Copy constructor.
	 * @param r the Rect to copy
	 */
	public Rect(Rect r) {
		this(r.cenx, r.ceny, r.width, r.height, PApplet.CENTER);
	}
	
	@Override
	public Rect clone() {
		return new Rect(this);
	}
	
	private void recompute() {
		perimeter = width*2 + height*2;
		breaks[0] = width / perimeter;
		breaks[1] = 0.5f;
		breaks[2] = 0.5f + breaks[0];
		breaks[3] = 1;
	}
	
	/*************************
	 ***** Functionality *****
	 *************************/
	
	@Override
	public void display(PApplet pa) {
		pa.rectMode(pa.CENTER);
		pa.rect(cenx, ceny, width, height);
	}

	@Override
	public void trace(Point pt, float amt) {
		if (reversed) amt = PApplet.map(amt, 0, 1, 1, 0);
		if (amt < breaks[0]) {			
			amt = PApplet.map(amt, 0, breaks[0], 0, 1);
			float x = PApplet.lerp(getX1(), getX2(), amt);
			pt.x = x;
			pt.y = getY1();
		}
		else if (amt < breaks[1]) {
			amt = PApplet.map(amt, breaks[0], breaks[1], 0, 1);
			float y = PApplet.lerp(getY1(), getY2(), amt);
			pt.x = getX2();
			pt.y = y;
		}
		else if (amt < breaks[2]) {
			amt = PApplet.map(amt, breaks[1], breaks[2], 0, 1);
			float x = PApplet.lerp(getX2(), getX1(), amt);
			pt.x = x;
			pt.y = getY2();
		}
		else {
			amt = PApplet.map(amt, breaks[2], breaks[3], 0, 1);
			float y = PApplet.lerp(getY2(), getY1(), amt);
			pt.x = getX1();
			pt.y = y;
		}
	}
	
	private boolean amtsEqual(float a, float b) {
		return amtsEqual(a, b, getPerimeter());
	}

	private boolean amtsEqual(float a, float b, float perimeter) {
		return PApplet.abs(a - b) <= (2f / perimeter);
	}

	public boolean isVertexLocation(float amt) {
		return amtsEqual(amt, breaks[0]) || amtsEqual(amt, breaks[1]) ||
			   amtsEqual(amt, breaks[2]) || amtsEqual(amt, breaks[3]);
	}

	@Override
	public void translate(float dx, float dy) {
		cenx += dx;
		ceny += dy;
	}
	
	@Override
	public boolean inside(float x, float y) {
		return this.getX1() <= x && x <= this.getX2() && this.getY1() <= y && y <= this.getY2();
	}
	
	/*******************************
	 ***** Getters and Setters *****
	 *******************************/
	
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
	
	/**
	 * 
	 * @return the center x-coordinate of the rectangle
	 */
	public float setCenx() {
		return cenx;
	}
	
	/**
	 * 
	 * @return the center y-coordinate of the rectangle
	 */
	public float setCeny() {
		return ceny;
	}
	
	/**
	 * 
	 * @return the rectangle's leftmost x-coordinate
	 */
	public float getX1() {
		return cenx - 0.5f*width;
	}
	
	/**
	 * 
	 * @return the rectangle's uppermost y-coordinate
	 */
	public float getY1() {
		return ceny - 0.5f*height;
	}
	
	public void setX1(float x1) {
		this.cenx = x1 + width/2;
	}
	
	public void setY1(float y1) {
		this.ceny = y1 + height/2;
	}
	
	/**
	 * 
	 * @return the rectangle's rightmost x-coordinate
	 */
	public float getX2() {
		return cenx + 0.5f*width;
	}
	
	/**
	 * 
	 * @return the rectangle's lowermost y-coordinate
	 */
	public float getY2() {
		return ceny + 0.5f*height;
	}
	
	/**
	 * Set the width of the rectangle to the given float
	 * @param width the width
	 */
	public void setWidth(float width) {
		this.width = width;
		recompute();
	}
	
	/**
	 * Set the height of the rectangle to the given float
	 * @param height the height
	 */
	public void setHeight(float height) {
		this.height = height;
		recompute();
	}
	
	@Override
	public void setCenter(float cenx, float ceny) {
		this.cenx = cenx;
		this.ceny = ceny;
	}

	@Override
	public String toString() {
		return "Rect [cenx=" + cenx + ", ceny=" + ceny + ", width=" + width
				+ ", height=" + height + "]";
	}
}
