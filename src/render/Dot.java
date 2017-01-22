package render;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PStyle;
import tracer.Drawable;
import tracer.Point;
import tracer.TStyle;

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
}
