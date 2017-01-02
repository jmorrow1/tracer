package render;

import java.util.ArrayList;
import java.util.List;

import processing.core.PGraphics;
import tracer.Tracer;

/**
 * Draws a list of Tracer objects in some way using the PGraphics class from
 * Processing.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public abstract class Render {
    protected List<Tracer> ts;

    public Render(Tracer[] ts) {
        this(listify(ts));
    }

    public Render(List<Tracer> ts) {
        this.ts = ts;
    }

    public void step() {
        for (Tracer t : ts) {
            t.step();
        }
    }

    public void step(int dt) {
        for (Tracer t : ts) {
            t.step(dt);
        }
    }

    public abstract void draw(PGraphics g);

    public static List<Tracer> listify(Tracer[] arr) {
        List<Tracer> list = new ArrayList<Tracer>();
        for (int i = 0; i < arr.length; i++) {
            list.add(arr[i]);
        }
        return list;
    }

    public int tracerCount() {
        return ts.size();
    }
}
