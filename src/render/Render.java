package render;

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
	
	public Render(List<Tracer> ts) {
		this.ts = ts;
	}
	
	public abstract void draw(PGraphics g);
}
