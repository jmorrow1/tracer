package tracer.paths;

import processing.core.PGraphics;
import tracer.Point;

/**
 * 
 * A TranslatedPath is a Path that wraps another Path, translating it by a given Point.
 * 
 * Any changes to the translation Point (by tracing a Path with it, for example) will
 * directly and automatically affect the TranslatedPath.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 * @param <T> The type of Path wrapped by the TranslatedPath
 */
public class TranslatedPath<T extends Path> extends Path {    
    private Point translation;
    protected T path;
    
    /**************************
     ***** Initialization *****
     **************************/
    
    /**
     * 
     * Constructs a Path that is the given Path translated by the given Point.
     * 
     * @param translation The Point by which to translate
     * @param path The Path
     */
    public TranslatedPath(Point translation, T path) {
        this.translation = translation;
        this.path = path;
    }
    
    /**
     * Clones the CenteredPath.
     * 
     * @param cpath The Path to clone
     */
    public TranslatedPath(TranslatedPath<T> cpath) {
        this(cpath.translation.clone(), (T)cpath.path.clone());
    }
    
    /**
     * Constructs a Path that is the given Path translated by the given Point.
     * 
     * @param x The x-coordinate of the translation point
     * @param y The y-coordinate of the translation point
     * @param path The Path
     */
    public TranslatedPath(float x, float y, T path) {
        this(new Point(x, y), path);
    }
    
    /********************
     ***** Behavior *****
     ********************/
    
    @Override
    public void trace(Point target, float u) {
        path.trace(target, u);
        target.translate(translation);
    }
    
    @Override
    public void draw(PGraphics g) {
        g.pushMatrix();
        g.translate(translation.x, translation.y);
        path.draw(g);
        g.popMatrix();
    }

    /******************
     ***** Events *****
     ******************/
    
    @Override
    public void translate(float dx, float dy) {
        translation.translate(dx, dy);
    }
    
    /*******************
     ***** Getters *****
     *******************/
    
    @Override
    public TranslatedPath<T> clone() {
        return new TranslatedPath(this);
    }
    
    @Override
    public int getGapCount() {
        return path.getGapCount();
    }

    @Override
    public float getGap(int i) {
        try {
            return path.getGap(i);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException(this.getClass().toString() + ".getGap(" + i + ")");
        }
    }
    
    /**
     * Gives the Path wrapped by this TranslatedPath.
     * @return The Path wrapped by this TranslatedPath
     */
    public T getPath() {
        return path;
    }
    
    /**
     * Sets the Path wrapped by this TranslatedPath.
     * @param path The Path wrapped by this TranslatedPath
     */
    public void setPath(T path) {
        this.path = path;
    }
    
    /**
     * Sets the translation Point.
     * @param translation The translation Point
     */
    public void setTranslation(Point translation) {
        this.translation = translation;
    }
    
    /**
     * Gives the translation Point.
     * @return The translation Point
     */
    public Point getPoint() {
        return translation;
    }
}
