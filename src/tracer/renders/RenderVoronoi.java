package tracer.renders;

import java.util.Collection;

import processing.core.PGraphics;
import tracer.Point;
import tracer.Tracer;

/**
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class RenderVoronoi extends Render {
//    protected int c1 = 0xff000000;
//    protected int c2 = 0xffffffff;
    
    public RenderVoronoi(Collection<? extends Point> pts) {
        super(pts);
    }
    
    public RenderVoronoi(Point[] pts) {
        super(pts);
    }
    
    public RenderVoronoi(Tracer[] pts) {
        super(pts);
    }
    
    @Override
    public void draw(PGraphics g) {
        if (pts.size() > 0) {
            
        }
    }

//    @Override
//    public void draw(PGraphics g) {
//        if (pts.size() > 0) {
//            g.loadPixels();
//            
//            int i=0;
//            for (float x=0; x<g.width; x++) {
//                for (float y=0; y<g.height; y++) {
//                    int closestIndex = 0;
//                    float minDistSq = (pts.get(0).y-y)*(pts.get(0).y-y) + (pts.get(0).x-x)*(pts.get(0).x-x);
//                    for (int j=1; j<pts.size(); j++) {
//                        Point pt = pts.get(j);
//                        float distSq = (pt.y-y)*(pt.y-y) + (pt.x-x)*(pt.x-x);
//                        if (distSq < minDistSq) {
//                            minDistSq = distSq;
//                            closestIndex = j;
//                        }
//                    }
//                    int color = PApplet.lerpColor(c1, c2, PApplet.map(closestIndex, 0, pts.size(), 0, 1), RGB);
//                    g.pixels[i] = color;
//                    i++;
//                }
//            }
//
//            g.updatePixels();
//        }
//    }

}
