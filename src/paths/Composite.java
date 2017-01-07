package paths;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PStyle;
import tracer.Point;
import tracer.TStyle;

/**
 * 
 * A path that joins two aggregate paths.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 * @param <T> the type of the first path
 * @param <U> the type of the second path
 */
public class Composite<T extends Path, U extends Path> extends Path {
    private T a;
    private U b;

    /**************************
     ***** Initialization *****
     **************************/

    /**
     * Copy constructor.
     * 
     * @param c The composite to copy
     */
    public Composite(Composite<T, U> c) {
        this(c.a, c.b);
        setSampleCount(c.sampleCount);
    }

    /**
     * 
     * @param a the first path
     * @param b the second path
     */
    public Composite(T a, U b) {
        this.a = a;
        this.b = b;
    }

    // /**
    // * Easy constructor.
    // *
    // * @param x The x-coordinate of the path.
    // * @param y The y-coordinate of the path.
    // * @param r The radius of the path.
    // */
    // public Composite(float x, float y, float r) {
    // //TODO
    // }

    /*************************
     ***** Functionality *****
     *************************/

    @Override
    public void trace(Point pt, float amt) {
        if (reversed) {
            amt = PApplet.map(amt, 0, 1, 1, 0);
        }
           
        if (amt < 0.5f) {
            a.trace(pt, 2f * amt);
        } else {
            b.trace(pt, 2f * (amt - 0.5f));
        }
    }

    @Override
    public void draw(PGraphics g) {
        a.draw(g);
        b.draw(g);
    }

    @Override
    public void translate(float dx, float dy) {
        a.translate(dx, dy);
        b.translate(dx, dy);
    }

    /*******************************
     ***** Getters and Setters *****
     *******************************/

    /**
     * 
     * @return the first path
     */
    public T getA() {
        return a;
    }

    /**
     * 
     * @param a the first path
     */
    public void setA(T a) {
        this.a = a;
    }

    /**
     * 
     * @return the second path
     */
    public U getB() {
        return b;
    }

    /**
     * 
     * @param b the second path
     */
    public void setB(U b) {
        this.b = b;
    }

    @Override
    public Composite<T, U> clone() {
        return new Composite(this);
    }

    @Override
    public int getGapCount() {
        return a.getGapCount() + b.getGapCount() + 1;
    }

    @Override
    public float getGap(int i) {
        if (i < a.getGapCount()) {
            return 0.5f * a.getGap(i);
        } else if (i == a.getGapCount()) {
            return 0.5f;
        } else {
            i -= a.getGapCount();
            return 0.5f + 0.5f * b.getGap(i);

        }
    }
    
    @Override
    public void setStyle(TStyle style) {
        super.setStyle(style);
        a.setStyle(style);
        b.setStyle(style);
    }
    
    @Override
    public void setStyle(PStyle style) {
        super.setStyle(style);
        a.setStyle(style);
        b.setStyle(style);
    }
    
    @Override
    public void setStyle(PApplet pa) {
        super.setStyle(pa);
        a.setStyle(pa);
        b.setStyle(pa);
    }
    
    /**
     * 
     * @param strokeCap
     */
    public void setStrokeCap(int strokeCap) {
        style.strokeCap = strokeCap;
        a.setStrokeCap(strokeCap);
        b.setStrokeCap(strokeCap);
    }
    
    /**
     * 
     * @param strokeJoin
     */
    public void setStrokeJoin(int strokeJoin) {
        style.strokeJoin = strokeJoin;
        a.setStrokeJoin(strokeJoin);
        b.setStrokeJoin(strokeJoin);
    }
    
    /**
     * 
     * @param strokeWeight
     */
    public void setStrokeWeight(float strokeWeight) {
        style.strokeWeight = strokeWeight;
        a.setStrokeWeight(strokeWeight);
        b.setStrokeWeight(strokeWeight);
    }
    
    /**
     * 
     * @param fillColor
     */
    public void setFillColor(int fillColor) {
        style.fillColor = fillColor;
        a.setFillColor(fillColor);
        b.setFillColor(fillColor);
    }
    
    /**
     * 
     * @param strokeColor
     */
    public void setStrokeColor(int strokeColor) {
        style.strokeColor = strokeColor;
        a.setStrokeColor(strokeColor);
        b.setStrokeColor(strokeColor);
    }
    
    /**
     * 
     * @param stroke
     */
    public void setStroke(boolean stroke) {
        style.stroke = stroke;
        a.setStroke(stroke);
        b.setStroke(stroke);
    }
    
    /**
     * 
     * @param fill
     */
    public void setFill(boolean fill) {
        style.fill = fill;
        a.setFill(fill);
        b.setFill(fill);
    }
}
