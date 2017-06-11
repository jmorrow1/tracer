package demos;

import processing.core.PApplet;
import tracer.Tracer;
import tracer.paths.Circle;
import tracer.paths.InfinitySymbol;
import tracer.paths.Path;

/**
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class MetaPathDemo extends PApplet {
    public static void main(String[] args) {
        PApplet.main("demos.MetaPathDemo");
    }
    
    private Path path;
    private Tracer tracer;
    private Path metapath;
    private Tracer metatracer;
    
    public void settings() {
        size(600, 600, P2D);
    }

    public void setup() {
        path = new InfinitySymbol(300, 300, 200);
        tracer = new Tracer(path, 0, 0.003f);
        metapath = new Circle(tracer, 25);
        metatracer = new Tracer(metapath, 0, 0.01f);
    }
    
    public void draw() {
        tracer.step();
        metatracer.step();
        
        background(255);
        
        stroke(60);
        strokeWeight(1);
        noFill();
        path.draw(g);
        metapath.draw(g);
        
        strokeWeight(8);
        stroke(15);
        point(metatracer.x, metatracer.y);
    }
}
