package tracer;

import processing.core.PGraphics;
import processing.core.PStyle;

/**
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class TStyle {
    public int strokeCap, strokeJoin;
    public float strokeWeight;
    public int fillColor, strokeColor;
    public boolean stroke, fill;
    
    /**************************
     ***** Initialization *****
     **************************/
    
    /**
     * 
     * @param style The style
     */
    public TStyle(PStyle style) {
        this.strokeCap = style.strokeCap;
        this.strokeJoin = style.strokeJoin;
        this.strokeWeight = style.strokeWeight;
        this.fillColor = style.fillColor;
        this.strokeColor = style.strokeColor;
        this.stroke = style.stroke;
        this.fill = style.fill;
    }
    
    /**
     * 
     * @param strokeCap
     * @param strokeJoin
     * @param strokeWeight
     * @param fillColor
     * @param strokeColor
     * @param stroke
     * @param fill
     */
    public TStyle(int strokeCap, int strokeJoin, float strokeWeight, int fillColor, int strokeColor, boolean stroke, boolean fill) {
        this.strokeCap = strokeCap;
        this.strokeJoin = strokeJoin;
        this.strokeWeight = strokeWeight;
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.stroke = stroke;
        this.fill = fill;
    }
    
    /**
     * 
     */
    public TStyle() {
        this(2, 8, 1.0f, -1, -16777216, true, true);
    }
    
    /*******************
     ***** Getters *****
     *******************/
    
    /**
     * Applies this style to the given PGraphics instance.
     * @param g The PGraphics instance
     */
    public void apply(PGraphics g) {
        g.strokeCap(strokeCap);
        g.strokeJoin(strokeJoin);
        g.strokeWeight(strokeWeight);
        
        if (fill) {
            g.fill(fillColor);
        }
        else {
            g.noFill();
        }
        
        if (stroke) {
            g.stroke(strokeColor);
        }
        else {
            g.noStroke();
        }      
    }

    @Override
    public String toString() {
        return "Style [strokeCap=" + strokeCap + ", strokeJoin=" + strokeJoin + ", strokeWeight=" + strokeWeight
                + ", fillColor=" + fillColor + ", strokeColor=" + strokeColor + ", stroke=" + stroke + ", fill=" + fill
                + "]";
    }
}
