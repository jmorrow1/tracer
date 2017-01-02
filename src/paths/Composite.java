package paths;

import processing.core.PApplet;
import processing.core.PGraphics;
import tracer.Point;

/**
 * 
 * A path that joins two aggregate paths.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 * @param <T>
 *            the type of the first path
 * @param <U>
 *            the type of the second path
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
     * @param c
     *            The composite to copy
     */
    public Composite(Composite<T, U> c) {
        this(c.a, c.b);
    }

    /**
     * 
     * @param a
     *            the first path
     * @param b
     *            the second path
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
        if (reversed)
            amt = PApplet.map(amt, 0, 1, 1, 0);
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
     * @param a
     *            the first path
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
     * @param b
     *            the second path
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
}
