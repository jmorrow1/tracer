package render;

import java.util.List;

import processing.core.PApplet;
import processing.core.PGraphics;
import tracer.Point;
import tracer.Tracer;

/**
 * Draws a list of Tracers as a clique (a fully-connected graph), where lines are drawn between each Tracer and every other Tracer
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class RenderClique extends Render {
	//style
	protected int strokeCap, strokeColor;
	protected float strokeWeight;

	public RenderClique(List<Tracer> ts, int strokeCap, int strokeColor, float strokeWeight) {
		super(ts);
		this.strokeCap = strokeCap;
		this.strokeColor = strokeColor;
		this.strokeWeight = strokeWeight;
	}

	public RenderClique(List<Tracer> ts) {
		this(ts, PApplet.ROUND, 0x000000ff, 2f);
	}

	@Override
	public void draw(PGraphics g) {
		g.strokeCap(strokeCap);
		g.stroke(strokeColor);
		g.strokeWeight(strokeWeight);
		
		for (int i=0; i<ts.size(); i++) {
			for (int j=i+1; j<ts.size(); j++) {
				Point a = ts.get(i).location();
				Point b = ts.get(j).location();
				g.line(a.x, a.y, b.x, b.y);
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
}
