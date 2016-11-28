package render;

import java.util.List;

import processing.core.PApplet;
import processing.core.PGraphics;
import tracer.Point;
import tracer.Tracer;

/**
 * Draws a list of Tracer objects as a procedural mesh.
 * When two Tracers get within a certain distance of each other, a line is drawn between them.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class RenderProcMesh extends Render {
	//minimum distance squared between two Tracers for which a line will be drawn
	protected float minSqDist;
	
	//style
	protected int strokeCap, strokeColor;
	protected float strokeWeight;
	
	public RenderProcMesh(List<Tracer> ts, float minDist) {
		this(ts, minDist, PApplet.ROUND, 0xff000000, 2f);
	}

	public RenderProcMesh(List<Tracer> ts, float minDist, int strokeCap, int strokeColor, float strokeWeight) {
		super(ts);
		this.minSqDist = minDist*minDist;
		this.strokeCap = strokeCap;
		this.strokeColor = strokeColor;
		this.strokeWeight = strokeWeight;
	}
	
	public RenderProcMesh(Tracer[] ts, float minDist) {
		this(listify(ts), minDist);
	}
	
	public RenderProcMesh(Tracer[] ts, float minDist, int strokeCap, int strokeColor, float strokeWeight) {
		this(listify(ts), minDist, strokeCap, strokeColor, strokeWeight);
	}

	@Override
	public void draw(PGraphics g) {
		//style
		g.strokeCap(strokeCap);
		g.stroke(strokeColor);
		g.strokeWeight(strokeWeight);
		
		//analyze and draw
		for (int i=0; i<ts.size(); i++) {
			for (int j=i+1; j<ts.size(); j++) {
				Point a = ts.get(i).location();
				Point b = ts.get(j).location();
				float term1 = (a.y - b.y);
				float term2 = (a.x - b.x);
				float sqDist = term1*term1 + term2*term2;
				if (sqDist <= minSqDist) {
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
		minSqDist = minDist*minDist;
	}
}
