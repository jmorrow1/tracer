package render;

import ease.Easing;
import ease.Easing.Linear;
import paths.IPath;
import processing.core.PGraphics;
import tracer.Tracer;

/**
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class RenderSegments extends Render {

	public RenderSegments(IPath path, float[] startus, float[] dus) {
		super(tracers(path, startus, dus));
	}
	
	public RenderSegments(IPath path, float[] startus, float[] dus, Easing[] easings) {
		super(tracers(path, startus, dus, easings));
	}
	
	private static Tracer[] tracers(IPath path, float[] startus, float[] dus) {
		Easing[] easings = new Easing[startus.length];
		for (int i=0; i<easings.length; i++) {
			easings[i] = new Linear();
		}
		return tracers(path, startus, dus, easings);
	}
	
	private static Tracer[] tracers(IPath path, float[] startus, float[] dus, Easing[] easings) {
		if (startus.length != dus.length) {
			throw new IllegalArgumentException("In RenderSegments(startus, dus), startus and dus should have the same length.");
		}
		Tracer[] ts = new Tracer[startus.length];
		for (int i=0; i<ts.length; i++) {
			ts[i] = new Tracer(path, startus[i], dus[i], easings[i]);
		}
		return ts;
	}

	@Override
	public void draw(PGraphics g) {		
		for (int i=0; i<ts.size()-1; i+=2) {
			Tracer a = ts.get(i);
			Tracer b = ts.get(i+1);
			
			a.getPath().draw(g, a.getU(), b.getU());
		}
	}
	
	public void setU(int i, float u) {
		ts.get(i).setU(u);
	}
	
	public void setU(float u) {
		for (Tracer t : ts) {
			t.setU(u);;
		}
	}
	
	public void setDu(int i, float du) {
		ts.get(i).setDu(du);
	}
	
	public void setDu(float du) {
		for (Tracer t : ts) {
			t.setDu(du);
		}
	}
	
	public void setEasing(int i, Easing easing) {
		ts.get(i).setEasing(easing);
	}
	
	public void setEasing(Easing easing) {
		for (Tracer t : ts) {
			t.setEasing(easing);
		}
	}
	
	public void setPath(IPath path) {
		for (Tracer t : ts) {
			t.setPath(path);
		}
	}
}
