package render;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PStyle;
import tracer.TStyle;
import tracer.Point;

/**
 * Draws a list of Point objects in some way using the PGraphics class from
 * Processing.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public abstract class Render implements PConstants {
    protected List<Point> pts;
    protected TStyle style;

    /**
     * Constructs a Render containing the array of Points.
     * @param pts The array of Points
     */
    public Render(Point[] pts) {
        this(listify(pts));
        style = new TStyle();
        PApplet pa = null;
    }
    
    /**
     * Constructs a Render containing the list of Points.
     * @param pts The list of Points
     */
    public Render(List<Point> pts) {
        this.pts = pts;
        style = new TStyle();
    }
    
    /**
     * Steps every Point in the list.
     */
    public void step() {
        for (Point pt : pts) {
            pt.step();
        }
    }

    /**
     * Steps every Point in the list.
     * @param dt The change in time
     */
    public void step(int dt) {
        for (Point pt : pts) {
            pt.step(dt);
        }
    }

    /**
     * Draws something based on the Points in the Render.
     * @param g The PGraphics to draw to
     */
    public abstract void draw(PGraphics g);

    /**
     * Converts an array of Points into an ArrayList of points.
     * @param arr The array of Points
     * @return The ArrayList of Points.
     */
    public static ArrayList<Point> listify(Point[] arr) {
        ArrayList<Point> list = new ArrayList<Point>();
        for (int i = 0; i < arr.length; i++) {
            list.add(arr[i]);
        }
        return list;
    }

    /**
     * Gives the number of points stored by the Render.
     * @return The number of points
     */
    public int getPointCount() {
        return pts.size();
    }
    
    /**
     * Sets the style of the Render.
     * @param style the style
     */
    public void setStyle(PStyle style) {
        this.style = new TStyle(style);
    }
    
    /**
     * Sets the style of the Render.
     * @param style the style
     */
    public void setStyle(TStyle style) {
        this.style = style;
    }
    
    /**
     * Sets the style of the Render to the current style of the PApplet.
     * @param pa the PApplet
     */
    public void setStyle(PApplet pa) {
        this.style = new TStyle(pa.getGraphics().getStyle());
    }
    
    /**
     * Sets the style of the Render to the current style of the PGraphics.
     * @param g The PGraphics
     */
    public void setStyle(PGraphics g) {
        this.style = new TStyle(g.getStyle());
    }
   
    /**
     * Sets the stroke cap.
     * @param strokeCap
     */
    public void setStrokeCap(int strokeCap) {
        style.strokeCap = strokeCap;
    }
    
    /**
     * Sets the stroke join.
     * @param strokeJoin
     */
    public void setStrokeJoin(int strokeJoin) {
        style.strokeJoin = strokeJoin;
    }
    
    /**
     * Sets the stroke weight.
     * @param strokeWeight
     */
    public void setStrokeWeight(float strokeWeight) {
        style.strokeWeight = strokeWeight;
    }
    
    /**
     * Sets the fill color.
     * @param fillColor
     */
    public void setFillColor(int fillColor) {
        style.fillColor = fillColor;
    }
    
    /**
     * Sets the stroke color.
     * @param strokeColor
     */
    public void setStrokeColor(int strokeColor) {
        style.strokeColor = strokeColor;
    }
    
    /**
     * Sets a boolean that, if true, sets a stroke color, but, if false, turns off the stroke color.
     * @param stroke
     */
    public void setStroke(boolean stroke) {
        style.stroke = stroke;
    }
    
    /**
     * Sets a boolean that, if true, sets a fill color, but, if false, turns off the fill color.
     * @param fill
     */
    public void setFill(boolean fill) {
        style.fill = fill;
    }
}
