package render;

import java.util.List;

import processing.core.PApplet;
import processing.core.PGraphics;
import tracer.Tracer;

/**
 * 
 * Draws a list of Tracers as a Delaunay triangulation.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class RenderDelaunay extends Render {
	//style
	protected int strokeCap, strokeColor, fillColor;
	protected float strokeWeight;
	protected boolean showStroke, showFill;

	public RenderDelaunay(List<Tracer> ts) {
		this(ts, PApplet.ROUND, 0xff000000, 1, true, false, -1);
	}
	
	public RenderDelaunay(List<Tracer> ts, int strokeCap, int strokeColor, float strokeWeight,
			boolean showStroke, boolean showFill, int fillColor) {
		super(ts);
		this.strokeCap = strokeCap;
		this.strokeColor = strokeColor;
		this.strokeWeight = strokeWeight;
		this.showStroke = showStroke;
		this.showFill = showFill;
		this.fillColor = fillColor;
	}
	
	public RenderDelaunay(Tracer[] ts) {
		this(ts, PApplet.ROUND, 0xff000000, 1, true, false, -1);
	}
	
	public RenderDelaunay(Tracer[] ts, int strokeCap, int strokeColor, float strokeWeight,
			boolean showStroke, boolean showFill, int fillColor) {
		super(ts);
		this.strokeCap = strokeCap;
		this.strokeColor = strokeColor;
		this.strokeWeight = strokeWeight;
		this.showStroke = showStroke;
		this.showFill = showFill;
		this.fillColor = fillColor;
	}

	@Override
	public void draw(PGraphics g) {
		if (showStroke) {
			g.strokeCap(strokeCap);
			g.strokeWeight(strokeWeight);
			g.stroke(strokeColor);
		}
		else {
			g.noStroke();
		}
		
		if (showFill) {
			g.fill(fillColor);
		}
		else {
			g.noFill();
		}
		
		g.noFill();
		// determine if i-j-k is a circle with no interior points 
        for (int i = 0; i < ts.size(); i++) {
            for (int j = i+1; j < ts.size(); j++) {
                for (int k = j+1; k < ts.size(); k++) {
                    boolean isTriangle = true;
                    for (int a = 0; a < ts.size(); a++) {
                        if (a == i || a == j || a == k) continue;
                        if (triangleContainsPoint(ts.get(i), ts.get(j), ts.get(k), ts.get(a))) {
                        	isTriangle = false;
                        	break;
                        }
                    }
                    if (isTriangle) {
                    	g.triangle(ts.get(i).getX(), ts.get(i).getY(),
                    			ts.get(j).getX(), ts.get(j).getY(),
                    			ts.get(k).getX(), ts.get(k).getY());
                    }
                }
            }
        }
	}
	
	private boolean triangleContainsPoint(Tracer t, Tracer u, Tracer v, Tracer a) {
		return triangleContainsPoint(t.getX(), t.getY(), u.getX(), u.getY(), v.getX(), v.getY(), a.getX(), a.getY());
	}
	
	private boolean triangleContainsPoint(float x1, float y1, float x2, float y2, float x3, float y3, float x, float y) {
		float ABC = Math.abs (x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2));
		float ABP = Math.abs (x1 * (y2 - y) + x2 * (y - y1) + x * (y1 - y2));
		float APC = Math.abs (x1 * (y - y3) + x * (y3 - y1) + x3 * (y1 - y));
		float PBC = Math.abs (x * (y2 - y3) + x2 * (y3 - y) + x3 * (y - y2));

		return ABP + APC + PBC == ABC;
	}

	public void setStrokeCap(int strokeCap) {
		this.strokeCap = strokeCap;
	}

	public void setStrokeColor(int strokeColor) {
		this.strokeColor = strokeColor;
	}

	public void setFillColor(int fillColor) {
		this.fillColor = fillColor;
	}

	public void setStrokeWeight(float strokeWeight) {
		this.strokeWeight = strokeWeight;
	}

	public void showStroke(boolean showStroke) {
		this.showStroke = showStroke;
	}

	public void showFill(boolean showFill) {
		this.showFill = showFill;
	}
}
