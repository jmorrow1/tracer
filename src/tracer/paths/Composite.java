package tracer.paths;

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
     * 
     * @param a the first path
     * @param b the second path
     */
    public Composite(T a, U b) {
        this.a = a;
        this.b = b;
        setSamplesPerUnitLength(Path.defaultSamplesPerUnitLength);
    }

    /**
     * Copy constructor.
     * 
     * @param c The composite to copy
     */
    public Composite(Composite<T, U> c) {
        this((T)c.a.clone(), (U)c.b.clone());
        setSampleCount(c.sampleCount);
    }

    /********************
     ***** Behavior *****
     ********************/

    @Override
    public void trace(Point target, float u) {
        u = Path.remainder(u, 1.0f);
        
        if (reversed) {
            u = 1.0f - u;
            if (u == 1.0f) {
                u = ALMOST_ONE;
            }
        }

        if (u < 0.5f) {
            a.trace(target, 2f * u);
        } else {
            b.trace(target, 2f * (u - 0.5f));
        }
    }

    @Override
    public void draw(PGraphics g) {
        a.draw(g);
        b.draw(g);
    }
    
    /******************
     ***** Events *****
     ******************/

    @Override
    public void translate(float dx, float dy) {
        a.translate(dx, dy);
        b.translate(dx, dy);
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
     * @param b the second path
     */
    public void setB(U b) {
        this.b = b;
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

    /*******************
     ***** Getters *****
     *******************/

    /**
     * Gives the first Path.
     * @return the first path
     */
    public T getA() {
        return a;
    }

    /**
     * Gives the second Path.
     * @return the second path
     */
    public U getB() {
        return b;
    }

    @Override
    public Composite<T, U> clone() {
        return new Composite(this);
    }

    @Override
    public int getGapCount() {
        int gapCount = 1 + a.getGapCount() + 1 + b.getGapCount();
        
        if (a.getGapCount() > 0 && a.getGap(0) == 0) {
            gapCount--;
        }
        
        if (b.getGapCount() > 0 && b.getGap(0) == 0) {
            gapCount--;
        }
        
        return gapCount;
    }

    @Override
    //TODO Test
    public float getGap(int i) {
        int aGapCount = a.getGapCount();
        int bGapCount = b.getGapCount();
        
        if (i == 0) {
            return 0;
        }
        else if (aGapCount > 0 && a.getGap(0) == 0) {
            if (i < aGapCount) {
                return a.getGap(i);
            }
            else if (i == aGapCount) {
                return 0.5f;
            }
            else if (bGapCount > 0 && b.getGap(0) == 0) {
                if (i < aGapCount + bGapCount) {
                    return b.getGap(i-aGapCount);
                }
            }
            else {
                if (i < aGapCount + bGapCount + 1) {
                    return b.getGap(i-aGapCount-1);
                }
            }
        }
        else {
            if (i < 1 + aGapCount) {
                return a.getGap(i-1);
            }
            else if (i == aGapCount+1) {
                return 0.5f;
            }
            else if (bGapCount > 0 && b.getGap(0) == 0) {
                if (i < aGapCount + 1 + bGapCount) {
                    return b.getGap(i-aGapCount-1);
                }
            }
            else {
                if (i < aGapCount + 1 + bGapCount + 1) {
                    return b.getGap(i-bGapCount-2);
                }
            }
        }
        
        throw new IndexOutOfBoundsException(this.getClass().toString() + ".getGap(" + i + ")");
    }

    @Override
    public String toString() {
        return "Composite [a=" + a + ", b=" + b + "]";
    }
}
