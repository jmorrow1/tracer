package render;

import java.util.ArrayList;
import java.util.List;

import processing.core.PGraphics;
import tracer.Tracer;

/**
 * Draws a list of Tracer objects in some way using the PGraphics class from Processing.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public abstract class Render {
	protected List<Tracer> ts;
	
	public Render(Tracer[] ts) {
		this.ts = new ArrayList<Tracer>();
		for (int i=0; i<ts.length; i++) {
			this.ts.add(ts[i]);
		}
	}
	
	public Render(List<Tracer> ts) {
		this.ts = ts;
	}
	
	public abstract void draw(PGraphics g);
}
