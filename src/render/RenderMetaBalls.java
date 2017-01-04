package render;

import java.util.List;

import processing.core.PApplet;
import processing.core.PGraphics;
import tracer.Tracer;

//TODO Implement w/ GLSL

/**
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class RenderMetaBalls extends Render {
    protected float maxDist;   
    protected float ballRadius;
    
    //style
    protected int bgColor, fgColor;

    public RenderMetaBalls(List<Tracer> ts) {
        this(ts, 10, 100, 0xff000000, 0xffffffff);
    }

    public RenderMetaBalls(List<Tracer> ts, float ballRadius, float maxDist, int bgColor, int fgColor) {
        super(ts);
        this.ballRadius = ballRadius;
        this.maxDist = maxDist;
        this.bgColor = bgColor;
        this.fgColor = fgColor;
    }

    public RenderMetaBalls(Tracer[] ts) {
        this(listify(ts));
    }

    public RenderMetaBalls(Tracer[] ts, float ballRadius, float maxDist, int bgColor, int fgColor) {
        this(listify(ts), ballRadius, maxDist, bgColor, fgColor);
    }

    @Override
    public void draw(PGraphics g) {
        g.loadPixels();
        int i = 0;
        for (int y = 0; y < g.height; y++) {
            for (int x = 0; x < g.width; x++) {
                float val = 0;
                for (Tracer t : ts) {
                    float dist = PApplet.dist(t.x, t.y, x, y);
                    val += 50 * maxDist / dist;
                }
                float amt = PApplet.constrain(PApplet.map(val, 0, maxDist, 0, 1), 0, 1);
                g.pixels[i] = PApplet.lerpColor(fgColor, bgColor, amt, PApplet.RGB);
            }
        }
        g.updatePixels();
    }

    public void setMaxDist(float maxDist) {
        this.maxDist = maxDist;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public void setFgColor(int fgColor) {
        this.fgColor = fgColor;
    }

    public void setBallRadius(float ballRadius) {
        this.ballRadius = ballRadius;
    }
}
