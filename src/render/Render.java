package render;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PStyle;
import tracer.Tracer;

/**
 * Draws a list of Tracer objects in some way using the PGraphics class from
 * Processing.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public abstract class Render implements PConstants {
    protected List<Tracer> ts;
    protected RStyle style;

    public Render(Tracer[] ts) {
        this(listify(ts));
    }
    
    public Render(List<Tracer> ts) {
        this.ts = ts;
    }
    
    public void step() {
        for (Tracer t : ts) {
            t.step();
        }
    }

    public void step(int dt) {
        for (Tracer t : ts) {
            t.step(dt);
        }
    }

    public abstract void draw(PGraphics g);

    public static List<Tracer> listify(Tracer[] arr) {
        List<Tracer> list = new ArrayList<Tracer>();
        for (int i = 0; i < arr.length; i++) {
            list.add(arr[i]);
        }
        return list;
    }

    public int tracerCount() {
        return ts.size();
    }
    
    public void setStyle(PStyle style) {
        this.style = new RStyle(style);
    }
    
    public void setStyle(RStyle style) {
        this.style = style;
    }
    
    public void setStyle(PApplet pa) {
        this.style = new RStyle(pa.getGraphics().getStyle());
    }

    public void setStrokeCap(int strokeCap) {
        style.strokeCap = strokeCap;
    }
    
    public void setStrokeJoin(int strokeJoin) {
        style.strokeJoin = strokeJoin;
    }
    
    public void setStrokeWeight(float strokeWeight) {
        style.strokeWeight = strokeWeight;
    }
    
    public void setFillColor(int fillColor) {
        style.fillColor = fillColor;
    }
    
    public void setStrokeColor(int strokeColor) {
        style.strokeColor = strokeColor;
    }
    
    public void setStroke(boolean stroke) {
        style.stroke = stroke;
    }
    
    public void setFill(boolean fill) {
        style.fill = fill;
    }
}
