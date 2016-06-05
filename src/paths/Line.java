package paths;

import json_lib.JSONable;
import processing.core.PApplet;
import processing.data.JSONObject;
import traceables.Point;

/**
 * 
 * @author James Morrow
 *
 */
public class Line implements Path, JSONable {
	private Point a, b;
	private float length;
	private static int intersectionRadius = 8;
	
	/**************************
	 ***** Initialization *****
	 **************************/
	
	public Line(float x1, float y1, float x2, float y2){
		a = new Point(x1, y1);
		b = new Point(x2, y2);
		recompute();
	}
	
	public Line(Line line) {
		this(line.getAx(), line.getAy(), line.getBx(), line.getBy());
	}
	
	public Line(JSONObject j) {
		a = new Point(j.getJSONObject("a"));
		b = new Point(j.getJSONObject("b"));
		length = j.getFloat("length");
	}
	
	public JSONObject toJSON() {
		JSONObject j = new JSONObject();
		j.setJSONObject("a", a.toJSON());
		j.setJSONObject("b", b.toJSON());
		j.setFloat("length", length);
		return j;
	}
	
	private void recompute() {
		length = PApplet.dist(a.x, a.y, b.x, b.y);
	}
	
	/**************************
	 ***** Functionality *****
	 **************************/
	
	@Override
	public void display(PApplet pa) {
		pa.line(a.x, a.y, b.x, b.y);
	}

	@Override
	public void trace(Point pt, float amt) {
		amt %= 1;
		pt.x = PApplet.lerp(a.x, b.x, amt);
		pt.y = PApplet.lerp(a.y, b.y, amt);
	}
	
	public static void trace(Point pt, float ax, float ay, float bx, float by, float amt) {
		amt %= 1;
		pt.x = PApplet.lerp(ax, bx, amt);
		pt.y = PApplet.lerp(ay, by, amt);
	}

	@Override
	public boolean inside(float x, float y) {
		return false;
	}
	
	public boolean touches(float x, float y) {
		return touches(a.x, a.y, b.x, b.y, x, y);
	}
	
	public static boolean touches(float x1, float y1, float x2, float y2, float x, float y) {
		if (!((x1 - intersectionRadius <= x && x <= x2 + intersectionRadius && y1 - intersectionRadius <= y && y <= y2 + intersectionRadius) ||
               (x1 - intersectionRadius <= x && x <= x2 + intersectionRadius && y2 - intersectionRadius <= y && y <= y1 + intersectionRadius) ||
               (x2 - intersectionRadius <= x && x <= x1 + intersectionRadius && y1 - intersectionRadius <= y && y <= y2 + intersectionRadius) ||
               (x2 - intersectionRadius <= x && x <= x1 + intersectionRadius && y2 - intersectionRadius <= y && y <= y1 + intersectionRadius))) {
            return false;
        }
        
        //compute the euclidean distance between (x1, y1) and (x2, y2)
        float lab = PApplet.dist(x1, y1, x2, y2);
        //compute the direction vector D from (x1, y1) to (x2, y2)
        float dx = (x2 - x1) / lab;
        float dy = (y2 - y1) / lab;
        
        float t = dx * (x - x1) + dy * (y - y1);
         
        float ex = t*dx + x1;
        float ey = t*dy + y1;
   
        //compute the eculidean distance from e to (x, y)
        float lec = PApplet.dist(ex, ey, x, y);
   
        //test if line touches circle centered about (x, y)
        if (lec < intersectionRadius) {
            return true;
        }     
        else {
            return false;
        }
	}
	
	@Override
	public void translate(float dx, float dy) {
		a.translate(dx, dy);
		b.translate(dx, dy);
	}
	
	@Override
	public Line clone() {
		return new Line(this);
	}
	
	/*******************************
	 ***** Getters and Setters *****
	 *******************************/
	
	public static float getPerimeter(float x1, float y1, float x2, float y2) {
		return PApplet.dist(x1, y1, x2, y2);
	}
	
	public static float getLength(float x1, float y1, float x2, float y2) {
		return PApplet.dist(x1, y1, x2, y2);
	}
	
	@Override
	public float getPerimeter() {
		return length;
	}
	
	@Override
	public float getCenx() {
		return PApplet.lerp(a.x, b.x, 0.5f);
	}
	
	@Override
	public float getCeny() {
		return PApplet.lerp(a.y, b.y, 0.5f);
	}
	
	/**
	 * 
	 * @return the length of the line
	 */
	public float getLength() {
		return length;
	}
	
	@Override
	public float getWidth() {
		return PApplet.max(a.x, b.x) - PApplet.min(a.x, b.x);
	}
	
	@Override
	public float getHeight() {
		return PApplet.max(a.y, b.y) - PApplet.min(a.y, b.y);
	}
	
	/**
	 * 
	 * @return the x-coordinate of the line's start point
	 */
	public float getAx() {
		return a.x;
	}
	
	/**
	 * 
	 * @return the y-coordinate of the line's start point
	 */
	public float getAy() {
		return a.y;
	}
	
	/**
	 * Set the start point of the line.
	 * @param x1
	 * @param y1
	 */
	public void setStartPoint(float x1, float y1) {
		a.x = x1;
		a.y = y1;
		recompute();
	}
	
	/**
	 * 
	 * @return the x-coordinate of the line's end point
	 */
	public float getBx() {
		return b.x;
	}
	
	/**
	 * 
	 * @return the y-coordinate of the line's end point
	 */
	public float getBy() {
		return b.y;
	}
	
	/**
	 * Set the line's end point.
	 * @param x1
	 * @param y1
	 */
	public void setEndPoint(float x1, float y1) {
		b.x = x1;
		b.y = y1;
		recompute();
	}
}
