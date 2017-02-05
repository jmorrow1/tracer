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

    /*************************
     ***** Functionality *****
     *************************/

    @Override
    public void trace(Point pt, float u) {
        if (u < 0 || u >= 1) {
            throw new IllegalArgumentException("trace(pt, " + u + ") called where the second argument is outside the range 0 (inclusive) to 1 (exclusive).");
        }
        
        if (reversed) {
            u = 1.0f - u;
            if (u == 1.0f) {
                u = 0.0f;
            }
        }
           
        if (u < 0.5f) {
            a.trace(pt, 2f * u);
        } else {
            b.trace(pt, 2f * (u - 0.5f));
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
     * Gives the first Path.
     * @return the first path
     */
    public T getA() {
        return a;
    }

    /**
     * Gives the first Path.
     * @param a the first path
     */
    public void setA(T a) {
        this.a = a;
    }

    /**
     * Gives the second Path.
     * @return the second path
     */
    public U getB() {
        return b;
    }

    /**
     * Gives the second Path.
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
        return a.getGapCount() + b.getGapCount() + 2;
    }

    @Override
    public float getGap(int i) {
        Path a = reversed ? this.b : this.a;
        Path b = reversed ? this.a : this.b;
        
        if (i == 0) {
            return 0;
        }
        if (i < a.getGapCount()+1) {
            return 0.5f * a.getGap(i);
        }
        else if (i == a.getGapCount()+1) {
            return 0.5f;
        }
        else {
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
    
    @Override
    public void setStrokeCap(int strokeCap) {
        style.strokeCap = strokeCap;
        a.setStrokeCap(strokeCap);
        b.setStrokeCap(strokeCap);
    }
    
    @Override
    public void setStrokeJoin(int strokeJoin) {
        style.strokeJoin = strokeJoin;
        a.setStrokeJoin(strokeJoin);
        b.setStrokeJoin(strokeJoin);
    }
    
    @Override
    public void setStrokeWeight(float strokeWeight) {
        style.strokeWeight = strokeWeight;
        a.setStrokeWeight(strokeWeight);
        b.setStrokeWeight(strokeWeight);
    }
    
    @Override
    public void setFillColor(int fillColor) {
        style.fillColor = fillColor;
        a.setFillColor(fillColor);
        b.setFillColor(fillColor);
    }
    
    @Override
    public void setStrokeColor(int strokeColor) {
        style.strokeColor = strokeColor;
        a.setStrokeColor(strokeColor);
        b.setStrokeColor(strokeColor);
    }
    
    @Override
    public void setStroke(boolean stroke) {
        style.stroke = stroke;
        a.setStroke(stroke);
        b.setStroke(stroke);
    }
    
    @Override
    public void setFill(boolean fill) {
        style.fill = fill;
        a.setFill(fill);
        b.setFill(fill);
    }

    @Override
    public String toString() {
        return "Composite [a=" + a + ", b=" + b + "]";
    }
}
