package render;

import processing.core.PStyle;

/**
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class RStyle {
    public int strokeCap, strokeJoin;
    public float strokeWeight;
    public int fillColor, strokeColor;
    public boolean stroke, fill;
    
    public RStyle(PStyle style) {
        this.strokeCap = style.strokeCap;
        this.strokeJoin = style.strokeJoin;
        this.strokeWeight = style.strokeWeight;
        this.fillColor = style.fillColor;
        this.strokeColor = style.strokeColor;
        this.stroke = style.stroke;
        this.fill = style.fill;
    }
    
    public RStyle(int strokeCap, int strokeJoin, float strokeWeight, int fillColor, int strokeColor, boolean stroke, boolean fill) {
        this.strokeCap = strokeCap;
        this.strokeJoin = strokeJoin;
        this.strokeWeight = strokeWeight;
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.stroke = stroke;
        this.fill = fill;
    }
    
    public RStyle() {
        this(2, 8, 1.0f, -1, -16777216, true, true);

    }

    @Override
    public String toString() {
        return "RStyle [strokeCap=" + strokeCap + ", strokeJoin=" + strokeJoin + ", strokeWeight=" + strokeWeight
                + ", fillColor=" + fillColor + ", strokeColor=" + strokeColor + ", stroke=" + stroke + ", fill=" + fill
                + "]";
    }
}
