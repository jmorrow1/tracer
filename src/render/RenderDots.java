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
	protected LabelScheme labelScheme = new Enumerate();
	protected boolean drawLabels = false;
	protected float labelTextSize = 6;
	
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
		if (drawLabels) {
			g.textSize(labelTextSize);
			g.textAlign(g.CENTER, g.BOTTOM);
		}
		for (int i=0; i<ts.size(); i++) {
			Tracer t = ts.get(i);
			Point pt = t.getLocation();
			g.point(pt.x, pt.y);
			if (drawLabels) {
				g.text(labelScheme.nthLabel(i), pt.x, pt.y - 2);
			}
		}	
	}

	public void setLabelScheme(LabelScheme labelScheme) {
		this.labelScheme = labelScheme;
	}

	public void setDrawLabels(boolean drawLabels) {
		this.drawLabels = drawLabels;
	}

	public void setLabelTextSize(float labelTextSize) {
		this.labelTextSize = labelTextSize;
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
	
	public static interface LabelScheme {
		public String nthLabel(int n);
	}
	
	public final static class Enumerate implements LabelScheme {
		public String nthLabel(int n) {
			return "" + n;
		}
	}
	
	public final static class Alphabet implements LabelScheme {
		public String nthLabel(int n) {
			String[] ss = convertBase(n, 26).split(" ");
			String t = "";
			for (int i=0; i<ss.length; i++) {
				t += (char)(((int)'a') + Integer.valueOf(ss[i]));
			}
			return t;
		}
	}
	
	private static String convertBase(int n, int base) {
		int q = n / base;
		int r = n % base;
		
		if (q == 0) {
			return Integer.toString(r);
		}
		else {
			return convertBase(q, base) + " " + Integer.toString(r);
		}
	}
}
