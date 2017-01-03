package render;

import java.util.List;

import processing.core.PApplet;
import processing.core.PGraphics;
import tracer.Point;
import tracer.Tracer;

/**
 * Draws a list of Tracer objects as a procedural mesh. When two Tracers get
 * within a certain distance of each other, a line is drawn between them.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class RenderMesh extends Render {
    // the square of the minimum between two Tracers for them to be connected by
    // a line
    protected float sqMinDist;

    // the square of the distance between two Tracers in which the stroke weight
    // is increased
    protected float sqStrokeRampDist;

    // style
    protected int strokeCap, strokeColor;
    protected float strokeWeight;

    public RenderMesh(List<Tracer> ts, float minDist) {
        this(ts, minDist, 0, PApplet.ROUND, 0xff000000, 2f);
    }

    public RenderMesh(List<Tracer> ts, float minDist, float strokeRamp, int strokeCap, int strokeColor,
            float strokeWeight) {
        super(ts);
        this.sqMinDist = minDist * minDist;
        this.sqStrokeRampDist = strokeRamp * strokeRamp;
        this.strokeCap = strokeCap;
        this.strokeColor = strokeColor;
        this.strokeWeight = strokeWeight;
    }

    public RenderMesh(Tracer[] ts, float minDist) {
        this(listify(ts), minDist);
    }

    public RenderMesh(Tracer[] ts, float minDist, float strokeRamp, int strokeCap, int strokeColor,
            float strokeWeight) {
        this(listify(ts), minDist, strokeRamp, strokeCap, strokeColor, strokeWeight);
    }

    @Override
    public void draw(PGraphics g) {
        // style
        g.strokeCap(strokeCap);
        g.stroke(strokeColor);

        // analyze and draw
        for (int i = 0; i < ts.size(); i++) {
            for (int j = i + 1; j < ts.size(); j++) {
                Point a = ts.get(i);
                Point b = ts.get(j);
                float term1 = (a.y - b.y);
                float term2 = (a.x - b.x);
                float sqDist = term1 * term1 + term2 * term2;
                if (sqDist <= sqMinDist) {
                    if (sqDist >= sqMinDist - sqStrokeRampDist) {
                        float sw = PApplet.constrain(
                                PApplet.map(sqDist, sqMinDist, sqMinDist - sqStrokeRampDist, 0, strokeWeight), 0.00001f,
                                strokeWeight);
                        g.strokeWeight(sw);
                    } else {
                        g.strokeWeight(strokeWeight);
                    }
                    g.line(a.x, a.y, b.x, b.y);
                }
            }
        }
    }

    public void setStrokeCap(int strokeCap) {
        this.strokeCap = strokeCap;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }

    public void setStrokeWeight(float strokeWeight) {
        this.strokeWeight = strokeWeight;
    }

    public void setMinDist(float minDist) {
        sqMinDist = minDist * minDist;
    }

    public void setStrokeRamp(float strokeRampDist) {
        sqStrokeRampDist = strokeRampDist * strokeRampDist;
    }
}
