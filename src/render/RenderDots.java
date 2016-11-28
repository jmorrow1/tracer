package render;

import java.util.List;

import processing.core.PApplet;
import processing.core.PGraphics;
import tracer.Point;
import tracer.Tracer;

/**
 * 
 * Draws a list of Tracers as dots using Processing's point() function.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class RenderDots extends Render {
	//style
	protected float strokeWeight;
	protected int strokeColor, strokeCap;
	
	public RenderDots(List<Tracer> ts, float strokeWeight, int strokeColor, int strokeCap) {
		super(ts);
		this.strokeWeight = strokeWeight;
		this.strokeColor = strokeColor;
		this.strokeCap = strokeCap;
	}

	public RenderDots(List<Tracer> ts) {
		this(ts, 8f, 0xff000000, PApplet.ROUND);
	}
	
	public RenderDots(Tracer[] ts, float strokeWeight, int strokeColor, int strokeCap) {
		this(listify(ts), strokeWeight, strokeColor, strokeCap);
	}

	public RenderDots(Tracer[] ts) {
		this(listify(ts));
	}
	
	@Override
	public void draw(PGraphics g) {
		//style
		g.strokeWeight(strokeWeight);
		g.stroke(strokeColor);
		g.strokeCap(strokeCap);
		
		//dots
		for (Tracer t : ts) {
			Point pt = t.getLocation();
			g.point(pt.x, pt.y);
		}
	}

	public void setStrokeWeight(float strokeWeight) {
		this.strokeWeight = strokeWeight;
	}

	public void setStrokeColor(int strokeColor) {
		this.strokeColor = strokeColor;
	}

	public void setStrokeCap(int strokeCap) {
		this.strokeCap = strokeCap;
	}
}
