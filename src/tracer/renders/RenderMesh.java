package tracer.renders;

import java.util.ArrayList;
import java.util.Collection;

import processing.core.PApplet;
import processing.core.PGraphics;
import tracer.Point;
import tracer.Tracer;

/**
 * Draws a list of Point objects as a procedural mesh. When two Points get
 * within a certain distance of each other, a line is drawn between them.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class RenderMesh extends Render {
    
    // the minimum distance between two Points for them to be connected by a line
    protected float minDist, sqMinDist;

    // the minimum distance between two Points in which the stroke weight is increased
    protected float strokeRamp, sqStrokeRampDist;
    
    /**************************
     ***** Initialization *****
     **************************/
    
    public RenderMesh() {
        this(new ArrayList<Point>(), 100, 0);
    }

    /**
     * 
     * @param ts
     */
    public RenderMesh(Collection<? extends Point> ts) {
        this(ts, 100, 0);
    }

    /**
     * 
     * @param ts
     * @param minDist
     */
    public RenderMesh(Collection<? extends Point> ts, float minDist) {
        this(ts, minDist, 0);
    }

    /**
     * 
     * @param ts
     * @param minDist
     * @param strokeRamp
     */
    public RenderMesh(Collection<? extends Point> ts, float minDist, float strokeRamp) {
        super(ts);
        this.minDist = minDist;
        this.strokeRamp = strokeRamp;
        this.sqMinDist = minDist * minDist;
        this.sqStrokeRampDist = strokeRamp * strokeRamp;
    }
    
    /**
     * 
     * @param ts
     */
    public RenderMesh(Point[] ts) {
        this(ts, 100, 0);
    }

    /**
     * 
     * @param ts
     * @param minDist
     */
    public RenderMesh(Point[] ts, float minDist) {
        this(listify(ts), minDist);
    }

    /**
     * 
     * @param ts
     * @param minDist
     * @param strokeRamp
     */
    public RenderMesh(Point[] ts, float minDist, float strokeRamp) {
        this(listify(ts), minDist, strokeRamp);
    }
    
    /**
     * 
     * @param ts
     */
    public RenderMesh(Tracer[] ts) {
        this(ts, 100, 0);
    }

    /**
     * 
     * @param ts
     * @param minDist
     */
    public RenderMesh(Tracer[] ts, float minDist) {
        this(listify(ts), minDist);
    }

    /**
     * 
     * @param ts
     * @param minDist
     * @param strokeRamp
     */
    public RenderMesh(Tracer[] ts, float minDist, float strokeRamp) {
        this(listify(ts), minDist, strokeRamp);
    }
    
    /********************
     ***** Behavior *****
     ********************/

    @Override
    public void draw(PGraphics g) {
        // style
        g.strokeCap(style.strokeCap);
        g.stroke(style.strokeColor);

        // analyze and draw
        for (int i = 0; i < pts.size(); i++) {
            for (int j = i + 1; j < pts.size(); j++) {
                Point a = pts.get(i);
                Point b = pts.get(j);
                float term1 = (a.y - b.y);
                float term2 = (a.x - b.x);
                float sqDist = term1 * term1 + term2 * term2;
                if (sqDist <= sqMinDist) {
                    if (sqDist >= sqMinDist - sqStrokeRampDist) {
                        float sw = PApplet.constrain(
                                PApplet.map(sqDist, sqMinDist, sqMinDist - sqStrokeRampDist, 0, style.strokeWeight),
                                0.00001f,
                                style.strokeWeight);
                        g.strokeWeight(sw);
                    } else {
                        g.strokeWeight(style.strokeWeight);
                    }
                    g.line(a.x, a.y, b.x, b.y);
                }
            }
        }
    }
    
    /******************
     ***** Events *****
     ******************/

    /**
     * 
     * @param minDist
     */
    public void setMinDist(float minDist) {
        sqMinDist = minDist * minDist;
    }

    /**
     * 
     * @param strokeRampDist
     */
    public void setStrokeRamp(float strokeRampDist) {
        sqStrokeRampDist = strokeRampDist * strokeRampDist;
    }
    
    /*******************
     ***** Getters *****
     *******************/
    
    /**
     * 
     * @return
     */
    public float getMinDist() {
        return minDist;
    }
    
    /**
     * 
     * @return
     */
    public float getStrokeRamp() {
        return strokeRamp;
    }
}
