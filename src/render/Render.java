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

    public Render(Point[] ts) {
        this(listify(ts));
        style = new TStyle();
        PApplet pa = null;
    }
    
    public Render(List<Point> ts) {
        this.pts = ts;
        style = new TStyle();
    }
    
    public void step() {
        for (Point t : pts) {
            t.step();
        }
    }

    public void step(int dt) {
        for (Point t : pts) {
            t.step(dt);
        }
    }

    public abstract void draw(PGraphics g);

    public static List<Point> listify(Point[] arr) {
        List<Point> list = new ArrayList<Point>();
        for (int i = 0; i < arr.length; i++) {
            list.add(arr[i]);
        }
        return list;
    }

    public int tracerCount() {
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
     * 
     * @param strokeCap
     */
    public void setStrokeCap(int strokeCap) {
        style.strokeCap = strokeCap;
    }
    
    /**
     * 
     * @param strokeJoin
     */
    public void setStrokeJoin(int strokeJoin) {
        style.strokeJoin = strokeJoin;
    }
    
    /**
     * 
     * @param strokeWeight
     */
    public void setStrokeWeight(float strokeWeight) {
        style.strokeWeight = strokeWeight;
    }
    
    /**
     * 
     * @param fillColor
     */
    public void setFillColor(int fillColor) {
        style.fillColor = fillColor;
    }
    
    /**
     * 
     * @param strokeColor
     */
    public void setStrokeColor(int strokeColor) {
        style.strokeColor = strokeColor;
    }
    
    /**
     * 
     * @param stroke
     */
    public void setStroke(boolean stroke) {
        style.stroke = stroke;
    }
    
    /**
     * 
     * @param fill
     */
    public void setFill(boolean fill) {
        style.fill = fill;
    }
}
