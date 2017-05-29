package tracer;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PStyle;

/**
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class Dot implements Drawable {
    //style
    protected int strokeCap;
    protected int strokeColor;
    protected float strokeWeight;
    
    //point
    protected Point pt;
    
    /**
     * Constructs a dot located at the given Point.
     * @param pt The Point
     */
    public Dot(Point pt) {
        this.pt = pt;
    }
    
    /**
     * Draws a list of Points as dots using the style settings of the given PGraphics
     * @param pts The list of Points
     * @param g The PGraphics to draw to
     */
    public static void draw(ArrayList<Point> pts, PGraphics g) {        
        for (Point pt : pts) {
            g.point(pt.x, pt.y);
        }
    }
    
    /**
     * Draws the dot.
     * @param g The PGraphics to draw to
     */
    public void draw(PGraphics g) {
        g.strokeWeight(strokeWeight);
        g.stroke(strokeColor);
        g.strokeCap(strokeCap);
        
        g.point(pt.x, pt.y);
    }
    
    /**
     * Sets the style of the Render.
     * @param style the style
     */
    public void setStyle(PStyle style) {
        this.strokeCap = style.strokeCap;
        this.strokeColor = style.strokeColor;
        this.strokeWeight = style.strokeWeight;
    }
    
    /**
     * Sets the style of the Render.
     * @param style the style
     */
    public void setStyle(TStyle style) {
        this.strokeCap = style.strokeCap;
        this.strokeColor = style.strokeColor;
        this.strokeWeight = style.strokeWeight;
    }
    
    /**
     * Sets the style of the Render to the current style of the PApplet.
     * @param pa the PApplet
     */
    public void setStyle(PApplet pa) {
        setStyle(pa.g.getStyle());
    }
    
    /**
     * Sets the style of the Render to the current style of the PGraphics.
     * @param g The PGraphics
     */
    public void setStyle(PGraphics g) {
        setStyle(g.getStyle());
    }
    
    /**
     * Sets the stroke cap.
     * @param strokeCap
     */
    public void setStrokeCap(int strokeCap) {
        this.strokeCap = strokeCap;
    }
    
    /**
     * Sets the stroke color.
     * @param strokeColor
     */
    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }
    
    /**
     * Sets the stroke weight.
     * @param strokeWeight
     */
    public void setStrokeWeight(float strokeWeight) {
        this.strokeWeight = strokeWeight;
    }
    
    /**
     * 
     * @return
     */
    public int getStrokeCap() {
        return strokeCap;
    }
    
    /**
     * 
     * @return
     */
    public int getStrokeColor() {
        return strokeColor;
    }
    
    /**
     * 
     * @return
     */
    public float getStrokeWeight() {
        return strokeWeight;
    }
}
