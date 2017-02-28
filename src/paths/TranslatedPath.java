package paths;

import tracer.Point;

/**
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 * @param <T>
 */
public class TranslatedPath<T extends Path> extends Path {    
    protected Point translation;
    protected T path;
    
    /**
     * Clones the CenteredPath.
     * 
     * @param cpath The Path to clone
     */
    public TranslatedPath(TranslatedPath<T> cpath) {
        this(cpath.translation.clone(), (T)cpath.path.clone());
    }

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
    
    /********************
     ***** Behavior *****
     ********************/
    

    @Override
    public void trace(Point pt, float u) {
        path.trace(pt, u);
        pt.translate(translation);
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
        return path.getGap(i);
    }
}
