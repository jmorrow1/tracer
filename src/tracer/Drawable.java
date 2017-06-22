package tracer;

import processing.core.PConstants;
import processing.core.PGraphics;

/**
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public interface Drawable extends PConstants {
    
    /**
     * 
     * @param g
     */
    public void draw(PGraphics g);
}
