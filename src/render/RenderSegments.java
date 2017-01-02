package render;

import ease.Easing;
import ease.Easing.Linear;
import paths.Path;
import processing.core.PGraphics;
import tracer.Tracer;

/**
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class RenderSegments extends Render {
    protected float[] strokeWeights;
    protected int[] strokeColors;

    public RenderSegments(Path path, int n, float startu, float du) {
        this(path, fillArray(startu, n), fillArray(du, n), fillArray(new Linear(), n));
    }

    public RenderSegments(Path path, float[] startus, float[] dus) {
        this(path, startus, dus, fillArray(new Linear(), startus.length));
    }

    public RenderSegments(Path path, float[] startus, float[] dus, Easing[] easings) {
        super(tracers(path, startus, dus, easings));
        this.strokeColors = fillArray(0xff000000, startus.length);
        if (startus.length != dus.length || dus.length != easings.length) {
            throw new IllegalArgumentException(
                    "In RenderSegments constructor, the arguments startus, dus, and easings should all have the same length.");
        }
        this.strokeWeights = fillArray(1f, startus.length / 2);
        this.strokeColors = fillArray(0xff000000, startus.length / 2);
    }

    private static Easing[] fillArray(Easing e, int n) {
        Easing[] vals = new Easing[n];
        for (int i = 0; i < n; i++) {
            vals[i] = e;
        }
        return vals;
    }

    private static float[] fillArray(float val, int n) {
        float[] vals = new float[n];
        for (int i = 0; i < n; i++) {
            vals[i] = n;
        }
        return vals;
    }

    private static int[] fillArray(int val, int n) {
        int[] vals = new int[n];
        for (int i = 0; i < n; i++) {
            vals[i] = n;
        }
        return vals;
    }

    private static Tracer[] tracers(Path path, float[] startus, float[] dus, Easing[] easings) {
        if (startus.length != dus.length) {
            throw new IllegalArgumentException(
                    "In RenderSegments constructor, the arguments startus and dus should have the same length.");
        }
        Tracer[] ts = new Tracer[startus.length];
        for (int i = 0; i < ts.length; i++) {
            ts[i] = new Tracer(path, startus[i], dus[i], easings[i]);
        }
        return ts;
    }

    @Override
    public void draw(PGraphics g) {
        for (int i = 0; i < ts.size() - 1; i += 2) {
            Tracer a = ts.get(i);
            Tracer b = ts.get(i + 1);

            g.strokeWeight(strokeWeights[i / 2]);
            g.stroke(strokeColors[i / 2]);

            a.getPath().draw(g, a.getU(), b.getU());
        }
    }

    public void setU(int i, float u) {
        ts.get(i).setU(u);
    }

    public void setAllUs(float u) {
        for (Tracer t : ts) {
            t.setU(u);
            ;
        }
    }

    public void setDu(int i, float du) {
        ts.get(i).setDu(du);
    }

    public void setAllDus(float du) {
        for (Tracer t : ts) {
            t.setDu(du);
        }
    }

    public void setEasing(int i, Easing easing) {
        ts.get(i).setEasing(easing);
    }

    public void setAllEasings(Easing easing) {
        for (Tracer t : ts) {
            t.setEasing(easing);
        }
    }

    public void setStrokeColor(int i, int strokeColor) {
        strokeColors[i] = strokeColor;
    }

    public void setAllStrokeColors(int strokeColor) {
        for (int i = 0; i < strokeColors.length; i++) {
            strokeColors[i] = strokeColor;
        }
    }

    public void setStrokeColors(int[] strokeColors) {
        this.strokeColors = strokeColors;
    }

    public void setStrokeWeight(int i, float strokeWeight) {
        strokeWeights[i] = strokeWeight;
    }

    public void setAllStrokeWeights(float strokeWeight) {
        for (int i = 0; i < strokeWeights.length; i++) {
            strokeWeights[i] = strokeWeight;
        }
    }

    public void setStrokeWeights(float[] strokeWeights) {
        this.strokeWeights = strokeWeights;
    }

    public void setAllPaths(Path path) {
        for (Tracer t : ts) {
            t.setPath(path);
        }
    }

    public int segmentCount() {
        return strokeWeights.length;
    }
}
